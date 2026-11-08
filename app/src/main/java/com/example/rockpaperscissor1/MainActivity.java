package com.example.rockpaperscissor1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

    private static int wins = 0;
    private static int losses = 0;
    private static int draws = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Rock Paper Scissors");
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_back_to_menu) {
            showMenuFragment();
        }

        return super.onOptionsItemSelected(item);
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

    public static void updateStats(int wins, int losses, int draws) {
        MainActivity.wins = wins;
        MainActivity.losses = losses;
        MainActivity.draws = draws;
    }

    public static void clearStats() {
        MainActivity.wins = 0;
        MainActivity.losses = 0;
        MainActivity.draws = 0;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }
}