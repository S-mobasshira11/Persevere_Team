package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PersevereGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Brick;
import com.mygdx.game.Sprites.Coin;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Halt;
import com.mygdx.game.Sprites.Turtle;

public class B2WorldCreator {
    private Array<Halt> halts;
    //private static Array<Turtle> turtles;
    private Array<Turtle> turtles; ////////////////////////////////////////////


    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //creating ground body or fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ PersevereGame.PPM,(rect.getY()+rect.getHeight()/2)/PersevereGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/PersevereGame.PPM , rect.getHeight()/2/PersevereGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //creating pipe bodies or fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/PersevereGame.PPM,(rect.getY()+rect.getHeight()/2)/PersevereGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/PersevereGame.PPM , rect.getHeight()/2/PersevereGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = PersevereGame.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //creating brick bodies or fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Brick(screen,object);
        }

        //creating coin bodies or fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new Coin(screen,object);
        }

        //creating halts
        halts = new Array<Halt>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            halts.add(new Halt(screen,rect.getX()/PersevereGame.PPM,rect.getY()/PersevereGame.PPM));
        }

        turtles = new Array<Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen,rect.getX()/PersevereGame.PPM,rect.getY()/PersevereGame.PPM));
        }
    }

    public Array<Halt> getHalts() {
        return halts;
    }

    //public static void removeTurtle(Turtle turtle){/////////////////////////////////////////////////////////////////////
    //    turtles.removeValue(turtle,true);
    //}

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(halts);
        enemies.addAll(turtles);
        return enemies;
    }

}












