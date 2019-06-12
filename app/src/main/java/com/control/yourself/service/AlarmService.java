package com.control.yourself.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.control.yourself.db.AppSQLiteData;

/**
 * Created by Administrator on 2019/6/12.
 */
public class AlarmService extends Service {
    private AlarmManager am;
    private PendingIntent pi;
    private Long time;
    private String title;
    private String content;
    private AppSQLiteData mRemindSQL;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getAlarmTime();
        return START_REDELIVER_INTENT;    } //这里为了提高优先级，选择START_REDELIVER_INTENT 没那么容易被内存清理时杀死
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void getAlarmTime() {
        mRemindSQL= new AppSQLiteData(this,"Remind.db", null, 1);
        SQLiteDatabase db = mRemindSQL.getWritableDatabase();
        Cursor cursor = db.query("Remind_data", null, null, null, null, null, null);
        if (cursor.moveToFirst()) { //遍历数据库的表，拿出一条，选择最近的时间赋值，作为第一条提醒数据。
            time = cursor.getLong(cursor.getColumnIndex("remindTime"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            content = cursor.getString(cursor.getColumnIndex("content"));
            do {   if (time > cursor.getLong(cursor.getColumnIndex("remindTime"))) {
                time = cursor.getLong(cursor.getColumnIndex("remindTime"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                content = cursor.getString(cursor.getColumnIndex("content"));
            }
            } while (cursor.moveToNext());
        } else {
            time = null;
        }
        db.delete("Remind_data", "remindTime=?", new String[]{String.valueOf(time)});      //删除已经发送提醒的时间
        cursor.close();     //记得关闭游标，防止内存泄漏
        Intent startNotification = new Intent(this, AlarmReceiver.class);   //这里启动的广播，下一步会教大家设置
        startNotification.putExtra("title", title);
        startNotification.putExtra("content", content);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);   //这里是系统闹钟的对象
        pi = PendingIntent.getBroadcast(this, 0, startNotification, PendingIntent.FLAG_UPDATE_CURRENT);     //设置事件
        if (time != null) {
            am.set(AlarmManager.RTC_WAKEUP, time, pi);    //提交事件，发送给 广播接收器
        } else {
            //当提醒时间为空的时候，关闭服务，下次添加提醒时再开启
            stopService(new Intent(this, AlarmService.class));
        }
    }}
