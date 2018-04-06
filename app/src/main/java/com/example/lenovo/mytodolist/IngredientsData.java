package com.example.lenovo.mytodolist;

/**
 * Created by lenovo on 2018/3/16.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 用于菜篮子界面
 * 数据包括菜名 材料 菜名放在header中 材料放在item中
 * 本数据是指一组材料
 */

public class IngredientsData {

    long dish_id;//材料所属菜的id
    String name;//菜名
    List<String> ingredient;//材料名+用量
    int[] status;
    int header_status=0;
    IngredientsData(String n,List<String> ingre)
    {

        this.dish_id= StaticData.totaldish_inbasket;
        name = new String(n);
        ingredient = ingre;
        status=new int[ingre.size()];
    }
    IngredientsData( long dish_id)
    {
        this.dish_id=dish_id;
    }
    String getName()
    {
        return name;
    }
    List<String> getIngredient(){
        return ingredient;
    }


    void setName(String n)
    {
        //String初始化后不能再改变
        name = new String(n);
    }

   long getDish_id()
    {
        return dish_id;
    }
    void setIngredients(List<String> ingre)
    {
        ingredient=ingre;
    }


    int getItemCount()
    {
        return ingredient.size()+1;
    }

    public String getItem(int pPosition) {
        // Category排在第一位
        if (pPosition == 0) {
            return name;
        } else {
            return ingredient.get(pPosition - 1);
        }
    }

    public  int getStatus(int index)
    {
        return status[index];
    }

    public void setStatus(int index,int value)
    {
        status[index]=value;
    }
}
