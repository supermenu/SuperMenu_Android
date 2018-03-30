package com.example.lenovo.mytodolist;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * 注册界面后的详细信息填写界面
 */
public class DetailsInfoActivity extends AppCompatActivity {
    EditText height,weight;
    RadioGroup sex;
    Button finish;

    String  string_height,string_weight,string_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info);
        height=(EditText)findViewById(R.id.heightinfo);
        weight=(EditText)findViewById(R.id.weightinfo);
        sex=(RadioGroup)findViewById(R.id.sex);
        finish=(Button)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check())
                {
                    Toast.makeText(getApplicationContext(),"信息未填写完整",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    register_finish();
                }
            }
        });
    }


    public void register_finish(){
            /*
            注册成功，启动login activity
             */
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //参数是包名，类全限定名，注意直接用类名不行
        ComponentName cn=new ComponentName("com.example.lenovo.mytodolist",
                "com.example.lenovo.mytodolist.loginActivity");
        intent.setComponent(cn);
        startActivity(intent);

    }

    //检查是否所有信息填写完整
    public boolean check()
    {
        string_height=height.getText().toString();
        string_weight=weight.getText().toString();
        if(string_height.equals("")||string_weight.equals(""))
            return false;
        if(sex.getCheckedRadioButtonId()==-1)
            return false;
        else if(sex.getCheckedRadioButtonId()==R.id.man) {
            string_sex= "男";
            return true;
        }
        else{
            string_sex="女";
        return true;
        }
    }
}


