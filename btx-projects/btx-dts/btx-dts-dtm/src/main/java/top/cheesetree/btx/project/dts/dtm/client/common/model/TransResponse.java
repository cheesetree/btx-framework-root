package top.cheesetree.btx.project.dts.dtm.client.common.model;

import lombok.Data;

@Data
public class TransResponse {

    private String dtmResult;

    public TransResponse(String dtmResult) {
        this.dtmResult = dtmResult;
    }

    public static TransResponse buildTransResponse(String result) {
        return new TransResponse(result);
    }
}
