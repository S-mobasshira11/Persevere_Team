package com.mygdx.game.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PersevereGame;
import com.mygdx.game.Screens.PlayScreen;

public class Perse extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion perseStand;
    private Animation perseRun;
    private TextureRegion perseJump;
    private TextureRegion perseDead;

    private TextureRegion bigPerseStand;
    private TextureRegion bigPerseJump;
    private Animation bigPerseRun;
    private Animation growPerse;

    private float stateTimer;
    private boolean runningRight;
    private boolean perseIsBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigPerse;
    private boolean timeToRedefinePerse;
    private boolean perseIsDead;
    private PlayScreen screen;     ///////////////////////////////////////////////
   // private Array<FireBall> fireballs;   ///////////////////////////////////////////////

    public Perse(PlayScreen screen){
        this.screen = screen;     ////////////////////////////////////////////////////
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=1; i<4; i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"),i*16,0,16,16));
        }
        perseRun = new Animation(0.1f,frames);
        frames.clear();
        for(int i=1; i<4; i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),i*16,0,16,32));
        }
        bigPerseRun = new Animation(0.1f,frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),240,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),240,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32));
        growPerse = new Animation(0.2f,frames);

        perseJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"),80,0,16,16);
        bigPerseJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"),80,0,16,32);

        perseStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0 , 0 , 16 , 16);
        bigPerseStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32);

        perseDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"),96,0,16,16);
        definePerse();
        setBounds(0,0,16/PersevereGame.PPM,16/PersevereGame.PPM);
        setRegion(perseStand);

        //fireballs = new Array<FireBall>();/////////////////////////////////////////////////
    }

    public void update(float dt){
        if (screen.getHud().isTimeUp() && !isDead()) {  //////////////////////////////////////////////
            die();
        }
        if(perseIsBig)
            setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight() / 2 - 6/PersevereGame.PPM);
        else
            setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
        if(timeToDefineBigPerse){
            defineBigPerse();
        }
        if(timeToRedefinePerse)
        {
            redefinePerse();
        }
        /*for(FireBall  ball : fireballs) { //////////////////////////////////////////////////
            ball.update(dt);
            if(ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }*/
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = perseDead;
                break;
            case GROWING:
                region = (TextureRegion) growPerse.getKeyFrame(stateTimer);
                if(growPerse.isAnimationFinished(stateTimer))
                {
                    runGrowAnimation = false;
                }
                break;
            case JUMPING:
                region = perseIsBig ? bigPerseJump : perseJump;
                break;
            case RUNNING:
                region = perseIsBig ? ((TextureRegion) bigPerseRun.getKeyFrame(stateTimer,true)) : ((TextureRegion) perseRun.getKeyFrame(stateTimer,true));
                break;
            case FALLING:
            case STANDING:
            default:
                region = perseIsBig ? bigPerseStand : perseStand;
                break;
        }

        if((b2body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true,false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x>0 || runningRight) && region.isFlipX())
        {
            region.flip(true,false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer+dt : 0;
        previousState = currentState;
        return  region;
    }


    /*public State getState(){
        if(perseIsDead)
            return State.DEAD;
        else if(runGrowAnimation)
            return State.GROWING;
        else if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y<0 && previousState == State.JUMPING) )
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y<0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x!=0)
            return State.RUNNING;
        else
            return State.STANDING;
    }*/

    public State getState(){
        if(perseIsDead)
            return State.DEAD;
        else if(runGrowAnimation)
            return State.GROWING;
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

   /* public void grow(){
        runGrowAnimation = true;
        perseIsBig = true;
        timeToDefineBigPerse = true;
        setBounds(getX(),getY(),getWidth(),getHeight()*2);
        PersevereGame.manager.get("audio/sounds/powerup.wav", Sound.class).play();
    }*/

    public void grow(){ //////////////////////////////////////////////////////////////////////
        if( !isBig() ) {
            runGrowAnimation = true;
            perseIsBig = true;
            timeToDefineBigPerse = true;
            setBounds(getX(), getY(), getWidth(), getHeight() * 2);
            PersevereGame.manager.get("audio/sounds/powerup.wav", Sound.class).play();
        }
    }

    public void die() {     /////////////////////////////////////////////////////////////////////

        if (!isDead()) {

            PersevereGame.manager.get("audio/music/mario_music.ogg", Music.class).stop();
            PersevereGame.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            perseIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = PersevereGame.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return perseIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public boolean isBig(){
        return perseIsBig;
    }

    public void jump(){             //////////////////////////////////////////////////////////////////////////////
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    /*public void hit(Enemy enemy){
        if(enemy instanceof Turtle && ((Turtle)enemy).getCurrentState()==Turtle.State.STANDING_SHELL){
            ((Turtle) enemy).kick(this.getX() <= enemy.getX() ? Turtle.KICK_RIGHT_SPEED : Turtle.KICK_LEFT_SPEED);
        }
        else {
            if (perseIsBig) {
                perseIsBig = false;
                timeToRedefinePerse = true;
                setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                PersevereGame.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
            }
            else {
                PersevereGame.manager.get("audio/sounds/mario_music.ogg", Music.class).stop();
                PersevereGame.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
                perseIsDead = true;
                Filter filter = new Filter();
                filter.maskBits = PersevereGame.NOTHING_BIT;
                for (Fixture fixture : b2body.getFixtureList()) {
                    fixture.setFilterData(filter);
                }
                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            }
        }
    }*/

    public void hit(Enemy enemy){
        if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.STANDING_SHELL)
            ((Turtle) enemy).kick(enemy.b2body.getPosition().x > b2body.getPosition().x ? Turtle.KICK_RIGHT : Turtle.KICK_LEFT);
        else {
            if (perseIsBig) {
                perseIsBig = false;
                timeToRedefinePerse = true;
                setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                PersevereGame.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
            } else {
                die();
            }
        }
    }

    public  void redefinePerse(){
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/PersevereGame.PPM);

        fdef.filter.categoryBits = PersevereGame.PERSE_BIT;
        fdef.filter.maskBits = PersevereGame.GROUND_BIT | PersevereGame.COIN_BIT | PersevereGame.BRICK_BIT | PersevereGame.ENEMY_BIT | PersevereGame.OBJECT_BIT | PersevereGame.ENEMY_HEAD_BIT |PersevereGame.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/PersevereGame.PPM, 6/PersevereGame.PPM),new Vector2(2/PersevereGame.PPM, 6/PersevereGame.PPM));
        fdef.filter.categoryBits = PersevereGame.PERSE_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToRedefinePerse = false;
    }

    public void defineBigPerse(){
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0,10/PersevereGame.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/PersevereGame.PPM);

        fdef.filter.categoryBits = PersevereGame.PERSE_BIT;
        fdef.filter.maskBits = PersevereGame.GROUND_BIT | PersevereGame.COIN_BIT | PersevereGame.BRICK_BIT | PersevereGame.ENEMY_BIT | PersevereGame.OBJECT_BIT | PersevereGame.ENEMY_HEAD_BIT |PersevereGame.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/PersevereGame.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/PersevereGame.PPM, 6/PersevereGame.PPM),new Vector2(2/PersevereGame.PPM, 6/PersevereGame.PPM));
        fdef.filter.categoryBits = PersevereGame.PERSE_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigPerse = false;
    }

    public void definePerse(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ PersevereGame.PPM,32/PersevereGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/PersevereGame.PPM);

        fdef.filter.categoryBits = PersevereGame.PERSE_BIT;
        fdef.filter.maskBits = PersevereGame.GROUND_BIT | PersevereGame.COIN_BIT | PersevereGame.BRICK_BIT | PersevereGame.ENEMY_BIT | PersevereGame.OBJECT_BIT | PersevereGame.ENEMY_HEAD_BIT |PersevereGame.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/PersevereGame.PPM, 6/PersevereGame.PPM),new Vector2(2/PersevereGame.PPM, 6/PersevereGame.PPM));
        fdef.filter.categoryBits = PersevereGame.PERSE_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    /*public void fire(){///////////////////////////////////////////////////////////////////////////////////////////////////////////
        fireballs.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }*/

    public void draw(Batch batch){
        super.draw(batch);
        //for(FireBall ball : fireballs)//////////////////////////////////////////////////////////////
        //    ball.draw(batch);////////////////////////////////////////////////////////////
    }

}
