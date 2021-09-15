package top.cheesetre.btx.project.simplefile.model.dto;

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
    private Long fileid;
    private FileInfoDTO fileResult = null;
    private String errmsg;
}
