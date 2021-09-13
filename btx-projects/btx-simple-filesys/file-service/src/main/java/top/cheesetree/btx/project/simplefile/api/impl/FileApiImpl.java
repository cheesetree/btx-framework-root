package top.cheesetree.btx.project.simplefile.api.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.cheesetre.btx.project.simplefile.api.FileApi;
import top.cheesetre.btx.project.simplefile.model.dto.FileInfoDTO;
import top.cheesetree.btx.framework.cache.redis.RedisTemplateFactoryImpl;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.project.simplefile.comm.FileConsts;
import top.cheesetree.btx.project.simplefile.comm.FileErrorMessage;
import top.cheesetree.btx.project.simplefile.config.ResServiceProperties;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileArchiveResourceBO;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTagBO;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO;
import top.cheesetree.btx.project.simplefile.model.dto.FileAccessDTO;
import top.cheesetree.btx.project.simplefile.service.BtxFileArchiveResourceService;
import top.cheesetree.btx.project.simplefile.service.BtxFileTagService;
import top.cheesetree.btx.project.simplefile.service.BtxFileTmpResourceService;
import top.cheesetree.btx.project.simplefile.util.ResUtil;
import top.cheesetree.btx.simplefile.file.client.IFileClient;
import top.cheesetree.btx.simplefile.file.client.model.FileStorageDTO;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/9/15 13:50
 * @Version: 1.0
 * @Description:
 */
@RestController
@Slf4j
public class FileApiImpl implements FileApi {
    @Resource
    private RedisTemplateFactoryImpl redisTemplateFactory;
    @Resource
    private IFileClient fileClient;
    @Resource
    private ResServiceProperties resServiceConfig;
    @Resource
    BtxFileArchiveResourceService btxFileArchiveResourceService;
    @Resource
    BtxFileTmpResourceService btxFileTmpResourceService;
    @Resource
    BtxFileTagService btxFileTagService;
    @Resource
    ResServiceProperties resServiceProperties;

    static Pattern tagPattern = Pattern.compile("^\\w{1,20}$");

    private static DefaultRedisScript<Boolean> checkLua;
    private static List filekeys = new ArrayList<String>() {{
        add(FileConsts.REDIS_SYS_CURRENT_KEY);
        add(FileConsts.REDIS_SYS_LIMIT_KEY);
    }};


    static {
        checkLua = new DefaultRedisScript<>();
        checkLua.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/checkStorgeLimit.lua")));
    }

