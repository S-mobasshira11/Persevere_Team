package com.mygdx.game;

public class MyCoreClass
{
        public void Name(String name)
        {
            MyDatabaseHolder.getDb().savename(name);
        }
        public void Score(String score)
        {
            MyDatabaseHolder.getDb().savescore(score);
        }
        public void Time(String time)
        {
            MyDatabaseHolder.getDb().savetime(time);
        }
        public void Save()
        {
            MyDatabaseHolder.getDb().save();
        }
        public void SaveFile()
        {
            MyDatabaseHolder.getDb().saveTofile();
        }


}
