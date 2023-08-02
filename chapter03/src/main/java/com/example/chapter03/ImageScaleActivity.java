package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageScaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);
        // 从布局文件中获取名为iv_scale的图像视图
        ImageView iv_scale = findViewById(R.id.iv_scale);
        // 设置图像视图的图片资源
        iv_scale.setImageResource(R.drawable.yaomeng);
        // 将缩放类型设置为“保持宽高比例，缩放图片使其位于视图中间”
        iv_scale.setScaleType(ImageView.ScaleType.CENTER);

    }
}