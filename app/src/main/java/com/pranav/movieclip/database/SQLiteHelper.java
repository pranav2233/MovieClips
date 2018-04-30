package com.pranav.movieclip.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pranav.movieclip.constant.Constants;

/**
 * Created by kumar-5753 on 28/04/18.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Constants.MOVIECLIP_TABLE_NAME + " ("
                + Constants.MOVIECLIP_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.MOVIECLIP_TABLE_VIDEO_TITLE + " TEXT NOT NULL,"
                + Constants.MOVIECLIP_TABLE_VIDEO_DESCRIPTION + " TEXT NOT NULL,"
                + Constants.MOVIECLIP_TABLE_VIDEO_THUMBNAIL_URL + " TEXT NOT NULL,"
                + Constants.MOVIECLIP_TABLE_VIDEO_CHANNEL_NAME + " TEXT NOT NULL,"
                + Constants.MOVIECLIP_TABLE_VIDEO_ID + " TEXT NOT NULL,"
                + " UNIQUE (" + Constants.MOVIECLIP_TABLE_VIDEO_ID + ") ON CONFLICT REPLACE)"); //No I18N

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
