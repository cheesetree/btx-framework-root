package top.cheesetree.btx.project.dts.dtm.client.barrier;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cheesetree.btx.project.dts.dtm.client.barrier.mapper.BranchBarrierMapper;
import top.cheesetree.btx.project.dts.dtm.client.common.constant.ParamFieldConstant;

import java.util.LinkedHashMap;

/**
 * @author van
 * @date 2022/3/1 17:32
 * @description TODO
 */
@Service
public class BranchBarrierSevice extends ServiceImpl<BranchBarrierMapper, BranchBarrierBO> implements IService<BranchBarrierBO> {
    @Autowired
    BranchBarrierMapper branchBarrierMapper;

    @Transactional(rollbackFor = Exception.class)
    boolean insertBarrier(BranchBarrierBO branchBarrierBO) throws Exception {
        boolean ret = false;

        if (branchBarrierMapper.insert(branchBarrierBO) > 0) {
            if (ParamFieldConstant.CANCEL.equals(branchBarrierBO.getOp())) {
                if (branchBarrierMapper.selectCount(new LambdaQueryWrapper<BranchBarrierBO>().allEq(new LinkedHashMap<SFunction<BranchBarrierBO, ?>,
                        Object>() {
                    {
                        put(BranchBarrierBO::getGid, branchBarrierBO.getGid());
                        put(BranchBarrierBO::getBranchId, branchBarrierBO.getBranchId());
                        put(BranchBarrierBO::getOp, ParamFieldConstant.TRY);
                        put(BranchBarrierBO::getBarrierId, branchBarrierBO.getBarrierId());
                    }
                })) == 0) {
                    throw new Exception("");
                }
                ret = true;
            }
        }

        return ret;
    }
}
