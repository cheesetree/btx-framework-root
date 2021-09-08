package top.cheesetre.btx.project.simplefile.model.dto;

import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/31 11:53
 * @Version: 1.0
 * @Description:
 */
@Data
public class FileInfoDTO implements ValueObject {
    private long fileId;
    private String filePath;
    private String fileType;
    private String originName;
}
