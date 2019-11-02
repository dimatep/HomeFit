package com.example.homefit.Models;

import java.util.List;

public class User {
    private String email;
    private String id;
    private String time;
    private String key;
    private List<Workout> workoutList;

    public User(){

    }

    public User(String email, String id, String time, String key, List<Workout> workoutList) {
        this.email = email;
        this.id = id;
        this.key = key;

        if(time != null)
            this.time = time;

        if(workoutList != null)
            this.workoutList = workoutList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public String getModelName(){
        return "users";
    }
}