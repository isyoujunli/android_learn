package com.example.chapter07_client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class SendMmsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_appendix;
    private ActivityResultLauncher<Intent> mResultLauncher;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mms);
        iv_appendix = findViewById(R.id.iv_appendix);
        iv_appendix.setOnClickListener(this);
        // 跳转到系统相册，选择图片，并返回
        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    // 获得选中图片的路径对象
                    // content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fting2.jpg
                    picUri = intent.getData();
                    if (picUri != null) {
                        // ImageView 显示刚刚选中的图片
                        iv_appendix.setImageURI(picUri);
                        Log.d("ning", "picUri:" + picUri.toString());
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_appendix:
                // 跳转到系统相册，选择图片，并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 设置内容类型为图片类型
                intent.setType("image/*");
                // 打开系统相册，并等待图片选择结果
                mResultLauncher.launch(intent);

                break;

        }
    }
}