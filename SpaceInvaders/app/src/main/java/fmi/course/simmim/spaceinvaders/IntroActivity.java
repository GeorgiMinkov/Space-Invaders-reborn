package fmi.course.simmim.spaceinvaders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        Button startButton = findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent startGame = new Intent(IntroActivity.this, SpaceInvadersActivity.class);
                startActivity(startGame);
            }
        });

        Button quitButton = findViewById(R.id.quit_game_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quitGame = new Intent(IntroActivity.this, LoginActivity.class);
            }
        });
    }


}
