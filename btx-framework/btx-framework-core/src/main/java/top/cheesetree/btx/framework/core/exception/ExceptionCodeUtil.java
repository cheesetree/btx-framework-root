package top.cheesetree.btx.framework.core.exception;

import top.cheesetree.btx.framework.core.constants.BtxMessage;

/**
 * @Author: van
 * @Date: 2021/8/27 11:01
 * @Description: 异常编码有4段组成 系统编码-业务编码-预留码(0000)-具体异常码(4位数字)
 */
public class ExceptionCodeUtil {
    public static String generateExceptionCode(String sysid, String busid, String defcode, String errcode) {
        if (isNotBlank(sysid) && isNotBlank(busid) && isNotBlank(defcode) && isNotBlank(errcode)) {
            if (defcode.length() != 4 || errcode.length() != 4) {
                throw new SystemException(BtxMessage.EXCEPTION_ERROR_ERROR.getCode().toString(),
                        BtxMessage.EXCEPTION_ERROR_ERROR.getMessage());
            }
        } else {
            throw new SystemException(BtxMessage.EXCEPTION_ERROR_ERROR.getCode().toString(),
                    BtxMessage.EXCEPTION_ERROR_ERROR.getMessage());
        }

        return String.format("%s-%s%s%s", sysid, busid, defcode, errcode);
    }

    public static String generateExceptionCode(String sysid, String busid, String errcode) {
        return generateExceptionCode(sysid, busid, "0000", errcode);
    }

    public static boolean isBlank(CharSequence cs) {
        if (cs != null) {
            int length = cs.length();

            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
}
