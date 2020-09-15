package com.mygdx.game;

public class MyDatabaseHolder {
    private static MyDatabase DB;

    public static void setDb(MyDatabase db) {
        DB = db;
    }

    public static MyDatabase getDb() {
        return DB;
    }

    public interface MyDatabase{
        void savename(String name);
        void savescore(String score);
        void savetime(String time);
        void saveTofile();
        void save();
    }

}
