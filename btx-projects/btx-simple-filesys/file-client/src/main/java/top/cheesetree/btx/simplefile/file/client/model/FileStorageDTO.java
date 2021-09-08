package top.cheesetree.btx.simplefile.file.client.model;

import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/15 09:50
 * @Version: 1.0
 * @Description:
 */
@Data
public class FileStorageDTO implements ValueObject {
    private String path;
    private Object metaStorgeData;
}
