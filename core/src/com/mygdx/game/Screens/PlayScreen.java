package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controller;
import com.mygdx.game.PersevereGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Halt;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.Power;
import com.mygdx.game.Sprites.Perse;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen extends ApplicationAdapter implements Screen {

    public static SpriteBatch batch;
    private PersevereGame game;      //Red
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private Perse player;



    //Tiled Map Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Music music;

    Controller controller;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    public PlayScreen(PersevereGame game){
        atlas = new TextureAtlas("Perse_and_Enemies.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        batch = new SpriteBatch();
        //gamePort = new StretchViewport(800, 480, gamecam);
        //gamePort = new ScreenViewport(gamecam);
        gamePort = new FitViewport(PersevereGame.V_WIDTH/PersevereGame.PPM,PersevereGame.V_HEIGHT/PersevereGame.PPM, gamecam);
        hud = new Hud(game.batch);
        controller = new Controller();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map , 1 / PersevereGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() /2 , gamePort.getWorldHeight() /2 , 0);
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        player = new Perse(this);
        world.setContactListener(new WorldContactListener());

        music = PersevereGame.manager.get("audio/music/mario_music.ogg",Music.class);
        music.setLooping(true);
        music.setVolume(0.3f); //////////////////////////////////////////////////////////////////////////////////////////////
        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

    }

    //public void btnLoginClicked(){
        //game.setScreen(new MenuScreen(game));
        //goforward();
    //}

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Power.class){
                items.add(new Power(this,idef.position.x,idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas(){

        return atlas;
    }

    @Override
    public void show() {

    }

    /*public void handleInput(float dt){

        if(player.currentState != Perse.State.DEAD)
        {
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                player.b2body.applyLinearImpulse(new Vector2(0,4f),player.b2body.getWorldCenter(),true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=2){
                player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-2){
                player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
            }
        }
    }*/


    public void handleInput(float dt){
        //control our player using immediate impulses
        if(player.currentState != Perse.State.DEAD) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                player.jump();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2 )
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            //if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            //    player.fire();
           /* if(controller.isRightPressed())
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            else if (controller.isLeftPressed())
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            else
                player.b2body.applyLinearImpulse(new Vector2(0f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0)
                player.jump();*/
            if(controller.isRightPressed())
                player.b2body.setLinearVelocity(new Vector2(1, player.b2body.getLinearVelocity().y));
            else if (controller.isLeftPressed())
                player.b2body.setLinearVelocity(new Vector2(-1, player.b2body.getLinearVelocity().y));
            else
                player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));
            if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0)
                //player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
                player.jump();

        }

    }

    public void update(float dt){
        handleInput(dt);
        handleSpawningItems();
        world.step(1/60f , 6 , 2);
        player.update(dt);
        for(Enemy enemy : creator.getEnemies()){
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / PersevereGame.PPM){
                enemy.b2body.setActive(true);
            }
        }

        for(Item item : items)
            item.update(dt);

        hud.update(dt);

        if(player.currentState != Perse.State.DEAD)
        {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();      //for rendering game map
        b2dr.render(world,gamecam.combined);    //for rendering Box2DDebugLines

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getEnemies()){
            enemy.draw(game.batch);
        }
        for(Item item : items)
            item.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //if(Gdx.app.getType() == Application.ApplicationType.Android)
        //{
            controller.draw();
        //}

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public boolean gameOver(){
        if(player.currentState == Perse.State.DEAD && player.getStateTimer()>3){
            return true;
        }
        return false; //code red
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
        controller.resize(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud()
    {
        return hud;
    } //////////////////////////////////////////////////////////////////////

}
