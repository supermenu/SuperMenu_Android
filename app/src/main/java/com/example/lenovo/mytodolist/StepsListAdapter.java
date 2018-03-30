package com.example.lenovo.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2018/3/26.
 * 和ingredientsListAdapter相似
 */

public class StepsListAdapter extends ArrayAdapter<String> {
    private final LayoutInflater mInflater;
    private List<String> mData;

    public StepsListAdapter(Context context, int layoutResourceId, List<String> data) {
        super(context, layoutResourceId, data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_steps, parent, false);
            viewHolder = new StepsListAdapter.ViewHolder();
            viewHolder.Name = (TextView) convertView.findViewById(R.id.tv_step);
            viewHolder.Item = (TextView) convertView.findViewById(R.id.tv_steps);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //每一步之前显示是步骤几
        viewHolder.Name.setText("步骤"+Integer.toString(position+1));
        //显示步骤内容
        viewHolder.Item.setText(mData.get(position).toString());
        return convertView;
    }

    static class ViewHolder {
        TextView Name;
        TextView Item;
    }
}
