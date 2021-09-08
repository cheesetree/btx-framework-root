package top.cheesetree.btx.project.simplefile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cheesetree.btx.project.simplefile.mapper.BtxFileArchiveResourceMapper;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileArchiveResourceBO;
import top.cheesetree.btx.project.simplefile.service.BtxFileArchiveResourceService;

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

    @Override
    public void moveToHis(long lsh, long fileid, String sysid) {
        btxFileArchiveResourceMapper.moveToHis(lsh, fileid, sysid);
    }
}




