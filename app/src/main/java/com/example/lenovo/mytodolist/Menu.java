package com.example.lenovo.mytodolist;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lenovo on 2018/4/14.
 */

public class Menu {
    // public String get_user_sql = "SELECT * FROM user WHERE %s=%s";
    public String get_value_sql = "SELECT %s FROM menus WHERE %s='%s'";

    public String dish = null;
    public String ingredients = null;

    public DataBase dataBase = null;
    public Connection conn = null;
    public Statement st = null;
    public ResultSet rs = null;
    public Thread t2;

    Menu(String dish, DataBase db) {
        this.dish =dish;
        this.dataBase = db;
        // 数据库连接需要在线程中完成
        t2 = new Thread(new Runnable() {
            //@Override
            public void run() {
                try {
                    // 从连接池中获取一个连接
                    Menu.this.conn = dataBase.connPool.getConnection();
                    Menu.this.st = conn.createStatement();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }

    public String getIngredients() {
        // 获取对应菜谱的食材
        try {
            String sql1=String.format("SELECT %s FROM menus WHERE %s=\'%s\'",
                    "ingredients", "name", dish);
            Log.e("sql",sql1);
            rs = st.executeQuery(sql1);
            while (rs.next())
                ingredients = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public float get_energy()
    {
        float energy=0;
        try {
            String sql1=String.format("SELECT energy FROM menus WHERE %s='%s'", "name", dish);
            Log.e("sql",sql1);
            rs = st.executeQuery(sql1);
            while (rs.next())
                energy = rs.getFloat(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return energy;
    }

    public float get_axunge()
    {
        float axunge=0;
        try {
            String sql1=String.format("SELECT axunge FROM menus WHERE %s='%s'", "name", dish);
            Log.e("sql",sql1);
            rs = st.executeQuery(sql1);
            while (rs.next())
                axunge = rs.getFloat(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return axunge;
    }
    public float get_protein()
    {
        float protein=0;
        try {
            String sql1=String.format("SELECT protein FROM menus WHERE %s='%s'", "name", dish);
            Log.e("sql",sql1);
            rs = st.executeQuery(sql1);
            while (rs.next())
                protein = rs.getFloat(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return protein;
    }

    public void finish() {
        dataBase.connPool.returnConnection(conn);
        //	Thread.yield();
    }



}
