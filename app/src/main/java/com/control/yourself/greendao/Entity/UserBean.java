package com.control.yourself.greendao.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Yorashe on 19-6-13.
 */
@Entity
public class UserBean {
    @Id
    private Long _id;
    private String name;
    private String email;
    private String pwd;
    private String birthday;
    private int sex;//0保密,1man,2woman
    private boolean isNotify;


    @Generated(hash = 602391919)
    public UserBean(Long _id, String name, String email, String pwd,
            String birthday, int sex, boolean isNotify) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.birthday = birthday;
        this.sex = sex;
        this.isNotify = isNotify;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public UserBean(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public boolean getIsNotify() {
        return this.isNotify;
    }

    public void setIsNotify(boolean isNotify) {
        this.isNotify = isNotify;
    }
}
