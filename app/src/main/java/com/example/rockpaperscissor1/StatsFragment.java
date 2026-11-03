package com.example.rockpaperscissor1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StatsFragment extends Fragment {

    private TextView totalsText;
    private Button statsClearButton;
    private Button statsBackButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        totalsText = (TextView) rootView.findViewById(R.id.totalsText);
        statsClearButton = (Button) rootView.findViewById(R.id.statsClearButton);
        statsBackButton = (Button) rootView.findViewById(R.id.statsBackButton);

        updateStatsDisplay();

        statsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.showMenuFragment();
                }
            }
        });

        statsClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.clearStats();
                    updateStatsDisplay();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStatsDisplay();
    }

    private void updateStatsDisplay() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null && totalsText != null) {
            int w = activity.getWins();
            int l = activity.getLosses();
            int d = activity.getDraws();

            totalsText.setText("Wins: " + w + "\nLosses: " + l + "\nDraws: " + d);
        }
    }
}