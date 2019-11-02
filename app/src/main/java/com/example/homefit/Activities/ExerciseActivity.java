package com.example.homefit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.homefit.Fragments.ExerciseFragment;
import com.example.homefit.Models.Workout;
import com.example.homefit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class ExerciseActivity extends BaseActivity {

    private static final int REQUEST_CODE = 111;
    private static Workout exercise;
    FloatingActionButton fab;
    FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ActionBar ab = getSupportActionBar(); //add action bar
        fab = findViewById(R.id.fab);

        Bundle b =  new Bundle();
        b = getIntent().getExtras();
        exercise = b.getParcelable("workout");
        ab.setTitle(exercise.getName());


        FragmentPagerItems pages = new FragmentPagerItems(this);

        String[] exList = getDayNames();
        for (int i = 0; i < exList.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("w", exercise.getDays().get(i));
            pages.add(FragmentPagerItem.of(exList[i], ExerciseFragment.class, bundle));
        }

        final ViewPager viewPager = findViewById(R.id.pager);
        SmartTabLayout viewPagerTab = findViewById(R.id.viewpagertab);

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        //deal with view pager
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
        for (int i = 0; i < pages.size(); i++)
            viewPagerTab.getTabAt(i).setBackground(null);

        displayFloatingActionButtonIfNeeded(viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            } //do nothing

            @Override
            public void onPageSelected(int position) {

            } //do nothing

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_SETTLING:
                        displayFloatingActionButtonIfNeeded(viewPager.getCurrentItem());
                        break;

                    case ViewPager.SCROLL_STATE_IDLE:
                        displayFloatingActionButtonIfNeeded(viewPager.getCurrentItem());
                        break;

                    default:
                        fab.hide();
                }
            }
        });
    }

    private void displayFloatingActionButtonIfNeeded(final int position) {
        if (adapter.getItem(position) instanceof ExerciseFragment) {

            final ExerciseFragment floatingActionButtonFragment = (ExerciseFragment) adapter.getItem(position);

            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExerciseActivity.this, MuscleActivity.class);
                    intent.putExtra("currentWorkout", exercise);
                    intent.putExtra("currentDay", position);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Workout result = data.getExtras().getParcelable("currentWorkout");
                Bundle bundle = new Bundle();
                bundle.putParcelable("workout", result);
                Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                ExerciseActivity.this.finish();
            }
        }
    }

    public static String[] getDayNames() {
        String[] dayNames = new String[exercise.getSize()];
        for (int i = 0; i < exercise.getSize(); i++)
            dayNames[i] = exercise.getDays().get(i).getName();

        return dayNames;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //click on save btn
        switch (item.getItemId()) {
            case R.id.save:
                save();
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() { //save all the data to user's database in firebase
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("workouts");
        mRef.keepSynced(true);
        mRef.child(String.valueOf(exercise.getId())).setValue(exercise);
        Toast.makeText(this, "Your changes were successfully saved!", Toast.LENGTH_SHORT).show();
    }
}
