package com.example.lenovo.mytodolist;

/**
 * Created by lenovo on 2018/3/16.
 */

/**
 * 用于菜篮子界面
 * 数据包括菜名 材料 菜名放在header中 材料放在item中
 * 本数据是指一项材料，不是一组材料
 */

public class IngredientsData {

    long dish_id;//材料所属菜的id
    String name;//菜名
    String ingredient;//材料名+用量
    IngredientsData(long dish_id,String n,String ingre)
    {

        this.dish_id=dish_id;
        name = new String(n);
        ingredient = new String(ingre);
    }
    IngredientsData( long dish_id)
    {
        this.dish_id=dish_id;
    }
    String getName()
    {
        return name;
    }
    String getIngredient(){
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
    void setIngredients(String in)
    {
        ingredient=new String(in);
    }
}
