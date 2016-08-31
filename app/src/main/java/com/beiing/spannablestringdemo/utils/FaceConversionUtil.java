package com.beiing.spannablestringdemo.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import com.beiing.spannablestringdemo.R;
import com.beiing.spannablestringdemo.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * *****************************************
 *
 * @文件名称 : FaceConversionUtil.java
 * @文件描述 : 表情轉換工具  文本处理
 * *****************************************
 */
public class FaceConversionUtil {
    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断 进行相应的替换及颜色处理
     * @param context
     * @param str
     * @return
     */
    public static SpannableString getFirstStrChangeColor(final Context context, String str, String headStr, int titleColor, int start) {
        SpannableString spannableString = new SpannableString(str);
        if (!StringUtil.isNull(headStr)) {
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(titleColor)), start, start+headStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     * Card  有表情  有##处理
     * @param context
     * @param str
     * @return
     */
    public static SpannableString getCardText(final Context context, String str, List<User> atsList) {
        SpannableString spannableString = new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern patten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        String strP2 = "#[^#]+#";
        // 通过传入的正则表达式来生成一个pattern
        Pattern patten2 = Pattern.compile(strP2, Pattern.CASE_INSENSITIVE);
        dealCard(context, spannableString, patten, patten2, 0);
        getUrlClickText(context, spannableString);
        //at经纪人点击事件
        getAtBrokerCliclk(context, spannableString, atsList);
        return spannableString;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     * Card  转发贴文本处理   有表情  有##处理    有名字跳转
     *
     * @param context
     * @param str
     */
    public static SpannableString getRetweetedText(final Context context, String str, String headStr, final String brokerId, List<User> atsList) {
        SpannableString spannableString = new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        String strP2 = "#[^#]+#";
        // 通过传入的正则表达式来生成一个pattern
        Pattern patten2 = Pattern.compile(strP2, Pattern.CASE_INSENSITIVE);
        if (!StringUtil.isNull(headStr)) {
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), 0, headStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!brokerId.equals("0") && !StringUtil.isNull(brokerId)) {
                getClickBrokerDetail(context, spannableString, 0, headStr.length(), brokerId);
            }
        }
        dealCard(context, spannableString, sinaPatten, patten2, 0);
        getUrlClickText(context, spannableString);
        //at经纪人点击事件
        getAtBrokerCliclk(context, spannableString, atsList);
        return spannableString;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     * Card  有表情  有##处理  有点击事件  跳转到相应的话题列表
     *
     * @param context
     * @param str
     * @return
     */
    public static SpannableString getCardTopicText(Context context, String str, String talkType, String headStr, String brokerId, List<User> atsList) {
        SpannableString spannableString = new SpannableString(str);
        if (!StringUtil.isNull(headStr)) {
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), 0, headStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!brokerId.equals("0") && !StringUtil.isNull(brokerId)) {
                getClickBrokerDetail(context, spannableString, 0, headStr.length(), brokerId);
            }
        }
        String realStr = str;
        if (!StringUtil.isNull(headStr)) {
            realStr = str.substring(headStr.length(), str.length());
        }
        String strStart = "#[^#]+#";
        // 通过传入的正则表达式来生成一个pattern
        Pattern pattenStart = Pattern.compile(strStart, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattenStart.matcher(spannableString);
        if (matcher.find() && realStr.startsWith("#")) {
            getClickableSpan(context, spannableString, realStr, talkType, headStr);//部分文字点击事件
        }
        if (!StringUtil.isNull(headStr)) {
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), 0, headStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        String strP2 = "#[^#]+#";
        // 通过传入的正则表达式来生成一个pattern
        Pattern patten2 = Pattern.compile(strP2, Pattern.CASE_INSENSITIVE);

        dealCard(context, spannableString, sinaPatten, patten2, 0);
        getUrlClickText(context, spannableString);
        //at经纪人点击事件
        getAtBrokerCliclk(context, spannableString, atsList);
        return spannableString;
    }

    /**
     * 点赞人名字点击事件跳转
     */
    public static SpannableString getPraisePersonsText(Context context, String str, List<User> brosList) {
        SpannableString spannableString = new SpannableString(str);
        getUrlClickText(context, spannableString);
        //点赞经纪人点击事件
        getAtBrokerNotAtCliclk(context, spannableString, brosList);
        return spannableString;
    }


