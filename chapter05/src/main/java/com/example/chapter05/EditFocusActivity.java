package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditFocusActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_focus);

        et_phone = findViewById(R.id.et_phone);
        // 从布局文件中获取名为et_password的编辑框
        EditText et_password = findViewById(R.id.et_password);
        // 给编辑框注册一个焦点变化监听器，一旦焦点发生变化，就触发监听器的onFocusChange方法
        et_password.setOnFocusChangeListener(this);

    }

    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    // 为什么光标进入事件不选onClick？因为要点两下才会触发onClick动作（第一下是切换焦点动作）
    @Override
    public void onFocusChange(View view, boolean b) {
        // 判断密码编辑框是否获得焦点。hasFocus为true表示获得焦点，为false表示失去焦点
        if (b) {
        }

    }
}