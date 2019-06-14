package com.control.yourself.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.control.yourself.MyApplication;
import com.control.yourself.db.DbUtils;
import com.control.yourself.greendao.Entity.RemindBean;
import com.control.yourself.greendao.Entity.UserBean;
import com.control.yourself.service.AlarmReceiver;

import java.util.List;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText et_name, et_password;
    private Button btn_login, btn_register;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String name="";
    private String number="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = getSharedPreferences("StudentInfo", MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        initView();
        initClick();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        name=sharedPreferences.getString("name","");
        number=sharedPreferences.getString("password","");
        et_name.setText(name);
        et_password.setText(number);

    }

    private void initClick() {
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void remember(UserBean user) {
        editor =sharedPreferences.edit();
        //使用editor保存数据
        editor.putString("name", user.getName());
        editor.putString("password", user.getPwd());
        //注意一定要提交数据，此步骤容易忘记
        editor.commit();
        MyApplication.setUserItem(user);

    }

    private void login() {
        if (TextUtils.isEmpty(et_name.getText().toString())
                ||TextUtils.isEmpty(et_password.getText().toString())){
            Toast.makeText(this,"输入姓名和密码",Toast.LENGTH_LONG).show();
            return;
        }

        UserBean user = DbUtils.queryUserByName(et_name.getText().toString());
        if (user ==null){
            Toast.makeText(this,"用户不存在请先注册",Toast.LENGTH_LONG).show();
            return;
        }else if (TextUtils.equals(user.getPwd(),et_password.getText().toString())) {
            remember(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"姓名或学号错误，请重新输入",Toast.LENGTH_LONG).show();
        }
        initAlarm();
    }

    private void initAlarm() {
        List<RemindBean>  remindBeans =DbUtils.queryMessage(MyApplication.getUserItem().getName());
        for (RemindBean remindBean :remindBeans) {
            Intent intent = new Intent(this, AlarmReceiver.class);    //创建Intent对象
            // intent.setFlags(Integer.parseInt(id));//作为取消时候的标识
            intent.putExtra("msg",remindBean.getTitle());
            PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);    //创建PendingIntent
            //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
            MyApplication.getAlarmManager().set(AlarmManager.RTC_WAKEUP, remindBean.getRemindTime(), pi);        //设置闹钟，当前时间就唤醒

        }
    }
}