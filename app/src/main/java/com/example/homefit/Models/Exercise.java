package com.example.homefit.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Exercise implements Parcelable {

    private String name;
    private int id;
    private String mechanics;
    private List<Equipment> equip;

    public Exercise(){
    }

    public Exercise(String name, int id, String mechanics, List<Equipment> equipmentList) {
        this.name = name;
        this.id = id;
        this.mechanics = mechanics;
        this.equip = equipmentList;
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        id = in.readInt();
        mechanics = in.readString();
        equip = in.createTypedArrayList(Equipment.CREATOR);
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
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
        dest.writeString(mechanics);
        dest.writeTypedList(equip);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMechanics() {
        return mechanics;
    }

    public void setMechanics(String mechanics) {
        this.mechanics = mechanics;
    }

    public List<Equipment> getEquip() {
        return equip;
    }

    public void setEquip(List<Equipment> equip) {
        this.equip = equip;
    }
}
