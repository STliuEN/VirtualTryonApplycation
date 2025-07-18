package com.example.app_fittingroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login,register;
    private EditText name,password;
    private static boolean isExec = false;
    private MYsqliteopenhelper mYsqliteopenhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LinearLayout startLayout = findViewById(R.id.startPage);



        if(!isExec)
        {
            //隐藏状态栏
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            startLayout.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 在这里执行任何需要延迟的任务
                    startLayout.setVisibility(View.GONE);
                    actionBar.show();
                }
            }, 2500); // 延时2000毫秒
            isExec = true;
        }




        mYsqliteopenhelper =new MYsqliteopenhelper(this);
        find();
    }
    private  void find(){
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        name=findViewById(R.id.edname);
        password=findViewById(R.id.edpassword);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login) {
            String s = name.getText().toString();
            String s1 = password.getText().toString();
            boolean login = mYsqliteopenhelper.login(s, s1);
            if (login) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.register) {
            Intent i1 = new Intent(this, com.example.app_fittingroom.register.class);
            startActivity(i1);
        }
    }
}