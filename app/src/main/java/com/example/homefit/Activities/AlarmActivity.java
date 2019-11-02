package com.example.homefit.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.homefit.Adapters.AlertReceiver;
import com.example.homefit.Fragments.TimePickerFragment;
import com.example.homefit.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AlarmActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView chosenTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        ActionBar ab = getSupportActionBar(); //add action bar
        ab.setTitle("Set Alarm");

        chosenTimeTv = findViewById(R.id.time_tv);

        Button timePickerBtn = findViewById(R.id.open_time_picker_btn); //set time picker button
        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker =  new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        final Button cancelAlarmBtn = findViewById(R.id.cancel_alarm_btn);//set cancel alarm button
        cancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) { //set chosen time and start  alaram
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        updateTimeText(calendar);
        startAlarm(calendar);
    }

    private void updateTimeText(Calendar calendar){ //update alarm time text view
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        chosenTimeTv.setText(timeText);
    }

    private void startAlarm(Calendar calendar){ //staring the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        if(calendar.before(Calendar.getInstance())){ //to deal if the time that was chosen already passed
            calendar.add(Calendar.DATE,1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent); //fire the alarm in the exact point
    }

    private void cancelAlarm(){ //cancel the alarm the change text view
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);
        chosenTimeTv.setText("Alarm canceled");
    }
}
