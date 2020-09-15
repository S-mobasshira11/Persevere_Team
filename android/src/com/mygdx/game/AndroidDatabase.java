package com.mygdx.game;


import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AndroidDatabase implements MyDatabaseHolder.MyDatabase {

    String Name;
    String Score;
    String Time;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");




    /* @Override
    public void saveString(String string) {
        // here you have access to Firebase API
        //writeToFile(string,);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

    }*/

    @Override
    public void savename(String name) {
        Name=name;
       // myRef.setValue(name);
       // myRef.child("Users").push().setValue(name);
    }

    @Override
    public void savescore(String score) {
        Score=score;
    }

    @Override
    public void savetime(String time) {
        Time=time;
    }

    @Override
    public void saveTofile() {

        final FileHandle file = Gdx.files.local("highscore.txt");
        file.writeString("Name        "+"Score        "+"Time(Seconds)\n\n",false);
        //file.writeString("Score",false);
        myRef.addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    if(data.child("time").exists() && data.child("score").exists() && data.child("name").exists())
                    {
                        String time = data.child("time").getValue().toString();
                        String score = data.child("score").getValue().toString();
                        String name = data.child("name").getValue().toString();
                        String msg = name + "        " + score + "        " + time + " \n\n";
                        file.writeString(msg, true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void save()
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(Name);
        userInfo.setScore(Score);
        userInfo.setTime(Time);
        myRef.push().setValue(userInfo);
    }



    /*public void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("Score.txt",Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException exp) {
            exp.printStackTrace();
        }
    }*/


}
