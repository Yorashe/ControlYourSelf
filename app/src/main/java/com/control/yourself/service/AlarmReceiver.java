package com.control.yourself.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.control.yourself.activity.MainActivity;
import com.control.yourself.activity.R;

/**
 * Created by Administrator on 2019/6/12.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager manager;
    private static final int NOTIFICATION_ID_1 = 0x00113;
    private String title;
    private String content = "提醒的时间到啦，快看看你要做的事...";

    @Override
    public void onReceive(Context context, Intent intent) {
//此处接收闹钟时间发送过来的广播信息，为了方便设置提醒内容
        String msg = intent.getStringExtra("msg");
        Log.e("AlarmReceiver:", msg);
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }
        Intent clockIntent = new Intent(context, MainActivity.class);
        clockIntent.putExtra("msg", msg);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
    }

    /**
     * 发送通知
     */
    private void showNormal(Context context) {
        Intent intent = new Intent(context, MainActivity.class);//这里是点击Notification 跳转的界面，可以自己选择
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)     //设置通知图标。
                .setTicker(content)        //通知时在状态栏显示的通知内容
                .setContentInfo("便签提醒")        //内容信息
                .setContentTitle(title)        //设置通知标题。
                .setContentText(content)        //设置通知内容。
                .setAutoCancel(true)                //点击通知后通知消失
                .setDefaults(Notification.DEFAULT_ALL)        //设置系统默认的通知音乐、振动、LED等。
                .setContentIntent(pi)
                .build();
        manager.notify(NOTIFICATION_ID_1, notification);
    }

    public static void setAlarmTime(Context context, long timeInMillis, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int interval = (int) intent.getLongExtra("intervalMillis", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        }
        Toast.makeText(context, "闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户

    }
}
