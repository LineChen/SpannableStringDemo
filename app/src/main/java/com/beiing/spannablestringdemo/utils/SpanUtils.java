package com.beiing.spannablestringdemo.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import com.beiing.spannablestringdemo.bean.Topic;
import com.beiing.spannablestringdemo.bean.User;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenliu on 2016/8/31.<br/>
 * 描述：
 * </br>
 */
public class SpanUtils{

    public static class PatternString{
        /**
         * #号括起来的话题#
         */
        public static final String TOPIC_PATTERN = "#[^#]+#";

        /**
         * 表情[大笑]
         */
        public static final String EXPRESSION_PATTERN = "\\[[^\\]]+\\]";

        /**
         * 网址
         */
        public static final String URL_PATTERN = "(([hH]ttp[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";

    }

    /**
     *
     * @param <T>
     */
    public interface SpanClickListener<T>{
        void onSpanClick(T t);
    }


    /**
     * 关键词变色处理
     * @param str
     * @param patterStr 需要变色的关键词 或者 正则表达式
     * @return
     */
    public static SpannableString getKeyWordSpan(int color, String str, String patterStr) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten = Pattern.compile(patterStr, Pattern.CASE_INSENSITIVE);
        dealPattern(color, spannableString, patten, 0);
        return spannableString;
    }

    /**
     * 自动识别话题并做颜色处理,可点击
     * @param color
     * @param str
     */
    public static SpannableString getTopicSpan(int color, String str, boolean clickable,SpanClickListener spanClickListener, Topic topic) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten = Pattern.compile(PatternString.TOPIC_PATTERN, Pattern.CASE_INSENSITIVE);
        if(clickable){
            dealClick(spannableString, patten, 0, spanClickListener, topic);
        }
        dealPattern(color, spannableString, patten, 0);
        return spannableString;
    }

    /**
     * @用户 颜色处理、点击处理
     * @param color 前景色
     * @param str
     * @param clickable 是否可点击
     * @param spanClickListener
     * @param atUsers
     * @return
     * @throws Exception
     */
    public static SpannableString getAtUserSpan(int color, String str, boolean clickable, SpanClickListener spanClickListener, List<User> atUsers) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten;
        for (User u : atUsers) {
            patten = Pattern.compile("@" + u.getName(), Pattern.CASE_INSENSITIVE);
            if(clickable){
                dealClick(spannableString, patten, 0, spanClickListener, u);
            }
            dealPattern(color, spannableString, patten, 0);
        }
        return spannableString;
    }

    /**
     * 表情处理
     * @param context
     * @param str
     * @return
     */
    public static SpannableString getExpressionSpan(Context context, String str) throws Exception {
        return ExpressionConvertUtil.getInstace().getExpressionString(context, str);
    }


    /**
     * 对spanableString进行正则判断，如果符合要求，则将内容变色
     * @param color
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private static void dealPattern(int color, SpannableString spannableString, Pattern patten, int start) throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            //设置前景色span
            spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealPattern(color, spannableString, patten, end);
            }
            break;
        }
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，将内容设置可点击
     * @param spannableString
     * @param patten
     * @param start
     * @param spanClickListener
     * @param bean
     */
    private static void dealClick(SpannableString spannableString, Pattern patten, int start, final SpanClickListener spanClickListener, final Object bean){
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    spanClickListener.onSpanClick(bean);
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置画笔属性
                    ds.setUnderlineText(false);//默认有下划线
                }
            }, matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealClick(spannableString, patten, end, spanClickListener, bean);
            }
            break;
        }
    }


}
