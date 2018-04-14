package com.example.lenovo.mytodolist;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by lenovo on 2018/4/13.
 */

public class RefreshRecord {
    String sql_get="select dish from `cooking-record`  where user = '%s' and complete_time >= " +
            "'%s' and complete_time<='%s'";
    String w_1,w_2,w_3,w_4,w_5,w_6,w_7;
    String mor_1s,mor_2s,mor_3s,mor_4s,mor_5s,mor_6s,mor_7s;
    String mor_1e,mor_2e,mor_3e,mor_4e,mor_5e,mor_6e,mor_7e;
    String noon_1s,noon_2s,noon_3s,noon_4s,noon_5s,noon_6s,noon_7s;
    String noon_1e,noon_2e,noon_3e,noon_4e,noon_5e,noon_6e,noon_7e;
    String eve_1s,eve_2s,eve_3s,eve_4s,eve_5s,eve_6s,eve_7s;
    String eve_1e,eve_2e,eve_3e,eve_4e,eve_5e,eve_6e,eve_7e;
    public DataBase dataBase = null;
    public Connection conn = null;
    public Statement st = null;
    public ResultSet rs = null;
    public Thread t;
    ArrayList<String> cooked=new ArrayList<>();

    public void initconnection(DataBase db)
    {
        this.dataBase=db;
        // 数据库连接需要在线程中完成
        t = new Thread(new Runnable() {
            //@Override
            public void run() {
                try {
                    // 从连接池中获取一个连接
                    RefreshRecord.this.conn = dataBase.connPool.getConnection();
                    RefreshRecord.this.st = conn.createStatement();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void init()
    {
        getWeek1();
        getWeek2();
        getWeek3();
        getWeek4();
        getWeek5();
        getWeek6();
        getWeek7();
    }
    /**
     * start
     * 本周开始时间戳 - 以星期一为本周的第一天
     */
    public void getWeek1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale. getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        cal.add(Calendar.DATE , -day_of_week + 1 );
        mor_1s=simpleDateFormat.format(cal.getTime()) + " 00:00:00";
        mor_1e=simpleDateFormat.format(cal.getTime()) + " 10:59:59";
        noon_1s=simpleDateFormat.format(cal.getTime()) + " 11:00:00";
        noon_1e=simpleDateFormat.format(cal.getTime()) + " 15:59:59";
        eve_1s=simpleDateFormat.format(cal.getTime()) + " 16:00:00";
        eve_1e=simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }

    public void getWeek2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale. getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        cal.add(Calendar.DATE , -day_of_week + 2 );
        mor_2s=simpleDateFormat.format(cal.getTime()) + " 00:00:00";
        mor_2e=simpleDateFormat.format(cal.getTime()) + " 10:59:59";
        noon_2s=simpleDateFormat.format(cal.getTime()) + " 11:00:00";
        noon_2e=simpleDateFormat.format(cal.getTime()) + " 15:59:59";
        eve_2s=simpleDateFormat.format(cal.getTime()) + " 16:00:00";
        eve_2e=simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }


    public void getWeek3() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale. getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        cal.add(Calendar.DATE , -day_of_week + 3);
        mor_3s=simpleDateFormat.format(cal.getTime()) + " 00:00:00";
        mor_3e=simpleDateFormat.format(cal.getTime()) + " 10:59:59";
        noon_3s=simpleDateFormat.format(cal.getTime()) + " 11:00:00";
        noon_3e=simpleDateFormat.format(cal.getTime()) + " 15:59:59";
        eve_3s=simpleDateFormat.format(cal.getTime()) + " 16:00:00";
        eve_3e=simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }

    public void getWeek4() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale. getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        cal.add(Calendar.DATE , -day_of_week + 4);
        mor_4s=simpleDateFormat.format(cal.getTime()) + " 00:00:00";
        mor_4e=simpleDateFormat.format(cal.getTime()) + " 10:59:59";
        noon_4s=simpleDateFormat.format(cal.getTime()) + " 11:00:00";
        noon_4e=simpleDateFormat.format(cal.getTime()) + " 15:59:59";
        eve_4s=simpleDateFormat.format(cal.getTime()) + " 16:00:00";
        eve_4e=simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }
    public void getWeek5() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale. getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        cal.add(Calendar.DATE , -day_of_week + 5);
        mor_5s=simpleDateFormat.format(cal.getTime()) + " 00:00:00";
        mor_5e=simpleDateFormat.format(cal.getTime()) + " 10:59:59";
        noon_5s=simpleDateFormat.format(cal.getTime()) + " 11:00:00";
        noon_5e=simpleDateFormat.format(cal.getTime()) + " 15:59:59";
        eve_5s=simpleDateFormat.format(cal.getTime()) + " 16:00:00";
        eve_5e=simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }
    public void getWeek6() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale. getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        cal.add(Calendar.DATE , -day_of_week + 6);
        mor_6s=simpleDateFormat.format(cal.getTime()) + " 00:00:00";
        mor_6e=simpleDateFormat.format(cal.getTime()) + " 10:59:59";
        noon_6s=simpleDateFormat.format(cal.getTime()) + " 11:00:00";
        noon_6e=simpleDateFormat.format(cal.getTime()) + " 15:59:59";
        eve_6s=simpleDateFormat.format(cal.getTime()) + " 16:00:00";
        eve_6e=simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }
    public void getWeek7() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale. getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        cal.add(Calendar.DATE , -day_of_week + 7);
        mor_7s=simpleDateFormat.format(cal.getTime()) + " 00:00:00";
        mor_7e=simpleDateFormat.format(cal.getTime()) + " 10:59:59";
        noon_7s=simpleDateFormat.format(cal.getTime()) + " 11:00:00";
        noon_7e=simpleDateFormat.format(cal.getTime()) + " 15:59:59";
        eve_7s=simpleDateFormat.format(cal.getTime()) + " 16:00:00";
        eve_7e=simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }

    public String get_cooked_2m() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,mor_2s,mor_2e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_2n() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,noon_2s,noon_2e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    public String get_cooked_2e() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,eve_2s,eve_2e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_1m() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,mor_1s,mor_1e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_1n() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,noon_1s,noon_1e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    public String get_cooked_1e() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,eve_1s,eve_1e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public String get_cooked_3m() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,mor_3s,mor_3e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_3n() {
        String dishes="";
        //String sql_get="select dish from cooking_record  where user = '%s' and complete_time >= '%s' and complete_time<='%s'";
        try {
            String sql1=String.format(sql_get, StaticData.username,noon_3s,noon_3e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    public String get_cooked_3e() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,eve_3s,eve_3e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_4m() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,mor_4s,mor_4e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_4n() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,noon_4s,noon_4e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    public String get_cooked_4e() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,eve_4s,eve_4e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_5m() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,mor_5s,mor_5e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_5n() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,noon_5s,noon_5e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    public String get_cooked_5e() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,eve_5s,eve_5e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public String get_cooked_6m() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,mor_6s,mor_6e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_6n() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,noon_6s,noon_6e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    public String get_cooked_6e() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,eve_6s,eve_6e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_7m() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,mor_7s,mor_7e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public String get_cooked_7n() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,noon_7s,noon_7e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    public String get_cooked_7e() {
        String dishes="";
        try {
            String sql1=String.format(sql_get, StaticData.username,eve_7s,eve_7e );
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                dishes = dishes + rs.getString(1) + "\n";
                cooked.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }



}
