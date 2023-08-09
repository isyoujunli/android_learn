package com.example.online_shopping_cart.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

    public static SharedUtil mUtil;
    //    preferences: 这是一个SharedPreferences对象，用于进行共享参数的读写操作。
    private SharedPreferences preferences;

    //getInstance(Context ctx): 这是一个静态方法，用于获取SharedUtil的单例实例。
    // 它接收一个Context对象作为参数，用于获取共享参数实例。
    public static SharedUtil getInstance(Context context) {
        if (mUtil == null) {
            mUtil = new SharedUtil();
            mUtil.preferences = context.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        }
        return mUtil;
    }

    //    writeBoolean(String key, boolean value): 这是一个公共方法，
    //    用于将布尔类型的值写入共享参数。它接收一个键（key）和一个布尔值（value）作为参数，并将其写入共享参数。
    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //readBoolean(String key, boolean defaultValue): 这是一个公共方法，
    // 用于从共享参数中读取布尔类型的值。它接收一个键（key）和一个默认值（defaultValue）作为参数，并返回共享参数中对应键的布尔值。如果共享参数中不存在对应键，则返回默认值
    public boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

}
