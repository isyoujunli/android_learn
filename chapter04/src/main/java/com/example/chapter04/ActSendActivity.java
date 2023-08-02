package com.example.chapter04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter04.util.DateUtil;

public class ActSendActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_send);
        tv_send = findViewById(R.id.tv_send);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 创建一个意图对象，准备跳到指定的活动页面
        Intent intent = new Intent(this, ActReceiveActivity.class);
        // 创建一个新包裹
        Bundle bundle = new Bundle();
        // 往包裹存入名为request_time的字符串
        bundle.putString("request_time", DateUtil.getNowTime());
        // 往包裹存入名为request_content的字符串
        bundle.putString("request_content", tv_send.getText().toString());
        // 把快递包裹塞给意图
        intent.putExtras(bundle);
        // 跳转到意图指定的活动页面
        startActivity(intent);
    }
}