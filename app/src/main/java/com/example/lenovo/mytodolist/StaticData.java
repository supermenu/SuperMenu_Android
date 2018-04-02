package com.example.lenovo.mytodolist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/4/1.
 */

public class StaticData {
    static List<IngredientsData> IngredientsData=new ArrayList<>();
    static long  totaldish_inbasket=0;
    static int lastdishnum=0;
    void addIngredientsToBasket(IngredientsData newdata)
    {
        lastdishnum=getCount();
        IngredientsData.add(newdata);
}

    public int getCount() {
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
