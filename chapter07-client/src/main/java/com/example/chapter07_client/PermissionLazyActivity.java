package com.example.chapter07_client;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_client.util.PermissionUtil;
import com.example.chapter07_client.util.ToastUtil;

public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {
    //定义通讯录的读写权限
    private static final String[] PERMISSIONS_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS,//
            Manifest.permission.WRITE_CONTACTS
    };
    //定义通讯录的读写权限
    private static final String[] PERMISSIONS_SMS = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS
    };
    //判断是谁的权限，通讯录或者短信
    public static final int REQUEST_CODE_CONTACTS = 1;
    public static final int REQUEST_CODE_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contact:
                PermissionUtil.checkPermission(this, PERMISSIONS_CONTACTS, REQUEST_CODE_CONTACTS);
                break;
            case R.id.btn_sms:
                PermissionUtil.checkPermission(this, PERMISSIONS_SMS, REQUEST_CODE_SMS);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CONTACTS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "通讯录权限获取成功");
                } else {
                    ToastUtil.show(this, "获取通讯录读写权限失败！");
                    jumpTOSettings();
                }
                break;
            case REQUEST_CODE_SMS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "收发短信权限获取成功");
                } else {
                    ToastUtil.show(this, "获取收发短信权限失败！");
                    jumpTOSettings();
                }
                break;
        }
    }

    // 跳转到应用设置界面n
    private void  jumpTOSettings(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package",getPackageName(),null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}