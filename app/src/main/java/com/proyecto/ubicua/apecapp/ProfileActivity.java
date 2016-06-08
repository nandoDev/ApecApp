package com.proyecto.ubicua.apecapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.ubicua.apecapp.Modelo.Student;

public class ProfileActivity extends AppCompatActivity {

    Student s = new Student();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("student")) {
            s = intent.getParcelableExtra("student");
        }
       // System.out.println("PRUEBA: " + s.getNamestudent());
        ((TextView)findViewById(R.id.textView2))
                .setText("En Construccion");

        ((TextView)findViewById(R.id.textView10))
                .setText("En Construccion");

        ((TextView)findViewById(R.id.textView12))
                .setText("En Construccion");
    }

//    Select s.namestudent, s.regnumber, s.birth, s.address, g.grade  from student s
//    inner join grade g on g.Idgrade = s.grade


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
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
        }
        if (id == R.id.home) {
            Intent i = new Intent (this,HomeActivity.class);
            this.startActivity(i);
        }
        if (id == R.id.perfil) {
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
