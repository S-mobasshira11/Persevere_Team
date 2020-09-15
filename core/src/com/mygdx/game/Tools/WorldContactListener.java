package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.PersevereGame;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveTileObject;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Perse;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits; //here, cDef = collision Definition

        switch(cDef){
            case PersevereGame.PERSE_HEAD_BIT | PersevereGame.BRICK_BIT:
            case PersevereGame.PERSE_HEAD_BIT | PersevereGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == PersevereGame.PERSE_HEAD_BIT)
                {
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Perse)fixA.getUserData());
                }
                else
                {
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Perse)fixB.getUserData());
                }
                break;
            case PersevereGame.ENEMY_HEAD_BIT | PersevereGame.PERSE_BIT:
                if(fixA.getFilterData().categoryBits == PersevereGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Perse) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Perse) fixA.getUserData());
                break;

            case PersevereGame.ENEMY_BIT | PersevereGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == PersevereGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;

            case PersevereGame.PERSE_BIT | PersevereGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == PersevereGame.PERSE_BIT)
                {
                    ((Perse) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                }
                else
                {
                    ((Perse) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                }
                break;

            case PersevereGame.ENEMY_BIT | PersevereGame.ENEMY_BIT:
                //((Enemy) fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
                //((Enemy) fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData()); //////////////////////////////////
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData()); //////////////////////////////////
                break;
            case PersevereGame.ITEM_BIT | PersevereGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == PersevereGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case PersevereGame.ITEM_BIT | PersevereGame.PERSE_BIT:
                if(fixA.getFilterData().categoryBits == PersevereGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Perse) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Perse) fixA.getUserData());
                break;
            /*case PersevereGame.FIREBALL_BIT | PersevereGame.OBJECT_BIT:////////////////////////////////////////////////////////
                if(fixA.getFilterData().categoryBits == PersevereGame.FIREBALL_BIT)
                    ((FireBall)fixA.getUserData()).setToDestroy();
                else
                    ((FireBall)fixB.getUserData()).setToDestroy();
                break;    */
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
