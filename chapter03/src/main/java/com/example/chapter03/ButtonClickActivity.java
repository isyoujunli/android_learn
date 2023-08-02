package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.util.DateUtil;

public class ButtonClickActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);
        tv_result = findViewById(R.id.tv_result);
        // 从布局文件中获取名为btn_click_single的按钮控件
        Button btn_click_single = findViewById(R.id.btn_click_single);
        // 给btn_click_single设置点击监听器，一旦用户点击按钮，就触发监听器的onClick方法
        btn_click_single.setOnClickListener(new MyOnClickListener(tv_result));

        Button btn_click_public = findViewById(R.id.btn_click_public);
        btn_click_public.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click_public) {
            String desc = String.format("%s 你点击按钮: %s", DateUtil.getNowTime(), ((Button) v).getText());
            tv_result.setText(desc);
        }
    }

    // 定义一个点击监听器，它实现了接口View.OnClickListener
    static class MyOnClickListener implements View.OnClickListener {
        private final TextView tv_result;

        public MyOnClickListener(TextView tv_result) {
            this.tv_result = tv_result;
        }

        // 点击事件的处理方法
        @Override
        public void onClick(View view) {
            // 来自于按钮btn_click_public
            String desc = String.format("%s 你点击按钮: %s", DateUtil.getNowTime(), ((Button) view).getText());
            // 设置文本视图的文本内容
            tv_result.setText(desc);
        }
    }
}