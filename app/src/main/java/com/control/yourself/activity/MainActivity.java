package com.control.yourself.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.control.yourself.Fragments.NoteFragment;
import com.control.yourself.Fragments.PersonalFragment;
import com.control.yourself.bean.TabEntity;
import com.mocuz.tablayout.CommonTabLayout;
import com.mocuz.tablayout.listener.CustomTabEntity;
import com.mocuz.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

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
