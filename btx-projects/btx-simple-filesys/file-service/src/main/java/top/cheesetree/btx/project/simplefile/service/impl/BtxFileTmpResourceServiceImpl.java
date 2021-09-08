package top.cheesetree.btx.project.simplefile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cheesetree.btx.project.simplefile.mapper.BtxFileTmpResourceMapper;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO;
import top.cheesetree.btx.project.simplefile.service.BtxFileTmpResourceService;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class BtxFileTmpResourceServiceImpl extends ServiceImpl<BtxFileTmpResourceMapper, BtxFileTmpResourceBO>
        implements BtxFileTmpResourceService {
    @Resource
    BtxFileTmpResourceMapper btxFileTmpResourceMapper;

    @Override
    public void moveToArchive(long lsh, long fileid, String sysid) {
        btxFileTmpResourceMapper.moveToArchive(lsh, fileid, sysid);
    }
}




