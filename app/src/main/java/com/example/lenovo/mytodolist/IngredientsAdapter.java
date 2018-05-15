package com.example.lenovo.mytodolist;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
public class IngredientsAdapter extends BaseAdapter {

    private static final int TYPE_Header = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater inflater;

    //private List<IngredientsData> mListData;
    //private

    public IngredientsAdapter(Activity context, List<IngredientsData> Data){
        inflater = LayoutInflater.from(context);
       // mListData = Data;
    }


    @Override
    public int getCount() {
        int count = 0;

        if (null != StaticData.IngredientsData) {
            //  所有分类中item的总和是ListVIew  Item及header的总个数
            IngredientsData in;
            for (int i=0;i< StaticData.IngredientsData.size();i++) {
                in=StaticData.IngredientsData.get(i);
                count += in.getItemCount();
            }
        }

        return count;
    }

    @Override
    public Object getItem(int position) {

        // 异常情况处理
        if (null == StaticData.IngredientsData || position <  0|| position > getCount()) {
            return null;
        }

        // 同一分类内，第一个元素的索引值
        int ingredientsHeaderIndex = 0;
        IngredientsData in;
        for (int i=0;i< StaticData.IngredientsData.size();i++) {
            in=StaticData.IngredientsData.get(i);
            int size = in.getItemCount();
            // 在当前分类中的索引值
            int ingredientsIndex = position - ingredientsHeaderIndex;
            // item在当前分类内
            if (ingredientsIndex < size) {
                return  in.getItem( ingredientsIndex );
            }

            // 索引移动到当前分类结尾，即下一个分类第一个元素索引
            ingredientsHeaderIndex += size;
        }

        return null;
    }

    public IngredientsData getIngredients(int position) {

        // 异常情况处理
        if (null == StaticData.IngredientsData || position <  0|| position > getCount()) {
            return null;
        }

        // 同一分类内，第一个元素的索引值
        int ingredientsHeaderIndex = 0;
        IngredientsData in;
        for (int i=0;i< StaticData.IngredientsData.size();i++) {
            in=StaticData.IngredientsData.get(i);
            int size = in.getItemCount();
            // 在当前分类中的索引值
            int ingredientsIndex = position - ingredientsHeaderIndex;
            // item在当前分类内
            if (ingredientsIndex < size) {
                return  in;
            }

            // 索引移动到当前分类结尾，即下一个分类第一个元素索引
            ingredientsHeaderIndex += size;
        }

        return null;
    }
    public int getItemPositioninList(int position) {

        // 异常情况处理
        if (null == StaticData.IngredientsData || position <  0|| position > getCount()) {
            return -1;
        }

        // 同一分类内，第一个元素的索引值
        int ingredientsHeaderIndex = 0;
        IngredientsData in;
        for (int i=0;i< StaticData.IngredientsData.size();i++) {
            in=StaticData.IngredientsData.get(i);
            int size = in.getItemCount();
            // 在当前分类中的索引值
            int ingredientsIndex = position - ingredientsHeaderIndex;
            // item在当前分类内
            if (ingredientsIndex < size) {
                return  ingredientsIndex;
            }

            // 索引移动到当前分类结尾，即下一个分类第一个元素索引
            ingredientsHeaderIndex += size;
        }

        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        // 异常情况处理
        if (null == StaticData.IngredientsData || position <  0|| position > getCount()) {
            return TYPE_ITEM;
        }


        int ingredinetsFirstIndex = 0;
        IngredientsData temp;
        for (int i=0;i< StaticData.IngredientsData.size();i++) {
            temp=StaticData.IngredientsData.get(i);
            int size = temp.getItemCount();
            // 在当前分类中的索引值
            int ingredinetsIndex = position - ingredinetsFirstIndex;
            if (ingredinetsIndex== 0) {
                return TYPE_Header;
            }

            ingredinetsFirstIndex += size;
        }


        return TYPE_ITEM;
    }


