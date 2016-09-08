package com.beiing.spannablestringdemo.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 字符串操作封装
 */
public final class StringUtil {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() < 1;
    }

    public static String getNotNullString(String str, String ret) {
        return str == null ? ret : str;
    }

    public static String getNotNullString(String str) {
        return getNotNullString(str, "");
    }

    /**
     * 拼接多个字符串
     *
     * @param strs 多个字符串
     * @return 拼接后的字符串
     */
    public static String append(String... strs) {
        StringBuilder buffer = new StringBuilder(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            buffer.append(strs[i]);
        }
        return buffer.toString();
    }

    public static boolean isNumberic(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拼接多个字符串
     *
     * @param strs      多个字符串
     * @param separator 间隔符
     * @return 拼接后的字符串
     */
    public static String append(String[] strs, String separator) {
        StringBuilder buffer = new StringBuilder(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            buffer.append(separator).append(strs[i]);
        }
        return buffer.toString();
    }

    /**
     * 是否相等 如果两个都为null, 或者两个的值一样, 则返回true
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 如果两个都为null, 或者两个的值一样, 则返回true, 否则返回false.
     */
    public static boolean equals(String str1, String str2) {
        return str1 == str2 || equalsNotNull(str1, str2);
    }

    /**
     * 是否相等(不区分大小写) 如果两个都为null, 或者两个的值一样(不区分大小写), 则返回true
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 如果两个都为null, 或者两个的值一样(不区分大小写), 则返回true, 否则返回false.
     */
    public static boolean equalsIgnore(String str1, String str2) {
        return str1 == str2 || equalsIgnoreNotNull(str1, str2);
    }

    /**
     * 是否相等 如果两个的值一样, 则返回true
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 如果两个的值一样, 则返回true, 否则返回false.
     */
    public static boolean equalsNotNull(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }

    /**
     * 是否相等(不区分大小写) 如果两个的值一样, 则返回true
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 如果两个的值一样(不区分大小写), 则返回true, 否则返回false.
     */
    public static boolean equalsIgnoreNotNull(String str1, String str2) {
        return str1 != null && str1.equalsIgnoreCase(str2);
    }

    public static String trim(String str) {
        return str != null ? str.trim() : str;
    }

    public static boolean isCharAtIndex(char[] chars, int index, char c) {
        return chars != null && chars.length >= index + 1 && c == chars[index];
    }

    /**
     * 比较两时间相差多少秒 time1现在时间，time2以前时间
     */
    public static long spaceOfTime(String time1, String time2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long days = 0;
        try {
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            days = diff / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 去掉时间秒
     */
    public static String subStringTime(String oTime) {
        String nTime = "";
        if (!isNullOrEmpty(oTime)) {
            nTime = oTime.substring(0, oTime.length() - 3);
        }
        return nTime;

    }

    /**
     * 判断List是否为null
     *
     * @param list
     * @return 不为空和条数大于零返回true
     */
    public static boolean isListNoNull(List list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 如果如果内容为空的话，返回true
     *
     * @param content
     * @return
     */
    public static boolean isNull(String content) {
        if (TextUtils.isEmpty(content)) {
            return true;
        } else {
            if ("null".equals(content.trim().toLowerCase())) {
                return true;
            } else {
                return false;
            }
        }
    }

}