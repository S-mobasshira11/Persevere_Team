package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyCoreClass;
import com.mygdx.game.PersevereGame;

import static java.lang.System.exit;

public class MenuScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;

    public MenuScreen(Game g) {
        //FileHandle file = Gdx.files.local("highscore.txt");
        //file.emptyDirectory();
        MyCoreClass myCoreClass = new MyCoreClass();
        myCoreClass.SaveFile();
        game=g;
        stage =new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

       // Label gameTitle = new Label("PERSEVERE",skin,"big");
        //gameTitle.setPosition(500,500);
        //gameTitle.setSize(350,350);



        TextButton btnLogin = new TextButton("Start Game",skin);
        btnLogin.setPosition(550,430);
        btnLogin.setSize(350,80);

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point , int button){
                btnSGC();
            }

        });

        stage.addActor(btnLogin);

        btnLogin = new TextButton("About",skin);
        btnLogin.setPosition(550,340);
        btnLogin.setSize(350,80);

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point , int button){
                btnAC();
            }

        });

        stage.addActor(btnLogin);

        btnLogin = new TextButton("High Score",skin);
        btnLogin.setPosition(550,250);
        btnLogin.setSize(350,80);

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point , int button){
                btnHSC();
            }

        });

        stage.addActor(btnLogin);

        btnLogin = new TextButton("Credits",skin);
        btnLogin.setPosition(550,160);
        btnLogin.setSize(350,80);

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point , int button){
                btnCC();
            }

        });

        stage.addActor(btnLogin);

        btnLogin = new TextButton("Exit",skin);
        btnLogin.setPosition(550,70);
        btnLogin.setSize(350,80);

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point , int button){
                btnEC();
            }

        });
        //stage.addActor(gameTitle);
        stage.addActor(btnLogin);
        //stage.addActor(gameTitle);

    }

    public void btnSGC(){
        game.setScreen(new NameInput(game));
    }

    public void btnAC(){
        game.setScreen(new About(game));
    }

    public void btnHSC(){
        game.setScreen(new HighScores(game));
    }

    public void btnCC(){
        game.setScreen(new Credits(game));
    }

    public void btnEC(){
        exit(0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


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