    @Override
    public CommJSON<FileInfoDTO> uploadFile(MultipartFile file, String appid, String area, String pubtype,
                                            Integer livetime, String[] tags, String crypto) {
        CommJSON<FileInfoDTO> ret = new CommJSON<>();
        long fs = file.getSize();
        boolean islimit =
                !resServiceProperties.isStorageLimit() || redisTemplateFactory.generateRedisTemplate(boolean.class).execute(checkLua
                        , filekeys, appid,
                        fs);
        if (islimit) {
            Long requestid = IdWorker.getId();
            if (pubtype == null) {
                //默认私有
                pubtype = FileConsts.FILE_PRIVATE_FLAG;
            }

            if (crypto == null) {
                //默认不加密
                crypto = FileConsts.FILE_NOCRYPTO_FLAG;
            }

            if (livetime == null) {
                //默认不加密
                livetime = FileConsts.FILE_DEF_LIVE_TIME;
            }

            String fileName = file.getOriginalFilename();
            String type = fileName.substring(fileName.lastIndexOf(".") + 1);

            try {
                FileStorageDTO st = fileClient.uploadFile(file.getBytes(), type);
                if (st != null) {
                    Long fileid;
                    boolean isTagOk = false;
                    boolean isTagValid = true;

                    if (FileConsts.FILE_TMP_FLAG.equals(area)) {
                        BtxFileArchiveResourceBO abo = new BtxFileArchiveResourceBO();
                        abo.setFileOriName(file.getOriginalFilename());
                        abo.setSysId(appid);
                        abo.setFileStorage(fs);
                        abo.setFilePath(st.getPath());
                        abo.setFileType(type);
                        abo.setFileStorageInfo(JSON.toJSONString(st.getMetaStorgeData()));
                        abo.setIsCrypto(crypto);
                        abo.setIsPub(pubtype);
                        btxFileArchiveResourceService.save(abo);
                        fileid = abo.getLsh();
                        isTagOk = true;
                    } else {
                        BtxFileTmpResourceBO tbo = new BtxFileTmpResourceBO();
                        tbo.setFileOriName(file.getOriginalFilename());
                        tbo.setSysId(appid);
                        tbo.setFileStorage(fs);
                        tbo.setFilePath(st.getPath());
                        tbo.setFileType(type);
                        tbo.setFileStorageInfo(JSON.toJSONString(st.getMetaStorgeData()));
                        tbo.setIsCrypto(crypto);
                        tbo.setIsPub(pubtype);
                        tbo.setEndTime(new Date(System.currentTimeMillis() + livetime * 1000));
                        btxFileTmpResourceService.save(tbo);
                        fileid = tbo.getLsh();

                        if (tags != null && tags.length > 0) {
                            List<BtxFileTagBO> ts = new ArrayList<>();

                            for (String c : tags) {
                                if (tagPattern.matcher(c).find()) {
                                    BtxFileTagBO t = new BtxFileTagBO();
                                    t.setFileId(fileid);
                                    t.setTag(c);
                                    ts.add(t);
                                } else {
                                    isTagValid = false;
                                    ret = new CommJSON<>(FileErrorMessage.FILE_TAG_INVALID_ERROR);
                                    break;
                                }
                            }

                            if (isTagValid) {
                                isTagOk = btxFileTagService.saveBatch(ts);
                                if (!isTagOk) {
                                    ret = new CommJSON<>(FileErrorMessage.UPDATE_FILE_TAG_ERROR);
                                }
                            }
                        }
                    }

                    if (fileid != null) {
                        FileInfoDTO dto = new FileInfoDTO();
                        dto.setFileId(fileid);
                        if (isTagOk) {
                            ret = new CommJSON(dto);
                        } else {
                            ret.setResult(dto);
                        }
                    } else {
                        ret = new CommJSON(FileErrorMessage.FILE_UPLOAD_ERROR);
                        log.error("saveFileInfo error:{}", requestid);
                    }
                } else {
                    ret = new CommJSON(FileErrorMessage.FILE_UPLOAD_ERROR);
                }
            } catch (IOException e) {
                log.error("uploadFile [{}] error", requestid, e);
            }

            if (!ret.checkSuc()) {
                if (!resServiceProperties.isStorageLimit()) {
                    redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().increment(FileConsts.REDIS_SYS_CURRENT_KEY, appid, fs * -1);

                }
                ret.setSubcode(requestid.toString());
            }
        } else {
            ret = new CommJSON(FileErrorMessage.FILE_STORGE_LIMIT);
        }

        return ret;
    }

    @Override
    public CommJSON<FileInfoDTO> downloadFile(String appid, long fileid, String area) {
        CommJSON<FileInfoDTO> ret;
        FileAccessDTO fa = new FileAccessDTO();
        String pubflag = null;
        if (FileConsts.FILE_TMP_FLAG.equals(area)) {
            BtxFileTmpResourceBO tbo = btxFileTmpResourceService.getById(fileid);
            if (tbo != null && tbo.getSysId().equals(appid)) {
                fa.setFilepath(tbo.getFilePath());
                fa.setFiletype(tbo.getFileType());
                pubflag = tbo.getIsPub();
            }
        } else {
            BtxFileArchiveResourceBO abo = btxFileArchiveResourceService.getById(fileid);
            if (abo != null && abo.getSysId().equals(appid)) {
                fa.setFilepath(abo.getFilePath());
                fa.setFiletype(abo.getFileType());
                pubflag = abo.getIsPub();
            }
        }

        if (StringUtils.isNotBlank(fa.getFilepath())) {
            String tk = null;
            if (FileConsts.FILE_PRIVATE_FLAG.equals(pubflag)) {
                String key =
                        redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().get(FileConsts.REDIS_SYS_KEY, appid).toString();
                try {
                    tk = ResUtil.encryptPubFile(appid, key, JSON.toJSONString(fa));
                } catch (Exception e) {
                    log.error("downloadfile [{}] error:{}" + fileid, e);
                }
            } else {
                tk = UUID.randomUUID().toString().replaceAll("-", "");
                redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().put(tk, "filepath",
                        fa.getFilepath());
                redisTemplateFactory.generateRedisTemplate(String.class).expire(tk, 10 * 60, TimeUnit.SECONDS);
            }

            if (StringUtils.isNoneBlank(tk)) {
                FileInfoDTO dto = new FileInfoDTO();
                dto.setFileType(fa.getFiletype());
                dto.setFilePath(String.format("%s/file/%s/%s", resServiceConfig.getGatewayUrl(), appid, tk));
                ret = new CommJSON<>(dto);
            } else {
                ret = new CommJSON(FileErrorMessage.FILE_DOWNLOAD_ERROR);
            }
        } else {
            ret = new CommJSON(FileErrorMessage.FILE_UNEXIST);
        }

        return ret;
    }

