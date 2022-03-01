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

package top.cheesetree.btx.project.dts.dtm.client.tcc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import top.cheesetree.btx.project.dts.dtm.client.client.DtmClient;
import top.cheesetree.btx.project.dts.dtm.client.common.config.BtxDtmProperties;
import top.cheesetree.btx.project.dts.dtm.client.common.enums.TransTypeEnum;
import top.cheesetree.btx.project.dts.dtm.client.common.model.*;
import top.cheesetree.btx.project.dts.dtm.client.common.utils.BranchIdGeneratorUtil;

/**
 * @author lixiaoshuang
 */
@Slf4j
public class Tcc {
    private BtxDtmProperties btxDtmProperties = new BtxDtmProperties();

    private static final String DEFAULT_STATUS = "prepared";

    private static final String OP = "try";

    private static final String FAIL_RESULT = "FAILURE";

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
                DtmRegRequest.builder().branch_id(branchId).gid(transBase.getGid()).trans_type(TransTypeEnum.TCC.getValue()).status(DEFAULT_STATUS).data(JSONObject.toJSONString(body)).tryd(tryUrl).confirm(confirmUrl).cancel(cancelUrl).build();

        new DtmClient(btxDtmProperties).getRestTemplate().postForObject(dtmServerInfo.registerTccBranch(),
                req, String.class);

        return new DtmClient(btxDtmProperties).getRestTemplate().postForObject(splicingTryUrl(tryUrl,
                        transBase.getGid(),
                        TransTypeEnum.TCC.getValue(), branchId, OP),
                body, String.class);
    }

    /**
     * 和go 保持同样的发送方式 参数拼在url后边
     */
    private String splicingTryUrl(String tryUrl, String gid, String transType, String branchId, String op) {
        return tryUrl + "?gid=" + gid + "&trans_type=" + transType + "&branch_id=" + branchId + "&op=" + op;
    }

}