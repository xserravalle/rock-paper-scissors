
package com.example.rockpaperscissor1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StatsFragment extends Fragment {

    private TextView totalsText;
    private Button statsClearButton;
    private Button statsBackButton;
    private ListView historyList;
    private Button clearHistoryButton;


    private GameDatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_stats, container, false);

        totalsText = rootView.findViewById(R.id.totalsText);
        historyList = rootView.findViewById(R.id.historyList);

        historyList.setEmptyView(
                rootView.findViewById(R.id.emptyHistoryText)
        );

        statsClearButton = rootView.findViewById(R.id.statsClearButton);
        clearHistoryButton = rootView.findViewById(R.id.clearHistoryButton);
        statsBackButton = rootView.findViewById(R.id.statsBackButton);

        databaseHelper = new GameDatabaseHelper(requireContext());

        updateStatsDisplay();
        updateHistoryList();

        statsBackButton.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.showMenuFragment();
            }
        });

        clearHistoryButton.setOnClickListener(v -> {
            databaseHelper.clearStatsHistory();

            updateHistoryList();

            Toast.makeText(
                    requireContext(),
                    "Stats History Cleared!",
                    Toast.LENGTH_SHORT
            ).show();
        });


        statsClearButton.setOnClickListener(v -> {
            int[] stats = databaseHelper.getStats();

            databaseHelper.saveStatsHistory(
                    stats[0],
                    stats[1],
                    stats[2]
            );

            Toast.makeText(
                    requireContext(),
                    "Stats Saved to History!",
                    Toast.LENGTH_SHORT
            ).show();
            updateHistoryList();
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStatsDisplay();
    }

    private void updateStatsDisplay() {
        if (databaseHelper != null && totalsText != null) {

            int[] stats = databaseHelper.getStats();

            int wins = stats[0];
            int losses = stats[1];
            int draws = stats[2];

            totalsText.setText(
                    "Wins: " + wins +
                            "\nLosses: " + losses +
                            "\nDraws: " + draws
            );
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (databaseHelper != null) {
            databaseHelper.close();
            databaseHelper = null;
        }

        totalsText = null;
        statsClearButton = null;
        statsBackButton = null;
    }

    private void updateHistoryList() {
        if (databaseHelper == null || historyList == null) {
            return;
        }

        ArrayList<String> history = databaseHelper.getStatsHistory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                history
        );

        historyList.setAdapter(adapter);
    }

}
