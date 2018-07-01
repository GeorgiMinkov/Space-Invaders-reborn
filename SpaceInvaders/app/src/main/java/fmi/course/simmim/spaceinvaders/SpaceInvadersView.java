package fmi.course.simmim.spaceinvaders;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Map;

public class SpaceInvadersView extends SurfaceView implements Runnable {
    public SpaceInvadersView(Context context, int screenResolutionX, int screenResolutionY) {
        super(context);

        this.context = context;

        this.holder = getHolder();
        this.paint = new Paint();

        this.screenResolutionX = screenResolutionX;
        this.screenResolutionY = screenResolutionY;

        this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        loadAudio();

        prepareLevel();
    }

    private void prepareLevel() {
        this.playerShip = new PlayerShip(context, screenResolutionX, screenResolutionY);

        this.bullet = new Bullet(screenResolutionY);
        for (int index = 0; index < invadersBullets.length; ++index){
            this.invadersBullets[index] = new Bullet(screenResolutionY);
        }

        // build army of invaders
        numberOfInvaders = 0;
        for (int column = 0; column < 6; ++column) {
            for (int row = 0; row < 5; ++row) {
                this.invaders[numberOfInvaders++] =  new Invader(context, row, column, screenResolutionX, screenResolutionY);
            }
        }

        // Build the shelters for player ship
        numberOfBricks = 0;
        for(int shelterNumber = 0; shelterNumber < 4; shelterNumber++){
            for(int column = 0; column < 10; column ++ ) {
                for (int row = 0; row < 5; row++) {
                    bricks[numberOfBricks++] = new DefenceBrick(row, column, shelterNumber, screenResolutionX, screenResolutionY);
                }
            }
        }

        menaceInterval = 1000;
    }

