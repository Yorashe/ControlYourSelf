package com.control.yourself.db;

import android.app.AlarmManager;
import android.app.Application;

import com.control.yourself.MyApplication;
import com.control.yourself.greendao.Entity.RemindBean;
import com.control.yourself.greendao.Entity.UserBean;
import com.control.yourself.greendao.gen.DaoSession;
import com.control.yourself.greendao.gen.RemindBeanDao;
import com.control.yourself.greendao.gen.UserBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Yorashe on 19-6-13.
 */

public class DbUtils {
    public static UserBean queryUserByName(String name){
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        QueryBuilder<UserBean> qb = daoSession.queryBuilder(UserBean.class);
        QueryBuilder<UserBean> studentQueryBuilder = qb.where(UserBeanDao.Properties.Name.eq(name)).orderAsc(UserBeanDao.Properties.Name);
        List<UserBean> userBeans = studentQueryBuilder.list(); //查出当前对应的数据
        if (userBeans==null || userBeans.isEmpty()){
            return null;
        }
        return userBeans.get(0);
    }

    public static void regist(UserBean user){
        UserBeanDao daoSession = MyApplication.getInstance().getDaoSession().getUserBeanDao();
        daoSession.insert(user);

    }

    public static void save(UserBean user){
        UserBeanDao daoSession = MyApplication.getInstance().getDaoSession().getUserBeanDao();
        daoSession.delete(MyApplication.getUserItem());
        daoSession.insert(user);
    }

    public static void addMessage(RemindBean remindBean){
        RemindBeanDao remindBeanDao = MyApplication.getInstance().getDaoSession().getRemindBeanDao();
        remindBeanDao.insert(remindBean);
    }

    public static void deleteMessage(RemindBean remindBean){
        RemindBeanDao remindBeanDao = MyApplication.getInstance().getDaoSession().getRemindBeanDao();
        remindBeanDao.delete(remindBean);
    }

    public static void setTime(RemindBean remindBean){
        RemindBeanDao remindBeanDao = MyApplication.getInstance().getDaoSession().getRemindBeanDao();
//        remindBeanDao.delete(remindBean);
        remindBeanDao.update(remindBean);
    }
    public static List<RemindBean> queryMessage(String name){
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        QueryBuilder<RemindBean> qb = daoSession.queryBuilder(RemindBean.class);
        QueryBuilder<RemindBean> studentQueryBuilder = qb.where(RemindBeanDao.Properties.Name.eq(name)).orderAsc(RemindBeanDao.Properties.Name);
        List<RemindBean> remindBeanDaos = studentQueryBuilder.list(); //查出当前对应的数据
        return remindBeanDaos;
    }

}