    @Override
    public CommJSON<FileInfoDTO> deleteFile(String appid, long fileid, String area) {
        CommJSON<FileInfoDTO> ret = new CommJSON<>();
        String fileStorageInfo = null;
        long filestorage = 0;

        if (FileConsts.FILE_TMP_FLAG.equals(area)) {
            BtxFileTmpResourceBO tbo = btxFileTmpResourceService.getById(fileid);
            if (tbo != null && tbo.getSysId().equals(appid)) {
                if (btxFileTmpResourceService.removeById(fileid)) {
                    fileStorageInfo = tbo.getFileStorageInfo();
                    filestorage = tbo.getFileStorage();
                }
            }
        } else {
            BtxFileArchiveResourceBO abo = btxFileArchiveResourceService.getById(fileid);
            if (abo != null && abo.getSysId().equals(appid)) {
                if (btxFileArchiveResourceService.removeById(fileid)) {
                    fileStorageInfo = abo.getFileStorageInfo();
                    filestorage = abo.getFileStorage();
                }
            }
        }

        if (StringUtils.isNotBlank(fileStorageInfo)) {
            if (!resServiceProperties.isStorageLimit()) {
                redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().increment(FileConsts.REDIS_SYS_CURRENT_KEY, appid, filestorage * -1);
            }
            ret = new CommJSON("");
        } else {
            log.error("fileclient del error:{}", fileid);
        }

        return ret;
    }

    @Override
    public CommJSON<FileInfoDTO> archiveFile(String appid, long fileid) {
        CommJSON<FileInfoDTO> ret;

        BtxFileTmpResourceBO tbo = btxFileTmpResourceService.getById(fileid);
        if (tbo != null && tbo.getSysId().equals(appid)) {
            Long id = IdWorker.getId();
            btxFileTmpResourceService.moveToArchive(id, fileid, appid);

            List<BtxFileTagBO> nt = new ArrayList<>();
            btxFileTagService.lambdaQuery().eq(BtxFileTagBO::getFileId, fileid).list().forEach((BtxFileTagBO c) -> {
                c.setFileId(id);
                nt.add(c);
            });

            if (nt.size() > 0) {
                btxFileTagService.saveBatch(nt);
            }

            FileInfoDTO d = new FileInfoDTO();
            d.setFileId(id);
            ret = new CommJSON<>(d);
        } else {
            ret = new CommJSON(FileErrorMessage.FILE_UNEXIST);
        }

        return ret;
    }

