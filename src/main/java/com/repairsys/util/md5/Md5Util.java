package com.repairsys.util.md5;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Prongs
 */
public class Md5Util {

    //将字节数组转为十六进制
    private static String bytesToHex(byte[] bytes) {
        StringBuffer hex = new StringBuffer();
        int num;
        for (byte aByte : bytes) {
            num = aByte;
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(num));
        }
        return hex.toString().toUpperCase();
    }

    /**
     * MD5加密
     *
     * @param message 用户的原始密码
     * @return MD5加密后的密码
     */
    public static String getMd5(String message) {
        String md5 = "";
        try {
            //创建MD5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
            //获得MD5字节数组
            byte[] md5Byte = md.digest(messageByte);
            //转为十六进制
            md5 = bytesToHex(md5Byte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
