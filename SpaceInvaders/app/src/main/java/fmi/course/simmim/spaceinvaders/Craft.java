package fmi.course.simmim.spaceinvaders;

import android.graphics.RectF;

/**
 * Craft class that used for base of PlayerShip and AI Invaders
 * Consume basic parts for - position of object, size and object screen speed
 */

public class Craft {
    public Craft() {
        this.rect = new RectF();
        this.length = 0.0f;
        this.height = 0.0f;
        this.coordinateX = 0.0f;
        this.coordinateY = 0.0f;
        this.shipSpeed = 0.0f;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public void updateRect() {
        rect.top = coordinateY;
        rect.bottom = coordinateY + height;
        rect.left = coordinateX;
        rect.right = coordinateX + length;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public float getShipSpeed() {
        return shipSpeed;
    }

    public void setShipSpeed(float shipSpeed) {
        this.shipSpeed = shipSpeed;
    }

    private RectF rect;

    private float length;
    private float height;

    private float coordinateX;
    private float coordinateY;

    private float shipSpeed;
}
