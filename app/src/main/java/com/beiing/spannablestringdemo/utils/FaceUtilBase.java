package com.beiing.spannablestringdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.beiing.spannablestringdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/6/11.
 */
public class FaceUtilBase {
    private static boolean isFirst = true;//是否是第一次加载
    /**
     * 每一页表情的个数
     */
    private int pageSize = 20;

    private static FaceUtilBase mFaceConversionUtil;

    /**
     * 保存于内存中的表情HashMap
     */
    private HashMap<String, String> emojiMap = new HashMap<String, String>();

    private FaceUtilBase() {

    }

    public static FaceUtilBase getInstace() {
        if (mFaceConversionUtil == null) {
            mFaceConversionUtil = new FaceUtilBase();
        }
        return mFaceConversionUtil;
    }


    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     * 表情
     * @param context
     * @param str
     * @return
     */
    public SpannableString getExpressionString(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     *  点赞   消息里的[点赞]进行特殊处理
     * @param context
     * @param str
     * @return
     */
    public SpannableString getPraiseExpStr(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        Drawable emoji = context.getResources().getDrawable(R.drawable.expression_1);
        int w = (int) (emoji.getIntrinsicWidth());
        int h = (int) (emoji.getIntrinsicHeight());
        emoji.setBounds(0, 0, w, h);
        // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
        ImageSpan imageSpan = new ImageSpan(emoji);
        // 计算该图片名字的长度，也就是要替换的字符串的长度
        // 将该图片替换字符串中规定的位置中
        spannableString.setSpan(imageSpan, 0, str.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    public void dealExpression(Context context,
                               SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            String value = emojiMap.get(key);
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            int resId = context.getResources().getIdentifier(value, "drawable",
                    context.getPackageName());
            // 通过上面匹配得到的字符串来生成图片资源id
            // Field field=R.drawable.class.getDeclaredField(value);
            // int resId=Integer.parseInt(field.get(null).toString());
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(
                        context.getResources(), resId);
                //bitmap = Bitmap.createScaledBitmap(bitmap,context.getResources().getDimensionPixelSize(R.dimen.p_emoji_size), context.getResources().getDimensionPixelSize(R.dimen.p_emoji_size), true);
                // bitmap = Bitmap.createScaledBitmap(bitmap,48, 48, true);
                Drawable emoji = context.getResources().getDrawable(resId);
                int w = (int) (emoji.getIntrinsicWidth() * 0.40);
                int h = (int) (emoji.getIntrinsicHeight() * 0.40);
                emoji.setBounds(0, 0, w, h);
                bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                ImageSpan imageSpan = new ImageSpan(emoji);
                // 计算该图片名字的长度，也就是要替换的字符串的长度
                int end = matcher.start() + key.length();
                // 将该图片替换字符串中规定的位置中
                spannableString.setSpan(imageSpan, matcher.start(), end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                if (end < spannableString.length()) {
                    // 如果整个字符串还未验证完，则继续。。
                    dealExpression(context, spannableString, patten, end);
                }
                break;
            }
        }
    }




    /**
     * 添加表情
     *
     * @param context
     * @param imgId
     * @param spannableString
     * @return
     */
    public SpannableString addFace(Context context, int imgId,
                                   String spannableString) {
        if (TextUtils.isEmpty(spannableString)) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                imgId);
        bitmap = Bitmap.createScaledBitmap(bitmap, 48, 48, true);
        ImageSpan imageSpan = new ImageSpan(context, bitmap);
        SpannableString spannable = new SpannableString(spannableString);
        spannable.setSpan(imageSpan, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

}
