package com.example.lenovo.mytodolist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐界面点击某道菜后弹出的详细介绍界面
 */
public class DishDetailsActivity extends Activity {
    ListViewForScrollView lv_ingredients,lv_steps;//用在scrollview内部的listview
    ImageView tb_back,iv_dish;
    TextView tv_dishname,tv_ingredients,tv_steps,tv_others,add_intobaskaet;
    RelativeLayout toolbar_details;
    ScrollView sv_details;
    List<DishData> dish_data;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
        Bundle b=getIntent().getExtras();
        pos=(int)b.get("position");
        initViews();
        //设置每道菜的信息，测试用
        setDishData();
        //点击pos位置的listview的item显示对应的菜谱内容
        setDishDetails(pos);
        sv_details.scrollTo(0,0);
    }

    public void initViews()
    {
        iv_dish=findViewById(R.id.iv_dish);
        sv_details=findViewById(R.id.sv_details);
        toolbar_details=findViewById(R.id.toolbar_details);
        tb_back=findViewById(R.id.toolbar_back);
        tv_dishname=findViewById(R.id.tv_dishname);
        tv_ingredients=findViewById(R.id.tv_ingredients);
        tv_steps=findViewById(R.id.tv_steps);
        tv_others=findViewById(R.id.tv_others);
        lv_ingredients=findViewById(R.id.lv_ingredients);
        lv_steps=findViewById(R.id.lv_steps);
        tb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();//当前页面关闭
            }
        });
        add_intobaskaet=(TextView)findViewById(R.id.tv_add_intolanzi);
        add_intobaskaet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = dish_data.get(pos).getIngredients().size();
                String dish_name = dish_data.get(pos).getDish_name();
                StaticData.IngredientsData.add(new IngredientsData( dish_name, dish_data.get(pos).getIngredients()));
                StaticData.totaldish_inbasket++;
                Toast.makeText(DishDetailsActivity.this,"已丢进菜篮子",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDishData()
    {
        dish_data=new ArrayList<>();
        dish_data.add(new DishData("粉蒸排骨","普通","五香","半小时","蒸",getResources().getStringArray
                (R.array.in0),getResources().getStringArray(R.array.steps0) ));
        dish_data.add(new DishData("锅包肉","高级","酸甜","四十分钟","炸",getResources().getStringArray
                (R.array.in1),getResources().getStringArray(R.array.steps1) ));
        dish_data.add(new DishData("红烧排骨","高级","咸鲜","一小时","烧",getResources().getStringArray
                (R.array.in2),getResources().getStringArray(R.array.steps2) ));
        dish_data.add(new DishData("京酱肉丝","普通","酱香","四十分钟","炒",getResources().getStringArray
                (R.array.in3),getResources().getStringArray(R.array.steps3) ));
        dish_data.add(new DishData("酱香牛肉","普通","微辣","一小时","烧",getResources().getStringArray
                (R.array.in4),getResources().getStringArray(R.array.steps4) ));
        dish_data.add(new DishData("鱼香茄子","简单","鱼香","十五分钟","炒",getResources().getStringArray
                (R.array.in5),getResources().getStringArray(R.array.steps5) ));
        dish_data.add(new DishData("辣子鸡","简单","中辣","半小时","炸",getResources().getStringArray
                (R.array.in6),getResources().getStringArray(R.array.steps6) ));
    }

    public void setDishDetails(int position)
    {
        //显示position对应的菜谱内容
        //菜谱图片
        int[] avatars = {
                R.drawable.fzpaigu2,
                R.drawable.guobaorou2,
                R.drawable.hspaigu2,
                R.drawable.jjrousi2,
                R.drawable.jxniurou2,
                R.drawable.yxqiezi2,
                R.drawable.laziji2,
        };
        //加载对应菜谱图片
        Picasso.with(DishDetailsActivity.this).load(avatars[position])
                .resize(TuijianFragment.sScreenWidth, TuijianFragment.sImageHeight).centerCrop()
                .placeholder(R.color.ltgray)
                .into(iv_dish);
        //设置对应菜谱详细文字
        tv_dishname.setText(dish_data.get(position).getDish_name());
        //菜谱的口味、工艺等信息
        tv_others.setText(dish_data.get(position).getOthers());
        //菜谱的材料表，用listview显示
        lv_ingredients.setAdapter(new IngredientsListAdapter(this, R.layout
                .layout_ingredients,dish_data.get(position).getIngredients()));
        //菜谱的步骤表
        lv_steps.setAdapter(new StepsListAdapter(this, R.layout
                .layout_steps,dish_data.get(position).getSteps()));
        //用于解决显示不全的问题
        UIHelper.setListViewHeightBasedOnChildren(lv_ingredients);
        UIHelper.setListViewHeightBasedOnChildren(lv_steps);

    }

    //点击返回键退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

//解决scroller嵌套listview显示不全的问题
class UIHelper {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}

