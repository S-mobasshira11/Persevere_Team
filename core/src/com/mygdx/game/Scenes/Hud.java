package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyCoreClass;
import com.mygdx.game.PersevereGame;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private boolean timeUp;     ////////////////////////////////////////////////////////////
    private float timeCount;
    private static Integer score;

    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label persevereLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 0;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(PersevereGame.V_WIDTH,PersevereGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, sb);


        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countdownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("Level 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("Persevere", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        persevereLabel = new Label("Game Points", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(persevereLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    /*public void update(float dt){
        timeCount+=dt;
        if(timeCount>=1)
        {
            worldTimer--;
            countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount = 0;
        }
    }*/

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer >= 0) {
                worldTimer++;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }


    public static void addScore(int value){
        score+=value;
        scoreLabel.setText(String.format("%06d",score));
        //MyCoreClass myCoreClass = new MyCoreClass();
        //myCoreClass.test();
    }

    @Override
    public void dispose()
    {
        String scores=String.valueOf(score);
        scores+="\n";
        FileHandle file = Gdx.files.local("highscore.txt");
        file.writeString(String.valueOf(scores), true);
        MyCoreClass myCoreClass = new MyCoreClass();
        myCoreClass.Score(String.valueOf(score));
        myCoreClass.Time(String.valueOf(worldTimer));
        myCoreClass.Save();
        stage.dispose();
    }

    public boolean isTimeUp()
    {
        if(timeUp==true)
        {
            MyCoreClass myCoreClass = new MyCoreClass();
            myCoreClass.Time(String.valueOf(worldTimer));
        }
        return timeUp;
    }   //////////////////////////////////////////////////////

}
