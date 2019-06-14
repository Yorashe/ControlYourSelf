package com.control.yourself.greendao.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/12.
 */
@Entity
public class RemindBean implements Serializable{
    private static final long serialVersionUID = -3099431778593310885L;
    @Id
    private Long _id;
    private String name;
    private long remindTime;
    private String title;
    private String level;

    public RemindBean(String name, long remindTime, String title) {
        this.name = name;
        this.remindTime = remindTime;
        this.title = title;
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

    public long getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(long remindTime) {
        this.remindTime = remindTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public RemindBean() {
    }



    public RemindBean(long remindTime, String title, String name) {
        this.remindTime = remindTime;
        this.title = title;
        this.name = name;
    }

    @Generated(hash = 769301325)
    public RemindBean(Long _id, String name, long remindTime, String title,
            String level) {
        this._id = _id;
        this.name = name;
        this.remindTime = remindTime;
        this.title = title;
        this.level = level;
    }
    @Override
    public String toString() {
        return "RemindBean{" +
                "remindTime=" + remindTime +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                '}';
    }


}
