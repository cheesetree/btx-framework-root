package top.cheesetree.btx.project.dts.dtm.client.common.utils;

import lombok.Data;

/**
 * @author van
 * @date 2022/3/1 19:57
 * @description TODO
 */
@Data
public class BranchIdGeneratorUtil {

    private static final int MAX_BRANCH_ID = 99;

    private static final int LENGTH = 20;

    private String branchId;

    private int subBranchId;

    public BranchIdGeneratorUtil(String branchId) {
        this.branchId = branchId;
    }

    /**
     * 生成注册分支id
     *
     * @return
     * @throws Exception
     */
    public String genBranchId() throws Exception {
        if (this.subBranchId >= MAX_BRANCH_ID) {
            throw new Exception("branch id is larger than 99");
        }
        if (this.branchId.length() >= LENGTH) {
            throw new Exception("total branch id is longer than 20");
        }
        this.subBranchId++;
        return this.branchId + String.format("%02d", this.subBranchId);
    }
}