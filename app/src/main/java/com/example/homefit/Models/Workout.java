package com.example.homefit.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Workout implements Parcelable{

    private String name;
    private String type;
    private String level;
    private int size;
    private int id;
    private boolean def;
    private List<Days> days;

    public Workout(){ //needed for serialization

    }

    public Workout(String name, String type, String level, int size, int id, boolean def, List<Days> days) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.size = size;
        this.id = id;
        this.def = def;
        this.days = days;
    }

    protected Workout(Parcel in) {
        name = in.readString();
        type = in.readString();
        level = in.readString();
        size = in.readInt();
        id = in.readInt();
        def = in.readByte() != 0;
        days = in.createTypedArrayList(Days.CREATOR);
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(level);
        dest.writeInt(size);
        dest.writeInt(id);
        dest.writeByte((byte) (def ? 1 : 0));
        dest.writeTypedList(days);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public List<Days> getDays() {
        return days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }
}
