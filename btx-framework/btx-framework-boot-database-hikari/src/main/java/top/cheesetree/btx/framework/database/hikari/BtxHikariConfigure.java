package top.cheesetree.btx.framework.database.hikari;

import top.cheesetree.btx.framework.database.hikari.config.BtxDataSourceConfigure;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/10/26 10:23
 * @Version: 1.0
 * @Description:
 */
@EnableConfigurationProperties({BtxDataSourceConfigure.class})
@Configuration
public class BtxHikariConfigure {
    public static void main(String[] args) throws Exception {
        if (args.length > 2) {
            String key = MD5Encode(String.format("%s_%s_%s", args[0], args[1], args[0]));
            String iv = key.substring(3, 19);
            System.out.println("encrypt result:" + Base64.getUrlEncoder().withoutPadding().encodeToString(AESEncode(args[2], key, iv.getBytes("UTF-8"))));
        } else {
            System.out.println("need at least three args");
        }
    }

    public static String MD5Encode(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 输入的字符串转换成字节数组
        byte[] inputByteArray = input.getBytes("UTF-8");
        // inputByteArray是输入字符串转换得到的字节数组
        messageDigest.update(inputByteArray);
        // 转换并返回结果，也是字节数组，包含16个元素
        byte[] resultByteArray = messageDigest.digest();
        // 字符数组转换成字符串返回
        return new String(encodeHex(resultByteArray, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a'
                , 'b', 'c'
                , 'd', 'e', 'f'}));
    }

    static byte[] AESEncode(String message, String sKey, byte[] iv)
            throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(sKey.getBytes("UTF-8"));
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
        byte[] byteContent = message.getBytes("UTF-8");

        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(byteContent);
    }

    static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

}
