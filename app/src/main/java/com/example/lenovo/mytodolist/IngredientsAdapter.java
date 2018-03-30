package com.example.lenovo.mytodolist;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
/**
 * Created by lenovo on 2018/3/16.
 */

/**
 * 将IngredientsData显示到listview的adapter
 * 这是菜篮子界面的适配器
 */

public class IngredientsAdapter extends BaseAdapter implements StickyListHeadersAdapter{

    private LayoutInflater inflater;

    private List<IngredientsData> mData;

    public IngredientsAdapter(Activity context, List<IngredientsData> Data){
        inflater = LayoutInflater.from(context);
        mData = Data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder.tv_ingredient = (TextView) convertView.findViewById(R.id.ingredient);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //取得当前材料表对应位置的材料
        IngredientsData data = this.mData.get(position);

        if (data != null) {

          //给该组对应item赋值
           holder.tv_ingredient.setText(data.getIngredient());
        }

        return convertView;
    }

    @Override
    //头部显示菜名
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
      final  long  dish= this.mData.get(position).dish_id;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.tvHeader);
            holder.delete = (ImageButton) convertView.findViewById(R.id.deleteButton);
            holder.delete.setTag(dish);//将dishid作为tag
            //点击删除图标按钮删除整个材料组
            holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int remnum=0;
                        for(int i=0;i<mData.size();i++)
                        {
                            if(mData.get(i).dish_id==(long)(v.getTag())) {
                                mData.remove(i);
                                i--;//删除后下一项改变到现在的位置了
                            }
                            else if(mData.get(i).dish_id>(long)(v.getTag()))
                                mData.get(i).dish_id--;
                        }
                        IngredientsAdapter.this.notifyDataSetChanged();
                    }
                });
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //给组名赋值菜名
        String headerText = this.mData.get(position).getName();
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return this.mData.get(position).getDish_id();
    }

    class HeaderViewHolder {
        TextView text;
        ImageButton delete;
    }

    class ViewHolder {
        TextView tv_ingredient;
    }

}
