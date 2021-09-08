package top.cheesetre.btx.project.simplefile.model.dto;

import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/31 11:55
 * @Version: 1.0
 * @Description:
 */
@Data
public class FileShareInfoDTO implements ValueObject {
    private String shareUrl;
    private String verCode;
}
