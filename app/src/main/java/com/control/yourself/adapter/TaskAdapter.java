package com.control.yourself.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.control.yourself.activity.R;
import com.control.yourself.bean.TaskBean;

import java.util.List;



public class TaskAdapter extends BaseQuickAdapter<TaskBean,BaseViewHolder> {


    public TaskAdapter(List<TaskBean> data) {
        super(R.layout.task_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskBean item) {
//        helper.setText(R.id.title,item.getFLOW_NAME());

    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        super.setOnItemChildClickListener(listener);
    }

}
