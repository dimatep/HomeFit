package com.example.homefit.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homefit.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

public class FragBNutri extends Fragment {

    int tdee = 0;
    int goal = 0;
    int cals = 0;
    double wt = 0;
    int protein = 0;
    int carbs = 0;
    int fats = 0;

    public FragBNutri() {
        // Required empty public constructor
    }

    public static FragBNutri newInstance() {
        return new FragBNutri();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tdee = getArguments().getInt("TDEE");
            wt = getArguments().getDouble("WEIGHT");
            goal = getArguments().getInt("GOAL");
            update(tdee, wt, goal);
        }
    }

    private void update(int tdee, double wt, int goal) { //when press on update tdee
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putInt("TDEE", tdee);
        mEditor.putString("WEIGHT", String.valueOf(wt));
        mEditor.putInt("GOAL", goal);
        mEditor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutri_b, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get all the text from fragment
        TextView tdee1 = getView().findViewById(R.id.tdee2);
        final TextView tdee2 = getView().findViewById(R.id.tdee);
        final TextView protein = getView().findViewById(R.id.cal_protein);
        final TextView carb = getView().findViewById(R.id.cal_carb);
        final TextView fat = getView().findViewById(R.id.cal_fat);
        final TextView gramProtein = getView().findViewById(R.id.gram_protein);
        final TextView gramCarb = getView().findViewById(R.id.gram_carb);
        final TextView gramFat = getView().findViewById(R.id.gram_fat);

        final PieChart pieChart = getView().findViewById(R.id.chart);
        ToggleSwitch toggleSwitch = getView().findViewById(R.id.goal_switch);

        tdee1.setText(String.valueOf(tdee));
        toggleSwitch.setCheckedTogglePosition(1);
        tdee2.setText(String.valueOf(tdee));
        cals = tdee;

        List<PieEntry> entries = new ArrayList<>();
        //math thing from google
        entries.add(new PieEntry((int) (wt * 1.8), "Protein"));
        entries.add(new PieEntry((int) (cals - cals * 0.3 - wt * 1.8 * 4) / 4, "Carb"));
        entries.add(new PieEntry((int) (cals * 0.3 / 9), "Fat"));

        //pie chart setting (found on google)
        PieDataSet set = new PieDataSet(entries, null);
        set.setColors(getResources().getColor(R.color.bluez), getResources().getColor(R.color.greenz), getResources().getColor(R.color.redz));
        set.setSliceSpace(3f);
        set.setSelectionShift(9f);
        set.setValueFormatter(new PercentFormatter());
        PieData data = new PieData(set);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleColor(getResources().getColor(R.color.colorGrey));
        pieChart.setHoleRadius(35f);
        pieChart.setData(data);
        pieChart.spin(500, 0, -360f, Easing.EasingOption.EaseInOutQuad);
        protein.setText(String.valueOf(((int) (wt * 1.8 * 4))));
        fat.setText(String.valueOf(((int) (cals * 0.3))));
        carb.setText(String.valueOf(cals - ((int) (wt * 1.8 * 4)) - ((int) (cals * 0.3))));
        gramProtein.setText(String.valueOf(((int) (wt * 1.8))));
        gramFat.setText(String.valueOf(((int) (cals * 0.3 / 9))));
        gramCarb.setText(String.valueOf((int) (cals - (wt * 1.8 * 4) - (cals * 0.3)) / 4));
        update(tdee, wt, tdee);

        toggleSwitch.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) { //show result
                switch (position) {
                    case 0:
                        cals = tdee - 250;
                        FragBNutri.this.protein = ((int) (wt * 2.1 * 4));
                        fats = ((int) (wt * 9));

                        break;
                    case 2:
                        cals = tdee + 200;
                        FragBNutri.this.protein = ((int) (wt * 1.8 * 4));
                        fats = ((int) (cals * 0.25));
                        break;
                    default:
                        cals = tdee;
                        FragBNutri.this.protein = ((int) (wt * 1.8 * 4));
                        fats = ((int) (cals * 0.3));
                        break;
                }

                goal = cals;
                carbs = cals - FragBNutri.this.protein - fats;
                tdee2.setText(String.valueOf(cals));
                protein.setText(String.valueOf(FragBNutri.this.protein));
                fat.setText(String.valueOf(fats));
                carb.setText(String.valueOf(carbs));
                gramProtein.setText(String.valueOf(FragBNutri.this.protein / 4));
                gramFat.setText(String.valueOf(fats / 9));
                gramCarb.setText(String.valueOf(carbs / 4));

                List<PieEntry> entries = new ArrayList<>(); //save all the inputs to chart

                entries.add(new PieEntry(FragBNutri.this.protein / 4, "Protein"));
                entries.add(new PieEntry(carbs / 4, "Carb"));
                entries.add(new PieEntry(fats / 9, "Fat"));

                //pie chart settings
                PieDataSet set = new PieDataSet(entries, null);
                set.setColors(getResources().getColor(R.color.bluez), getResources().getColor(R.color.greenz), getResources().getColor(R.color.redz));
                set.setSliceSpace(3f);
                set.setSelectionShift(9f);
                set.setValueFormatter(new PercentFormatter());
                PieData data = new PieData(set);
                pieChart.getDescription().setEnabled(false);
                pieChart.getLegend().setEnabled(false);
                pieChart.setUsePercentValues(true);
                pieChart.setHoleColor(getResources().getColor(R.color.colorGrey));
                pieChart.setHoleRadius(35f);
                pieChart.setData(data);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                update(tdee, wt, goal);
            }
        });
    }
}
