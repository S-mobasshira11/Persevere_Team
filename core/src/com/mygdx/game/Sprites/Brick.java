package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.PersevereGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(PersevereGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Perse perse) {
        Gdx.app.log("Brick", "Collision");
        if(perse.isBig()) {
            setCategoryFilter(PersevereGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            PersevereGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
        PersevereGame.manager.get("audio/sounds/bump.wav", Sound.class).play();

    }
}
