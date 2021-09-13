package top.cheesetree.btx.project.simplefile.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cheesetree.btx.project.simplefile.mapper.BtxFileTagMapper;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTagBO;
import top.cheesetree.btx.project.simplefile.service.BtxFileTagService;

import java.util.List;

/**
 *
 */
@Service
public class BtxFileTagServiceImpl extends ServiceImpl<BtxFileTagMapper, BtxFileTagBO>
        implements BtxFileTagService {
    @Autowired
    BtxFileTagMapper btxFileTagMapper;

    @Override
    public boolean updateTags(Long fileid, List<BtxFileTagBO> tags) {
        boolean r = true;
        btxFileTagMapper.delete(Wrappers.<BtxFileTagBO>lambdaQuery().eq(BtxFileTagBO::getFileId, fileid));

        if (!saveBatch(tags)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            r = false;
        }

        return r;
    }
}




