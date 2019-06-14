package com.control.yourself.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.control.yourself.greendao.Entity.UserBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistActivity extends Activity implements View.OnClickListener {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }

    private void remember(UserBean user) {
        sharedPreferences = getSharedPreferences("StudentInfo", MODE_PRIVATE);
        editor =sharedPreferences.edit();
        //使用editor保存数据
        editor.putString("name", user.getName());
        editor.putString("password", user.getPwd());
        //注意一定要提交数据，此步骤容易忘记
        editor.commit();
        MyApplication.setUserItem(user);

    }
    private void regist() {
        if (TextUtils.isEmpty(etName.getText().toString())
                || TextUtils.isEmpty(etPassword.getText().toString())
                || TextUtils.isEmpty(etPassword2.getText().toString())
                ) {
            Toast.makeText(this, "输入姓名和学号", Toast.LENGTH_LONG).show();
            return;
        }
        if (!TextUtils.equals(etPassword.getText().toString(),etPassword2.getText().toString())){
            Toast.makeText(this, "两次输入密码不相同，请重新确认", Toast.LENGTH_LONG).show();
            return;
        }

        UserBean user = DbUtils.queryUserByName(etName.getText().toString());
        if (user != null) {
            Toast.makeText(this, "当前用户已存在", Toast.LENGTH_LONG).show();
            return;
        } else {
            user =new UserBean(etName.getText().toString(),etPassword.getText().toString());
            remember(user);
            DbUtils.regist(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();

        }
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                regist();
                break;
            case R.id.btn_login:
                startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }
}