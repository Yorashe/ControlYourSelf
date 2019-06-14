package com.control.yourself.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.control.yourself.MyApplication;
import com.control.yourself.activity.R;
import com.control.yourself.db.DbUtils;
import com.control.yourself.greendao.Entity.UserBean;
import com.control.yourself.widget.MyToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2019/6/11.
 */
public class PersonalFragment extends Fragment {
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.myToolbar)
    MyToolbar myToolbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.birthday)
    EditText birthday;
    @BindView(R.id.man)
    RadioButton man;
    @BindView(R.id.woman)
    RadioButton woman;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.notify)
    Switch notify;
    Unbinder unbinder;
    private View view;
    private int sex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_view_personal, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        initUser();

    }

    private void initUser(){
        UserBean userBean = MyApplication.getUserItem();
        name.setText(userBean.getName());
        pwd.setText(userBean.getPwd());
        if (!TextUtils.isEmpty(userBean.getBirthday())){
            birthday.setText(userBean.getBirthday());
        }
        if (!TextUtils.isEmpty(userBean.getEmail())){
            email.setText(userBean.getEmail());
        }
        if (!TextUtils.isEmpty(userBean.getEmail())){
            email.setText(userBean.getEmail());
        }

        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sex=1;
                }
            }
        });
        woman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sex=2;
                }
            }
        });

        if(userBean.getSex()==1){
            man.setChecked(true);
        }else if (userBean.getSex()==2){
            woman.setChecked(true);
        }

        notify.setChecked(userBean.isNotify());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save:
                save();
                break;
        }
    }


    private void save(){
        if (TextUtils.isEmpty(name.getText().toString())
                ||TextUtils.isEmpty(pwd.getText().toString())){
            Toast.makeText(getContext(),"输入姓名和密码",Toast.LENGTH_LONG).show();
            return;
        }
        UserBean userBean =new UserBean(name.getText().toString(),pwd.getText().toString());
        userBean.setSex(sex);
        userBean.setBirthday(birthday.getText().toString());
        userBean.setEmail(email.getText().toString());
        userBean.setIsNotify(notify.isChecked());
        DbUtils.save(userBean);
        Toast.makeText(getContext(),"保存成功",Toast.LENGTH_LONG).show();
    }
}
