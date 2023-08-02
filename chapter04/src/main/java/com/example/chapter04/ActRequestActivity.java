package com.example.chapter04;

import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chapter04.util.DateUtil;

import android.widget.TextView;


public class ActRequestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_response;
    private static final String request = "你吃饭了吗？来我家吃吧";
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_request);

        TextView tv_request = findViewById(R.id.tv_request);
        tv_request.setText("待发送的消息为：" + request);


        tv_response = findViewById(R.id.tv_response);
        findViewById(R.id.btn_request).setOnClickListener(this);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    // 从包裹中取出名为request_time的字符串
                    String response_time = bundle.getString("response_time");
                    // 从包裹中取出名为request_content的字符串
                    String response_content = bundle.getString("response_content");
                    String desc = String.format("收到返回消息：\n应答时间为%s\n应答内容为%s", response_time, response_content);
                    // 把请求消息的详情显示在文本视图上
                    tv_response.setText(desc);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        // 创建一个意图对象，准备跳到指定的活动页面
        Intent intent = new Intent(this, ActResponseActivity.class);
        // 创建一个新包裹
        Bundle bundle = new Bundle();
        // 往包裹存入名为request_time的字符串
        bundle.putString("request_time", DateUtil.getNowTime());
        // 往包裹存入名为request_content的字符串
        bundle.putString("request_content", request);
        // 把快递包裹塞给意图
        intent.putExtras(bundle);
        // 跳转到意图指定的活动页面
        register.launch(intent);
    }
}