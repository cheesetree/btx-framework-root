package top.cheesetree.btx.simplefile.file.client.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.simplefile.file.client.IFileClient;
import top.cheesetree.btx.simplefile.file.client.model.FileStorageDTO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/15 09:49
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@ConditionalOnProperty(value = "btx.file.fastdfs.enabled", matchIfMissing = true)
@Component("fileClient")
public class FastdfsClient implements IFileClient<FastDfsInfo>, InitializingBean {
    @Resource
    FastdfsProperties fastdfsProperties;

    @Override
    public String queryFileInfo(FastDfsInfo fileinfo) {
        return null;
    }

    @Override
    public FileStorageDTO uploadFile(byte[] file, String ext) {
        StorageClient client = new StorageClient();
        try {
            String[] ret = client.upload_file(file, ext, null);
            if (ret != null && ret.length == 2) {
                FastDfsInfo meta = new FastDfsInfo();
                meta.setGroup(ret[0]);
                meta.setPath(ret[1]);

                FileStorageDTO dto = new FileStorageDTO();
                dto.setPath(meta.getFileAbsolutePath());
                dto.setMetaStorgeData(meta);
                return dto;
            } else {
                log.error("file upload error ");
            }
        } catch (IOException | MyException e) {
            log.error("file upload error ", e);
        }

        return null;
    }

    @Override
    public FileStorageDTO uploadAppendFile() {
        return null;
    }

    @Override
    public boolean deleteFile(FastDfsInfo fileinfo) {
        StorageClient client = new StorageClient();
        boolean ret = false;
        try {
            int errno = client.delete_file(fileinfo.getGroup(), fileinfo.getPath());
            if (errno == 0) {
                ret = true;
            } else {
                log.error("file[{}}] delete error ,errno:{}", fileinfo, errno);
            }
        } catch (IOException | MyException e) {
            log.error("file[{}}] delete error {}", fileinfo, e);
        }

        return ret;
    }

    @Override
    public FileStorageDTO modifyFile(FastDfsInfo newinfo, FastDfsInfo oldinfo) {
        return null;
    }

    @Override
    public FileStorageDTO copyFile(FastDfsInfo fileinfo, String ext) {
        StorageClient client = new StorageClient();
        try {
            byte[] fi = client.download_file(fileinfo.getGroup(), fileinfo.getPath());
            if (fi != null) {
                String[] ret = client.upload_file(fi, ext, null);
                if (ret != null && ret.length == 2) {
                    fileinfo.setGroup(ret[0]);
                    fileinfo.setPath(ret[1]);

                    FileStorageDTO dto = new FileStorageDTO();
                    dto.setPath(fileinfo.getFileAbsolutePath());
                    dto.setMetaStorgeData(fileinfo);
                    return dto;
                } else {
                    log.error("file copy error ");
                }

            }

        } catch (IOException | MyException e) {
            log.error("file copy error ", e);
        }

        return null;
    }

    @Override
    public String generateUrl(FastDfsInfo fileinfo) {
        return null;
    }

    /**
     * init ClientGlobal
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Properties p = new Properties();

        p.put("fastdfs.connect_timeout_in_seconds", fastdfsProperties.getConnectTimeout());
        p.put("fastdfs.network_timeout_in_seconds", fastdfsProperties.getNetworkTimeout());
        p.put("fastdfs.charset", fastdfsProperties.getCharset());
        p.put("fastdfs.http_anti_steal_token", fastdfsProperties.getHttpAntiStealToken());
        p.put("fastdfs.http_secret_key", fastdfsProperties.getHttpSecretKey());
        p.put("fastdfs.http_tracker_http_port", fastdfsProperties.getHttpTrackerHttpPort());
        p.put("fastdfs.tracker_servers", fastdfsProperties.getTrackerServers());
        p.put("fastdfs.connection_pool.max_count_per_entry", fastdfsProperties.getConnectionPoolMaxTotalPerEntry());
        p.put("fastdfs.connection_pool.max_idle_time", fastdfsProperties.getConnectionPoolIdleTime());
        p.put("fastdfs.connection_pool.max_wait_time_in_ms", fastdfsProperties.getConnectionPoolMaxWaitTimeInMs());

        ClientGlobal.initByProperties(p);
    }
}
