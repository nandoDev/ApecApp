package com.proyecto.ubicua.apecapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.proyecto.ubicua.apecapp.Modelo.Student;
import com.proyecto.ubicua.apecapp.data.ApecDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    Student student = new Student();
    String regnumberStudent;
    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String nameStudent;
        String gradeStudent;

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EXTRA_USERNAME")) {
            regnumberStudent = intent.getStringExtra("EXTRA_USERNAME");
            ApecDbHelper dbHelper = new ApecDbHelper(this);
            SQLiteDatabase data = dbHelper.getReadableDatabase();
          //  Cursor dataStudent = data.rawQuery("select * from student where regnumber = ?", new String[] {regnumberStudent});
            Cursor dataStudent = data.rawQuery("select s.regnumber,g.grade,s.namestudent,s.birth,s.address" +
                    " from student s inner join grade g on g.Idgrade = s.grade where s.regnumber = ?", new String[] {regnumberStudent});
//            Select s.regnumber,g.grade,s.namestudent, s.birth, s.address from student s
//            inner join grade g on g.Idgrade = s.grade
//            where s.regnumber = '20121452'

            if (dataStudent.moveToFirst()) {
                student.setRegnumber(dataStudent.getString(0));
                student.setGrade(dataStudent.getString(1));
                student.setNamestudent(dataStudent.getString(2));
                try {
                    student.setBirth( format.parse(dataStudent.getString(3)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                student.setAddress(dataStudent.getString(4));

//                    nameStudent= dataStudent.getString(1);
//                    gradeStudent = dataStudent.getString(6);


                ((TextView)findViewById(R.id.txtRegNumberStudent))
                        .setText(student.getRegnumber());

                ((TextView)findViewById(R.id.txtGradeStudent))
                        .setText(student.getGrade());

                ((TextView)findViewById(R.id.txtNameStudent))
                        .setText(student.getNamestudent());
            }
        }
    }

    public void HistorialOnClick (View view){
        Intent i = new Intent (this,HistoryActivity.class);
        i.putExtra("EXTRA_USERNAME", regnumberStudent);
        this.startActivity(i);
    }

    public void PerfilOnClick (View view){
        Intent i = new Intent (this,ProfileActivity.class);
        System.out.println("Prueba = " + student.getNamestudent());
        ProfileData(i,student);
        this.startActivity(i);
    }


    public void ProfileData(Intent i,Student s){
     i.putExtra("student",s);
    }

    //hola
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.historial) {
            HistorialOnClick(((TextView)findViewById(R.id.historial)));
            /*Intent i = new Intent (this,HistoryActivity.class);
            i.putExtra("EXTRA_USERNAME", regnumberStudent);
            this.startActivity(i);*/
        }
        if (id == R.id.bloques) {
            Intent i = new Intent (this,BlockActivity.class);
           this.startActivity(i);

        }

        if (id == R.id.perfil) {
            Intent i = new Intent (this,ProfileActivity.class);
            System.out.println("Prueba = " + student.getNamestudent());
            ProfileData(i,student);
           this.startActivity(i);
        }
        if (id == R.id.cerrar) {
            Intent i = new Intent (this,LoginActivity.class);
            this.startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }



}
