package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.chapter05.util.ViewUtil;

public class EditHideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hide);

        EditText et_phone = findViewById(R.id.et_phone);
        // 从布局文件中获取名为et_password的密码编辑框
        EditText et_password = findViewById(R.id.et_password);

        et_phone.addTextChangedListener(new HideTextWather(et_phone, 11));
        // 给密码编辑框添加文本变化监听器
        et_password.addTextChangedListener(new HideTextWather(et_password, 6));

    }

    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWather implements TextWatcher {
        private EditText mView;// 声明一个编辑框对象
        private int mMaxLength;// 声明一个最大长度变量

        public HideTextWather(EditText v, int maxLength) {
            this.mView = v;
            this.mMaxLength = maxLength;
        }

        // 在编辑框的输入文本变化前触发
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        // 在编辑框的输入文本变化时触发
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        // 在编辑框的输入文本变化后触发
        @Override
        public void afterTextChanged(Editable editable) {
            // 获得已输入的文本字符串
            String str = editable.toString();
            // 输入文本达到11位（如手机号码），或者达到6位（如登录密码）时关闭输入法
            if (str.length() == mMaxLength) {
                // 隐藏输入法软键盘
                ViewUtil.hideOneInputMethod(EditHideActivity.this, mView);
            }
        }
    }
}