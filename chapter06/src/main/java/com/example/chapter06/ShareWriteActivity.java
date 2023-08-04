package com.example.chapter06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class ShareWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_write);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);

        // 从share.xml获取共享参数实例
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        reload();
    }

    private void reload() {
        //从共享参数获取名为name的字符串
        String name = preferences.getString("name", null);
        if (name != null) {
            et_name.setText(name);
        }

        int age = preferences.getInt("age", 0);
        if (age != 0) {
            et_age.setText(String.valueOf(age));
        }

        float height = preferences.getFloat("height", 0f);
        if (height != 0f) {
            et_height.setText(String.valueOf(height));
        }

        float weight = preferences.getFloat("weight", 0f);
        if (weight != 0f) {
            et_weight.setText(String.valueOf(weight));
        }

        boolean married = preferences.getBoolean("married", false);
        ck_married.setChecked(married);
    }

    @Override
    public void onClick(View view) {

        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        // 获得编辑器的对象
        SharedPreferences.Editor editor = preferences.edit();
        // 添加一个名为name的字符串参数
        editor.putString("name", name);
        // 添加一个名为age的整型参数
        editor.putInt("age", Integer.parseInt(age));
        editor.putFloat("height", Float.parseFloat(height));
        editor.putFloat("weight", Float.parseFloat(weight));
        // 添加一个名为married的布尔型参数
        editor.putBoolean("married", ck_married.isChecked());
        // 提交编辑器中的修改
        editor.commit();

    }
}