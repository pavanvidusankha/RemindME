package com.example.pavan.re_mindme.PasswordManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pavan.re_mindme.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordsMainActivity extends AppCompatActivity {

    private ListView mListRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_main);

        mListRecords =  findViewById(R.id.main_Reclistview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.records_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create: //run RecordActivity in new note mode
                startActivity(new Intent(this, RecordActivity.class));
                break;

            case R.id.action_settings:
                //TODO show settings activity
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //load saved notes into the listview
        //first, reset the listview
        mListRecords.setAdapter(null);
        ArrayList<Record> records = Utilities.getAllSavedRecords(getApplicationContext());

        //sort notes from new to old
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record lhs, Record rhs) {
                if (lhs.getDateTime() > rhs.getDateTime()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        if(records != null && records.size() > 0) { //check if we have any notes!
            final RecordAdapter na = new RecordAdapter(this, R.layout.view_record_item, records);
            mListRecords.setAdapter(na);

            //set click listener for items in the list, by clicking each item the note should be loaded into RecordActivity
            mListRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //run the RecordActivity in view/edit mode
                    String fileName = ((Record) mListRecords.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), RecordActivity.class);
                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                    startActivity(viewNoteIntent);
                }
            });
        } else { //remind user that we have no notes!
            Toast.makeText(getApplicationContext(), "you have no saved records!\ncreate some new records :)"
                    , Toast.LENGTH_SHORT).show();
        }
    }







}
