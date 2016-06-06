package com.proyecto.ubicua.apecapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.ubicua.apecapp.data.ApecDbHelper;

public class MainActivity extends AppCompatActivity {

        EditText passField;
        EditText userField;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            userField = (EditText) findViewById(R.id.userField);
            passField = (EditText) findViewById(R.id.passwordField);

            //TextView textView = (TextView) findViewById(R.id.textView);

            ApecDbHelper dbHelper = new ApecDbHelper(this);

            SQLiteDatabase db= dbHelper.getReadableDatabase();
            db.close();

            SQLiteDatabase database = openOrCreateDatabase("ApecDb.db", MODE_PRIVATE,null);



            database.execSQL("create  table if not exists usuarios (user, password)");
            database.execSQL("insert into usuarios values ('nando05','@123')");
            database.execSQL("insert into usuarios values ('rodiel1','@45')");
            database.execSQL("insert into usuarios values ('cristian1','@14')");
            database.execSQL("insert into usuarios values ('cindy4','@88')");
            database.execSQL("insert into usuarios values ('willi4','@77')");

            database.close();
        }

        public void loginClick(View view) {
            String uname = userField.getText().toString();
            String pass = passField.getText().toString();

            boolean check = checkCredentials(uname, pass);
            if(check)
            {
                Toast.makeText(getApplicationContext(),"LOGIN EXITOSO",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent ("com.proyecto.ubicua.apecapp.HomeActivity");
                Intent intent = new Intent (getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(),"LOGIN FALLIDO",Toast.LENGTH_SHORT).show();
        }

        public void clearClick(View view) {
            userField.setText("");
            passField.setText("");
            userField.setHint("USERNAME");
            passField.setHint("PASSWORD");
        }

        public boolean checkCredentials(String username, String password){

            SQLiteDatabase db= openOrCreateDatabase("ApecDb.db", MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("select * from usuarios", null);

            cursor.moveToFirst();
            /*int i=0;
            while (!cursor.isAfterLast()) {
                if (username.equalsIgnoreCase(cursor.getString(0))&& password.equalsIgnoreCase(cursor.getString(1))){
                    db.close();
                    return true;
                }
                i++;
                cursor.moveToNext();
            }*/



            for (int i=0; i < cursor.getCount();i++){
                if (username.equalsIgnoreCase(cursor.getString(0))&& password.equalsIgnoreCase(cursor.getString(1))){
                    db.close();
                    return true;
                 }
                i++;
                cursor.moveToNext();
                cursor.close();
            }
            db.close();
            return false;
        }
     }

