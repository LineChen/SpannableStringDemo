package com.beiing.spannablestringdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.beiing.spannablestringdemo.bean.Topic;
import com.beiing.spannablestringdemo.bean.User;
import com.beiing.spannablestringdemo.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;

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

        textAtUsers();
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
        String topic = "#舌尖上的大连#四种金牌烤芝士吃法爱吃芝士的盆友不要错过了~L秒拍视频#舌尖上的大连#\n";
        SpannableString topicText = null;
        try {
            topicText = SpanUtils.getTopicSpan(Color.BLUE, topic, true, new SpanUtils.SpanClickListener<Topic>() {
                @Override
                public void onSpanClick(Topic t) {
                    Toast.makeText(MainActivity.this, "点击话题:" + t.toString() , Toast.LENGTH_SHORT).show();
                }
            }, new Topic(1, "舌尖上的大连"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvTopic.setText(topicText);
        //如果想实现点击，必须要设置这个
        tvTopic.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 测试@好友
     */
    private void textAtUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User(1, "好友1"));
        users.add(new User(2, "好友2"));
        StringBuilder sb = new StringBuilder("快来看看啊");
        for (User u : users) {
            sb.append("@").append(u.getName());
        }
        sb.append("\n");
        try {
            SpannableString atSpan = SpanUtils.getAtUserSpan(Color.BLUE, sb.toString(), true, new SpanUtils.SpanClickListener<User>() {
                @Override
                public void onSpanClick(User user) {
                    Toast.makeText(MainActivity.this, "@好友:" + user.toString(), Toast.LENGTH_SHORT).show();
                }
            }, users);

            tvTestAt.setText(atSpan);
            tvTestAt.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initViews() {
        tvColoredKeywd = (TextView) findViewById(R.id.tv_keyword_colored);
        tvTopic = (TextView) findViewById(R.id.tv_topic);
        tvTestAt = (TextView) findViewById(R.id.tv_test_at);
    }


}























