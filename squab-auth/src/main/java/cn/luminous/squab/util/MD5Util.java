package cn.luminous.squab.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * MD5 32位
     * <p>
     * signature 要加密的字符串 encode 编码 isUpperCase 是否转成大写
     *
     * @return 加密后的字符串
     */
    public final static String MD5(String signature, String encode)
            throws RuntimeException {
        if (signature == null) {
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密算法错误");
        }
        String ret = null;
        byte[] plainText = null;
        try {
            if (encode==null) {
                plainText = signature.getBytes();
            } else {
                plainText = signature.getBytes(encode);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5加密异常", e);
        }
        md5.update(plainText);
        ret = bytes2HexString(md5.digest());
        return ret;

    }

    public static String bytes2HexString(byte[] bytes) {
        String hs = "";
        if (bytes != null) {
            for (byte b : bytes) {
                String tmp = (Integer.toHexString(b & 0XFF));
                if (tmp.length() == 1) {
                    hs += "0" + tmp;
                } else {
                    hs += tmp;
                }
            }
        }
        return hs;
    }

    public static void main(String[] args) {
        String jiami = MD5("123456", "utf-8");
        //Zynsun666
        System.out.println(jiami);
    }
}
