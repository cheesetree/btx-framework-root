package top.cheesetree.btx.project.idgenerator.client.generator.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import top.cheesetree.btx.project.idgenerator.client.config.SnowflakeConfig;
import top.cheesetree.btx.project.idgenerator.core.exception.BtxIdgeneratorException;
import top.cheesetree.btx.project.idgenerator.core.generator.IdGenerator;
import top.cheesetree.btx.project.idgenerator.core.util.NetWorkUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/13 09:53
 * @Version: 1.0
 * @Description: 1bit(signbit) +41bit(timestamp)  +10bit(workid) +2bit(sequencestep) +10bit(sequence)
 */
@Slf4j
public class SnowflakeIdGenerator implements IdGenerator, InitializingBean {
    @Resource
    SnowflakeConfig snowflakeConfig;

    private long workid = -1L;
    private long lastTimestamp = -1L;
    private int sequenceStep = 0;
    private long sequence = 0L;

    private final static long WORKERID_BIT = 10L;
    private final static long SEQUENCE_BIT = 10L;
    private final static long SEQUENCE_STEP_BIT = 2L;
    private final static long TIMESTAMP_BIT = 41L;


    private final static long SEQUENCE_STEP_SHIFT = SEQUENCE_BIT;
    private final static long WORKERID_SHIFT = SEQUENCE_STEP_SHIFT + SEQUENCE_STEP_BIT;
    private final static long TIMESTAMP_SHIFT = WORKERID_SHIFT + WORKERID_BIT;
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    private final static long MAX_WORKID = -1L ^ (-1L << WORKERID_BIT);

    @Override
    public Long getId() {
        try {
            return nextId();
        } catch (Exception e) {
            log.error("Generate unique id exception. ", e);
            throw new BtxIdgeneratorException(e);
        }
    }

    @Override
    public List<Long> getId(Integer batchSize) {
        List<Long> ret = new ArrayList<>();

        while (batchSize-- > 0) {
            ret.add(getId());
        }
        return ret;
    }

    @Override
    public String parseId(long id) {
        long tm = id >>> TIMESTAMP_SHIFT;
        long wid = id << TIMESTAMP_BIT >>> TIMESTAMP_BIT;
        long seq =
                (id << (TIMESTAMP_BIT + WORKERID_BIT + SEQUENCE_STEP_BIT)) >>> (TIMESTAMP_BIT + WORKERID_BIT + SEQUENCE_STEP_BIT);
        return String.format("{\"id\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}", id, tm,
                wid, seq);

    }

    protected synchronized long nextId() throws InterruptedException {
        long t = System.currentTimeMillis();
        if (t != lastTimestamp) {
            if (t < lastTimestamp) {
                if (lastTimestamp - t <= snowflakeConfig.getTimeOffset()) {
                    Thread.sleep(snowflakeConfig.getTimeOffset());
                } else if (++sequenceStep >= 3) {
                    throw new BtxIdgeneratorException("sequenceStep is out of 3");
                }
            } else {
                sequenceStep = 0;
            }

            sequence = 0L;
            lastTimestamp = t;
        } else if (++sequence > MAX_SEQUENCE) {
            throw new BtxIdgeneratorException("sequence is out of" + MAX_SEQUENCE);
        }

        return (t << TIMESTAMP_SHIFT) | (workid << WORKERID_SHIFT) | (sequenceStep << SEQUENCE_STEP_SHIFT) | sequence;
    }

    @Override
    public void afterPropertiesSet() {
        int wid = snowflakeConfig.getWorkId();
        if (wid == -1) {
            String rip;
            if (snowflakeConfig.getInterfaceName() != null) {
                rip = NetWorkUtil.getIp(snowflakeConfig.getInterfaceName());
            } else {
                rip = NetWorkUtil.getIp();
            }

            wid = snowflakeConfig.getWorkips().indexOf(rip);
        }

        if (wid > -1 && wid <= MAX_WORKID) {
            workid = wid;
        } else {
            throw new BtxIdgeneratorException("workid is out of range:" + wid);
        }


    }
}
