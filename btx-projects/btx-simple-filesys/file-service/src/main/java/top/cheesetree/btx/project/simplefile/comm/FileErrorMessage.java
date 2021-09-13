package top.cheesetree.btx.project.simplefile.comm;

import top.cheesetree.btx.framework.core.constants.BtxMessage;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/10 17:19
 * @Version: 1.0
 * @Description:
 */
public class FileErrorMessage extends BtxMessage {
    public static final FileErrorMessage FILE_UPLOAD_ERROR = new FileErrorMessage(100000, "文件上传出错");
    public static final FileErrorMessage FILE_STORGE_LIMIT = new FileErrorMessage(100001, "文件存储超过限制");
    public static final FileErrorMessage FILE_UNEXIST = new FileErrorMessage(100002, "文件不存在");
    public static final FileErrorMessage FILE_DEL_ERROR = new FileErrorMessage(100003, "文件删除失败");
    public static final FileErrorMessage FILE_MODIFY_ERROR = new FileErrorMessage(100004, "文件修改失败");
    public static final FileErrorMessage FILE_DOWNLOAD_ERROR = new FileErrorMessage(100005, "文件下载失败");
    public static final FileErrorMessage SYS_UNAUTH = new FileErrorMessage(100006, "系统未授权");
    public static final FileErrorMessage TOKEN_UNEXIST = new FileErrorMessage(100007, "token失效");
    public static final FileErrorMessage UPDATE_FILE_TAG_ERROR = new FileErrorMessage(100008, "更新文件标签失败");
    public static final FileErrorMessage FILE_TAG_INVALID_ERROR = new FileErrorMessage(100009,
            "文件标签不符合[数字、大小写字母、下划线且最多20位]");

    public FileErrorMessage(Integer code, String message) {
        super(code, message);
    }

}
