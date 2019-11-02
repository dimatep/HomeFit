package com.example.homefit.Activities;

import android.app.Dialog;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homefit.Adapters.BodyWeightAdapter;
import com.example.homefit.Models.Weight;
import com.example.homefit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;
import com.robinhood.spark.animation.LineSparkAnimator;

import java.util.LinkedList;
import java.util.List;

public class ProgressActivity extends BaseActivity {

    private SparkView sparkView;
    private RecyclerView recyclerView;
    private TextView scrubInfoTextView;
    public BodyWeightAdapter adapterW;
    private WeightAdapter weightAdapter;
    private List<Weight> tempList;
    private TextView week, month, year;
    private View line;
    private int paddingRight;
    Dialog weightDialog;
    private DatabaseReference mRef = null;
    private List<Weight> weightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Progress");
        sparkView = findViewById(R.id.sparkView);
        sparkView.setAdapter(weightAdapter);
        week = findViewById(R.id.week);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        line = findViewById(R.id.line);
        FloatingActionButton fab = findViewById(R.id.fab_weight);

        recyclerView = findViewById(R.id.weightRV);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        scrubInfoTextView = findViewById(R.id.scrub_info_textview);
        mRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());
        mRef.keepSynced(true);
        weightList = new LinkedList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(llm);

        mRef.child("weight").orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Weight weight = childDataSnapshot.getValue(Weight.class);
                    weightList.add(weight);

                }
                adapterW = new BodyWeightAdapter(weightList);
                recyclerView.setAdapter(adapterW);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("NOPE", "Failed to read value.", error.toException());
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line.animate().translationX(0).withLayer();
                week.setTextColor(getResources().getColor(R.color.colorPrimary));
                month.setTextColor(getResources().getColor(R.color.white));
                year.setTextColor(getResources().getColor(R.color.white));
                sparkView.setSparkAnimator(new LineSparkAnimator());
                tempList = new LinkedList<>();
                tempList.clear();
                if (weightList.size() <= 7) {
                    tempList = weightList;
                    paddingRight = 350 - Math.round(350 * tempList.size() / (float) 7);
                    sparkView.setPadding(sparkView.getPaddingLeft(), sparkView.getPaddingTop(), dpToPx(paddingRight), sparkView.getPaddingBottom());

                } else {
                    for (int b = 7; b > 0; b--) {
                        tempList.add(weightList.get(weightList.size() - b));
                    }
                    sparkView.setPadding(sparkView.getPaddingLeft(), sparkView.getPaddingTop(), sparkView.getPaddingTop(), sparkView.getPaddingBottom());
                }
                weightAdapter = new WeightAdapter(tempList);
                sparkView.setAdapter(weightAdapter);
                sparkView.setLineWidth(4);
                weightAdapter.init(tempList);
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line.animate().translationX(145).withLayer();
                month.setTextColor(getResources().getColor(R.color.colorPrimary));
                week.setTextColor(getResources().getColor(R.color.white));
                year.setTextColor(getResources().getColor(R.color.white));
                sparkView.setSparkAnimator(new LineSparkAnimator());
                tempList = new LinkedList<>();
                tempList.clear();
                if (weightList.size() <= 30) {
                    tempList = weightList;
                    paddingRight = 350 - Math.round(350 * tempList.size() / (float) 30);
                    sparkView.setPadding(sparkView.getPaddingLeft(), sparkView.getPaddingTop(), dpToPx(paddingRight), sparkView.getPaddingBottom());

                } else {
                    for (int b = 30; b > 0; b--) {
                        tempList.add(weightList.get(weightList.size() - b));
                    }
                    sparkView.setPadding(sparkView.getPaddingLeft(), sparkView.getPaddingTop(), sparkView.getPaddingTop(), sparkView.getPaddingBottom());

                }
                weightAdapter = new WeightAdapter(tempList);
                sparkView.setAdapter(weightAdapter);
                sparkView.setLineWidth(4);
                weightAdapter.init(tempList);
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line.animate().translationX(275).withLayer();
                year.setTextColor(getResources().getColor(R.color.colorPrimary));
                month.setTextColor(getResources().getColor(R.color.white));
                week.setTextColor(getResources().getColor(R.color.white));
                sparkView.setSparkAnimator(new LineSparkAnimator());
                tempList = new LinkedList<>();
                tempList.clear();
                if (weightList.size() <= 365) {
                    tempList = weightList;
                    paddingRight = 350 - Math.round(350 * tempList.size() / (float) 365);
                    sparkView.setPadding(sparkView.getPaddingLeft(), sparkView.getPaddingTop(), dpToPx(paddingRight), sparkView.getPaddingBottom());

                } else {
                    for (int b = 365; b > 0; b--) {
                        tempList.add(weightList.get(weightList.size() - b));
                    }
                    sparkView.setPadding(sparkView.getPaddingLeft(), sparkView.getPaddingTop(), sparkView.getPaddingTop(), sparkView.getPaddingBottom());

                }
                weightAdapter = new WeightAdapter(tempList);
                sparkView.setAdapter(weightAdapter);
                sparkView.setLineWidth(1);
                weightAdapter.init(tempList);
            }
        });

        sparkView.setScrubListener(new SparkView.OnScrubListener() {
            @Override
            public void onScrubbed(Object value) {
                if (value == null)
                    scrubInfoTextView.setText("Tap and hold the graph to scrub.");
                else
                    scrubInfoTextView.setText(getString(R.string.scrub_format, value));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button okBtn;
                weightDialog.setContentView(R.layout.addweight);
                final TextInputLayout name = weightDialog.findViewById(R.id.input_weight);
                okBtn = weightDialog.findViewById(R.id.done_adding);

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Weight weight = new Weight();
                        weight.setWeight(Float.valueOf(name.getEditText().getText().toString()));
                        weight.setTime();
                        weightList.add(weight);
                        mRef.child("weight").setValue(weightList);
                        weightDialog.dismiss();
                        adapterW.notifyDataSetChanged();
                        weightAdapter.notifyDataSetChanged();
                    }
                });
                weightDialog.show();
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                graphInit();
            }
        }, 500);
        weightDialog = new Dialog(this);
    }

    private void graphInit() {
        sparkView.setSparkAnimator(new LineSparkAnimator());
        tempList = new LinkedList<>();
        tempList.clear();
        if (weightList.size() <= 7) {
            tempList = weightList;
        } else {
            for (int b = 7; b > 0; b--) {
                tempList.add(weightList.get(weightList.size() - b));
            }
        }
        weightAdapter = new WeightAdapter(tempList);
        sparkView.setAdapter(weightAdapter);
        sparkView.setLineWidth(4);
        weightAdapter.init(tempList);
    }

    public static class WeightAdapter extends SparkAdapter {
        private final float[] yData;

        public WeightAdapter(List<Weight> weights) {
            yData = new float[weights.size()];
            init(weights);

        }

        public void init(List<Weight> weights) {
            for (int i = 0; i < weights.size(); i++) {
                yData[i] = weights.get(i).getWeight();
            }
            notifyDataSetChanged();
        }

        //math to draw the line
        @Override
        public int getCount() {
            return yData.length;
        }

        @Override
        public Object getItem(int index) {
            return yData[index];
        }

        @Override
        public float getY(int index) {
            return yData[index];
        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}