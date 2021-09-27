package top.cheesetree.btx.project.simplefile.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cheesetree.btx.project.simplefile.mapper.BtxFileArchiveResourceMapper;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileArchiveResourceBO;
import top.cheesetree.btx.project.simplefile.service.BtxFileArchiveResourceService;
import top.cheesetree.btx.simplefile.file.client.IFileClient;
import top.cheesetree.btx.simplefile.file.client.fastdfs.FastDfsInfo;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class BtxFileArchiveResourceServiceImpl extends ServiceImpl<BtxFileArchiveResourceMapper,
        BtxFileArchiveResourceBO>
        implements BtxFileArchiveResourceService {
    @Resource
    BtxFileArchiveResourceMapper btxFileArchiveResourceMapper;
    @Resource
    private IFileClient fileClient;

    @Override
    public boolean moveToHis(long lsh) {
        boolean ret = false;
        String fd = this.getById(lsh).getFileStorageInfo();
        if (btxFileArchiveResourceMapper.moveToHis(lsh) > 0) {
            ret = fileClient.deleteFile(JSON.parseObject(fd, FastDfsInfo.class));
            if (!ret) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

        return ret;
    }
}




