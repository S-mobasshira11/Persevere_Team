package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.PersevereGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.Power;

public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 134;

    public Coin(PlayScreen screen, MapObject object){
        super(screen,object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(PersevereGame.COIN_BIT);
    }

    /*@Override
    public void onHeadHit(Perse perse) {
        Gdx.app.log("Coin", "Collision");
        if(getCell().getTile().getId() == BLANK_COIN)
            PersevereGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else
        {
            if(object.getProperties().containsKey("mushroom")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PersevereGame.PPM), Power.class));
                PersevereGame.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
            {
                PersevereGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
            }
            Hud.addScore(100);
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        //Hud.addScore(100);
    }*/


    @Override
    public void onHeadHit(Perse perse) {
        if(getCell().getTile().getId() == BLANK_COIN)
            PersevereGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            if(object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / PersevereGame.PPM),
                        Power.class));
                PersevereGame.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
                PersevereGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(100);
        }
    }

}
