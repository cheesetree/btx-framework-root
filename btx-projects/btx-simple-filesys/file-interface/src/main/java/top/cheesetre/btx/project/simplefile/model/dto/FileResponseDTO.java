package top.cheesetre.btx.project.simplefile.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/9/14 09:14
 * @Version: 1.0
 * @Description:
 */
@Data
public class FileResponseDTO {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long fileid;
    private FileInfoDTO fileResult = null;
    private String errmsg;
}
