package com.example.lenovo.mytodolist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录界面
 */
public class loginActivity extends AppCompatActivity {
    EditText et_userName,et_password;
    CheckBox cb_checkbox;
    ImageView iv_pwdClear,iv_unameClear;
    Button login;
    User user = null;
    String loginname;
    TextView re;
    String name;
    String pwd, pwd_md5;
    TextView fail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_userName = findViewById(R.id.et_userName);
        et_password = findViewById(R.id.et_password);
        //cb_checkbox=(CheckBox)findViewById(R.id.cb_checkbox);
        iv_pwdClear = findViewById(R.id.iv_pwdClear);
        iv_unameClear = findViewById(R.id.iv_unameClear);
        re = findViewById(R.id.tv_register);
        fail = findViewById(R.id.tv_fail);
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        login = findViewById(R.id.btn_login);
        initPool();
        precheck();
        login.setOnClickListener(new ButtonClickListener());
        /*
        当editview输入时，出现删除按钮，可以点击清空
         */
        EditTool.addClearListener(et_userName,iv_unameClear);
        EditTool.addClearListener(et_password,iv_pwdClear);

    }

    void initPool() {
        Thread newpool = new Thread(new Runnable() {
            @Override
            public void run() {
                StaticData.datapool = new DataBase();//连接池
                try {
                    StaticData.datapool.tbase.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        newpool.start();
        try {
            newpool.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void register(){
            /*
            启动registeractivity，注册界面
             */
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //参数是包名，类全限定名，注意直接用类名不行
        ComponentName cn=new ComponentName("com.example.lenovo.mytodolist",
                "com.example.lenovo.mytodolist.RegisterActivity");
        intent.setComponent(cn);
        startActivity(intent);

    }

    public void login_succeed(){
        /*
         * 登陆成功，启动主activity
         *
         * 密码比对成功后需要将用户名传给StaticData同时去掉MainActivity中username的默认小羊苏珊
         *
         * 以及MainActivity对StaticData的初始化
         */
        //  fail.setVisibility(View.INVISIBLE);
        StaticData.username = new String(this.loginname);
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //参数是包名，类全限定名，注意直接用类名不行
        ComponentName cn=new ComponentName("com.example.lenovo.mytodolist",
                "com.example.lenovo.mytodolist.MainActivity");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //传递用户名字符串
        // intent.putExtra("username",name);
        startActivity(intent);
        finish();
    }

    public void login_fail(){
        fail.setVisibility(View.VISIBLE);
        Toast toast=Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT);
        toast.show();
    }

    void precheck() {
        SharedPreferences sPreferences = getSharedPreferences("config", MODE_PRIVATE);
        if (sPreferences != null) {
            final String username = sPreferences.getString("username", "");
            final String password = sPreferences.getString("password", "");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    user = new User(username, StaticData.datapool);
                    try {
                        user.t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (user.check_md5password(password)) {
                        loginname = username;
                        user.finish();
                        login_succeed();
                    } else {
                        user.finish();
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 保存用户名 密码的业务方法
     *
     * @param context  上下文
     * @param username 用户名
     * @param pas      密码
     * @return true 保存成功  false 保存失败
     */
    public void saveUserInfo(Context context, String username, String pas) {
        /**
         * SharedPreferences将用户的数据存储到该包下的shared_prefs/config.xml文件中，
         * 并且设置该文件的读取方式为私有，即只有该软件自身可以访问该文件
         */
        SharedPreferences sPreferences = context.getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPreferences.edit();
        //当然sharepreference会对一些特殊的字符进行转义，使得读取的时候更加准确
        editor.putString("username", loginname);
        editor.putString("password", pwd_md5);
        //切记最后要使用commit方法将数据写入文件
        editor.commit();
    }

    class ButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            name = et_userName.getText().toString();
            pwd = et_password.getText().toString();
            if (name.equals("") || pwd.equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), "请先输入", Toast.LENGTH_SHORT);
                toast.show();
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    user = new User(name, StaticData.datapool);
                    try {
                        user.t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (user.verify_password(pwd)) {
                        loginname = name;
                        pwd_md5 = user.password_hash;
                        user.finish();
                        saveUserInfo(loginActivity.this, loginname, pwd_md5);
                        login_succeed();
                    } else {
                        user.finish();
                        login_fail();//登陆失败出现提示
                    }
                }
            });
            thread.start();
        }
    }

}
