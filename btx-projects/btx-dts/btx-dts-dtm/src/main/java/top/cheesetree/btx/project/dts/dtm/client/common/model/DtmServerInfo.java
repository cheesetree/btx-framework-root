package top.cheesetree.btx.project.dts.dtm.client.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DtmServerInfo {

    /**
     * ip+port
     */
    private String ipPort;

    public static final String PREFIX = "http://";

    public static final String BASE = "/api/dtmsvr";

    public static final String NEW_GID = BASE + "/newGid";

    public static final String PREPARE = BASE + "/prepare";

    public static final String SUBMIT = BASE + "/submit";

    public static final String ABORT = BASE + "/abort";

    public static final String REGISTER_TCC_BRANCH = BASE + "/registerTccBranch";

    public DtmServerInfo(String ipPort) {
        this.ipPort = ipPort;
    }

    /**
     * 生成gid url
     *
     * @return
     */
    public String newGid() {
        return PREFIX + ipPort + NEW_GID;
    }

    /**
     * 生成gid url
     *
     * @return
     */
    public String prepare() {
        return PREFIX + ipPort + PREPARE;
    }

    /**
     * submit 阶段 url
     *
     * @return
     */
    public String submit() {
        return PREFIX + ipPort + SUBMIT;
    }

    /**
     * abort 阶段 url
     *
     * @return
     */
    public String abort() {
        return PREFIX + ipPort + ABORT;
    }

    /**
     * 注册tcc 事务分支
     *
     * @return
     */
    public String registerTccBranch() {
        return PREFIX + ipPort + REGISTER_TCC_BRANCH;
    }

}
