package com.example.pavan.re_mindme.PasswordManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pavan.re_mindme.R;

public class RecordActivity extends AppCompatActivity {

    private boolean mIsViewingOrUpdating; //state of the activity
    private long mNoteCreationTime;
    private String mFileName;
    private Record mLoadedRecord = null;

    private EditText mEtTitle;
    private EditText mEtUname;
    private EditText mEtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mEtTitle = (EditText) findViewById(R.id.record_et_title);
        mEtPass = (EditText) findViewById(R.id.password);
        mEtUname=(EditText) findViewById(R.id.uname);

        //check if view/edit note bundle is set, otherwise user wants to create new note
        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION)) {
            mLoadedRecord = Utilities.getRecordByFileName(getApplicationContext(), mFileName);
            if (mLoadedRecord != null) {
                //update the widgets from the loaded note
                mEtTitle.setText(mLoadedRecord.getTitle());
                mEtPass.setText(mLoadedRecord.getpassword());
                mEtUname.setText(mLoadedRecord.getuname());
                mNoteCreationTime = mLoadedRecord.getDateTime();
                mIsViewingOrUpdating = true;
            }
        } else { //user wants to create a new note
            mNoteCreationTime = System.currentTimeMillis();
            mIsViewingOrUpdating = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //load menu based on the state we are in (new, view/update/delete)
        if(mIsViewingOrUpdating) { //user is viewing or updating a note
            getMenuInflater().inflate(R.menu.menu_record_view, menu);
        } else { //user wants to create a new note
            getMenuInflater().inflate(R.menu.menu_record_add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save: //save the note
            case R.id.action_update: //or update :P
                validateAndSaveRecord();
                break;

            case R.id.action_delete:
                actionDelete();
                break;

            case R.id.action_cancel: //cancel the note
                actionCancel();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Back button press is same as cancel action...so should be handled in the same manner!
     */
    @Override
    public void onBackPressed() {
        actionCancel();
    }

    /**
     * Handle delete action
     */
    private void actionDelete() {
        //ask user if he really wants to delete the note!
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                .setTitle("delete record")
                .setMessage("Do you really want to delete the record?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mLoadedRecord != null && Utilities.deleteFile(getApplicationContext(), mFileName)) {
                            Toast.makeText(RecordActivity.this, mLoadedRecord.getTitle() + " is deleted"
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RecordActivity.this, "can not delete the record '" + mLoadedRecord.getTitle() + "'"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })
                .setNegativeButton("NO", null); //do nothing on clicking NO button :P

        dialogDelete.show();
    }

    /**
     * Handle cancel action
     */
    private void actionCancel() {

        if(!checkNoteAltred()) { //if note is not altered by user (user only viewed the note/or did not write anything)
            finish(); //just exit the activity and go back to MainActivity
        } else { //we want to remind user to decide about saving the changes or not, by showing a dialog
            AlertDialog.Builder dialogCancel = new AlertDialog.Builder(this)
                    .setTitle("discard changes...")
                    .setMessage("are you sure you do not want to save changes to this record?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish(); //just go back to main activity
                        }
                    })
                    .setNegativeButton("NO", null); //null = stay in the activity!
            dialogCancel.show();
        }
    }

    /**
     * Check to see if a loaded note/new note has been changed by user or not
     * @return true if note is changed, otherwise false
     */
    private boolean checkNoteAltred() {
        if(mIsViewingOrUpdating) { //if in view/update mode
            return mLoadedRecord != null && (!mEtTitle.getText().toString().equalsIgnoreCase(mLoadedRecord.getTitle())
                    || !mEtPass.getText().toString().equalsIgnoreCase(mLoadedRecord.getpassword())|| !mEtUname.getText().toString().equalsIgnoreCase(mLoadedRecord.getuname()));
        } else { //if in new note mode
            return !mEtTitle.getText().toString().isEmpty() || !mEtPass.getText().toString().isEmpty()|| !mEtUname.getText().toString().isEmpty();
        }
    }

    /**
     * Validate the title and content and save the note and finally exit the activity and go back to MainActivity
     */
    private void validateAndSaveRecord() {

        //get the content of widgets to make a note object
        String title = mEtTitle.getText().toString();
        String pass = mEtPass.getText().toString();
        String uname = mEtUname.getText().toString();

        //see if user has entered anything :D lol
        if(title.isEmpty()) { //title
            Toast.makeText(RecordActivity.this, "please enter a title!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(pass.isEmpty()) { //content
            Toast.makeText(RecordActivity.this, "please enter a password!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(uname.isEmpty()) { //content
            Toast.makeText(RecordActivity.this, "please enter a username!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        //set the creation time, if new note, now, otherwise the loaded note's creation time
        if(mLoadedRecord != null) {
            mNoteCreationTime = mLoadedRecord.getDateTime();
        } else {
            mNoteCreationTime = System.currentTimeMillis();
        }

        //finally save the note!
        if(Utilities.saveRecord(this, new Record(mNoteCreationTime, title, uname,pass))) { //success!
            //tell user the note was saved!
            Toast.makeText(this, "record has been saved", Toast.LENGTH_SHORT).show();
        } else { //failed to save the note! but this should not really happen :P :D :|
            Toast.makeText(this, "can not save the record. make sure you have enough space " +
                    "on your device", Toast.LENGTH_SHORT).show();
        }

        finish(); //exit the activity, should return us to MainActivity
    }
}
