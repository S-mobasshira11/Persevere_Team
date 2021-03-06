package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.PersevereGame;

public class Splash implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Music music;
    public Splash(Game game) {
        this.game=game;
        viewport = new FitViewport(PersevereGame.V_WIDTH,PersevereGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,((PersevereGame)game).batch);
        //stage = new Stage();

        Label.LabelStyle font1 = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        music = PersevereGame.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f); //////////////////////////////////////////////////////////////////////////////////////////////
        music.play();

        Label chapterLabel = new Label("PERSEVERE ",font1);
        chapterLabel.setSize(1555,1555);

        table.add(chapterLabel).expandX();
        table.row();

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            game.setScreen(new MenuScreen((PersevereGame) game));
            dispose();
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
