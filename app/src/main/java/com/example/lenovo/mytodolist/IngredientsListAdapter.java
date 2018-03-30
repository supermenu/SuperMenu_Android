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
 * 本类是推荐页面弹出的 菜谱详细页面 的 材料表 显示适配器
 */


public class IngredientsListAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;//材料表布局文件
    private List<String> mData;//材料数据

    public IngredientsListAdapter(Context context, int layoutResourceId, List<String> data) {
        super(context, layoutResourceId, data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    //设置每一个item的数据信息
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_ingredients, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.Item = (TextView) convertView.findViewById(R.id.tv_ingredients);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Item.setText(mData.get(position).toString());
        return convertView;
    }

    static class ViewHolder {
        TextView Item;
    }

}
