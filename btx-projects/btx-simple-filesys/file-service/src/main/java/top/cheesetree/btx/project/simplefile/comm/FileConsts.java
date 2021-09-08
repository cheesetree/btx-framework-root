package top.cheesetree.btx.project.simplefile.comm;

import top.cheesetree.btx.framework.core.constants.BtxConsts;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/21 16:20
 * @Version: 1.0
 * @Description:
 */
public class FileConsts implements BtxConsts {
    public final static String REDIS_SYS_LIMIT_KEY = "RES_SYS_FILE_LIMIT";
    public final static String REDIS_SYS_CURRENT_KEY = "RES_SYS_FILE_CURRENT";
    public final static String REDIS_SYS_KEY = "RES_SYS_INFO";
    public final static String FILE_PRIVATE_FLAG = "0";
    public final static String FILE_NOCRYPTO_FLAG = "0";
    public final static String FILE_TMP_FLAG = "0";
    public final static Integer FILE_DEF_LIVE_TIME = 604800;
}
