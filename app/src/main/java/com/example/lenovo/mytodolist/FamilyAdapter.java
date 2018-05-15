package com.example.lenovo.mytodolist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2018/4/18.
 */

public class FamilyAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private List<String> mData;

    public FamilyAdapter(Activity context, int layoutResourceId, List<String> Data) {
        super(context, layoutResourceId, Data);
        inflater = LayoutInflater.from(context);
        mData = Data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.family_members, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.Item = convertView.findViewById(R.id.tv_mem_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Item.setText(mData.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView Item;
    }
}