    /**
     * url点击跳转到相应的webview  activity
     * 评论内容   有表情  无#   有回复某人的需要处理
     *
     * @param context
     * @return
     */
    public static SpannableString getUrlClickText(Context context, SpannableString spannableString) {
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "(([hH]ttp[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealPatternUrlOnclick(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spannableString;
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则将内容变色
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private static void dealPatternUrlOnclick(Context context,
                                              SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end <= spannableString.length()) {
                getClickUrlActivity(context, spannableString, matcher.start(), end);
                //如果整个字符串还未验证完，则继续。。。。。。
                dealPatternUrlOnclick(context, spannableString, patten, end);
            }
            break;
        }
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替  以及##内容处理
     * Card  有表情  有##处理
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private static void dealCard(Context context,
                                 SpannableString spannableString, Pattern patten, Pattern patten2, int start) {
        try {
            FaceUtilBase.getInstace().dealExpression(context, spannableString, patten, 0);
            dealAnd(context, spannableString, patten2, 0);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
    }


    /**
     * 对spanableString进行正则判断，如果符合要求，则将内容变色
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private static void dealAnd(Context context,
                                SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealAnd(context, spannableString, patten, end);
            }
            break;
        }
    }

    /**
     * 一个SpanableString对象，通过传入的字符串,并进行正则判断
     * 经纪人名字点击事件且 蓝色处理  不是@的  只是名字
     */
    public static SpannableString getAtBrokerNotAtCliclk(final Context context, SpannableString spannableString, List<User> brosList) {
        if (brosList == null) {
            brosList = new ArrayList<>();
        }
        // 正则表达式比配字符串里是否含有经纪人名字
        for (final User brokerInfo : brosList) {
            String name = brokerInfo.getName().trim();
            int startIndex = spannableString.toString().indexOf(name);
            if (startIndex < 0) {
                continue;
            }
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = startIndex + brokerInfo.getName().length();
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), startIndex, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // TODO 跳转到用户详情页
//                    Intent intent = new Intent(context, BrokerDetailActivity.class);
//                    intent.putExtra("brokerId", brokerInfo.getId() + "");
//                    ActivityManager.getManager().goFoResult((Activity) context, intent, 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    // TODO Auto-generated method stub
                    ds.setUnderlineText(false); //是否有下划线
                }
            }, startIndex, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 一个SpanableString对象，通过传入的字符串,并进行正则判断
     * 经纪人名字点击事件且 蓝色处理
     */
    public static SpannableString getAtBrokerCliclk(final Context context, SpannableString spannableString, List<User> atsList) {
        if (atsList == null) {
            atsList = new ArrayList<>();
        }
        // 正则表达式比配字符串里是否含有经纪人名字
        for (User atUser : atsList) {
            // 通过传入的正则表达式来生成一个pattern
            Pattern sinaPatten = null;
            try {
                sinaPatten = Pattern.compile("@" + atUser.getName().trim(), Pattern.CASE_INSENSITIVE);
                dealAtBrokerTextChange(context, spannableString, sinaPatten, atUser, 0);
            } catch (Exception e) {
                continue;
            }
        }
        return spannableString;
    }

    /**
     * 对spanableString进行正则判断，将相应文字变色
     * 经纪人名字点击事件且 蓝色处理
     */
    private static void dealAtBrokerTextChange(Context context,
                                               SpannableString spannableString, Pattern patten, final User atUser, int start) {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getClickAtBrokers(context, spannableString, atUser, matcher.start(), end);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealAtBrokerTextChange(context, spannableString, patten, atUser, end);
            }
            break;
        }
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     * 普通文字蓝色处理
     */
    public static SpannableString getTextChange(final Context context, String str, String keyStr) {
        SpannableString spannableString = new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(keyStr, Pattern.CASE_INSENSITIVE);
        dealTextChange(context, spannableString, sinaPatten, 0);
        return spannableString;
    }

    /**
     * 对spanableString进行正则判断，将相应文字变色
     * @param context
     * @param patten
     * @param start
     * @throws Exception
     */
    private static void dealTextChange(Context context, SpannableString spannableString, Pattern patten, int start) {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue1)), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealTextChange(context, spannableString, patten, end);
            }
            break;
        }
    }

    /**
     * 点击url 跳转到url自制webview页
     * @param context
     * @param spanableInfo
     */
    public static void getClickUrlActivity(final Context context, final SpannableString spanableInfo, final int startIndex, final int endIndex) {
        spanableInfo.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO 跳转到WebView界面
//                Intent intent = new Intent(context, ActivityWebViewClub.class);
//                intent.putExtra("urlStr", spanableInfo.toString().substring(startIndex, endIndex));
//                ActivityManager.getManager().goFoResult((Activity) context, intent, 1);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // TODO Auto-generated method stub
                ds.setUnderlineText(false); //是否有下划线
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 点击名字 跳转到经纪人详情页
     *
     * @param context
     * @param spanableInfo
     * @param brokerId
     */
    public static void getClickBrokerDetail(final Context context, SpannableString spanableInfo, int startIndex, int endIndex, final String brokerId) {
        spanableInfo.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO 跳转到用户信息详情页
//                Intent intent = new Intent(context, BrokerDetailActivity.class);
//                intent.putExtra("brokerId", brokerId);
//                ActivityManager.getManager().goFoResult((Activity) context, intent, 1);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // TODO Auto-generated method stub
                ds.setUnderlineText(false); //是否有下划线
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 点击名字 跳转到经纪人详情页  @的经纪人名字点击事件
     *
     * @param context
     * @param spanableInfo
     */
    public static void getClickAtBrokers(final Context context, SpannableString spanableInfo, final User User, int start, int end) {
        spanableInfo.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO
//                Intent intent = new Intent(context, BrokerDetailActivity.class);
//                intent.putExtra("brokerId", User.getBrokerId() + "");
//                ActivityManager.getManager().goFoResult((Activity) context, intent, 1);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // TODO Auto-generated method stub
                ds.setUnderlineText(false); //是否有下划线
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    /**
     * 部分文字设置点击事件  跳转到话题列表界面   MainTopicActivity.class
     *
     * @return
     */
    public static SpannableString getClickableSpan(final Context context, SpannableString spanableInfo, final String content, final String talkType, String headStr) {
        final String title = content.substring(0, content.substring(1, content.length()).indexOf("#") + 2);

        int start = headStr.length();
        int end = headStr.length() + title.length();

        spanableInfo.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO 跳转到话题详情界面
//                Intent intent = new Intent(context, MainTopicActivity.class);
//                intent.putExtra("isTopic", true);
//                intent.putExtra("title", title);
//                intent.putExtra("talkType", talkType);
//                ActivityManager.getManager().goFoResult((Activity) context, intent, 1);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // TODO Auto-generated method stub
                ds.setUnderlineText(false); //是否有下划线
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanableInfo;
    }
}
