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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.proyecto.ubicua.apecapp.data.ApecDbHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {


    String regnumberStudent = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("EXTRA_USERNAME")){
            regnumberStudent = intent.getStringExtra("EXTRA_USERNAME");
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new HistoryFragment())
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
        if (id == R.id.historial) {
            Intent i = new Intent (this,HomeActivity.class);
            this.startActivity(i);
        }if (id == R.id.bloques) {
            Intent i = new Intent (this,BlockActivity.class);
            i.putExtra("EXTRA_USERNAME",regnumberStudent);
            this.startActivity(i);
        } if (id == R.id.perfil) {
            Intent i = new Intent (this,ProfileActivity.class);
            i.putExtra("EXTRA_USERNAME",regnumberStudent);
            this.startActivity(i);
        }
        if (id == R.id.cerrar) {
            Intent i = new Intent (this,LoginActivity.class);
           this.startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public static class HistoryFragment extends Fragment {

        private ArrayAdapter <String> ListHistorialAdapter;

        public HistoryFragment() {
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
            Intent intent = getActivity().getIntent();

            if (intent != null && intent.hasExtra("EXTRA_USERNAME")) {
                regnumberStudent = intent.getStringExtra("EXTRA_USERNAME");
                ApecDbHelper dbHelper = new ApecDbHelper(getActivity().getApplicationContext());
                SQLiteDatabase data = dbHelper.getReadableDatabase();
                Cursor dataHistorialStudent = data.rawQuery("select distinct q.Idquarter, q.quarter \n" +
                        "from record r \n" +
                        "inner join quarter q on r.Idquarter = q.Idquarter\n" +
                        "inner join student s on r.Idstudent = s.Idstudent\n" +
                        "where s.regnumber  = ? \n" +
                        "order by q.Idquarter asc;", new String[]{regnumberStudent});

                if (dataHistorialStudent.moveToFirst()) {

                    final List<String> ListHistorialStudent = new ArrayList<String>();
                    do {
                        ListHistorialStudent.add(dataHistorialStudent.getString(1));
                    } while (dataHistorialStudent.moveToNext());

                    // The ArrayAdapter will take data from a source and
                    // use it to populate the ListView it's attached to.
                    ListHistorialAdapter =
                            new ArrayAdapter<String>(
                                    getActivity(), // The current context (this activity)
                                    R.layout.list_item_quarterhistorial, // The name of the layout ID.
                                    R.id.list_item_quarterhistorial_textview, // The ID of the textview to populate.
                                    ListHistorialStudent);

                    View rootView = inflater.inflate(R.layout.fragment_history, container, false);

                    // Get a reference to the ListView, and attach this adapter to it.
                    ListView listView = (ListView) rootView.findViewById(R.id.listview_quarterhistorial);
                    listView.setAdapter(ListHistorialAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            String quarter = ListHistorialAdapter.getItem(position);
                            Intent intent = new Intent(getActivity(), HistoryDetailActivity.class);
                            intent.putExtra("EXTRA_QUARTER", quarter);
                            intent.putExtra ("EXTRA_USERNAME", regnumberStudent);
                            startActivity(intent);
                        }
                    });

                    return rootView;

                }
            }
            View rootView = inflater.inflate(R.layout.fragment_history, container, false);
            return rootView;
        }
    }
}
