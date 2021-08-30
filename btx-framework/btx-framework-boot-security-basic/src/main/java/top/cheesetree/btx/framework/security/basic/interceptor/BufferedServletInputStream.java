package top.cheesetree.btx.framework.security.basic.interceptor;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @Description TODO
 * @author van
 * @date 创建时间：2018年5月22日 上午9:56:17
 * @version 1.0
 * @since
 */
public class BufferedServletInputStream extends ServletInputStream {

	private ByteArrayInputStream inputStream;
	public BufferedServletInputStream(byte[] buffer) {
		this.inputStream = new ByteArrayInputStream(buffer);
	}
	@Override
	public int available() throws IOException {
		return inputStream.available();
	}
	@Override
	public int read() throws IOException {
		return inputStream.read();
	}
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return inputStream.read(b, off, len);
	}
	@Override
	public boolean isFinished() {
		return false;
	}
	@Override
	public boolean isReady() {
		return false;
	}
	@Override
	public void setReadListener(ReadListener readListener) {

	}
}
