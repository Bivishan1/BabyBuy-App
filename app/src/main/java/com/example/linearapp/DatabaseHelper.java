package com.example.linearapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

//Database Version
    private static final int DATABASE_VERSION = 1;
//    Database Name
    private static final String DATABASE_NAME = "BabyBuy.db";
//    User table Name
    private static final String TABLE_USER = "user";
//    User Table Columns names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";

//    create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
    + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT," +
    COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

//    to understand in general query
//    private String createTable = "create table user(id integer autoincrement, name text, email text, )";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//Drop User Table if exist
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

//        Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user)     {
    SQLiteDatabase db = this.getWritableDatabase();
//    ContentValues: A key/value store that inserts
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
    }

    public Boolean checkUserName (String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_USER+" where "+ COLUMN_USER_EMAIL+"=?",new String[] {username});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUserDetails (String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = " select * from " + TABLE_USER+ " where " + COLUMN_USER_EMAIL + "=?"+"AND "+ COLUMN_USER_PASSWORD+"=?";
        Cursor cursor  = sqLiteDatabase.rawQuery(query,
                        new String[] {username,password});
        if(cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }
}
