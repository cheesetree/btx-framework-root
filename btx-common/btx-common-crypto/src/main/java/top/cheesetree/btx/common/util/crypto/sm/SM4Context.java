package top.cheesetree.btx.common.util.crypto.sm;
/**
 * @Description TODO
 * @author van
 * @date 创建时间：Jun 18, 2019 3:30:06 PM
 * @version 1.0
 * @since
 */
public class SM4Context {

	public int mode;
	public long[] sk;
	public boolean isPadding;
	public int paddingMode;

	public SM4Context() {
		this.mode = 1;
		this.paddingMode = 1;
		this.isPadding = true;
		this.sk = new long[32];
	}
}
