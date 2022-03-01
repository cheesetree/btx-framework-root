package top.cheesetree.btx.project.dts.dtm.client.barrier;

import lombok.extern.slf4j.Slf4j;
import top.cheesetree.btx.framework.boot.spring.ApplicationBeanFactory;
import top.cheesetree.btx.project.dts.dtm.client.common.model.DtmConsumer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class BranchBarrier {
    private BranchBarrierBO branchBarrierBO = new BranchBarrierBO();
    private int barrierId;

    public BranchBarrier(HttpServletRequest request) throws Exception {
        branchBarrierBO.setBranchId(request.getParameter("branch_id"));
        branchBarrierBO.setGid(request.getParameter("gid"));
        branchBarrierBO.setOp(request.getParameter("op"));
        branchBarrierBO.setTransType(request.getParameter("trans_type"));
    }

    public void call(DtmConsumer<BranchBarrier> consumer) throws Exception {
        branchBarrierBO.setBarrierId(String.format("%02d", ++this.barrierId));
        if (ApplicationBeanFactory.getBean(BranchBarrierSevice.class).insertBarrier(branchBarrierBO)) {
            consumer.accept(this);
        }
    }
}
