package com.example.mad2019pastpaperb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.TimeZoneFormat;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Games.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER =
                "CREATE TABLE " + DatabaseMaster.Users.TABLE_NAME + " (" +
                        DatabaseMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        DatabaseMaster.Users.COLUMN_NAME + " TEXT," +
                        DatabaseMaster.Users.COLUMN_PASSWORD + " TEXT," +
                        DatabaseMaster.Users.COLUMN_USER_TYPE + " TEXT)";
        db.execSQL(SQL_CREATE_USER);

        String SQL_CREATE_GAME =
                "CREATE TABLE " + DatabaseMaster.Game.TABLE_NAME + " (" +
                        DatabaseMaster.Game._ID + " INTEGER PRIMARY KEY," +
                        DatabaseMaster.Game.COLUMN_NAME + " TEXT," +
                        DatabaseMaster.Game.COLUMN_YEAR + " INTEGER)";
        db.execSQL(SQL_CREATE_GAME);

        String SQL_CREATE_COMMENT =
                "CREATE TABLE " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                        DatabaseMaster.Comments._ID + " INTEGER PRIMARY KEY," +
                        DatabaseMaster.Comments.COLUMN_NAME + " TEXT," +
                        DatabaseMaster.Comments.COLUMN_RATING + " INTEGER," +
                        DatabaseMaster.Comments.COLUMN_COMMENTS + " TEXT)";
        db.execSQL(SQL_CREATE_COMMENT);
    }

    public long registerUser(String userName, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Users.COLUMN_NAME, userName);
        values.put(DatabaseMaster.Users.COLUMN_PASSWORD, password);

        if(userName.equals("admin") || userName.equals("Admin")){
            String userType = "Admin";
            values.put(DatabaseMaster.Users.COLUMN_USER_TYPE, userType);
        }

        return db.insert(DatabaseMaster.Users.TABLE_NAME, null, values);
    }

    public int userLogin(String userName, String password){
        SQLiteDatabase db = getReadableDatabase();
        String pwd = null;

        String query = "SELECT "+ DatabaseMaster.Users.COLUMN_PASSWORD +" FROM " + DatabaseMaster.Users.TABLE_NAME + " WHERE " +
                DatabaseMaster.Users.COLUMN_NAME + " = '" + userName + "'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                pwd = cursor.getString(0);
            } while(cursor.moveToNext());
        }

        if(pwd.equals(password)){
            if(userName.equals("admin")||userName.equals("Admin")){
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }

    public boolean addGame(String gameName, int year){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Game.COLUMN_NAME, gameName);
        values.put(DatabaseMaster.Game.COLUMN_YEAR, year);

        long status = db.insert(DatabaseMaster.Game.TABLE_NAME, null, values);

        if(status > 0){
            return true;
        }
        return false;
    }

    public ArrayList viewGames(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Game.COLUMN_NAME,
                DatabaseMaster.Game.COLUMN_YEAR
        };

        Cursor cursor = db.query(
                DatabaseMaster.Game.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                DatabaseMaster.Game.COLUMN_NAME + " ASC"
        );

        ArrayList gameData = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(1);
                gameData.add(name);
            } while(cursor.moveToNext());
        }

        return gameData;
    }

    public ArrayList getAllGameYears(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Game.COLUMN_NAME,
                DatabaseMaster.Game.COLUMN_YEAR
        };

        Cursor cursor = db.query(
                DatabaseMaster.Game.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                DatabaseMaster.Game.COLUMN_NAME + " ASC"
        );

        ArrayList gameYearData = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(2);
                gameYearData.add(name);
            } while(cursor.moveToNext());
        }

        return gameYearData;
    }

    public ArrayList viewComments(String gameName){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Comments.COLUMN_NAME,
                DatabaseMaster.Comments.COLUMN_RATING,
                DatabaseMaster.Comments.COLUMN_COMMENTS
        };

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,
                projection,
                DatabaseMaster.Comments.COLUMN_NAME + " LIKE ?",
                new String[]{gameName},
                null,
                null,
                DatabaseMaster.Comments.COLUMN_NAME + " ASC"
        );

        ArrayList commentData = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(3);
                commentData.add(name);
            } while(cursor.moveToNext());
        }

        return commentData;
    }

    public long insertComments(String comment, String gameName, int rating){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Comments.COLUMN_COMMENTS, comment);
        values.put(DatabaseMaster.Comments.COLUMN_NAME, gameName);
        values.put(DatabaseMaster.Comments.COLUMN_RATING, rating);

        return db.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);
    }

    public double getTotalRating(String gameName){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT SUM(" + DatabaseMaster.Comments.COLUMN_RATING + ") AS TotalRate FROM " + DatabaseMaster.Comments.TABLE_NAME +
                " WHERE " + DatabaseMaster.Comments.COLUMN_NAME + " LIKE ?";

        Cursor cursor = db.rawQuery(query, new String[]{gameName});

        int tot;

        if(cursor.moveToFirst()){
            //System.out.println("cursor" + Integer.parseInt(cursor.getString(0)));
            if(cursor.getString(0) == null){
                return 0;
            } else {
                tot = Integer.parseInt(cursor.getString(0));
                return tot;
            }
        } else{
            System.out.println("No data");
            return 0;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
