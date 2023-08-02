package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chapter04.util.DateUtil;

public class ActResponseActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String response = "我吃过了，还是你来我家吃";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_response);
        TextView tv_request = findViewById(R.id.tv_request);
        // 从上一个页面传来的意图中获取快递包裹
        Bundle bundle = getIntent().getExtras();
        // 从包裹中取出名为request_time的字符串
        String request_time = bundle.getString("request_time");
        // 从包裹中取出名为request_content的字符串
        String request_content = bundle.getString("request_content");
        String desc = String.format("收到请求消息：\n请求时间为%s\n请求内容为%s", request_time, request_content);
        // 把请求消息的详情显示在文本视图上
        tv_request.setText(desc);

        findViewById(R.id.btn_response).setOnClickListener(this);
        TextView tv_response = findViewById(R.id.tv_response);
        tv_response.setText("待返回的消息：" + response);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(); // 创建一个新意图
        Bundle bundle = new Bundle(); // 创建一个新包裹
        // 往包裹存入名为response_time的字符串
        bundle.putString("response_time", DateUtil.getNowTime());
        // 往包裹存入名为response_content的字符串
        bundle.putString("response_content", response);
        intent.putExtras(bundle); // 把快递包裹塞给意图
        // 携带意图返回上一个页面。RESULT_OK表示处理成功
        setResult(Activity.RESULT_OK, intent);
        finish(); // 结束当前的活动页面
    }
}