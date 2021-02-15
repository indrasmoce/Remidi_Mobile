package com.example.katalog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.katalog.model.Team;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbteam";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TEAM = "team";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TEAMBADGE = "teambadge";

    private static final String CREATE_TABLE_TEAM = "CREATE TABLE "
            + TABLE_TEAM + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT, " + KEY_TEAMBADGE + " TEXT);";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TEAM + "'");
        onCreate(db);
    }

    public long addTeamDetail(String name, String teambadge){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_TEAMBADGE, teambadge);
        long insert = db.insert(TABLE_TEAM, null, values);

        return insert;
    }

    public ArrayList<Team> getAllTeams(){
        ArrayList<Team> teamModelArrayList = new ArrayList<Team>();

        String selectQuery = "SELECT * FROM " + TABLE_TEAM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                Team team = new Team();
                team.setIdTeam(c.getInt(c.getColumnIndex(KEY_ID)));
                team.setStrTeam(c.getString(c.getColumnIndex(KEY_NAME)));
                team.setStrTeamBadge(c.getString(c.getColumnIndex(KEY_TEAMBADGE)));

                teamModelArrayList.add(team);
            } while (c.moveToNext());
        }

        return teamModelArrayList;
    }

    public void deleteTeam(int id){
        Log.d("ID TEAM", "value" + id);
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TEAM, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
