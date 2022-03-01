/*
 * MIT License
 *
 * Copyright (c) 2021 yedf
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package top.cheesetree.btx.project.dts.dtm.client.barrier;

import lombok.extern.slf4j.Slf4j;
import top.cheesetree.btx.framework.boot.spring.ApplicationBeanFactory;
import top.cheesetree.btx.project.dts.dtm.client.common.model.DtmConsumer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixiaoshuang
 */
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