    @Override
    public void run() {
        while (isPlaying()) {
            long startFrameTime = System.currentTimeMillis();

            if (!isPaused()) {
                update();
            }

            draw();

            timeForThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeForThisFrame >= 1) {
                fps = 1000 / timeForThisFrame;
            }

            // Play a sound based on the menace level
            if(!paused) {
                if ((startFrameTime - lastMenaceTime) > menaceInterval) {
                    if (uhOrOh) {
                        soundPool.play(uhID, 1, 1, 0, 0, 1);

                    } else {
                        soundPool.play(ohID, 1, 1, 0, 0, 1);
                    }

                    // Reset the last menace time
                    lastMenaceTime = System.currentTimeMillis();

                    // Alter value of uhOrOh
                    uhOrOh = !uhOrOh;
                }
            }
        }
    }

    private void update() {
        boolean bumped = false;
        boolean lost = false;

        // Move the player's ship
        playerShip.update(fps);

        if(bullet.getStatus()){
            bullet.update(fps);
        }

        // Update all the invaders bullets if active
        for(int i = 0; i < invadersBullets.length; i++){
            if(invadersBullets[i].getStatus()) {
                invadersBullets[i].update(fps);
            }
        }

        for(int i = 0; i < numberOfInvaders; i++){
            if(invaders[i].getVisibility()) {
                invaders[i].update(fps);

                if(invaders[i].takeAim(playerShip.getCoordinateX(), playerShip.getLength())){
                    if(invadersBullets[nextBullet].shoot(invaders[i].getCoordinateX() + invaders[i].getLength() / 2, invaders[i].getCoordinateY(), bullet.DOWN)) {
                        nextBullet++;

                        if (nextBullet == MAX_INVADERS_BULLETS) {
                            nextBullet = 0;
                        }
                    }
                }

                if (invaders[i].getCoordinateX() > screenResolutionX - invaders[i].getLength()
                        || invaders[i].getCoordinateX() < 0) {
                    bumped = true;
                }
            }

        }

        if(bumped){
            for(int i = 0; i < numberOfInvaders; ++i){
                invaders[i].dropDownAndReverse();

                // Have the invaders landed
                if(invaders[i].getCoordinateY() > screenResolutionY - screenResolutionY / 10){
                    lost = true;
                }
            }

            menaceInterval = menaceInterval - 80;
        }


        if(lost){
            prepareLevel();
        }

        if(bullet.getImplactPointY() < 0){
            bullet.setInactive();
        }

        for(int i = 0; i < invadersBullets.length; ++i){

            if(invadersBullets[i].getImplactPointY() > screenResolutionY){
                invadersBullets[i].setInactive();
            }
        }

        // Has the player's bullet hit an invader
        if(bullet.getStatus()) {
            for (int i = 0; i < numberOfInvaders; i++) {
                if (invaders[i].getVisibility()) {
                    if (RectF.intersects(bullet.getRect(), invaders[i].getRect())) {
                        invaders[i].setInvisible();
                        soundPool.play(invaderExplodeID, 1, 1, 0, 0, 1);
                        bullet.setInactive();
                        score = score + 10;

                        // Has the player won
                        if(score == numberOfInvaders * 10){
                            paused = true;
                            score = 0;
                            lives = 3;
                            prepareLevel();
                        }
                    }
                }
            }
        }

        // Has an alien bullet hit a shelter brick
        for(int i = 0; i < invadersBullets.length; i++){
            if(invadersBullets[i].getStatus()){
                for(int j = 0; j < numberOfBricks; j++){
                    if(bricks[j].getVisibility()){
                        if(RectF.intersects(invadersBullets[i].getRect(), bricks[j].getRect())){
                            // A collision has occurred
                            invadersBullets[i].setInactive();
                            bricks[j].setInvisible();
                            soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                        }
                    }
                }
            }

        }

        // Has a player bullet hit a shelter brick
        if(bullet.getStatus()){
            for(int i = 0; i < numberOfBricks; i++){
                if(bricks[i].getVisibility()){
                    if(RectF.intersects(bullet.getRect(), bricks[i].getRect())){
                        // A collision has occurred
                        bullet.setInactive();
                        bricks[i].setInvisible();
                        soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                    }
                }
            }
        }

        // Has an invader bullet hit the player ship
        for(int i = 0; i < invadersBullets.length; i++){
            if(invadersBullets[i].getStatus()){
                if(RectF.intersects(playerShip.getRect(), invadersBullets[i].getRect())){
                    invadersBullets[i].setInactive();
                    lives--;
                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);

                    if(lives == 0) {
                        paused = true;
                        lives = 3;
                        score = 0;
                        prepareLevel();

                    }
                }
            }
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            // draw background layout
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.intro_background), 0, 0, null);
            paint.setColor(Color.argb(255,255,255,255));

            // Draw the player spaceship
            canvas.drawBitmap(playerShip.getBitmap(), playerShip.getCoordinateX(), screenResolutionY - playerShip.getHeight(), paint);

            // Draw the invaders
            for(int i = 0; i < numberOfInvaders; i++){
                if(invaders[i].getVisibility()) {
                    if(uhOrOh) {
                        canvas.drawBitmap(invaders[i].getMovementShiftUp(), invaders[i].getCoordinateX(), invaders[i].getCoordinateY(), paint);
                    } else {
                        canvas.drawBitmap(invaders[i].getMovementShiftDown(), invaders[i].getCoordinateX(), invaders[i].getCoordinateY(), paint);
                    }
                }
            }


            // Draw the bricks if visible
            for(int i = 0; i < numberOfBricks; i++) {
                if(bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }

            // Draw the players bullet if active
            if(bullet.getStatus()) {
                canvas.drawRect(bullet.getRect(), paint);
            }

            // Draw the invaders bullets

            // Update all the invader's bullets if active
            for(int i = 0; i < invadersBullets.length; i++){
                if(invadersBullets[i].getStatus()) {
                    canvas.drawRect(invadersBullets[i].getRect(), paint);
                }
            }

            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives: " + lives, 10,50, paint);

            // Draw everything to the screen
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        paused = false;

        try {
            gameThread.join();
        } catch(InterruptedException ex) {
            Log.e("Error: ", "joining thread");
        }
    }

    public void resume() {
        playing = true;

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                paused = false;

                if(motionEvent.getY() > screenResolutionY - screenResolutionY / 8) {
                    if (motionEvent.getX() > screenResolutionX / 2) {
                        playerShip.setShipMovement(playerShip.RIGHT);
                    } else {
                        playerShip.setShipMovement(playerShip.LEFT);
                    }


                }

                if(motionEvent.getY() < screenResolutionY - screenResolutionY / 8) {
                    if(bullet.shoot(playerShip.getCoordinateX()+ playerShip.getLength() / 2,
                            screenResolutionY,bullet.UP)){
                        soundPool.play(shootID, 1, 1, 0, 0, 1);
                    }
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                if(motionEvent.getY() > screenResolutionY - screenResolutionY / 10) {
                    playerShip.setShipMovement(playerShip.STOPPED);
                }
                break;
        }
        return true;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isPaused() {
        return paused;
    }

    private void loadAudio() {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd(context.getString(R.string.shoot_sound));
            this.shootID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd(context.getString(R.string.invader_explode));
            this.invaderExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd(context.getString(R.string.damage_shelter));
            this.damageShelterID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd(context.getString(R.string.player_explode));
            this.playerExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd(context.getString(R.string.damage_shelter));
            this.damageShelterID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd(context.getString(R.string.oh_sound));
            this.ohID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd(context.getString(R.string.uh_sound));
            this.uhID = soundPool.load(descriptor, 0);
        } catch(IOException ex) {
            Log.e("error", "failed to load sound files");
        }

    }

    Context context;

    private Thread gameThread = null;

    private SurfaceHolder holder;

    private volatile boolean playing;

    private boolean paused = true;

    private Canvas canvas;
    private Paint paint;

    private long fps;
    private long timeForThisFrame;

    private int screenResolutionX;
    private int screenResolutionY;

    // Player ammo
    private PlayerShip playerShip;
    private Bullet bullet;

    // Invaders stuff
    private Bullet[] invadersBullets = new Bullet[200];
    private int nextBullet;
    private final int MAX_INVADERS_BULLETS = 10;

    private Invader[] invaders = new Invader[60];
    private int numberOfInvaders = 0;

    // defence btrick
    private DefenceBrick[] bricks = new DefenceBrick[400];
    private int numberOfBricks = 0;

    // SOUNDS
    private SoundPool soundPool;
    private int playerExplodeID = -1;
    private int invaderExplodeID = -1;
    private int shootID = -1;
    private int damageShelterID = -1;
    private int uhID = -1;
    private int ohID = -1;
    private Map<String, String> soungs;

    int score = 0;

    private int lives = 3;

    private long menaceInterval = 1000;
    private long lastMenaceTime = System.currentTimeMillis();

    private boolean uhOrOh;

}