    @Override
    public CommJSON updateFileTags(String appid, long fileid, String[] tags) {
        CommJSON ret = new CommJSON();
        boolean isTagValid = true;

        Map<SFunction<BtxFileTmpResourceBO, ?>, Object> params = new HashMap<>();
        params.put(BtxFileTmpResourceBO::getSysId, appid);
        params.put(BtxFileTmpResourceBO::getLsh, fileid);
        Map<SFunction<BtxFileArchiveResourceBO, ?>, Object> params1 = new HashMap<>();
        params1.put(BtxFileArchiveResourceBO::getSysId, appid);
        params1.put(BtxFileArchiveResourceBO::getLsh, fileid);
        if (btxFileTmpResourceService.lambdaQuery().allEq(params).count() > 0 || btxFileArchiveResourceService.lambdaQuery().allEq(params1).count() > 0) {
            List<BtxFileTagBO> ts = new ArrayList<>();
            for (String c : tags) {
                if (tagPattern.matcher(c).find()) {
                    BtxFileTagBO t = new BtxFileTagBO();
                    t.setFileId(fileid);
                    t.setTag(c);
                    ts.add(t);
                } else {
                    isTagValid = false;
                    ret = new CommJSON<>(FileErrorMessage.FILE_TAG_INVALID_ERROR);
                    break;
                }
            }

            if (isTagValid) {
                if (btxFileTagService.updateTags(fileid, ts)) {
                    ret = new CommJSON("");
                } else {
                    ret = new CommJSON<>(FileErrorMessage.UPDATE_FILE_TAG_ERROR);
                }
            }
        } else {
            ret = new CommJSON<>(FileErrorMessage.FILE_UNEXIST);
        }

        return ret;
    }

    @Override
    public CommJSON getFileToken(String appid) {
        String tk = UUID.randomUUID().toString();
        redisTemplateFactory.generateRedisTemplate(String.class).opsForValue().set(FileConsts.REDIS_FILE_TMP_KEY + tk
                , appid, 10, TimeUnit.MINUTES);
        return new CommJSON(tk);
    }

//    @Override
//    public CommJSON<FileInfoDTO> changeFile(String appid, long fileid, String pubtype) {
//        CommJSON<FileInfoDTO> ret;
//
//        FileResourceBO bo = fileResourceService.getById(fileid);
//        if (bo != null && bo.getSysCode().equals(appid)) {
//            FileStorageDTO st = fileClient.copyFile(bo.getFileStorageInfo(), bo.getFileType());
//            if (st != null) {
//                fileResourceService.moveToHis(IdWorker.getId(), fileid, appid);
//                bo.setLsh(null);
//                bo.setFileStorageInfo(JSON.toJSONString(st.getMetaStorgeData()));
//                bo.setFilePath(st.getPath());
//                bo.setFileArear(pubtype);
//                if (fileResourceService.save(bo)) {
//                    FileInfoDTO dto = new FileInfoDTO();
//                    dto.setFileId(bo.getLsh());
//                    ret = new CommJSON(dto);
//                } else {
//                    ret = new CommJSON(FileErrorMessage.FILE_MODIFY_ERROR);
//                    log.error("changeFileInfo error:{}", fileid);
//                }
//            } else {
//                ret = new CommJSON(FileErrorMessage.FILE_MODIFY_ERROR);
//            }
//        } else {
//            ret = new CommJSON(FileErrorMessage.FILE_UNEXIST);
//        }
//
//        return ret;
//    }
//
//    @Override
//    public CommJSON<FileShareInfoDTO> shareFile(String appid, long fileid, long livetime) {
//        CommJSON<FileShareInfoDTO> ret;
//
//        FileResourceBO bo = fileResourceService.getById(fileid);
//        if (bo != null && bo.getSysCode().equals(appid)) {
//            String tk = UUID.randomUUID().toString().replaceAll("-+", "");
//            String code = ResUtil.createRandomCharData(8);
//
//            FileShareInfoDTO dto = new FileShareInfoDTO();
//            dto.setVerCode(code);
//            dto.setShareUrl(String.format("%s/file/share/xx.html?appid=%s&token=%s", resServiceConfig.getShareUrl(),
//                    appid, tk));
//            redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().putAll(tk,
//                    JSON.parseObject(JSONObject.toJSONString(dto), HashMap.class));
//            redisTemplateFactory.generateRedisTemplate(String.class).expire(tk, livetime * 24 * 60, TimeUnit.SECONDS);
//
//            ret = new CommJSON(dto);
//        } else {
//            ret = new CommJSON(FileErrorMessage.FILE_UNEXIST);
//        }
//        return ret;
//    }


}
