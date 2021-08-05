package top.cheesetree.btx.common.util.crypto;


import top.cheesetree.btx.common.util.crypto.sm.Encode;
import top.cheesetree.btx.common.util.crypto.sm.SM3Digest;
import top.cheesetree.btx.common.util.crypto.sm.SM4;
import top.cheesetree.btx.common.util.crypto.sm.SM4Context;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class SecureUtil {
    /**
     * 加密方式
     */
    public enum ChpMode {
        AES, // AES/ECB/PKCS5Padding
        MD5, //
        TDES, // 3DES(CBC/PKCS5Padding)
        AES_CBC_P5, // CBC/PKCS5Padding)
        SHA256, SM3, // SM3
        SM4_CBC_P7// CBC/PKCS7Padding
    }

    ;
    private final static String ENCODING = "UTF-8";
    private final static String AES_IV = "RVJXRTkwV0VXRVc=";
    private final static String TDES_IV = "MjEyMWl+";
    private final static String AES_KEY_ALGORITHM = "AES";
    private final static String TDES_KEY_ALGORITHM = "DESede";


    public static void main(String[] args) throws Exception {
        System.out.println(encrypt(ChpMode.AES_CBC_P5, "Root_123456", "nbwd@2020"));
    }

    /**
     * 加密
     *
     * @param cm
     * @param message
     * @param sKey
     * @return
     * @throws Exception
     */
    @Deprecated
    public static String encrypt(ChpMode cm, String message, String sKey) throws Exception {
        String ret = "";

        try {
            switch (cm) {
                case AES:
                    ret = Encode.encodeHexString(AESEncode(message, sKey, "AES/ECB/PKCS5Padding", AES_IV.getBytes(ENCODING), true), false);
                    break;
                case AES_CBC_P5:
                    ret = Encode.encodeHexString(AESEncode(message, sKey, "AES/CBC/PKCS5Padding", AES_IV.getBytes(ENCODING), false),
                            false);
                    break;
                case MD5:
                    ret = MD5Encode(message);
                    break;
                case TDES:
                    ret = Encode.encodeHexString(TDESEncode(message, sKey, "DESede/CBC/PKCS5Padding", TDES_IV.getBytes(ENCODING), false));
                    break;
                case SHA256:
                    ret = SHA(message, 256);
                    break;
                case SM3:
                    ret = SM3(message);
                    break;
                case SM4_CBC_P7:
                    ret = Encode.encodeHexString(SM4Encode(message, sKey, AES_IV.getBytes(ENCODING), 2, false), false);
                    break;
                default:
                    break;
            }
        } catch (Exception err) {
            err.printStackTrace();
            throw new Exception("加密失败" + err.toString());
        }

        return ret;
    }

    /**
     * 解密
     *
     * @param cm
     * @param message
     * @param sKey
     * @return
     * @throws Exception
     */
    @Deprecated
    public static String decrypt(ChpMode cm, String message, String sKey) throws Exception {
        String ret = "";

        try {
            switch (cm) {
                case AES:
                    ret = new String(AESDecode(Encode.decodeHex(message), sKey, "AES/ECB/PKCS5Padding", AES_IV.getBytes(ENCODING), true),
                            ENCODING);
                    break;
                case AES_CBC_P5:
                    ret = new String(AESDecode(Encode.decodeHex(message), sKey, "AES/CBC/PKCS5Padding", AES_IV.getBytes(ENCODING), false),
                            ENCODING);
                    break;
                case TDES:
                    ret = new String(
                            TDESDecode(Encode.decodeHex(message), sKey, "DESede/CBC/PKCS5Padding", TDES_IV.getBytes(ENCODING), false),
                            ENCODING);
                    break;
                case SM4_CBC_P7:
                    ret = new String(SM4Decode(Encode.decodeHex(message), sKey, AES_IV.getBytes(ENCODING), 2, false), ENCODING);
                    break;
                default:
                    break;
            }

        } catch (Exception err) {
            err.printStackTrace();
            throw new Exception("解密失败-->" + err.toString());
        }

        return ret;
    }

    public static String encryptBase64(ChpMode cm, String message, String sKey) throws Exception {
        return encryptBase64(cm, message, sKey, null);
    }

    /**
     * @param cm
     * @param message
     * @param sKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static String encryptBase64(ChpMode cm, String message, String sKey, String iv) throws Exception {
        String ret = "";


        try {
            switch (cm) {
                case AES_CBC_P5:
                    if (iv == null || "".equals(null)) {
                        iv = AES_IV;
                    }
                    ret = Encode.byte2b64(AESEncode(message, sKey, "AES/CBC/PKCS5Padding", iv.getBytes(ENCODING),
                            false));
                    break;
                case TDES:
                    if (iv == null || "".equals(null)) {
                        iv = TDES_IV;
                    }
                    ret = Encode.byte2b64(TDESEncode(message, sKey, "DESede/CBC/PKCS5Padding", iv.getBytes(ENCODING),
                            false));
                    break;
                case SM4_CBC_P7:
                    ret = Encode.byte2b64(SM4Encode(message, sKey, iv.getBytes(ENCODING), 2, false));
                    break;
                default:
                    break;
            }
        } catch (Exception err) {
            err.printStackTrace();
            throw new Exception("加密失败" + err.toString());
        }

        return ret;
    }

    public static String decryptBase64(ChpMode cm, String message, String sKey) throws Exception {
        return decryptBase64(cm, message, sKey, null);
    }

    /**
     * @param cm
     * @param message
     * @param sKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static String decryptBase64(ChpMode cm, String message, String sKey, String iv) throws Exception {
        String ret = "";

        try {
            switch (cm) {
                case AES_CBC_P5:
                    if (iv == null || "".equals(null)) {
                        iv = AES_IV;
                    }
                    ret = new String(AESDecode(Encode.b642byte(message), sKey, "AES/CBC/PKCS5Padding", iv.getBytes(
                            ENCODING), false),
                            ENCODING);
                    break;
                case TDES:
                    if (iv == null || "".equals(null)) {
                        iv = TDES_IV;
                    }
                    ret = new String(
                            TDESDecode(Encode.b642byte(message), sKey, "DESede/CBC/PKCS5Padding",
                                    iv.getBytes(ENCODING), false),
                            ENCODING);
                    break;
                case SM4_CBC_P7:
                    if (iv == null || "".equals(null)) {
                        iv = AES_IV;
                    }
                    ret = new String(SM4Decode(Encode.b642byte(message), sKey, iv.getBytes(ENCODING), 2, false), ENCODING);
                    break;
                default:
                    break;
            }

        } catch (Exception err) {
            err.printStackTrace();
            throw new Exception("解密失败-->" + err.toString());
        }

        return ret;
    }

    /**
     * MD5加密
     *
     * @param input
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String MD5Encode(String input) throws Exception {
        // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 输入的字符串转换成字节数组
        byte[] inputByteArray = input.getBytes(ENCODING);
        // inputByteArray是输入字符串转换得到的字节数组
        messageDigest.update(inputByteArray);
        // 转换并返回结果，也是字节数组，包含16个元素
        byte[] resultByteArray = messageDigest.digest();
        // 字符数组转换成字符串返回
        return Encode.encodeHexString(resultByteArray);
    }

    /**
     * AES 加密
     *
     * @param message
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] AESEncode(String message, String sKey, String chpAlgorithm, byte[] iv, boolean isEbc)
            throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AES_KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(sKey.getBytes(ENCODING));
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(chpAlgorithm);// 创建密码器
        byte[] byteContent = message.getBytes(ENCODING);

        if (!isEbc) {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));// 初始化
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        }
        return cipher.doFinal(byteContent);
    }

    /**
     * AES解密
     *
     * @param message
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] AESDecode(byte[] message, String sKey, String chpAlgorithm, byte[] iv, boolean isEbc)
            throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AES_KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(sKey.getBytes(ENCODING));
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(chpAlgorithm);// 创建密码器

        if (!isEbc) {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));// 初始化
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        }
        return cipher.doFinal(message);
    }

    /**
     * 3des解密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] TDESDecode(byte[] message, String key, String chpAlgorithm, byte[] iv, boolean isEbc)
            throws Exception {
        byte[] keybyte = buildTDESKey(key);
        SecretKey deskey = new SecretKeySpec(keybyte, TDES_KEY_ALGORITHM);
        // 解密
        Cipher c1 = Cipher.getInstance(chpAlgorithm);
        if (!isEbc) {
            c1.init(Cipher.DECRYPT_MODE, deskey, new IvParameterSpec(iv));
        } else {
            c1.init(Cipher.DECRYPT_MODE, deskey);
        }

        return c1.doFinal(message);
    }

    /**
     * 3des加密
     *
     * @param value
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] TDESEncode(String value, String key, String chpAlgorithm, byte[] iv, boolean isEbc)
            throws Exception {
        byte[] keybyte = buildTDESKey(key);
        SecretKey deskey = new SecretKeySpec(keybyte, TDES_KEY_ALGORITHM);
        // Security.addProvider(new
        // org.bouncycastle.jce.provider.BouncyCastleProvider());//支持pkcs7
        Cipher cipher = Cipher.getInstance(chpAlgorithm);
        byte[] valuebyte = value.getBytes(ENCODING);

        if (!isEbc) {
            byte[] ivs = new byte[iv.length];
            System.arraycopy(iv, 0, ivs, 0, iv.length);
            cipher.init(Cipher.ENCRYPT_MODE, deskey, new IvParameterSpec(ivs));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
        }
        return cipher.doFinal(valuebyte);
    }

    /**
     * AES 加密
     *
     * @param message
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] SM4Encode(String message, String sKey, byte[] iv, int padmode, boolean isEbc)
            throws Exception {
        SM4Context ctx = new SM4Context();
        ctx.isPadding = padmode > 0;
        ctx.mode = SM4.SM4_ENCRYPT;
        ctx.paddingMode = padmode;

        byte[] keyBytes = sKey.getBytes(ENCODING);
        SM4 sm4 = new SM4();
        sm4.sm4_setkey_enc(ctx, keyBytes);
        if (isEbc) {
            return sm4.sm4_crypt_ecb(ctx, message.getBytes("UTF-8"));
        } else {
            byte[] ivs = new byte[iv.length];
            System.arraycopy(iv, 0, ivs, 0, iv.length);
            return sm4.sm4_crypt_cbc(ctx, ivs, message.getBytes("UTF-8"));
        }
    }

    /**
     * AES解密
     *
     * @param message
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] SM4Decode(byte[] message, String sKey, byte[] iv, int padmode, boolean isEbc)
            throws Exception {
        SM4Context ctx = new SM4Context();
        ctx.isPadding = padmode > 0;
        ctx.mode = SM4.SM4_DECRYPT;
        ctx.paddingMode = padmode;

        byte[] keyBytes = sKey.getBytes(ENCODING);
        SM4 sm4 = new SM4();
        sm4.sm4_setkey_dec(ctx, keyBytes);
        if (isEbc) {
            return sm4.sm4_crypt_ecb(ctx, message);
        } else {
            byte[] ivs = new byte[iv.length];
            System.arraycopy(iv, 0, ivs, 0, iv.length);
            return sm4.sm4_crypt_cbc(ctx, ivs, message);
        }
    }

    /**
     * 3des key生成
     *
     * @param keyStr
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] buildTDESKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes(ENCODING); // 将字符串转成字节数组

        /*
         * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
         */
        if (key.length > temp.length) {
            // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    /**
     * 字符串 SHA 加密
     *
     * @param
     * @return
     */
    public static String SHA(final String strText, final int strType) throws Exception {
        // 返回值
        byte[] strResult = new byte[0];

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            // SHA 加密开始
            // 创建加密对象 并傳入加密類型
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-" + strType);
            // 传入要加密的字符串
            messageDigest.update(strText.getBytes());
            // 得到 byte 類型结果
            strResult = messageDigest.digest();
        }

        return Encode.encodeHexString(strResult);
    }

    public static String SM3(String input) throws Exception {
        SM3Digest digest = new SM3Digest();
        byte[] srcData = input.getBytes(ENCODING);
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return Encode.encodeHexString(hash, false);
    }

}