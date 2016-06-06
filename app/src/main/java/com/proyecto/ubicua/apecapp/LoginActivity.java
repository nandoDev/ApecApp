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

public class LoginActivity extends AppCompatActivity {

    EditText passField;
    EditText userField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void LoginOnClick(View view) {

        userField = (EditText) findViewById(R.id.etUsuario);
        passField = (EditText) findViewById(R.id.etPassword);

        String uname = userField.getText().toString();
        String pass = passField.getText().toString();

        boolean check = checkCredentials(uname, pass);
        if(check)
        {
            clear();
            Toast.makeText(getApplicationContext(),"LOGIN EXITOSO",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (getApplicationContext(),HomeActivity.class);
            intent.putExtra("EXTRA_USERNAME", uname);
            startActivity(intent);
        }
        else
            clear();
            Toast.makeText(getApplicationContext(),"LOGIN FALLIDO",Toast.LENGTH_SHORT).show();
    }

    public void clearClick(View view) {
        userField.setText("");
        passField.setText("");
        userField.setHint("usuario");
        passField.setHint("contraseña");
    }

    public void clear() {
        userField.setText("");
        passField.setText("");
        userField.setHint("USERNAME");
        passField.setHint("PASSWORD");
    }

    public boolean checkCredentials(String username, String password){

        ApecDbHelper dbHelper = new ApecDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student", null);
        cursor.moveToFirst();
        String count = String.valueOf(cursor.getCount());
        for (int i=0; i < cursor.getCount();i++){
            if (username.equalsIgnoreCase(cursor.getString(2))&& password.equalsIgnoreCase(cursor.getString(3))){
                db.close();
                return true;
            }
            cursor.moveToNext();
        }
        db.close();
        return false;
    }
}
