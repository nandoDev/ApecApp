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

    //sdsd
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
        if (check) {
            clear();
            Toast.makeText(getApplicationContext(), "LOGIN EXITOSO", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("EXTRA_USERNAME", uname);
            startActivity(intent);
        } else {
            clear();
            Toast.makeText(getApplicationContext(), "LOGIN FALLIDO", Toast.LENGTH_SHORT).show();
        }
    }


    public void clear() {
        userField.setText("");
        passField.setText("");
        userField.setHint("USERNAME");
        passField.setHint("PASSWORD");
    }

    public boolean checkCredentials(String username, String password) {

//        Select s.regnumber, s.pass  from student s
//        where s.regnumber = '20131366' and s.pass = '@123'
        //Cursor cursor = db.rawQuery("Select s.regnumber,s.pass from student s where s.regnumber = '"+ username + "' and s.pass='"+ password +"'", null);
        Boolean acceso = false;
        ApecDbHelper dbHelper = new ApecDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student s where s.regnumber = " + username + " and " + "s.pass = '" + password + "'", null);
        cursor.moveToFirst();
        Integer count = cursor.getCount();
        if (count == 0) {
            acceso = false;
        } else if (username.equalsIgnoreCase(cursor.getString(2)) && password.equalsIgnoreCase(cursor.getString(3))) {
            db.close();
            acceso = true;
        }
//        for (int i=0; i < cursor.getCount();i++){
//            if (username.equalsIgnoreCase(cursor.getString(2))&& password.equalsIgnoreCase(cursor.getString(3))){
//                db.close();
//                return true;
//            }
//            cursor.moveToNext();
//        }
//        db.close();
//        return false;
        return acceso;
    }
}
