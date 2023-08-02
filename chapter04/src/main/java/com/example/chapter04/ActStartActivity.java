package com.example.chapter04;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.icu.number.CompactNotation;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// 活动类直接实现点击监听器的接口View.OnClickListener
public class ActStartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_start);
        Button btn_act_next = findViewById(R.id.btn_act_next);
        // setOnClickListener来自于View，故而允许直接给View对象注册点击监听器
        btn_act_next.setOnClickListener(this);
    }

    @Override// 点击事件的处理方法
    public void onClick(View view) {
        // 从当前页面跳到指定的新页面
        //startActivity(new Intent(ActStartActivity.this,
        //startActivity(new Intent(this, ActFinishActivity.class));
        //1.Intent intent = new Intent(this,ActFinishActivity.class);

        Intent intent = new Intent();
        //2.intent.setClass(this,ActFinishActivity.class)
        ComponentName componentName = new ComponentName("","");
        intent.setComponent(componentName);

        startActivity(intent);


    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "ActStartActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "ActStartActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ActStartActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "ActStartActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ActStartActivity onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "ActStartActivity onRestart");
    }

}