package top.cheesetree.btx.framework.security.basic.interceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Author: van
 * @Date: 2019/11/4 16:41
 * @Description: TODO
 */
public class BufferedServletOutputStream extends ServletOutputStream {
    private ByteArrayOutputStream bos;

    public BufferedServletOutputStream(ByteArrayOutputStream stream) throws IOException {
        bos = stream;
    }

    @Override
    public void write(int b) {
        bos.write(b);
    }

    @Override
    public void write(byte[] b) {
        bos.write(b, 0, b.length);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
