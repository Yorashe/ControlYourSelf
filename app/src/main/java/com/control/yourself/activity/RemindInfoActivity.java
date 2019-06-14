package com.control.yourself.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.control.yourself.MyApplication;
import com.control.yourself.db.DbUtils;
import com.control.yourself.greendao.Entity.RemindBean;
import com.control.yourself.service.AlarmReceiver;
import com.control.yourself.widget.MyToolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yorashe on 19-6-13.
 */

public class RemindInfoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.delete)
    TextView delete;
    @BindView(R.id.myToolbar)
    MyToolbar myToolbar;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private long remindTime;
    private RemindBean remindBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remind_lay);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbUtils.deleteMessage(remindBean);
                finish();
                Toast.makeText(RemindInfoActivity.this, "删除成功成功", Toast.LENGTH_LONG).show();//提示用户

            }
        });
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
        remindBean= (RemindBean) getIntent().getSerializableExtra("data");
        content.setText(remindBean.getTitle());
        time.setText(dateFormat.format(new Date(remindBean.getRemindTime())));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(RemindInfoActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");

            }
        });

    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        TimePickerDialog.newInstance(RemindInfoActivity.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        remindTime = calendar.getTime().getTime();
        time.setText(dateFormat.format(new Date(remindTime)));
        remindBean.setRemindTime(calendar.getTimeInMillis());
        DbUtils.setTime(remindBean);
        Intent intent = new Intent(RemindInfoActivity.this, AlarmReceiver.class);    //创建Intent对象
        // intent.setFlags(Integer.parseInt(id));//作为取消时候的标识
        intent.putExtra("msg",remindBean.getTitle());
        PendingIntent pi = PendingIntent.getBroadcast(RemindInfoActivity.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);    //创建PendingIntent

        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
        MyApplication.getAlarmManager().set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);        //设置闹钟，当前时间就唤醒
        Toast.makeText(RemindInfoActivity.this, "闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户
    }
}
