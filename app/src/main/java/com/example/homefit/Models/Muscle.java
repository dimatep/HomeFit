package com.example.homefit.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Muscle implements Parcelable {

    private String name;
    private int id;
    private List<Exercise> exercises;

    public Muscle(){
    }

    public Muscle(String name, List<Exercise> exerciseList, int id) {
        this.id = id;
        this.name = name;
        this.exercises = exerciseList;
    }

    protected Muscle(Parcel in) {
        name = in.readString();
        id = in.readInt();
        exercises = in.createTypedArrayList(Exercise.CREATOR);
    }

    public static final Creator<Muscle> CREATOR = new Creator<Muscle>() {
        @Override
        public Muscle createFromParcel(Parcel in) {
            return new Muscle(in);
        }

        @Override
        public Muscle[] newArray(int size) {
            return new Muscle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeList(exercises);
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public int getId() {
        return id;
    }
}