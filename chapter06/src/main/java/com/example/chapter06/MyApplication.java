package com.example.chapter06;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.chapter06.database.BookDatabase;

import java.util.HashMap;

public class MyApplication extends Application {

    private static MyApplication mAPP; // 声明一个当前应用的静态实例

    // 声明一个公共的信息映射对象，可当作全局变量使用
    public HashMap<String, String> infoMap = new HashMap<>();
    // 声明一个书籍数据库对象
    private BookDatabase bookDatabase;

    // 利用单例模式获取当前应用的唯一实例
    public static MyApplication getInstance() {
        return mAPP;
    }

    //在App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        mAPP = this; // 在打开应用时对静态的应用实例赋值
        Log.d("ning", "MyApplication onCreate");

        // 构建书籍数据库的实例
        bookDatabase = Room.databaseBuilder(this, BookDatabase.class, "book")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库。如此一来原来的记录会丢失，
                // 故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                // 允许在主线程中操作数据库（Room默认不能在主线程中操作数据库）
                .allowMainThreadQueries()
                .build();
    }

    //在App终止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("ning", "onTerminate");
    }


    //在配置改变时调用，例如从竖屏变为横屏。
    //在配置改变时调用，例如从竖屏变为横屏。
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("ning", "onConfigurationChanged");
    }

    // 获取书籍数据库的实例
    public BookDatabase getBookDB() {
        return bookDatabase;
    }

}
