package com.proyecto.ubicua.apecapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.ubicua.apecapp.data.ApecDbHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerHistoryDetail, new HistoryDetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    public static class HistoryDetailFragment extends Fragment {

        private ArrayAdapter<String> ListHistorialDetailAdapter;

        public HistoryDetailFragment() {
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Add this line in order for this fragment to handle menu events.
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){

            final String regnumberStudent;
            final String quarter;
            Intent intent = getActivity().getIntent();

            if (intent != null && intent.hasExtra("EXTRA_USERNAME") && intent.hasExtra("EXTRA_QUARTER")) {
                regnumberStudent = intent.getStringExtra("EXTRA_USERNAME");
                quarter = intent.getStringExtra("EXTRA_QUARTER");
                ApecDbHelper dbHelper = new ApecDbHelper(getActivity().getApplicationContext());
                SQLiteDatabase data = dbHelper.getReadableDatabase();
                Cursor dataHistorialSubjectStudent = data.rawQuery("select su.subject\n" +
                        "from record r \n" +
                        "inner join  subject su on r.Idsubject = su.Idsubject\n" +
                        "inner join  student s on s.Idstudent = r.Idstudent\n" +
                        "inner join  quarter q on  q.Idquarter = r.Idquarter\n" +
                        "where s.regnumber = ?  and q.quarter = ? ;", new String[]{regnumberStudent,quarter});

                if (dataHistorialSubjectStudent.moveToFirst()) {

                    final List<String> ListHistorialSubjectStudent = new ArrayList<String>();
                    do {
                        ListHistorialSubjectStudent.add(dataHistorialSubjectStudent.getString(0));
                    } while (dataHistorialSubjectStudent.moveToNext());

                    // The ArrayAdapter will take data from a source and
                    // use it to populate the ListView it's attached to.
                    ListHistorialDetailAdapter =
                            new ArrayAdapter<String>(
                                    getActivity(), // The current context (this activity)
                                    R.layout.list_item_subjecthistorial, // The name of the layout ID.
                                    R.id.list_item_subjecthistorial_textview, // The ID of the textview to populate.
                                    ListHistorialSubjectStudent);

                    View rootView = inflater.inflate(R.layout.fragment_historydetail, container, false);

                    // Get a reference to the ListView, and attach this adapter to it.
                    ListView listView = (ListView) rootView.findViewById(R.id.listview_subjecthistorial);


                    ((TextView)rootView.findViewById(R.id.title_Cuatrimestre))
                            .setText(quarter);
                    listView.setAdapter(ListHistorialDetailAdapter);

                    return rootView;

                }
            }
            View rootView = inflater.inflate(R.layout.fragment_history, container, false);
            return rootView;
        }
    }
}
