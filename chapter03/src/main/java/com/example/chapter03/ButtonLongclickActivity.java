package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.util.DateUtil;

public class ButtonLongclickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_longclick);
        TextView tv_result = findViewById(R.id.tv_result);
        Button btn_long_click = findViewById(R.id.btn_long_click);
        btn_long_click.setOnLongClickListener(view -> {
            // 来自于按钮btn_click_public
            String desc = String.format("%s 你点击按钮: %s", DateUtil.getNowTime(), ((Button) view).getText());
            // 设置文本视图的文本内容
            tv_result.setText(desc);
            return true;
        });
    }
}