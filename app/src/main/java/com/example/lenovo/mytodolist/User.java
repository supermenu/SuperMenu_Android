package com.example.lenovo.mytodolist;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lenovo on 2018/4/14.
 */

public class User {
    public String username = null;
    public String password_hash = null;
    public String basket_dish = null;

    public DataBase dataBase = null;
    public Connection conn = null;
    public Statement st = null;
    public ResultSet rs = null;

    public String get_user_sql = "SELECT * FROM user WHERE %s='%s'";
    public String get_value_sql = "SELECT %s FROM users WHERE %s='%s'";
    public String set_value_sql = "UPDATE %s SET basket='%s' WHERE username='%s'";
    public Thread t1;

    User(String username, DataBase db) {
        this.username = username;
        this.dataBase = db;
        //数据库连接需要在线程中完成
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 从连接池中获取一个连接
                    conn = dataBase.connPool.getConnection();
                    st = conn.createStatement();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

    }

    public String getPassword_hash() {
        // 获取Hash密码
        String hash_password = null;
        try {
            rs = st.executeQuery(String.format(get_user_sql, "username", this.username));
            while (rs.next())
                hash_password = rs.getString(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hash_password;
    }

    public Boolean verify_password(String password) {
        // 验证密码
        if (this.password_hash == null)
            return false;
        /*
		 * 这部分需要python的去验证密码， 需要通过服务器完成。
		 *
		 */
        return true;
    }

    public String get_basket() {

        // 获取正在做的菜
        try {
            rs = st.executeQuery(String.format(get_value_sql, "basket", "username", this
                    .username));
            while (rs.next()) {
                basket_dish = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return basket_dish;
    }
/*
    public Boolean is_cooking() {
        // 判断是否在做菜
        this.cooking_dish = get_cooking();
        if (cooking_dish == null)
            return false;
        else
            return true;
    }*/

    public void setBasket(String dishes) {
        // 将菜篮子的菜放入数据库
        try {
         //更新语句，应该用statement的execute()方法
            st.execute(String.format(set_value_sql, "users", dishes, this.username));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public float get_energy()
    {
        float energy=0;
        try {
            String sql1=String.format("SELECT need_energy FROM family_member WHERE %s='%s'", "user",
                    StaticData.username);
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
            String sql1=String.format("SELECT need_axunge FROM family_member WHERE %s='%s'", "user",
                    StaticData.username);
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
            String sql1=String.format("SELECT need_protein FROM family_member WHERE %s='%s'",
                    "user",
                    StaticData.username);
            Log.e("sql",sql1);
            rs = st.executeQuery(sql1);
            while (rs.next())
                protein = rs.getFloat(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return protein;
    }

    //返回连接
    public void finish() {
        dataBase.connPool.returnConnection(conn);

    }
}