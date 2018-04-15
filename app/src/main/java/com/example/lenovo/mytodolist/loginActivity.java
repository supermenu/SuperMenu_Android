package com.example.lenovo.mytodolist;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
    User user;
    String loginname;
    TextView re;
    String name;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_userName=(EditText)findViewById(R.id.et_userName);
        et_password=(EditText)findViewById(R.id.et_password);
        cb_checkbox=(CheckBox)findViewById(R.id.cb_checkbox);
        iv_pwdClear=(ImageView) findViewById(R.id.iv_pwdClear);
        iv_unameClear=(ImageView) findViewById(R.id.iv_unameClear);
        re=(TextView)findViewById(R.id.tv_register) ;
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        login=(Button)findViewById(R.id.btn_login) ;
        et_userName.setText("admin");
        et_password.setText("123456");
        user = null;
        login.setOnClickListener(new ButtonClickListener());
        /*
        当editview输入时，出现删除按钮，可以点击清空
         */
        EditTool.addClearListener(et_userName,iv_unameClear);
        EditTool.addClearListener(et_password,iv_pwdClear);

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

    //密码比对//
    class ButtonClickListener implements View.OnClickListener{
        public void onClick(View view){
            name=et_userName.getText().toString();
            pwd=et_password.getText().toString();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    StaticData.datapool= new DataBase();//连接池
                    try {
                        StaticData.datapool.tbase.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user = new User(StaticData.username,StaticData.datapool);
                    try {
                        user.t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            if(user.verify_password(pwd)){
                login_succeed();
            }
            if(name.equals("admin")&&pwd.equals("123456")) {
                loginname=name;//获得用户名
                login_succeed();
            }
            else
                login_fail();//登陆失败出现提示
            if(name.equals("")||pwd.equals(""))
            {
                Toast toast=Toast.makeText(getApplicationContext(),"请先输入",Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        public void login_succeed(){
            /*
               登陆成功，启动主activity
             */
            //密码比对成功后需要将用户名传给StaticData
            // 同时去掉MainActivity中username的默认小羊苏珊，以及MainActivity对StaticData的初始化
            //StaticData.username=new String(this.loginname);
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            //参数是包名，类全限定名，注意直接用类名不行
            ComponentName cn=new ComponentName("com.example.lenovo.mytodolist",
                    "com.example.lenovo.mytodolist.MainActivity");
            intent.setComponent(cn);
            //传递用户名字符串
            intent.putExtra("username",name);
            startActivity(intent);
            finish();

        }


        public void login_fail(){

            Toast toast=Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
