package top.cheesetre.btx.project.simplefile.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.cheesetre.btx.project.simplefile.model.dto.FileInfoDTO;
import top.cheesetree.btx.framework.core.json.CommJSON;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/9/15 10:31
 * @Version: 1.0
 * @Description:
 */
@RequestMapping("/file/{appid}")
public interface FileApi {
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/{area:^[01]{1}$}")
    CommJSON<FileInfoDTO> uploadFile(@RequestParam(value = "file") MultipartFile file, @PathVariable String appid
            , @PathVariable String area, @RequestParam(required = false) String pubtype,
                                     @RequestParam(required = false) Integer livetime,
                                     @RequestParam(required = false) String[] tags,
                                     @RequestParam(required = false) String crypto);

    /**
     * 获取文件路径
     *
     * @return
     */
    @PostMapping("/get/{area:^[01]{1}$}/{fileid}")
    CommJSON<FileInfoDTO> downloadFile(@PathVariable String appid, @PathVariable long fileid,
                                       @PathVariable String area);

    /**
     * 删除文件
     *
     * @return
     */
    @PostMapping("/del/{area:^[01]{1}$}/{fileid}")
    CommJSON deleteFile(@PathVariable String appid, @PathVariable long fileid, @PathVariable String area);

    /**
     * 归档文件
     *
     * @param appid
     * @param fileid
     * @return
     */
    @PostMapping("/archive/{fileid}")
    CommJSON archiveFile(@PathVariable String appid, @PathVariable long fileid);

    @PostMapping("/tags/{fileid}")
    CommJSON updateFileTags(@PathVariable String appid, @PathVariable long fileid,
                            @RequestParam(required = false) String[] tags);

    @PostMapping("/token")
    CommJSON getFileToken(@PathVariable String appid);

//    /**
//     * 更改文件发布类型
//     *
//     * @return
//     */
//    @PostMapping("/edit/{fileid}/{pubtype:^[01]{1}$}")
//    CommJSON<FileInfoDTO> changeFile(@PathVariable String appid, @PathVariable long fileid,
//                                     @PathVariable String pubtype);
//
//    /**
//     * 分享文件
//     *
//     * @param appid
//     * @param fileid
//     * @param livetime
//     * @return
//     */
//    @PostMapping("/share/{fileid}/{livetime}")
//    CommJSON<FileShareInfoDTO> shareFile(@PathVariable String appid, @PathVariable long fileid,
//                                         @PathVariable long livetime);
}
