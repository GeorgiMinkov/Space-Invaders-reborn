package fmi.course.simmim.spaceinvaders;

import android.graphics.RectF;

public class DefenceBrick {
    public DefenceBrick(int row, int column, int shelterNumber, int screenResolutionX, int screenResolutionY){
        int width = screenResolutionX / WIDTH_APPROXIMATION ;
        int height = screenResolutionY / HEIGHT_APPROXIMATION;

        isVisible = true;

        int brickPadding = 1;

        int shelterPadding = screenResolutionX / SHELTER_APPROXIMATION;
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

    private final int WIDTH_APPROXIMATION = 90;
    private final int HEIGHT_APPROXIMATION = 40;
    private final int SHELTER_APPROXIMATION = 9;
}
