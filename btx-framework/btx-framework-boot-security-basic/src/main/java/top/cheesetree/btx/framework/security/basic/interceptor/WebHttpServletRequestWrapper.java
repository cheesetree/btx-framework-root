package top.cheesetree.btx.framework.security.basic.interceptor;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author van
 * @version 1.0
 * @Description TODO
 * @date 创建时间：2018年5月22日 上午9:09:55
 * @since
 */
public class WebHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public WebHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream is = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buff[] = new byte[1024];
        int read;
        while ((read = is.read(buff)) > 0) {
            baos.write(buff, 0, read);
        }
        this.body = baos.toByteArray();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), "UTF-8"));
    }

    @Override
    public ServletInputStream getInputStream() {
        return new com.wondersgroup.sbpc.framework.web.security.interceptor.BufferedServletInputStream(this.body);
    }
}
