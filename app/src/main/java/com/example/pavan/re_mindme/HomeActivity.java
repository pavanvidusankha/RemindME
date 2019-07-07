package com.example.pavan.re_mindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.pavan.re_mindme.Notes.NotesMainActivity;
import com.example.pavan.re_mindme.PasswordManager.RecordsMainActivity;
import com.example.pavan.re_mindme.Reminder.AlarmActivity;
import com.example.pavan.re_mindme.ToDoList.TodoListMainActivity;
import com.example.pavan.re_mindme.Countdown.CountDTMainActivity;

public class HomeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.action_settings:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ReminderOnclick(View view){
        Intent intent=new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    public void TodoOnclick(View view){
        Intent intent=new Intent(this, TodoListMainActivity.class);
        startActivity(intent);
    }

    public void NotesOnclick(View view){
        Intent intent=new Intent(this, NotesMainActivity.class);
        startActivity(intent);
    }

    public void CountdownOnclick(View view){
        Intent intent=new Intent(this, CountDTMainActivity.class);
        startActivity(intent);
    }

    public void PassManOnclick(View view){
        Intent intent=new Intent(this, RecordsMainActivity.class);
        startActivity(intent);
    }
}
