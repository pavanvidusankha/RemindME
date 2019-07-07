package com.example.pavan.re_mindme.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pavan.re_mindme.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mTextView;
    private EditText mTxt;
    public  static String  reminderTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mTextView  =findViewById(R.id.textView);
        mTxt=(EditText)findViewById(R.id.Text);




        Button btnTimePicker=findViewById(R.id.button);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                String input = mTxt.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(AlarmActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
                reminderTxt=mTxt.getText().toString();





            }
        });

        Button cancelbtn=findViewById(R.id.button2);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void startAlarm(Calendar c) {

        AlarmManager alarmManager=(AlarmManager)  getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);







    }

    private void updateTimeText(Calendar c) {
        String timeText ="Alarm set for :";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mTextView.setText(timeText);


    }

    private void cancelAlarm(){
        AlarmManager alarmManager=(AlarmManager)  getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm Canceled");

    }
}
