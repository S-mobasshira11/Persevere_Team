package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygdx.game.PersevereGame;

public class Credits implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Game game;

    public Credits(Game game) {

        this.game=game;
        //viewport = new FitViewport(PersevereGame.V_WIDTH,PersevereGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        TextButton btnLogin = new TextButton("Back",skin);
        btnLogin.setPosition(50,100);
        btnLogin.setSize(100,40);

        Label.LabelStyle font1 = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label highscore = new Label("Credits:",font);


        table.add(highscore).expandX();
        table.row();

        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point , int button){
                btnLoginClicked();
            }

        });


        Label hridi = new Label("Sadia Mobasshira Hridi : 18.01.04.014 ",font);
        Label Peom = new Label("Peom Dutta : 18.01.04.017",font);
        Label Mahdi = new Label("Mahdi Hassan Khan : 18.01.04.018",font);


        table.add(hridi).expandX();
        table.row();
        table.add(Peom).expandX();
        table.row();
        table.add(Mahdi).expandX();
        table.row();

        stage.addActor(table);
        stage.addActor(btnLogin);

    }

    public void btnLoginClicked(){
        game.setScreen(new MenuScreen(game));
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

    }
}
