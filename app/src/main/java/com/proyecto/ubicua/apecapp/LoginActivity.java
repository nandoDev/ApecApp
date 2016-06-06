package com.proyecto.ubicua.apecapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.proyecto.ubicua.apecapp.data.ApecDbContract;

public class LoginActivity extends AppCompatActivity {

    ApecDbContract.StudentEntry Se;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Se = new ApecDbContract.StudentEntry();

    }


    public void LoginOnClick(View view){
        Intent Home = new Intent (this,HomeActivity.class);
        this.finish();
        this.startActivity(Home);





    }
}
