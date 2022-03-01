package top.cheesetree.btx.project.dts.dtm.client.common.enums;

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