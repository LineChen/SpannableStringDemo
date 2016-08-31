package com.beiing.spannablestringdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.widget.TextView;

import com.beiing.spannablestringdemo.utils.SpanUtils;

public class MainActivity extends AppCompatActivity {

    TextView tvColoredKeywd;

    TextView tvTopic;

    TextView tvTestAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        testColoredKeywd();

        testTopic();

    }

    /**
     * 关键字变色
     */
    private void testColoredKeywd() {
        String string = "Android一词的本义指“机器人”，同时也是Google于2007年11月5日,Android logo相关图片,Android logo相关图片(36张)\n";
        SpannableString cardText = null;
        try {
            cardText = SpanUtils.getKeyWordSpan(getResources().getColor(R.color.md_green_600), string, "Android");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvColoredKeywd.setText(cardText);
    }

    /**
     * 测试话题
     */
    private void testTopic() {
        String topic = "#舌尖上的大连#四种金牌烤芝士吃法爱吃芝士的盆友不要错过了~L秒拍视频#舌尖上的大连#";
        SpannableString cardText = null;
        try {
            cardText = SpanUtils.getKeyWordSpan(Color.BLUE, topic, SpanUtils.PatternString.TOPIC_PATTERN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvTopic.setText(cardText);
    }

    private void initViews() {
        tvColoredKeywd = (TextView) findViewById(R.id.tv_keyword_colored);
        tvTopic = (TextView) findViewById(R.id.tv_topic);
        tvTestAt = (TextView) findViewById(R.id.tv_test_at);
    }


}























