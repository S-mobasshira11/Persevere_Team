package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyCoreClass;
import com.mygdx.game.PersevereGame;

import javax.swing.JTextArea;

public class NameInput implements Screen {

    private Game game;
    private Stage stage;
    private TextField txfUsername;
    public String username;

    public NameInput(Game game) {
        this.game=game;
        //this.skin= new Skin();
        stage =new Stage();
        Gdx.input.setInputProcessor(stage);

        //TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        //this.skin.load(Gdx.files.internal("ui/uiskin.json"));


        TextButton btnLogin = new TextButton("Back",skin);
        btnLogin.setPosition(50,100);
        btnLogin.setSize(100,40);

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x,float y, int point , int button){
                btnLoginClicked();
            }

        });

        stage.addActor(btnLogin);

        btnLogin = new TextButton("PLAY",skin);
        btnLogin.setPosition(550,340);
        btnLogin.setSize(300,60);

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x,float y, int point , int button){
                btnLoginClicked2();
            }

        });

        txfUsername = new TextField("", skin);
        txfUsername.setPosition(550,250);
        txfUsername.setSize(300,60);
        stage.addActor(txfUsername);

        stage.addActor(btnLogin);
    }

    public void btnLoginClicked(){
        game.setScreen(new MenuScreen(game));
    }

    public void btnLoginClicked2(){
        username = txfUsername.getText();
        MyCoreClass myCoreClass = new MyCoreClass();
        myCoreClass.Name(username);
        //System.out.println(username);
        game.setScreen(new ChapterMenuPlay(game));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
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

    }
}
