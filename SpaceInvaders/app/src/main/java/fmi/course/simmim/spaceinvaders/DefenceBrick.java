package fmi.course.simmim.spaceinvaders;

import android.graphics.RectF;

public class DefenceBrick {
    public DefenceBrick(int row, int column, int shelterNumber, int screenResolutionX, int screenResolutionY){
        // TO DO : all number to go as coeff
        int width = screenResolutionX / 90;
        int height = screenResolutionY / 40;

        isVisible = true;

        int brickPadding = 1;

        int shelterPadding = screenResolutionX / 9;
        int startHeight = screenResolutionY - (screenResolutionY / 8 * 2);

        rect = new RectF(column * width + brickPadding + (shelterPadding * shelterNumber) + shelterPadding + shelterPadding * shelterNumber,
                row * height + brickPadding + startHeight,
                column * width + width - brickPadding + (shelterPadding * shelterNumber) + shelterPadding + shelterPadding * shelterNumber,
                row * height + height - brickPadding + startHeight);
    }

    public RectF getRect(){
        return this.rect;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }

    private RectF rect;

    private boolean isVisible;
}
