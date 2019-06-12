package com.control.yourself.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by Administrator on 2019/6/11.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button btn_login, btn_register;
    private EditText et_name, et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                Intent intents = new Intent(this, RegisterActivity.class);
                startActivity(intents);
                break;
        }
    }

}
