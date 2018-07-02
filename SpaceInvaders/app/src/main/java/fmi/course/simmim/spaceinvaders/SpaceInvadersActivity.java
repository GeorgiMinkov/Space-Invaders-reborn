package fmi.course.simmim.spaceinvaders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Point;
import android.view.Display;
import android.view.View;

/**
 * SpaceInvadersActivity is the entry point to the game.
 * This activity uses SpaceInvadersView instance @param spaceInvadersView to construct
 * game engine and to visualize gaming components
 *
 * @author GeorgiMinkov
 * @since 29.06.2018
 * @version 1.0
 */

public class SpaceInvadersActivity extends AppCompatActivity {
    SpaceInvadersView spaceInvadersView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        fullScreenMode();

        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        spaceInvadersView = new SpaceInvadersView(this, resolution.x, resolution.y);
        setContentView(spaceInvadersView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        spaceInvadersView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        spaceInvadersView.pause();
    }

    private void fullScreenMode() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}

