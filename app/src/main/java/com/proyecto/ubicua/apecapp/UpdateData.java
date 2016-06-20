package com.proyecto.ubicua.apecapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.ubicua.apecapp.Modelo.Student;
import com.proyecto.ubicua.apecapp.data.ApecDbHelper;

public class UpdateData extends AppCompatActivity {

    String regnumberStudent = "";
    ApecDbHelper dbHelper = new ApecDbHelper(this);
    Student student = new Student();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        regnumberStudent = getIntent().getStringExtra("EXTRA_USERNAME");
        onLoadData();
    }

    public void CancelarOnClick(View view){
        this.finish();
    }


    public void UpdateOnClick(View view){

        EditText name = (EditText) findViewById(R.id.txtNombre);
        EditText address = (EditText) findViewById(R.id.TxtAddress);
        EditText phone = (EditText) findViewById(R.id.txtPhone);

        student.setRegnumber(this.regnumberStudent);
        student.setNamestudent(name.getText().toString());
        student.setAddress(address.getText().toString());
        student.setPhone(phone.getText().toString());

        if(UpdateData(student)){
            Toast.makeText(getApplicationContext(), "Tus datos han sido modificado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ProfileActivity.class);
            intent.putExtra("EXTRA_USERNAME",regnumberStudent);
            startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo modificar tus datos", Toast.LENGTH_SHORT).show();
        }

    }


    public Boolean UpdateData(Student s){

        boolean esModificado = true;
        ContentValues values = new ContentValues();
        values.put("namestudent", s.getNamestudent());
        values.put("address",s.getAddress());
        values.put("phone", s.getPhone());
        String where = "regnumber = ?";
        String[] whereArgs = {(s.getRegnumber())};

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        esModificado = db.update("student",values,where,whereArgs) > 0;
        return esModificado;
    }


    public void onLoadData(){

        SQLiteDatabase data = dbHelper.getReadableDatabase();
        Cursor dataStudent = data.rawQuery("select s.namestudent,s.address,s.phone" +
                " from student s inner join grade g on g.Idgrade = s.Idgrade where s.regnumber = ?", new String[]{regnumberStudent});

        if (dataStudent.moveToFirst()) {
            ((TextView) findViewById(R.id.txtNombre))
                    .setText(dataStudent.getString(0));
            ((TextView) findViewById(R.id.TxtAddress))
                    .setText(dataStudent.getString(1));
            ((TextView) findViewById(R.id.txtPhone))
                    .setText(dataStudent.getString(2));
        }
    }
}
