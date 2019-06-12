package com.control.yourself.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;



public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText et_name, et_password;
    private Button btn_login, btn_register;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = getSharedPreferences("StudentInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //使用editor保存数据
        editor.putString("name", "罗广政");
        editor.putString("number", "1730627");
        //注意一定要提交数据，此步骤容易忘记
        editor.commit();
        setContentView(R.layout.activity_login);
        initView();
        initClick();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

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
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void remember() {
        //获取键值数据

        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        //使用editor保存数据
        editor.putString("name", name);
        editor.putString("password", password);
        //注意一定要提交数据，此步骤容易忘记
        editor.commit();
    }

    private void login() {
        String name = sharedPreferences.getString("name", null);
        String number = sharedPreferences.getString("number", null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
//        if (et_name.getText().toString().equals(name) && et_number.getText().toString().equals(number)) {
//            Intent intent = new Intent(this, MyActivity.class);
//            intent.putExtra("name", "罗广政");
//            startActivity(intent);
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("姓名或学号错误，请重新输入");
//            builder.setNegativeButton("好的", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    et_name.setText("");
//                    et_number.setText("");
//                }
//            });
//            builder.show();
//        }
    }
}