package com.example.homefit.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Days implements Parcelable {

    private String name;
    private List<Exercises> exercises;

    public Days(){} //empty constructor is needed

    public Days(String name, List<Exercises> exercisesList) {
        this.name = name;
        this.exercises = exercisesList;
    }

    protected Days(Parcel in) {
        name = in.readString();
        exercises = in.createTypedArrayList(Exercises.CREATOR);
    }

    public static final Creator<Days> CREATOR = new Creator<Days>() {
        @Override
        public Days createFromParcel(Parcel in) {
            return new Days(in);
        }

        @Override
        public Days[] newArray(int size) {
            return new Days[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(exercises);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercises> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercises> excercisesList) {
        this.exercises = excercisesList;
    }
}
