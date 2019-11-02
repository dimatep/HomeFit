package com.example.homefit.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Exercises implements Parcelable {

    private String name;
    private String id;
    private String equip;
    private String mechanics;
    private List<Sets> sets;
    private List<Object> s;

    public Exercises(){
    }

    public Exercises(String name, String id, String equipment, String mechanics, List<Sets> setsList) {
        super();
        this.name = name;
        this.id = id;
        this.equip = equipment;
        this.mechanics = mechanics;
        this.sets = setsList;
    }

    protected Exercises(Parcel in) {
        name = in.readString();
        id = in.readString();
        equip = in.readString();
        mechanics = in.readString();
        sets = in.createTypedArrayList(Sets.CREATOR);
    }

    public static final Creator<Exercises> CREATOR = new Creator<Exercises>() {
        @Override
        public Exercises createFromParcel(Parcel in) {
            return new Exercises(in);
        }

        @Override
        public Exercises[] newArray(int size) {
            return new Exercises[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(equip);
        dest.writeString(mechanics);
        dest.writeTypedList(sets);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public String getMechanics() {
        return mechanics;
    }

    public void setMechanics(String mechanics) {
        this.mechanics = mechanics;
    }

    public List<Sets> getSets() {
        return sets;
    }

    public void setSets(List<Sets> sets) {
        this.sets = sets;
    }

}