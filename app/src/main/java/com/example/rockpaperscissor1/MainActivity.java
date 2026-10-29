package com.example.rockpaperscissor1;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    MenuFragment menuFragment;
    GameFragment gameFragment;
    StatsFragment statsFragment;
    InstructionsFragment instructionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainFragment = new MainFragment();
        menuFragment = new MenuFragment();
        gameFragment = new GameFragment();
        statsFragment = new StatsFragment();
        instructionsFragment = new InstructionsFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commit();
        }
    }

    public void showMenuFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, menuFragment).commit();
    }

    public void showGameFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, gameFragment).commit();
    }

    public void showStatsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, statsFragment).commit();
    }

    public void showInstructionsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, instructionsFragment).commit();
    }
}