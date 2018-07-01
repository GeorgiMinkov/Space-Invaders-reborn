package fmi.course.simmim.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PlayerShip extends Craft {
    PlayerShip(Context context, int screenResolutionX, int screenResolutionY) {
        super();

        this.setLength(screenResolutionX / SCREEN_PROPORTION);
        this.setHeight(screenResolutionY / SCREEN_PROPORTION);

        this.setCoordinateX(screenResolutionX / 2);
        this.setCoordinateY(screenResolutionY - SCREEN_PROPORTION * 2);

        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);

        this.bitmap = Bitmap.createScaledBitmap(this.bitmap,
                                            (int)this.getLength(),
                                            (int)this.getHeight(), false);
        this.setShipSpeed(350);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setShipMovement(int state) {
        if (state >= STOPPED && state <= RIGHT) {
            this.shipMovement = state;
        }
    }

    public void update(long fps) {
        if (this.shipMovement == this.LEFT) {
            this.setCoordinateX(this.getCoordinateX() - this.getShipSpeed() / fps);
        }

        if (this.shipMovement == this.RIGHT) {
            this.setCoordinateX(this.getCoordinateX() + this.getShipSpeed() / fps);
        }

        this.updateRect();
    }

    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    private Bitmap bitmap;

    private int shipMovement = STOPPED;

    private final int SCREEN_PROPORTION = 10;
}
