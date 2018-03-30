package com.example.lenovo.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/3/24.
 * 用于推荐页面显示每个菜谱的图片
 */

public class tuijianListAdapter extends ArrayAdapter<Map<String, Object>> {

    private final LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public tuijianListAdapter(Context context, int layoutResourceId, List<Map<String, Object>> data) {
        super(context, layoutResourceId, data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dishlist_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mListItemAvatar = (ImageView) convertView.findViewById(R.id.image_view_avatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //加载对应图片
        Picasso.with(getContext()).load((Integer) mData.get(position).get("image"))
                .resize(TuijianFragment.sScreenWidth, TuijianFragment.sImageHeight).centerCrop()
                .placeholder(R.color.ltgray)
                .into(viewHolder.mListItemAvatar);
        return convertView;
    }

    static class ViewHolder {
        ImageView mListItemAvatar;
    }

}
