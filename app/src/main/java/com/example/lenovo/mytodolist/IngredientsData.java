package com.example.lenovo.mytodolist;

/**
 * Created by lenovo on 2018/3/16.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于菜篮子界面
 * 数据包括菜名 材料 菜名放在header中 材料放在item中
 * 本数据是指一组材料
 */

public class IngredientsData {

    String name;//菜名
    String[] ingredient;//材料名+用量
    int[] status;
    int header_status=0;

    IngredientsData(String n, int[] status)
    {

        name = n;
        this.status = status;
        /*this.status=new int[status.length];
        for(int i=0;i<status.length;i++)
            this.status[i]=status[i];*/
    }

    IngredientsData(String n, String[] ingre) {

        name = new String(n);
        ingredient = ingre;
        status = new int[ingre.length];
    }

    String getName()
    {
        return name;
    }

    String[] getIngredient() {
        return ingredient;
    }


    void setName(String n)
    {
        //String初始化后不能再改变
        name = new String(n);
    }


    void setIngredients(String[] ingre)
    {
        ingredient=ingre;
    }


    int getItemCount()
    {
        return ingredient.length + 1;
    }

    public String getItem(int pPosition) {
        // Category排在第一位
        if (pPosition == 0) {
            return name;
        } else {
            return ingredient[pPosition - 1];
        }
    }

    public int[] getStatus() {
        return status;
    }

    public void setStatus(int[] status) {
        this.status = new int[status.length];
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
