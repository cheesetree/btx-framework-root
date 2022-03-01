package top.cheesetree.btx.project.dts.dtm.client.common.model;

/**
 * @author van
 * @date 2022/3/1 19:57
 * @description TODO
 */
@FunctionalInterface
public interface DtmConsumer<T> {

    void accept(T t) throws Exception;
}
