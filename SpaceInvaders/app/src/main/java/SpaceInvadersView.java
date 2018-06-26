package com.example.georgiminkov.spaceinvaders;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class SpaceInvadersView extends SurfaceView implements Runnable {
    public SpaceInvadersView(Context context, int screenResolutionX, int screenResolutionY) {
        super(context);

        this.context = context;

        this.holder = getHolder();
        this.paint = new Paint();

        this.screenResolutionX = screenResolutionX;
        this.screenResolutionY = screenResolutionY;

        this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("shoot.ogg");
            this.shootID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("invaderexplode.ogg");
            this.invaderExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("damageshelter.ogg");
            this.damageShelterID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("playerexplode.ogg");
            this.playerExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("damageshelter.ogg");
            this.damageShelterID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("oh.ogg");
            this.ohID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("uh.ogg");
            this.uhID = soundPool.load(descriptor, 0);
        } catch(IOException ex) {
            Log.e("error", "failed to load sound files");
        }

        prepareLevel();
    }

    private void prepareLevel() {

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

            //
        }
    }

    private void update() {
        boolean bubped = false;
        boolean lost = false;

        // Move the player's ship

        // Update the invaders if visible

        // Update all the invaders bullets if active

        // Did an invader bump into the edge of the screen

        if(lost){
            prepareLevel();
        }

        // Update the players bullet

        // Has the player's bullet hit the top of the screen

        // Has an invaders bullet hit the bottom of the screen

        // Has the player's bullet hit an invader

        // Has an alien bullet hit a shelter brick

        // Has a player bullet hit a shelter brick

        // Has an invader bullet hit the player ship
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            // background for alpha
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            paint.setColor(Color.argb(255,255,255,255));

            // Draw the player spaceship

            // Draw the invaders

            // Draw the bricks if visible

            // Draw the players bullet if active

            // Draw the invaders bullets if active

            // Draw the score and remaining lives
            // Change the brush color
            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(40);

            canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);

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
            case MotionEvent.ACTION_DOWN: break;

            case MotionEvent.ACTION_UP: break;
        }
        return true;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isPaused() {
        return paused;
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

    // Player snarejenie
    private PlayerShip playerShip;
    private Bullet bullet;

    // Invaders stuff
    private Bullet[] invadersBullets = new Bullet[200];
    private int nextBullet;
    private final int MAX_INVADERS_BULLETS = 10;

    private Invader[] invaders = new Invader[60];
    private int numberOfInvafers = 0;

    // defence btrick
    private DefenceBrick[] bricks = new DefenceBrick[400];
    private int numberOfBricks = 0; /// without 0

    // SOUNDS
    private SoundPool soundPool;
    private int playerExplodeID = -1;
    private int invaderExplodeID = -1;
    private int shootID = -1;
    private int damageShelterID = -1;
    private int uhID = -1;
    private int ohID = -1;

    int score = 0;

    private int lives = 3;

    private long menaceInterval = 1000;
    private long lastMenaceTime = System.currentTimeMillis();

    private boolean uhOrOh;

}
