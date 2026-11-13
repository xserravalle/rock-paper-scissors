package com.example.rockpaperscissor1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GameDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RockPaperScissors.db";
    private static final int DATABASE_VERSION = 2;

    public GameDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE game_stats (" +
                "id INTEGER PRIMARY KEY, " +
                "wins INTEGER, " +
                "losses INTEGER, " +
                "draws INTEGER)");

        db.execSQL("CREATE TABLE stats_history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "wins INTEGER, " +
                "losses INTEGER, " +
                "draws INTEGER, " +
                "saved_at TEXT DEFAULT CURRENT_TIMESTAMP)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("CREATE TABLE IF NOT EXISTS stats_history (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "wins INTEGER, " +
                    "losses INTEGER, " +
                    "draws INTEGER, " +
                    "saved_at TEXT DEFAULT CURRENT_TIMESTAMP)");
        }
    }


    public void saveStats(int wins, int losses, int draws) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("wins", wins);
        values.put("losses", losses);
        values.put("draws", draws);

        int updated = db.update("game_stats", values, "id = ?",
                new String[]{"1"});

        if (updated == 0) {
            values.put("id", 1);
            db.insertOrThrow("game_stats", null, values);
        }
    }

    public int[] getStats() {
        int[] stats = {0, 0, 0};

        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery(
                "SELECT wins, losses, draws FROM game_stats WHERE id = 1",
                null)) {

            if (cursor.moveToFirst()) {
                stats[0] = cursor.getInt(0);
                stats[1] = cursor.getInt(1);
                stats[2] = cursor.getInt(2);
            }
        }

        return stats;
    }

    public void clearStats() {
        saveStats(0, 0, 0);
    }

    public void saveStatsHistory(int wins, int losses, int draws) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("wins", wins);
        values.put("losses", losses);
        values.put("draws", draws);

        db.insertOrThrow("stats_history", null, values);
    }

    public ArrayList<String> getStatsHistory() {
        ArrayList<String> history = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery(
                "SELECT wins, losses, draws, saved_at " +
                        "FROM stats_history ORDER BY id DESC",
                null)) {

            while (cursor.moveToNext()) {
                int wins = cursor.getInt(0);
                int losses = cursor.getInt(1);
                int draws = cursor.getInt(2);
                String date = cursor.getString(3);

                history.add(
                        "Saved: " + date +
                                "\nWins: " + wins +
                                " | Losses: " + losses +
                                " | Draws: " + draws
                );
            }
        }

        return history;
    }

    public void clearStatsHistory() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("stats_history", null, null);
    }

}


