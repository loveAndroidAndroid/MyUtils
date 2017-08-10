package com.example.myutils.utils.general;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wen on 2017/8/10.
 * 提供些常用的字符串相关的工具方法
 */

public class StringUtil {

    /**
     * 判断是否是空字符串 null和"" 都返回 true
     */
    public  static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    /**
     * 把string array or list用给定的符号symbol连接成一个字符串
     */
    public  static String joinString(List list, String symbol) {
        String result = "";
        if (list != null) {
            for (Object o : list) {
                String temp = o.toString();
                if (temp.trim().length() > 0)
                    result += (temp + symbol);
            }
            if (result.length() > 1) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    /**
     * 判定第一个字符串是否等于的第二个字符串中的某一个值
     */
    public  static boolean requals(String str1, String str2) {
        if (str1 != null && str2 != null) {
            str2 = str2.replaceAll("\\s*", "");
            String[] arr = str2.split(",");
            for (String t : arr) {
                if (t.equals(str1.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 截取字符串　超出的字符用symbol代替
     */
    public  static String subStringSymbol(String str, int len, String symbol) {
        String temp = "";
        if (str != null && str.length() > len) {
            temp = str.substring(0, len) + symbol;
        }
        return temp;
    }


    /**
     * 把string array or list用给定的符号symbol连接成一个字符串
     */
    public  static String joinString(String[] array, String symbol) {
        String result = "";
        if (array != null&& symbol!= null) {
            for (String temp : array) {
                if (temp != null && temp.trim().length() > 0)
                    result += (temp + symbol);
            }
            if (result.length() > 1 && symbol!= null) {
                result = result.substring(0, result.length() - symbol.length());
            }
        }
        return result;
    }

    /**
     * 将一组字符才以指定的字符链接起来
     */
    public  static String join(String linkStr,String ... strs){
        StringBuilder result = new StringBuilder();
        for (String temp : strs) {
            if (temp != null && temp.trim().length() > 0)
                result.append(temp + linkStr);
        }
        if (result.length() > 1 && linkStr!= null) {
            return result.substring(0, result.length() - linkStr.length());
        }
        return result.toString();
    }


    /**
     * 隐藏邮件地址前缀。
     *
     * @param email - EMail邮箱地址 例如: ssss@koubei.com 等等...
     * @return 返回已隐藏前缀邮件地址, 如 *********@koubei.com.
     */
    public  static String getHideEmailPrefix(String email) {
        if (null != email) {
            int index = email.lastIndexOf('@');
            if (index > 0) {
                email = repeat("*", index).concat(email.substring(index));
            }
        }
        return email;
    }

    /**
     * repeat - 通过源字符串重复生成N次组成新的字符串。
     */
    public  static String repeat(String src, int num) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }


    /**
     * 根据指定的字符把源字符串分割成一个list

     */
    public  static List<String> parseString2List(String src, String pattern) {
        List<String> list = new ArrayList<>();
        if (src != null) {
            String[] tt = src.split(pattern);
            list.addAll(Arrays.asList(tt));
        }
        return list;
    }

    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param str 需要处理的字符串
     */
    public  static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }


    /**
     * 判断字符串数组中是否包含某字符串元素
     */
    public  static boolean isIn(String substring, String[] source) {
        if(isEmpty(substring) || source!=null){
            return false;
        }
        for (String t:source) {
            if (substring.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除所有的标点符号
     * @param str 处理的字符串
     */
    public  static String trimPunct(String str) {
        if(isEmpty(str)){
            return "";
        }
        return str.replaceAll("[\\pP\\p{Punct}]", "");
    }

    /**
     * 获取字符串str在String中出现的次数
     */
    public  static int countSubStr(String string, String str) {
        if ((str == null) || (str.length() == 0) || (string == null) || (string.length() == 0)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = string.indexOf(str, index)) != -1) {
            count++;
            index += str.length();
        }
        return count;
    }


    /**
     * 替换一个出现的子串
     */
    public  static String replaceFirst(String s, String sub, String with) {
        int i = s.indexOf(sub);
        if (i == -1) {
            return s;
        }
        return s.substring(0, i) + with + s.substring(i + sub.length());
    }


    /**
     * 替换最后一次出现的字串
     * Replaces the very last occurrence of a substring with supplied string.
     */
    public  static String replaceLast(String s, String sub, String with) {
        int i = s.lastIndexOf(sub);
        if (i == -1) {
            return s;
        }
        return s.substring(0, i) + with + s.substring(i + sub.length());
    }


    /**
     * 删除所有的子串
     * Removes all substring occurrences from the string.
     */
    public  static String remove(String s, String sub) {
        int c      = 0;
        int sublen = sub.length();
        if (sublen == 0) {
            return s;
        }
        int i = s.indexOf(sub, c);
        if (i == -1) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length());
        do {
            sb.append(s.substring(c, i));
            c = i + sublen;
        } while ((i = s.indexOf(sub, c)) != -1);
        if (c < s.length()) {
            sb.append(s.substring(c, s.length()));
        }
        return sb.toString();
    }

    /**
     * 将字符串首字母转大写
     * @param str 需要处理的字符串
     */
    public  static String upperFirstChar(String str){
        if(isEmpty(str)){
            return "";
        }
        char[] cs=str.toCharArray();
        if((cs[0] >= 'a') && (cs[0] <= 'z')){
            cs[0] -= (char) 0x20;
        }
        return String.valueOf(cs);
    }

    /**
     * 将字符串首字母转小写
     * @param str
     */
    public  static String lowerFirstChar(String str){
        if(isEmpty(str)){
            return "";
        }
        char[] cs=str.toCharArray();
        if((cs[0] >= 'A') && (cs[0] <= 'Z')){
            cs[0] += (char) 0x20;
        }
        return String.valueOf(cs);
    }
}
