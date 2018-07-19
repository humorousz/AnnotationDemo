package com.humorous.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.humorous.bindview.OnClick;
import com.humorous.bindview_api.ViewInjector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInjector.inject(this);
    }

    @OnClick(R.id.btn1)
    public void handle(){
        Toast.makeText(this,"handle",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn2)
    public void handle2(){
        Toast.makeText(this,"handle2",Toast.LENGTH_SHORT).show();
    }

}
