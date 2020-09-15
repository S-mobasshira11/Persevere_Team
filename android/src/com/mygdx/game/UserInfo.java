package com.mygdx.game;

public class UserInfo {

    String Name,Score,Time;

    public UserInfo() {
    }

    public UserInfo(String name, String score, String time) {
        Name = name;
        Score = score;
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
