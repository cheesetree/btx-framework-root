package top.cheesetree.btx.project.simplefile.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cheesetree.btx.project.simplefile.mapper.BtxFileTmpResourceMapper;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO;
import top.cheesetree.btx.project.simplefile.service.BtxFileTmpResourceService;
import top.cheesetree.btx.simplefile.file.client.IFileClient;
import top.cheesetree.btx.simplefile.file.client.fastdfs.FastDfsInfo;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class BtxFileTmpResourceServiceImpl extends ServiceImpl<BtxFileTmpResourceMapper, BtxFileTmpResourceBO>
        implements BtxFileTmpResourceService {
    @Resource
    BtxFileTmpResourceMapper btxFileTmpResourceMapper;
    @Resource
    private IFileClient fileClient;

    @Override
    public void moveToArchive(long lsh, long fileid, String sysid) {
        btxFileTmpResourceMapper.moveToArchive(lsh, fileid, sysid);
    }

    @Override
    public boolean deleteById(long lsh) {
        boolean ret = false;
        String fd = this.getById(lsh).getFileStorageInfo();
        if (btxFileTmpResourceMapper.deleteById(lsh) > 0) {
            ret = fileClient.deleteFile(JSON.parseObject(fd, FastDfsInfo.class));
            if (!ret) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

        return ret;
    }
}




