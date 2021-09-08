package top.cheesetree.btx.simplefile.file.client.fastdfs;

import lombok.Data;

import java.io.Serializable;

@Data
public class FastDfsInfo implements Serializable {
    private String group;
    private String path;
    private String fileAbsolutePath;

    public String getFileAbsolutePath() {
        return String.format("%s/%s", group, path);
    }
}
