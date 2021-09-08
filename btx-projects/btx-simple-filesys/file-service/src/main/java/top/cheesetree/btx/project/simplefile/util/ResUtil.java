package top.cheesetree.btx.project.simplefile.util;

import org.apache.commons.lang3.StringUtils;
import top.cheesetree.btx.common.util.crypto.SecureUtil;

import java.util.Random;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/22 14:44
 * @Version: 1.0
 * @Description:
 */
public class ResUtil {
    public static String encryptPubFile(String appid, String key, String context) throws Exception {
        String nk = SecureUtil.MD5Encode(String.format("%s%s%s", appid, key, appid)).substring(3, 18);

        return SecureUtil.encryptBase64(SecureUtil.ChpMode.AES_CBC_P5, context, nk, StringUtils.reverse(nk));
    }

    public static String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        Random randdata = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 65;
                    sb.append((char) data);
                    break;
                case 2:
                    data = randdata.nextInt(26) + 97;
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }
}
