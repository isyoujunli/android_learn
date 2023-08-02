package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ActionUriActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_uri);
        findViewById(R.id.btn_dial).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
        findViewById(R.id.btn_my).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();   // 创建一个新意图
        String phoneNo = "12345";
        switch (view.getId()) {

            case R.id.btn_dial:
                intent.setAction(Intent.ACTION_DIAL);   // 设置意图动作为准备拨号
                Uri uri = Uri.parse("tel:" + phoneNo);  // 声明一个拨号的Uri
                intent.setData(uri);    // 设置意图前往的路径
                startActivity(intent);  // 启动意图通往的活动页面
                break;

            case R.id.btn_sms:
                intent.setAction(Intent.ACTION_SENDTO);   // 设置意图动作为准备拨号
                Uri uri2 = Uri.parse("smsto:" + phoneNo);  // 声明一个拨号的Uri
                intent.setData(uri2);    // 设置意图前往的路径
                startActivity(intent);  // 启动意图通往的活动页面
                break;

            case R.id.btn_my:
                intent.setAction("android.intent.action.STARRY");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
                break;
        }
    }
}