package com.example.homefit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.homefit.Adapters.ParentAdapter;
import com.example.homefit.Adapters.SimpleItemTouchHelperCallback;
import com.example.homefit.Models.Days;
import com.example.homefit.R;

public class ExerciseFragment extends Fragment {

    Days days;
    private ItemTouchHelper itemTouchHelper;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        days = getArguments().getParcelable("w");

        RecyclerView exerciseRecyclerView = view.findViewById(R.id.exerciseRVuser);
        exerciseRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        exerciseRecyclerView.setLayoutManager(mLayoutManager);

        ParentAdapter adapter = new ParentAdapter(getActivity(), days.getExercises());
        exerciseRecyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(exerciseRecyclerView);
        //set animation
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
        exerciseRecyclerView.startAnimation(bottomUp);
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

}
