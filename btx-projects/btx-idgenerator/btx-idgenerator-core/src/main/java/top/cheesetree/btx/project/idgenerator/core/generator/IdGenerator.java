package top.cheesetree.btx.project.idgenerator.core.generator;

import java.util.List;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/12 15:46
 * @Version: 1.0
 * @Description:
 */
public interface IdGenerator {

    /**
     * @return
     */
    Long getId();

    /**
     * @param batchSize
     * @return
     */
    List<Long> getId(Integer batchSize);

    /**
     *
     * @param id
     * @return
     */
    String parseId(long id);
}
