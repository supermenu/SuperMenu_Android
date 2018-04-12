package com.example.lenovo.mytodolist;

/**
 * Created by lenovo on 2018/4/5.
 */
import android.util.Log;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.sql.SQLException;

public class DataBase {
    public static final String url =
            "jdbc:mysql://118.24.109.25/SuperMenu?useUnicode=true&characterEncoding" +
                    "=GBK";
    public static final String Driver = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "sql2017..";
    public Thread tbase;

    public ConnectionPool connPool = null;

    DataBase() {
        // 创建连接池对象
        connPool = new ConnectionPool(Driver, url, user, password);
       tbase =new Thread(new Runnable() {
        @Override
        	public void run() {
        try {

            // 创建连接池
            DataBase.this.connPool.createPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        	});
       tbase.start();
    }

    public void finish() {
        try {
            this.connPool.closeConnectionPool();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

class User {
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
        //    if(dishes==null)
          //      st.execute(String.format("UPDATE users SET basket=null WHERE username='%s'",this
            //            .username));
            //else//更新语句，应该用statement的execute()方法
                st.execute(String.format(set_value_sql, "users", dishes, this.username));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void finish() {
        dataBase.connPool.returnConnection(conn);
        //	Thread.yield();

    }
}

class Menu {
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
        this.dish = dish;
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

    public void finish() {
        dataBase.connPool.returnConnection(conn);
        //	Thread.yield();
    }



}
