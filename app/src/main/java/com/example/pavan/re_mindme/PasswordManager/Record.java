package com.example.pavan.re_mindme.PasswordManager;


import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Record implements Serializable {

    private long mDateTime; //creation time of the note
    private String mTitle; //title of the record
    private String uname; //content of the record
    private String password;
    public Record(long dateInMillis, String title, String uname,String password) {
        mDateTime = dateInMillis;
        mTitle = title;
        this.uname = uname;
        this.password=password;

    }

    public void setDateTime(long dateTime) {
        mDateTime = dateTime;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public void setuname(String uname) {
        this.uname = uname;
    }

    public long getDateTime() {
        return mDateTime;
    }

    /**
     * Get date time as a formatted string
     * @param context The context is used to convert the string to user set locale
     * @return String containing the date and time of the creation of the note
     */
    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(mDateTime));
    }

    public String getTitle() {
        return mTitle;
    }

    public String getpassword() {
        return password;
    }

    public String getuname() {
        return uname;
    }
}