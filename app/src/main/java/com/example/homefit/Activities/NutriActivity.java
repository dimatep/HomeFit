package com.example.homefit.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.homefit.Fragments.FragANutri;
import com.example.homefit.Fragments.FragBNutri;
import com.example.homefit.R;

public class NutriActivity extends BaseActivity {

    //Total Daily Energy Expenditure, a measure of how many calories you burn per day.
    //This calculator will also display your Macros many other useful statistics!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutri);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Nutrition & Macros");
        if (getData() == 0 && savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, FragANutri.newInstance())
                    .commit();
        } else if (getData() != 0) {
            Bundle bundle = new Bundle();
            bundle.putDouble("WEIGHT", getData2());
            bundle.putInt("TDEE", getData());
            bundle.putInt("GOAL", getData3());
            FragBNutri simpleFragmentB = FragBNutri.newInstance();
            simpleFragmentB.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, simpleFragmentB)
                    .commit();
        }
    }

    private int getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("TDEE", 0);
    }

    private double getData2() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return Double.valueOf(sharedPreferences.getString("WEIGHT", "0"));
    }

    private int getData3() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("GOAL", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reset, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                erase();
        }
        return super.onOptionsItemSelected(item);
    }

    private void erase() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putInt("TDEE", 0);
        mEditor.putString("WEIGHT", "0");
        mEditor.putInt("GOAL", 0);
        mEditor.apply();
        finish();
        startActivity(getIntent());
    }
}
