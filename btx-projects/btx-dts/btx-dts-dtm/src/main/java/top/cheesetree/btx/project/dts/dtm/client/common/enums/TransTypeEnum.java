package top.cheesetree.btx.project.dts.dtm.client.common.enums;

/**
 * @author van
 * @date 2022/3/1 19:57
 * @description TODO
 */
public enum TransTypeEnum {
    // 事务类型
    TCC("tcc");

    TransTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return this.value;
    }

}