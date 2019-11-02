package com.example.homefit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.example.homefit.Fragments.FragAMuscle;
import com.example.homefit.Models.Workout;
import com.example.homefit.R;

public class MuscleActivity extends BaseActivity {

    public Workout workout;
    public int dayNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle);
        //action bar
        ActionBar ab  =  getSupportActionBar();
        ab.setTitle("HomeFit");

        //get extras
        dayNum = getIntent().getExtras().getInt("currentDay");
        workout = getIntent().getExtras().getParcelable("currentWorkout");

        FragAMuscle simpleA = FragAMuscle.newInstance();
        //set fragment
        getSupportFragmentManager().beginTransaction().add(R.id.content, simpleA).commit();
    }

    @Override
    public void onBackPressed() { //press on back button
        Fragment f = MuscleActivity.this.getSupportFragmentManager().findFragmentById(R.id.content);
        if (f instanceof FragAMuscle) {
            Intent intent = new Intent();
            intent.putExtra("currentWorkout", workout);
            setResult(RESULT_OK, intent);
            finish();
        }
        super.onBackPressed();
    }
}
