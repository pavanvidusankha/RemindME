package com.example.pavan.re_mindme.PasswordManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pavan.re_mindme.R;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {

    public static final int WRAP_CONTENT_LENGTH = 50;

    public RecordAdapter(Context context, int resource, List<Record> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_record_item, null);
        }

        Record record = getItem(position);

        if (record != null) {
            TextView title = (TextView) convertView.findViewById(R.id.list_record_title);
            TextView date = (TextView) convertView.findViewById(R.id.list_record_date);
            TextView pass = (TextView) convertView.findViewById(R.id.list_record_content_preview);

            title.setText(record.getTitle());
            date.setText(record.getDateTimeFormatted(getContext()));

            //correctly show preview of the content (not more than 50 char or more than one line!)
            int toWrap = WRAP_CONTENT_LENGTH;
            int lineBreakIndex = record.getpassword().indexOf('\n');


            //not an elegant series of if statements...needs to be cleaned up!
//            if (record.getpassword().length() > WRAP_CONTENT_LENGTH || lineBreakIndex < WRAP_CONTENT_LENGTH) {
//                if (lineBreakIndex < WRAP_CONTENT_LENGTH) {
//                    toWrap = lineBreakIndex;
//                }
//                if (toWrap > 0) {
//                    pass.setText(record.getpassword().substring(0, toWrap) + "...");
//                } else {
//                    pass.setText(record.getpassword());
//                }
//            } else { //if less than 50 chars...leave it as is :P
//                pass.setText(record.getpassword());
                    pass.setText("");
//            }


        }
        return convertView;
    }
}