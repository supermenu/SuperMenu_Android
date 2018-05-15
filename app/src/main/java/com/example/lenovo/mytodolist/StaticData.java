package com.example.lenovo.mytodolist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/4/1.
 */

public class StaticData {
    static ArrayList<IngredientsData> IngredientsData=new ArrayList<>();
    static ArrayList<String> dishlist=new ArrayList<>();
    static DataBase datapool;
    static String username;
    static int age;
    static char sex;
   public static   void addIngredientsToBasket(IngredientsData newdata) {
       dishlist.add(0, newdata.getName());
       IngredientsData.add(0, newdata);
   }
   public static  void removeIngredientsFromBasket(int index){
       dishlist.remove(index);
       IngredientsData.remove(index);
   }


    public static   int getCount() {
        int count = 0;
            //  所有分类中item的总和是ListVIew  Item及header的总个数
            IngredientsData in;
            for (int i=0;i< IngredientsData.size();i++) {
                in=IngredientsData.get(i);
                count += in.getItemCount();
            }

        return count;
    }

}
