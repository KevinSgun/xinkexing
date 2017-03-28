package com.thinkeract.tka.common.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by minHeng on 2016/12/2 13:34.
 * mail:minhengyan@gmail.com
 */

public class StringUtils extends com.zitech.framework.utils.StringUtils {

    public static String convertToHideBankCode(String bankCode){
        if(TextUtils.isEmpty(bankCode)) return "";
        if(bankCode.length()<4)return bankCode;
        bankCode = bankCode.substring(bankCode.length()-4,bankCode.length());
        return "**** **** **** "+bankCode;
    }

    public static String getClassify(String classifyStr){
        if(TextUtils.isEmpty(classifyStr)) return "";
        String[] classifyArray = classifyStr.split("@");
        return classifyArray[0];
    }

    public static String[] splitPictureUrls(String urls) {
        return urls != null ? urls.split("@X@") : null;
    }

    public static String getFirstPictureUrl(String[] urls) {
        return urls != null&&urls.length>0 ? urls[0] : "";
    }

    public static ArrayList<String> splitPictureUrlToList(String urls) {
        String[] urlsArr = splitPictureUrls(urls);
        ArrayList<String> stringList = new ArrayList<>();
        if(urlsArr != null){
            for(int i=0,len =urlsArr.length;i<len;i++){
                stringList.add(urlsArr[i]);
            }
        }
        return stringList;
    }

    /**
     * 只显示前三位和后四位
     * @param str 需要处理的文本
     * @return
     */
    public static String hideMiddle(String str){
        if(TextUtils.isEmpty(str)) return "***";
        if(str.length() <= 7) return str+"***";
        String strFirst = str.substring(0,3);
        String strEnd = str.substring(str.length()-4,str.length());
        return strFirst+"****"+strEnd;
    }

    public static String convert2HtmlTxt(String htmlTxtA,String htmlTxtB){
        if(htmlTxtA == null)
            htmlTxtA = "";
        if(htmlTxtB == null)
            htmlTxtB = "";
        return  "<head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\" />\n" +
                "    <style type=\"text/css\">\n" +
                "        body{padding:0;margin:0}\n" +
                "        *{margin: 0}\n" +
                "        .details:nth-child(1){border-bottom:14px solid #eee}\n" +
                "        .details{padding:15px}\n" +
                "        .details *,.details{line-height:1.8}\n" +
                ".details p{font-size:small;color:#798394;letter-spacing:1px}\n" +
                ".details h4{color:#202020;margin-bottom:6px}\n" +
                "        .details img{max-width:100%;height:auto;min-height:auto;max-height:auto}\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"details\">\n" +
                "    <h4>讲师介绍</h4>\n" +
                "    <p>"+htmlTxtA+"</p>\n" +
                "</div>\n" +
                "<div class=\"details\">\n" +
                "    <h4>课程介绍</h4>\n" +
                "    <p>"+htmlTxtB+"</p>\n" +
                "</div>\n" +
                "\n" +
                "</body>";
    }

    public static String convertHtmlTxt(String htmlTxt){
        if(htmlTxt == null) return "";
        return  "<head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\" />\n" +
                "    <style type=\"text/css\">\n" +
                "        body{padding:0;margin:0}\n" +
                "        *{margin: 0}\n" +


                "        .details *,.details{line-height:1.8}\n" +
                ".details p{font-size:small;color:#798394;letter-spacing:1px}\n" +
                ".details h4{color:#202020;margin-bottom:6px}\n" +
                "        .details img{max-width:100%;height:auto;min-height:auto;max-height:auto}\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"details\">\n" +
                "    <p>"+htmlTxt+"</p>\n" +
                "</div>\n" +
                "</body>";
    }

    public static String getTranslatedCounts(int count) {

        if (999999 < count && count < 100000000) {
            return (count / 10000) + "万";
        } else if (count > 99999999) {
            return (count / 100000000) + "亿+";
        }

        return String.valueOf(count);
    }

}
