package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.util.DateUtil;

public class ButtonEnableActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_test;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_enable);
        Button btn_enable = findViewById(R.id.btn_enable);
        Button btn_disenable = findViewById(R.id.btn_disenable);
        // 获取名叫btn_test的按钮控件
        btn_test = findViewById(R.id.btn_test);

        tv_result = findViewById(R.id.tv_result);

        // 因为按钮控件的setOnClickListener方法来源于View基类，
        // 所以也可对findViewById得到的视图直接设置点击监听器
        btn_enable.setOnClickListener(this);
        btn_disenable.setOnClickListener(this);
        // 设置btn_test的点击监听器
        btn_test.setOnClickListener(this);


    }

    // 点击事件的处理方法
    @Override
    public void onClick(View view) {
        // 由于多个控件都把点击监听器设置到了当前页面，因此公共的onClick方法内部需要区分来自于哪个按钮
        switch (view.getId()) {
            // 点击了按钮“启用测试按钮”
            case R.id.btn_enable:
                // 启用当前控件
                btn_test.setEnabled(true);
                // 设置按钮的文字颜色
                btn_test.setTextColor(Color.BLACK);
                break;
            case R.id.btn_disenable:
                // 禁用当前控件
                btn_test.setEnabled(false);
                // 设置按钮的文字颜色
                btn_test.setTextColor(Color.GRAY);
                break;
            case R.id.btn_test:
                // 来自于按钮btn_click_public
                String desc = String.format("%s 你点击按钮: %s", DateUtil.getNowTime(), ((Button) view).getText());
                // 设置文本视图的文本内容
                tv_result.setText(desc);
        }
    }
}