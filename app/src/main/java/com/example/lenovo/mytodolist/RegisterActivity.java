package com.example.lenovo.mytodolist;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    ImageButton next;
    EditText et_pw, et_username, et_pwconfirm;
    TextView tishi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        next=(ImageButton)findViewById(R.id.reg_singup);
        et_pw=(EditText)findViewById(R.id.reg_password);
        et_pwconfirm=(EditText)findViewById(R.id.reg_confirm_password);
        et_username=(EditText)findViewById(R.id.reg_account) ;
        tishi=(TextView)findViewById(R.id.tishi);
        et_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tishi.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw,pw_con;
                String user;
                pw= et_pw.getText().toString();
                pw_con=et_pwconfirm.getText().toString();
                user=et_username.getText().toString();
                if(user.equals(""))
                {
                    tishi.setText("用户名不能为空");
                    tishi.setVisibility(View.VISIBLE);
                }
                else if((!pw_con.equals(pw))||pw.equals(""))
                {
                    tishi.setText("密码不匹配");
                    tishi.setVisibility(View.VISIBLE);
                }
                else
                    next();

            }
        });

    }

    public void next(){
            /*
            启动DetailsInfoActivity
             */
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //参数是包名，类全限定名，注意直接用类 名不行
        ComponentName cn=new ComponentName("com.example.lenovo.mytodolist",
                "com.example.lenovo.mytodolist.DetailsInfoActivity");
        intent.setComponent(cn);
        startActivity(intent);

    }
}