    public void delete(int position)
    {
        int ingredientsHeaderIndex = 0;
        int headerinderx=0;
        IngredientsData in;
        for (int i=0;i< StaticData.IngredientsData.size();i++) {
            final int dishindex = i;
            in=StaticData.IngredientsData.get(i);
            int size = in.getItemCount();
            // 在当前分类中的索引值
            int ingredientsIndex = position - ingredientsHeaderIndex;
            // item在当前分类内
            if (ingredientsIndex ==0) {
                final Thread tbase=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (dishindex == 0)//删除第一个
                        {
                            User u = new User(StaticData.username, StaticData.datapool);
                            try {
                                u.t1.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (StaticData.IngredientsData.size() > 1)
                                u.setBasket(StaticData.IngredientsData.get(1).getName());
                            else
                                u.setBasket("");
                            u.finish();
                        }
                    }
                });
                tbase.start();

                try {
                    tbase.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                StaticData.removeIngredientsFromBasket(i);
                    return;
            }

            // 索引移动到当前分类结尾，即下一个分类第一个元素索引
            ingredientsHeaderIndex += size;
            headerinderx++;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        ViewHeadHolder Holder=null;
        int type = getItemViewType(position);
        if (convertView==null){
            switch (type) {
                case TYPE_Header:
                    Holder = new ViewHeadHolder();
                    convertView = inflater.inflate(R.layout.ingredientsheader, null);
                    Holder.textView = convertView.findViewById(R.id.tvHeader);
                    Holder.delete = convertView.findViewById(R.id.deleteButton);
                    convertView.setTag(Holder);
                    break;
                case TYPE_ITEM:
                    convertView = inflater.inflate(R.layout.ingredientitem, null);
                    viewHolder = new ViewHolder();
                    viewHolder.content = convertView.findViewById(R.id.ingredient);
                    convertView.setTag(viewHolder);
                    break;

            }
        }else{
            switch (type){
                case TYPE_Header:
                    Holder= (ViewHeadHolder) convertView.getTag();
                    break;
                case TYPE_ITEM:
                    viewHolder= (ViewHolder) convertView.getTag();
                    break;
            }
        }
        int ingredientsHeaderIndex = 0;

        IngredientsData in=StaticData.IngredientsData.get(0);
        switch (type){
            case TYPE_Header:
               ingredientsHeaderIndex = 0;
                 in=StaticData.IngredientsData.get(0);
                for (int i=0;i< StaticData.IngredientsData.size();i++) {
                    in=StaticData.IngredientsData.get(i);
                    int size = in.getItemCount();
                    // 在当前分类中的索引值
                    // item在当前分类内
                    if (position - ingredientsHeaderIndex==0) {
                        break;
                    }
                    // 索引移动到当前分类结尾，即下一个分类第一个元素索引
                    ingredientsHeaderIndex += size;
                }
                int status=in.header_status;
                if(status==1)
                {
                    Holder.delete.setVisibility(View.VISIBLE);
                }else
                    Holder.delete.setVisibility(View.INVISIBLE);

                Holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(position);
                        IngredientsAdapter.this.notifyDataSetChanged();
                    }
                });
                String  Value = (String) getItem(position);
                Holder.textView.setText( Value );
                break;
            case TYPE_ITEM:
                // 同一分类内，第一个元素的索引值
               ingredientsHeaderIndex = 0;
                int ingredientsIndex=0;
                 in=StaticData.IngredientsData.get(0);
                for (int i=0;i< StaticData.IngredientsData.size();i++) {
                    in=StaticData.IngredientsData.get(i);
                    int size = in.getItemCount();
                    // 在当前分类中的索引值
                    ingredientsIndex = position - ingredientsHeaderIndex;
                    // item在当前分类内
                    if (ingredientsIndex < size) {
                        break;
                    }
                    // 索引移动到当前分类结尾，即下一个分类第一个元素索引
                    ingredientsHeaderIndex += size;
                }
               int status2=in.getStatus(ingredientsIndex-1);
                if(status2==0){
                    viewHolder.content.setTextColor(Color.parseColor("#000000"));
                    viewHolder.content.getPaint().setFlags(0);
                    } else
                        {
                     viewHolder.content.setTextColor(Color.parseColor("#ffcccccc"));
                       viewHolder.content.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                // 绑定数据
                viewHolder.content.setText( (String)getItem(position) );
                break;
        }


        return convertView;
        /*
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            /*case TYPE_Header:
                ViewHeadHolder Holder = null;
                if (null == convertView) {
                    Holder = new  ViewHeadHolder();
                    convertView = inflater.inflate(R.layout.ingredientsheader, null);
                    Holder.textView = (TextView) convertView.findViewById(R.id.tvHeader);
                    Holder.delete=convertView.findViewById(R.id.deleteButton);
                    Holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete(position);
                            IngredientsAdapter.this.notifyDataSetChanged();
                        }
                    });
                    convertView.setTag(Holder);
                }else
                {
                    Holder = ( ViewHeadHolder) convertView.getTag();
                }

                String  Value = (String) getItem(position);
                Holder.textView.setText( Value );
                break;
                */
/*


            case TYPE_ITEM:
                ViewHolder viewHolder = null;
                if (null == convertView||viewHolder.content==null) {
                    convertView = inflater.inflate(R.layout.ingredientitem, null);

                    viewHolder = new ViewHolder();
                    viewHolder.content = (TextView) convertView.findViewById(R.id.ingredient);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                // 绑定数据
                viewHolder.content.setText( (String)getItem(position) );
                break;
        }

        return convertView;*/
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        return true;
    }



    private class ViewHolder {
       // int type=2;
        TextView content;
      //  TextView textView ;
      //  ImageButton delete;
    }

    private class ViewHeadHolder {
        TextView textView ;
        ImageButton delete;
    }


}
