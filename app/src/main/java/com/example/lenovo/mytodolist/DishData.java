package com.example.lenovo.mytodolist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2018/3/22.
 * 推荐菜谱信息
 */

public class DishData /*implements Parcelable*/ {
    private String dish_name, diffculty, flavor, need_time, skill;//菜名，难度，口味，时间，工艺
    public List<String> ingredients;//材料列表
    public List<String> steps;//步骤列表

    public DishData(String dish_name, String diffculty, String flavor, String need_time, String
            skill, String[] ingredients, String[] steps) {
        this.dish_name = new String(dish_name);
        this.diffculty = new String(diffculty);
        this.flavor = new String(flavor);
        this.need_time = new String(need_time);
        this.skill = new String(skill);
        this.ingredients = Arrays.asList(ingredients);
        this.steps = Arrays.asList(steps);
    }

    public DishData(String dish_name, String diffculty, String flavor, String need_time, String
            skill) {
        this.dish_name = new String(dish_name);
        this.diffculty = new String(diffculty);
        this.flavor = new String(flavor);
        this.need_time = new String(need_time);
        this.skill = new String(skill);
    }

    public String getDish_name() {
        return dish_name;
    }

    public String getOthers() {
        //用于显示
        return diffculty + " · " + flavor + " · " + skill + " · " + need_time;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setIngredients(List<String> in) {
        ingredients = in;
    }

}
