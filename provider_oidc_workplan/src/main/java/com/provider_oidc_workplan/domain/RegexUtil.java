package com.provider_oidc_workplan.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex 正则表达式
 * @author
 */
public class RegexUtil {
    /**
     * 功能：判断邮箱是否合法
     *
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }

        String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * 功能：判断数字是否合法
     *
     * @param string
     * @return
     */
    public static boolean isNumber(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }

        String regex = "^-?\\d+(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * 功能：判断日期是否合法
     *
     * @param string
     * @return
     */
    public boolean isDate(String string)
    {
        if (string == null || string.length() == 0) {
            return false;
        }

        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        return matcher.matches();
    }

    /**
     * 功能：判断手机号是否合法
     *
     * @param string
     * @return
     */
    public static boolean isPhone(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }

        String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * 功能：从一个给定的字符串中找到数字串
     *
     * @param string
     * @return
     */
    public static List getNumber( String string) {
        if (string == null || string.length() == 0) {
            return null;
        }

        //按指定模式在字符串查找
        String regex = "(\\D*)(\\d+)(.*)";
        //创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);
        //现在创建 matcher 对象
        Matcher matcher = pattern.matcher(string);

        if (matcher.find( )) {
            System.out.println("Found value: " + matcher.group(0) );
            System.out.println("Found value: " + matcher.group(1) );
            System.out.println("Found value: " + matcher.group(2) );
            System.out.println("Found value: " + matcher.group(3) );

            List list = new ArrayList();
            list.add(matcher.group(0));
            list.add(matcher.group(1));
            list.add(matcher.group(2));
            list.add(matcher.group(3));
            return list;
        } else {
            System.out.println("NO MATCH");
        }

        return null;
    }
}
