package com.example.homefit.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homefit.Models.Days;
import com.example.homefit.Models.Workout;
import com.example.homefit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView workoutName, dayName, name, cals;
    ImageView next;
    ImageButton startBtn;
    private Workout workoutNow;
    Workout workout;
    private List<Days> daysList;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        day = 0;
        ActionBar ab = getSupportActionBar();
        ab.setTitle("HomeFit");

        name = findViewById(R.id.name);
        cals = findViewById(R.id.calsNum);
        next = findViewById(R.id.next);
        workoutName = findViewById(R.id.workoutName);
        dayName = findViewById(R.id.dayName);

        //other activities button
        findViewById(R.id.workout_manager_btn).setOnClickListener(this);
        findViewById(R.id.food_btn).setOnClickListener(this);
        findViewById(R.id.progress_btn).setOnClickListener(this);

        //set users name
        if (mAuth.getCurrentUser().getProviderId().equals("google.com"))
            name.setText(mAuth.getCurrentUser().getDisplayName());
        else
            name.setText(mAuth.getCurrentUser().getEmail().substring(0, mAuth.getCurrentUser().getEmail().indexOf("@")));

        //initiate next image press
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = daysList.size();
                if (day < x - 1)
                    day++;
                else
                    day = 0;
                dayName.setText(daysList.get(day).getName());
            }
        });

        //start workout activity button
        startBtn = findViewById(R.id.start);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WorkingOutActivity.class);
                intent.putExtra("day", workoutNow.getDays().get(day));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) { //set on click listeners to four image view
        int i = v.getId();
        switch (i) {

            case R.id.workout_manager_btn: {
                Intent intent = new Intent(MainActivity.this, BuilderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.food_btn: {
                Intent intent = new Intent(MainActivity.this, NutriActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.progress_btn: {
                Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private int getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("GOAL", 0);
    }

    private int getDefault() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("DEF", 101);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() { //when press back on the next activity
        super.onResume();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = this.getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        final SharedPreferences.Editor mEditor = sharedPreferences.edit();
        DatabaseReference mRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("workouts");
        mRef.keepSynced(true);
        mRef.orderByChild("def").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot != null)
                        workout = childDataSnapshot.getValue(Workout.class);
                }
                if (workout != null) {
                    workoutName.setText(workout.getName());
                    daysList = workout.getDays();
                    workoutNow = workout;
                    mEditor.putInt("DEF", workoutNow.getId()); //get the default workput
                    dayName.setText(daysList.get(0).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (getData() == 0) //set marcos text
            cals.setText("Calculate your macros");
        else
            cals.setText(getData() + "");

        if (getDefault() == 101) { //set workout
            workoutName.setText("Pick a workout");
            mEditor.putString("WORK", "Pick a workout");
        } else if (workout != null) {
            workoutName.setText(workoutNow.getName());
            dayName.setText(workoutNow.getDays().get(day).getName());
            mEditor.putString("WORK", workoutNow.getName());
        }
        mEditor.apply();
    }
}
