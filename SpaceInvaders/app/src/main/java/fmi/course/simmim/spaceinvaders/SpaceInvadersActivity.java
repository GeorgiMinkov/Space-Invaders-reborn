package fmi.course.simmim.spaceinvaders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Point;
import android.view.Display;

public class SpaceInvadersActivity extends AppCompatActivity {
    SpaceInvadersView spaceInvadersView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

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
}

