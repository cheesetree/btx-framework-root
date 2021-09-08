package top.cheesetree.btx.simplefile.file.client;

import top.cheesetree.btx.simplefile.file.client.model.FileStorageDTO;

import java.io.Serializable;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/14 09:48
 * @Version: 1.0
 * @Description:
 */
public interface IFileClient<T extends Serializable> {
    String queryFileInfo(T fileinfo);

    FileStorageDTO uploadFile(byte[] file, String ext);

    FileStorageDTO uploadAppendFile();

    boolean deleteFile(T fileinfo);

    FileStorageDTO modifyFile(T newfileinfo, T oldfileinfo);

    FileStorageDTO copyFile(T fileinfo, String ext);

    String generateUrl(T fileinfo);

}
