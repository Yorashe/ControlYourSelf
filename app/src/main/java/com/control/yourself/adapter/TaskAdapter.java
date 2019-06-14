package com.control.yourself.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.control.yourself.activity.R;
import com.control.yourself.greendao.Entity.RemindBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TaskAdapter extends BaseQuickAdapter<RemindBean, BaseViewHolder> {


    public TaskAdapter(List<RemindBean> data) {
        super(R.layout.task_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RemindBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.time, formatData(item.getRemindTime()));

    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        super.setOnItemChildClickListener(listener);




    }

    /**
     * 时间戳转特定格式时间
     *
     * @param timeStamp
     * @return
     */
    public String formatData(long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(timeStamp));
    }


}
