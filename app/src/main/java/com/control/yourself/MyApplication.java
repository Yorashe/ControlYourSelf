package com.control.yourself;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.control.yourself.greendao.Entity.UserBean;
import com.control.yourself.greendao.gen.DaoMaster;
import com.control.yourself.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Yorashe on 19-6-13.
 */

public class MyApplication extends Application {

    // 自己的全局应用
    private static MyApplication   mAppApplication;

    /**
     * 用户实体
     */
    public static UserBean userItem;

    private static DaoMaster mDaoMaster;
    public static DaoSession mDaoSession;
    @SuppressLint("StaticFieldLeak")
    private static DaoMaster.OpenHelper mHelper;
    private static Database db;
    private static AlarmManager alarmManager = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication=this;
        initGreenDao();
        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }


    public static UserBean getUserItem() {
        return userItem;
    }



    public static void setUserItem(UserBean userItem) {
        MyApplication.userItem = userItem;
    }

    // MyApplication 实例
    public static MyApplication getInstance() {
        if (null == mAppApplication) {
            synchronized (MyApplication.class) {
                if (null == mAppApplication) {
                    mAppApplication = new MyApplication();
                }
            }
        }
        return mAppApplication;

    }

    public static AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public static void setAlarmManager(AlarmManager alarmManager) {
        MyApplication.alarmManager = alarmManager;
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "userbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

//    public static DaoSession getDaoSession() {
//        if (null == mDaoSession) {
//            if (null == mHelper)
//                mHelper = new DaoMaster.DevOpenHelper(mAppApplication, "account_database.db", null);
//            if (null == db)
//                db = mHelper.getWritableDb();
//            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//            if (null == mDaoMaster)
//                mDaoMaster = new DaoMaster(db);
//            if (null == mDaoSession)
//                mDaoSession = mDaoMaster.newSession();
//        }
//        return mDaoSession;
//    }

    public Database getDb() {
        return db;
    }
}
