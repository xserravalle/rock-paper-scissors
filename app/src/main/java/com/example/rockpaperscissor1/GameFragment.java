package com.example.rockpaperscissor1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {
    private ImageView computerImage;
    private TextView computerChoiceText;
    private TextView playerChoiceText;
    private TextView resultText;
    private TextView recordText;

    private Button rockButton;
    private Button paperButton;
    private Button scissorsButton;
    private Button restartButton;
    private Button clearRecordButton;
    private Button backMenuButton;

    private GameChoice mComputerChoice;
    private int mPlayerChoice = GameChoice.DEFAULT;

    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    private GameDatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        mComputerChoice = new GameChoice();

        databaseHelper = new GameDatabaseHelper(requireContext());

        int[] savedStats = databaseHelper.getStats();

        wins = savedStats[0];
        losses = savedStats[1];
        draws = savedStats[2];

        Toast.makeText(
                requireContext(),
                "SQLite Database Connected Successfully!",
                Toast.LENGTH_LONG
        ).show();

        computerImage = (ImageView) rootView.findViewById(R.id.computerImage);
        computerChoiceText = (TextView) rootView.findViewById(R.id.computerChoiceText);
        playerChoiceText = (TextView) rootView.findViewById(R.id.playerChoiceText);
        resultText = (TextView) rootView.findViewById(R.id.resultText);
        recordText = (TextView) rootView.findViewById(R.id.recordText);
        rockButton = (Button) rootView.findViewById(R.id.rockButton);
        paperButton = (Button) rootView.findViewById(R.id.paperButton);
        scissorsButton = (Button) rootView.findViewById(R.id.scissorsButton);
        restartButton = (Button) rootView.findViewById(R.id.restartButton);
        clearRecordButton = (Button) rootView.findViewById(R.id.clearRecordButton);
        backMenuButton = (Button) rootView.findViewById(R.id.backMenuButton);

        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRound(GameChoice.ROCK);
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRound(GameChoice.PAPER);
            }
        });

        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRound(GameChoice.SCISSORS);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRound();
            }
        });

        clearRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRecord();
            }
        });

        backMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.showMenuFragment();
                }
            }
        });

        resetRound();
        updateRecordDisplay();

        return rootView;
    }

    private void resetRound() {
        mComputerChoice.roll();

        computerImage.setImageResource(R.drawable.questionmark);
        computerChoiceText.setText("Choose ROCK, PAPER, or SCISSORS");
        playerChoiceText.setText("Your choice: ");
        resultText.setText("");

        mPlayerChoice = GameChoice.DEFAULT;

        rockButton.setEnabled(true);
        paperButton.setEnabled(true);
        scissorsButton.setEnabled(true);
    }

    private void playRound(int playerChoice) {
        mPlayerChoice = playerChoice;

        rockButton.setEnabled(false);
        paperButton.setEnabled(false);
        scissorsButton.setEnabled(false);

        if (mPlayerChoice == GameChoice.ROCK) {
            playerChoiceText.setText("Player choice: ROCK");
        } else if (mPlayerChoice == GameChoice.PAPER) {
            playerChoiceText.setText("Player choice: PAPER");
        } else if (mPlayerChoice == GameChoice.SCISSORS) {
            playerChoiceText.setText("Player choice: SCISSORS");
        }

        computerImage.setImageResource(mComputerChoice.getImageId());

        if (mComputerChoice.getChoice() == GameChoice.ROCK) {
            computerChoiceText.setText("Computer choice: ROCK");
        } else if (mComputerChoice.getChoice() == GameChoice.PAPER) {
            computerChoiceText.setText("Computer choice: PAPER");
        } else if (mComputerChoice.getChoice() == GameChoice.SCISSORS) {
            computerChoiceText.setText("Opponent choice: SCISSORS");
        }

        checkOutcome();
    }

    private void checkOutcome() {
        int computerChoice = mComputerChoice.getChoice();

        if (mPlayerChoice == computerChoice) {
            draws++;
            resultText.setText("DRAW!");
        } else if (mPlayerChoice == GameChoice.ROCK && computerChoice == GameChoice.SCISSORS) {
            wins++;
            resultText.setText("YOU WIN!");
        } else if (mPlayerChoice == GameChoice.PAPER && computerChoice == GameChoice.ROCK) {
            wins++;
            resultText.setText("YOU WIN!");
        } else if (mPlayerChoice == GameChoice.SCISSORS && computerChoice == GameChoice.PAPER) {
            wins++;
            resultText.setText("YOU WIN!");
        } else {
            losses++;
            resultText.setText("YOU LOSE.");
        }


        databaseHelper.saveStats(wins, losses, draws);

        updateRecordDisplay();
        MainActivity.updateStats(wins, losses, draws);

    }


    private void clearRecord() {
        wins = 0;
        losses = 0;
        draws = 0;

        databaseHelper.clearStats();

        updateRecordDisplay();

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.clearStats();
        }
    }


    private void updateRecordDisplay() {
        recordText.setText(
                "SQLite Database Connected\n" +
                        "Wins: " + wins +
                        " | Losses: " + losses +
                        " | Draws: " + draws
        );
    }
}