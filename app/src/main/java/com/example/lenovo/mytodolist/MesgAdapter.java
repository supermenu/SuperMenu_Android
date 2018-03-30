package com.example.lenovo.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import java.util.List;

/**
 * Created by lenovo on 2018/3/8.
 * 消息数据的适配类
 */

public class MesgAdapter extends ArrayAdapter<Mesg> {
private int resourceId;//每个消息的布局id，即meg_item

public MesgAdapter(Context context, int textViewResourceId, List<Mesg> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        }

public View getView(int position, View convertView, ViewGroup parent) {
        Mesg mesg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        viewHolder = new ViewHolder();
        viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
        viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
        viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
        viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);
        view.setTag(viewHolder);
        } else {
        view = convertView;
        viewHolder = (ViewHolder) view.getTag();
        }
        //消息类型为收到，代表天猫精灵给人的信息
        if(mesg.getType() == Mesg.TYPE_RECEIVED) {
                //显示左侧布局
        viewHolder.leftLayout.setVisibility(View.VISIBLE);
        viewHolder.rightLayout.setVisibility(View.GONE);
        viewHolder.leftMsg.setText(mesg.getContent());
        }
        else if(mesg.getType() == Mesg.TYPE_SEND) {//人给天猫精灵的信息
                //显示右侧布局
        viewHolder.rightLayout.setVisibility(View.VISIBLE);
        viewHolder.leftLayout.setVisibility(View.GONE);
        viewHolder.rightMsg.setText(mesg.getContent());
        }
        return view;
        }

class ViewHolder {
    LinearLayout leftLayout;
    LinearLayout rightLayout;
    TextView leftMsg;
    TextView rightMsg;
}

}
