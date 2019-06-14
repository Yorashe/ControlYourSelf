package com.control.yourself.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.control.yourself.greendao.Entity.RemindBean;

import java.util.List;

/**
 * Created by Administrator on 2019/6/12.
 */
public class AppSQLiteData extends SQLiteOpenHelper {

    public static String Remind_data = "create table Remind_data("

            + "id integer primary key autoincrement, "

            + "remindTime long,"

            + "content text,"

            + "title text" + ")";

    private Context mContext;

    public AppSQLiteData(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);

        mContext = context;

    }


    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Remind_data);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private AppSQLiteData mRemindSQL;

    /***
     * 将获取到的便签列表时间缓存入SQL
     * */
    public void AddData(final Context context, final List<RemindBean> data) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Calendar c = Calendar.getInstance();//获取当前时间，为了判断添加时间是否已经过时
//                mRemindSQL = new AppSQLiteData(context, "Remind.db", null, 1);
//                mRemindSQL.getWritableDatabase();
//                SQLiteDatabase db = mRemindSQL.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                for (int i = 0; i < data.size() - 1; i++) {
//                    if (!data.get(i).getRemindTime().equals("") && Long.parseLong(data.get(i).getRemindTime()) > c.getTimeInMillis()) {
//                        values.put("remindTime", Long.parseLong(data.get(i).getRemindTime()));
//                        values.put("title", data.get(i).getTitle());
//                        values.put("content", data.get(i).getLevel());
//                        db.insert("Remind_data", null, values);
//                    }
//                    values.clear();
//                }
//            }
//        }).start();
    }
}

