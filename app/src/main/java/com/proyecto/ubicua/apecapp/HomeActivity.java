package com.proyecto.ubicua.apecapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.ubicua.apecapp.data.ApecDbHelper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String nameStudent;
        String regnumberStudent;
        String gradeStudent;

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EXTRA_USERNAME")) {
            regnumberStudent = intent.getStringExtra("EXTRA_USERNAME");
            ApecDbHelper dbHelper = new ApecDbHelper(this);
            SQLiteDatabase data = dbHelper.getReadableDatabase();
            Cursor dataStudent = data.rawQuery("select * from student where regnumber = ?", new String[] {regnumberStudent});

            if (dataStudent.moveToFirst()) {
                do {
                    nameStudent= dataStudent.getString(1);
                    gradeStudent = dataStudent.getString(6);
                } while(dataStudent.moveToNext());

                ((TextView)findViewById(R.id.txtRegNumberStudent))
                        .setText(regnumberStudent);

                ((TextView)findViewById(R.id.txtGradeStudent))
                        .setText(gradeStudent);

                ((TextView)findViewById(R.id.txtNameStudent))
                        .setText(nameStudent);
            }
        }
    }

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
            Intent i = new Intent (this,HistoryActivity.class);
            this.startActivity(i);
        }
        if (id == R.id.bloques) {
            Intent i = new Intent (this,BlockActivity.class);
           this.startActivity(i);
        } if (id == R.id.perfil) {
            Intent i = new Intent (this,ProfileActivity.class);
           this.startActivity(i);
        }
        if (id == R.id.cerrar) {
            Intent i = new Intent (this,LoginActivity.class);
            this.startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }



}
