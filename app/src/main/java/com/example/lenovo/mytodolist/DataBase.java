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

    //连接池关闭
    public void finish() {
        try {
            this.connPool.closeConnectionPool();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}



