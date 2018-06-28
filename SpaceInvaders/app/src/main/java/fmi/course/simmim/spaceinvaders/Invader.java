package fmi.course.simmim.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Invader extends Craft{
    public Invader(Context context, int row, int column, int screenResolutionX, int screenResolutionY) {
        super();

        this.setLength(screenResolutionX / 20);
        this.setHeight(screenResolutionY / 20); // 20 -> PROPORTION = 20

        this.isVisible = true;

        int padding = screenResolutionX / 25;

        this.setCoordinateX(column * (this.getLength() + padding));
        this.setCoordinateY(row * (this.getLength() + padding / 4));

        this.movementShiftUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader1);
        this.movementShiftDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader2);

        this.movementShiftUp = Bitmap.createScaledBitmap(this.movementShiftUp,
                (int) (this.getLength()),
                (int) (this.getHeight()),
                false);

        this.movementShiftDown = Bitmap.createScaledBitmap(this.movementShiftDown,
                (int) (this.getLength()),
                (int) (this.getHeight()),
                false);

        this.setShipSpeed(40.0f);
    }

    public void setInvisible() {
        this.isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public Bitmap getMovementShiftUp() {
        return movementShiftUp;
    }

    public Bitmap getMovementShiftDown() {
        return movementShiftDown;
    }

    public void update(long fps) {
        if (this.shipMoving == LEFT) {
            this.setCoordinateX(this.getCoordinateX() - this.getShipSpeed() / fps);
        }

        if (this.shipMoving == RIGHT) {
            this.setCoordinateX(this.getCoordinateX() + this.getShipSpeed() / fps);
        }

        this.updateRect();
    }

    public void dropDownAndReverse() {
        if (this.shipMoving == LEFT) {
            this.shipMoving = RIGHT;
        } else {
            this.shipMoving = LEFT;
        }

        this.setCoordinateY(this.getCoordinateY() + this.getHeight());
        this.setShipSpeed(this.getShipSpeed() * 1.18f); // 1.18f = SPEED_COEFFICIENT
    }

    public boolean takeAim(float playerShipX, float playerShipLength) {
        int randomNumber = -1;

        if(isInPosition(playerShipX, playerShipLength)) {
            // A 1 in 500 chance to shoot
            randomNumber = generator.nextInt(150);
            if(randomNumber == 0) {
                return true;
            }
        }

        // If firing randomly (not near the player) a 1 in 5000 chance
        randomNumber = generator.nextInt(2000);
        if(randomNumber == 0){
            return true;
        }

        return false;
    }

    private boolean isInPosition(float playerShipX, float playerShipLength) {
        return (playerShipX + playerShipLength > this.getCoordinateX() &&
                playerShipX + playerShipLength < this.getCoordinateX() + this.getLength()) ||
                (playerShipX > this.getCoordinateX() && playerShipX < this.getCoordinateX() + this.getLength());
    }

    private Random generator = new Random();

    private Bitmap movementShiftUp;
    private Bitmap movementShiftDown;

    public final int LEFT = 1;
    public final int RIGHT = 2;

    private int shipMoving = RIGHT;

    private boolean isVisible;

}
