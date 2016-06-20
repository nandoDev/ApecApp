package com.proyecto.ubicua.apecapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.proyecto.ubicua.apecapp.Modelo.Student;
import com.proyecto.ubicua.apecapp.data.ApecDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProfileActivity extends AppCompatActivity {

    SimpleDateFormat date = new SimpleDateFormat("dd/mm/yyyy");
    ApecDbHelper dbHelper = new ApecDbHelper(this);
    String regnumberStudent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        InfoData();
       }


    public void InfoData(){
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EXTRA_USERNAME")) {
            regnumberStudent = intent.getStringExtra("EXTRA_USERNAME");
            SQLiteDatabase data = dbHelper.getReadableDatabase();
            Cursor dataStudent = data.rawQuery("select s.regnumber,g.grade,s.namestudent,s.birth,s.address,s.phone" +
                    " from student s inner join grade g on g.Idgrade = s.Idgrade where s.regnumber = ?", new String[]{regnumberStudent});

            if (dataStudent.moveToFirst()) {
                ((TextView) findViewById(R.id.txtmatricula))
                        .setText(dataStudent.getString(0));
                ((TextView) findViewById(R.id.txtgrade))
                        .setText(dataStudent.getString(1));
                ((TextView) findViewById(R.id.txtname))
                        .setText(dataStudent.getString(2));
                ((TextView) findViewById(R.id.txtbirth))
                        .setText(dataStudent.getString(3));
                ((TextView) findViewById(R.id.txtaddress))
                        .setText(dataStudent.getString(4));
                ((TextView) findViewById(R.id.txttel))
                        .setText(dataStudent.getString(5));
            }
        }
    }

    public void UpdateOnClick (View view){
        Intent intent = new Intent(this,UpdateData.class);
        intent.putExtra("EXTRA_USERNAME",regnumberStudent);
        startActivity(intent);
        this.finish();
   }

    public void testOnClick(View v){
        final Context context = v.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.activity_update_data, null, false);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Test")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }

                        }).show();

    }

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
            this.finish();
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
