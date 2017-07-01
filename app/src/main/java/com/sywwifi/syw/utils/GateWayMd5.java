package com.sywwifi.syw.utils;

import java.security.MessageDigest;

/**
 * Created by song on 2016/11/4.
 */

public class GateWayMd5 {
    /**
     * JAVA IOS两边通用 加密工具类，MD5是不可逆的，只能加密而不能解密。
     *
     * @param s
     * @return
     */
    public final static String toMD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdTemp.update(strTemp);
            // 获得密文
            byte[] md = mdTemp.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
