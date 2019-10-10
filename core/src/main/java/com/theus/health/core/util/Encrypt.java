package com.theus.health.core.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密工具类
 * @author tangwei
 * @date 2019-08-03 22:35
 */
public class Encrypt {
    /**
     * MD5加密-32位小写
     * */
    public static String md5(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuilder hexValue = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }

    /**
     * MD5加密-16位小写
     */
    public static String md516(String encryptStr) {
        return md5(encryptStr).substring(8, 24);
    }

    /**
     * base64加密
     * */
    public static String base64Encode(String str) {
        byte[] b;
        String s;
        b = str.getBytes(StandardCharsets.UTF_8);
        s = new BASE64Encoder().encode(b);
        return s;
    }

    /**
     * Base64解密
     * */
    public static String base64Decode(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
