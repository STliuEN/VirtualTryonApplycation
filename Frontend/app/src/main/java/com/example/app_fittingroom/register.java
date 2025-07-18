package com.example.app_fittingroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_fittingroom.javabean.User;

public class register extends AppCompatActivity {
    private Button register1;
    private EditText name1,password1;
    private  MYsqliteopenhelper mYsqliteopenhelper1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mYsqliteopenhelper1 =new MYsqliteopenhelper(this);
        find();
    }

    private void find() {
        register1=findViewById(R.id.register1);
        name1=findViewById(R.id.edname1);
        password1=findViewById(R.id.edpassword1);

    }
    public void zhuce(View view){
        String s = name1.getText().toString();
        String s1=password1.getText().toString();
        User u = new User(s,s1);
        long l = mYsqliteopenhelper1.register(u);
        if(l!=-1){
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent i3 =new Intent(this,LoginActivity.class);
            startActivity(i3);
        }else{
            Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
        }
    }
}