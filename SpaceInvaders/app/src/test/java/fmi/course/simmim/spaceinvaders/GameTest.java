package fmi.course.simmim.spaceinvaders;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

import javax.annotation.processing.SupportedAnnotationTypes;

/**
 * GameTest - test basic functionality of the game
 */
public class GameTest {
   @Test
   public void testIfUnactiveBulletCanShoot() {
       boolean expectedResult = true;

       float startX = 1, startY = 1;
       int direction = Bullet.DOWN;

       int screenRezolutionY = 1;

       Bullet loadedBullet = new Bullet(screenRezolutionY);

       Assert.assertEquals(expectedResult, loadedBullet.shoot(startX, startY, direction));
   }

   @Test
   public void testIfInvaderWillShoot() {

   }

    @Test
    public void testIfPausingStateIsTrueWhenGameIsPaused() {
        Context currentState = InstrumentationRegistry.getTargetContext();
        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        SpaceInvadersView testView = new SpaceInvadersView(currentState, resolution.x, resolution.y);

        testView.pause();

        boolean expectedResult = true;

        Aseert.assertEquals(expectedResult, testView.isPaused());
    }

    @Test
    public void testIfPLayingStateIsTrueWhenGameIsResume() {
        Context currentState = InstrumentationRegistry.getTargetContext();
        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        SpaceInvadersView testView = new SpaceInvadersView(currentState, resolution.x, resolution.y);

        testView.pause();

        testView.resume();

        boolean expectedResult = true;

        Assert.assertEquals(expectedResult, testView.isPlaying());
    }

    @Test
   public void testIsTouchedScreenResponding() {
        MotionEvent motionEvent = new MotionEvent();
        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        Context testC = new Context();
        SpaceInvadersView test = new SpaceInvadersView(testC, 0, 0);
        Assert.assertEquals(true, test.onTouchEvent(motionEvent));
   }
}
