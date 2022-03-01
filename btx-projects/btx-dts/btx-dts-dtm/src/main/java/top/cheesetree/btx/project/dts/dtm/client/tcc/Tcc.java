package top.cheesetree.btx.project.dts.dtm.client.tcc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import top.cheesetree.btx.project.dts.dtm.client.client.DtmClient;
import top.cheesetree.btx.project.dts.dtm.client.common.config.BtxDtmProperties;
import top.cheesetree.btx.project.dts.dtm.client.common.constant.ParamFieldConstant;
import top.cheesetree.btx.project.dts.dtm.client.common.enums.TransTypeEnum;
import top.cheesetree.btx.project.dts.dtm.client.common.model.*;
import top.cheesetree.btx.project.dts.dtm.client.common.utils.BranchIdGeneratorUtil;

/**
 * @author van
 * @date 2022/3/1 19:57
 * @description TODO
 */
@Slf4j
public class Tcc {
    private BtxDtmProperties btxDtmProperties;
    /**
     * 事务信息
     */
    private TransBase transBase;

    /**
     * server 信息
     */
    private DtmServerInfo dtmServerInfo;

    /**
     * id 生成器
     */
    private BranchIdGeneratorUtil branchIdGeneratorUtil;

    public Tcc(BtxDtmProperties btxDtmProperties, String gid) {
        this.dtmServerInfo = new DtmServerInfo(btxDtmProperties.getTccUrl());
        this.branchIdGeneratorUtil = new BranchIdGeneratorUtil("");
        this.transBase = new TransBase(TransTypeEnum.TCC, gid, false);
        this.btxDtmProperties = btxDtmProperties;
    }

    public void tccGlobalTransaction(DtmConsumer<Tcc> consumer) throws Exception {
        DtmGLobalRequest req =
                DtmGLobalRequest.builder().gid(transBase.getGid()).trans_type(TransTypeEnum.TCC.getValue()).build();

        new DtmClient(btxDtmProperties).getRestTemplate().postForObject(dtmServerInfo.prepare(), req,
                String.class);

        try {
            consumer.accept(this);
            new DtmClient(btxDtmProperties).getRestTemplate().postForObject(dtmServerInfo.submit(), req,
                    String.class);
        } catch (Exception e) {
            new DtmClient(btxDtmProperties).getRestTemplate().postForObject(dtmServerInfo.abort(), req,
                    String.class);
            throw e;
        }
    }

    public String callBranch(Object body, String tryUrl, String confirmUrl, String cancelUrl) throws Exception {
        String branchId = branchIdGeneratorUtil.genBranchId();
        DtmRegRequest req =
                DtmRegRequest.builder().branch_id(branchId).gid(transBase.getGid()).trans_type(TransTypeEnum.TCC.getValue()).status(ParamFieldConstant.DEFAULT_STATUS).data(JSONObject.toJSONString(body)).tryd(tryUrl).confirm(confirmUrl).cancel(cancelUrl).build();

        new DtmClient(btxDtmProperties).getRestTemplate().postForObject(dtmServerInfo.registerTccBranch(),
                req, String.class);

        return new DtmClient(btxDtmProperties).getRestTemplate().postForObject(splicingTryUrl(tryUrl,
                        transBase.getGid(),
                        TransTypeEnum.TCC.getValue(), branchId, ParamFieldConstant.TRY),
                body, String.class);
    }

    /**
     * 和go 保持同样的发送方式 参数拼在url后边
     */
    private String splicingTryUrl(String tryUrl, String gid, String transType, String branchId, String op) {
        return tryUrl + "?gid=" + gid + "&trans_type=" + transType + "&branch_id=" + branchId + "&op=" + op;
    }

}