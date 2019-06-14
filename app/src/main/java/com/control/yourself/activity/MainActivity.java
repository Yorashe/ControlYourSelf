package com.control.yourself.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.control.yourself.Fragments.NoteFragment;
import com.control.yourself.Fragments.PersonalFragment;
import com.control.yourself.MyApplication;
import com.control.yourself.bean.TabEntity;
import com.control.yourself.db.DbUtils;
import com.control.yourself.greendao.Entity.RemindBean;
import com.control.yourself.service.AlarmReceiver;
import com.control.yourself.widget.SimpleDialog;
import com.mocuz.tablayout.CommonTabLayout;
import com.mocuz.tablayout.listener.CustomTabEntity;
import com.mocuz.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.ctab_layout)
    CommonTabLayout ctab_layout;
    @BindView(R.id.lin_tab)
    LinearLayout linTab;
    @BindView(R.id.layoutMainBlank)
    LinearLayout layoutMainBlank;
    @BindView(R.id.fl_body)
    FrameLayout flBody;
    @BindView(R.id.rel_parent)
    RelativeLayout relParent;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private String[] mTitles={"记事簿","个人中心"};//tab 文字
    private int[]                      mIconUnselectIds = {R.mipmap.ic_note_normal,
            R.mipmap.ic_home_normal};//tab 默认背景
    private int[]                      mIconSelectIds   = {R.mipmap.ic_note_selected,
            R.mipmap.ic_home_selected};//tab选中背景
    private ArrayList<CustomTabEntity> mTabEntities     = new ArrayList<>();

    private NoteFragment noteFragment;
    private PersonalFragment personalFragment;
    public static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTab();
        initFragment(savedInstanceState);
        String msg =getIntent().getStringExtra("msg");
        Log.e("AlarmReceiver:", MyApplication.getUserItem().isNotify()+"");
        if (msg!=null && MyApplication.getUserItem().isNotify()){
            Log.e("AlarmReceiver:", msg);
            showDialogInBroadcastReceiver(msg);
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showDialogInBroadcastReceiver(String message) {
            mediaPlayer = MediaPlayer.create(this, R.raw.in_call_alarm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);

        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("闹钟提醒");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        vibrator.cancel();
                    dialog.dismiss();
                    finish();
                }
            }
        });


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            personalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentByTag("personalFragment");
            noteFragment = (NoteFragment) getSupportFragmentManager().findFragmentByTag("noteFragment");
            currentTabPosition = savedInstanceState.getInt(HOME_CURRENT_TAB_POSITION);
        } else {
            personalFragment = new PersonalFragment();
            noteFragment = new NoteFragment();

            transaction.add(R.id.fl_body, personalFragment, "personalFragment");
            transaction.add(R.id.fl_body, noteFragment, "noteFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        ctab_layout.setCurrentTab(currentTabPosition);
    }


    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        ctab_layout.setTabData(mTabEntities);
        //点击监听
        ctab_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //笔记
            case 0:
                transaction.hide(personalFragment);
                transaction.show(noteFragment);
                transaction.commitAllowingStateLoss();
                break;
            //个人
            case 1:
                transaction.hide(noteFragment);
                transaction.show(personalFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View view) {

    }

}
