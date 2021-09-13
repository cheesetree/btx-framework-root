package top.cheesetree.btx.project.simplefile.model.dto;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/22 14:36
 * @Version: 1.0
 * @Description:
 */
@Getter
@Setter
public class FileAccessDTO implements ValueObject {
    private String filepath;
    private String filetype;
}
