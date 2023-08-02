package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

// 该页面实现了接口OnCheckedChangeListener，意味着要重写选中监听器的onCheckedChanged方法
public class RadioHorizontalActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView tv_result; // 声明一个文本视图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_horizontal);
        // 从布局文件中获取名叫tv_sex的文本视图
        tv_result = findViewById(R.id.tv_result);
        // 从布局文件中获取名叫rg_sex的单选组
        RadioGroup rg_gender = findViewById(R.id.rg_gender);
        // 设置单选监听器，一旦点击组内的单选按钮，就触发监听器的onCheckedChanged方法
        rg_gender.setOnCheckedChangeListener(this);
    }

    // 在用户点击组内的单选按钮时触发
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_male:
                tv_result.setText("哇哦，你是个帅气的男孩");
                break;
            case R.id.rb_famale:
                tv_result.setText("哇哦，你是个漂亮的女孩");
                break;
        }
    }
}