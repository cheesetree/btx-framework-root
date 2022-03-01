package top.cheesetree.btx.project.dts.dtm.client.common.model;

@FunctionalInterface
public interface DtmConsumer<T> {

    void accept(T t) throws Exception;
}
