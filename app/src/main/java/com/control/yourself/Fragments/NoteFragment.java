package com.control.yourself.Fragments;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.control.yourself.MyApplication;
import com.control.yourself.activity.R;
import com.control.yourself.activity.RemindInfoActivity;
import com.control.yourself.adapter.TaskAdapter;
import com.control.yourself.db.DbUtils;
import com.control.yourself.greendao.Entity.RemindBean;
import com.control.yourself.widget.MDEditDialog;
import com.github.clans.fab.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/6/11.
 */
public class NoteFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_post)
    FloatingActionButton btnPost;
    Unbinder unbinder;
    private View view;
    TaskAdapter adapter;
    private List<RemindBean> remindBeans =new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_view_note, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.setNewData(DbUtils.queryMessage(MyApplication.getUserItem().getName()));

    }

    private void initView() {
        btnPost.setColorNormal(getResources().getColor(R.color.white));
        btnPost.setColorPressed(getResources().getColor(R.color.white));
        btnPost.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        List<RemindBean> datas=DbUtils.queryMessage(MyApplication.getUserItem().getName());
        if (datas!=null)
        remindBeans.addAll(datas);
        adapter =new TaskAdapter(remindBeans);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent =new Intent(getActivity(), RemindInfoActivity.class);
                intent.putExtra("data",(RemindBean) adapter.getData().get(position));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_post:
                addDetail();
                break;
                default:
                    break;
        }
    }

    private void addDetail() {
        final MDEditDialog dialog6 = new MDEditDialog.Builder(getActivity())
                .setTitleVisible(true)
                .setTitleText("添加笔记")
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
//                .setContentText("Weavey")
                .setContentTextSize(18)
//                .setMaxLength(7)
//                .setHintText("7位字符")
//                .setMaxLines(1)
                .setContentTextColor(R.color.colorPrimary)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.colorPrimary)
                .setLeftButtonText("取消")
                .setRightButtonTextColor(R.color.colorPrimary)
                .setRightButtonText("确定")
                .setLineColor(R.color.colorPrimary)
                .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                    @Override
                    public void clickLeftButton(Dialog view, String text) {
                        if (view!=null)
                        view.dismiss();
                    }
                    @Override
                    public void clickRightButton(Dialog view, String text) {
                        if (TextUtils.isEmpty(text)){

                        }
                        RemindBean bean= new RemindBean(System.currentTimeMillis(),text, MyApplication.getUserItem().getName());
                        DbUtils.addMessage(bean);
//                        remindBeans.add(bean);
                        adapter.addData(bean);
                        if (view!=null)
                            view.dismiss();

                    }
                })
                .setMinHeight(0.3f)
                .setWidth(0.8f)
                .build();
        dialog6.show();
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_add_thing, null);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
