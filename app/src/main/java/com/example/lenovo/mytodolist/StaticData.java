package com.example.lenovo.mytodolist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/4/1.
 */

public class StaticData {
    static List<IngredientsData> IngredientsData=new ArrayList<>();
    static long  totaldish_inbasket=0;
    void addIngredientsToBasket(IngredientsData newdata)
    {
        IngredientsData.add(newdata);
    }

}
