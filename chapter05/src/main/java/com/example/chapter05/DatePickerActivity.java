package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.intellij.lang.annotations.JdkConstants;

import java.util.Calendar;

// 该页面类实现了接口OnDateSetListener，意味着要重写日期监听器的onDateSet方法
public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private DatePicker dp_date;
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_date).setOnClickListener(this);
        tv_date = findViewById(R.id.tv_date);
        dp_date = findViewById(R.id.dp_date);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_ok:
                String desc = String.format("您选择的日期是：%d年%d月%d日",
                        dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth());
                tv_date.setText(desc);
                break;
            case R.id.btn_date:
                // 获取日历的一个实例，里面包含了当前的年月日
                Calendar calendar = Calendar.getInstance();
                // 构建一个日期对话框，该对话框已经集成了日期选择器。
                // DatePickerDialog的第二个构造参数指定了日期监听器
                calendar.get(Calendar.YEAR);
                calendar.get(Calendar.MONTH);
                calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(this, this,
                        2090, 5, 11);
                dialog.show();
                break;
        }
    }

    // 一旦点击日期对话框上的确定按钮，就会触发监听器的onDateSet方法
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int getDayOfMonth) {
        String desc = String.format("您选择的日期是：%d年%d月%d日",
                i, i1, getDayOfMonth);
        tv_date.setText(desc);
    }
}