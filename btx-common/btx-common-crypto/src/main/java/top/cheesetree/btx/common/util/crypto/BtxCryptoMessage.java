package top.cheesetree.btx.common.util.crypto;

import top.cheesetree.btx.framework.core.constants.BtxMessage;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/9/3 11:24
 * @Version: 1.0
 * @Description:
 */
public class BtxCryptoMessage extends BtxMessage {

    public static final BtxCryptoMessage CRYPTO_ERROR = new BtxCryptoMessage(-1, "加密失败");
    public static final BtxCryptoMessage DECRYPT_ERROR = new BtxCryptoMessage(-1, "解密失败");


    public BtxCryptoMessage(Integer code, String message) {
        super(code, message);
    }
}
