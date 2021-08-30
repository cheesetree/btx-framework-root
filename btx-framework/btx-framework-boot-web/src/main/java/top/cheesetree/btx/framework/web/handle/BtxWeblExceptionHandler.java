package top.cheesetree.btx.framework.web.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.cheesetree.btx.framework.core.constants.BtxMessage;
import top.cheesetree.btx.framework.core.exception.BtxException;
import top.cheesetree.btx.framework.core.exception.BusinessException;
import top.cheesetree.btx.framework.core.json.CommJSON;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/27 10:05
 * @Version: 1.0
 * @Description:
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class BtxWeblExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommJSON errorHandler(Exception ex) {
        CommJSON ret;

        if (ex instanceof BtxException) {
            BtxException err = (BtxException) ex;

            if (err instanceof BusinessException) {
                ret = new CommJSON(BtxMessage.BUSI_ERROR.getCode(), err.getErrcode(), err.getMessage(), null);
            } else {
                ret = new CommJSON(BtxMessage.SYSTEM_ERROR.getCode(), err.getErrcode(),
                        err.getMessage());
            }

            log.error("系统异常:[{},{}]{}", err.getErrcode(), err.getMessage(), ex);
        } else {
            ret = new CommJSON(BtxMessage.UNKOWN_ERROR);
            ret.setMsg(String.format("未知异常%s:%s", ret.getMsg(), ex.getMessage()));
        }

        return ret;
    }
}
