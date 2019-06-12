package com.control.yourself.db;

/**
 * Created by Administrator on 2019/6/12.
 */
public class RemindBean {

    private String remindTime;
    private String title;
    private String level;
    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
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



    public RemindBean(String remindTime, String title, String level) {
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
