package com.example.pavan.re_mindme.rest.ToDoList;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pavan.re_mindme.R;
import com.example.pavan.re_mindme.rest.ToDoList.data.TaskContract;

public class AddTaskActivity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize to highest mPriority by default (mPriority = 1)
        ((RadioButton) findViewById(R.id.radio1)).setChecked(true);
        mPriority = 1;
    }
    public void onClickAddTask(View view) {
        // Not yet implemented
        // COMPLETED: 6. Check if EditText is empty, if not retrieve input and store it in a ContentValues object
        // If the EditText input is empty -> don't create an entry
        String input = ((EditText) findViewById(R.id.TaskName)).getText().toString();
        if (input.length() == 0) {
            return;
        }
// Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();

        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);

        // COMPLETED: 7. Insert new task data via a ContentResolver
        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

        // COMPLETED: 8. Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete

        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        // Finish activity (this returns back to MainActivity)
        finish();
    }


    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radio1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radio2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radio3)).isChecked()) {
            mPriority = 3;
        }
    }
}
