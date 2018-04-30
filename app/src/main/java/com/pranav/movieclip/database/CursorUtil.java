package com.pranav.movieclip.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pranav.movieclip.MovieClipGlobal;
import com.pranav.movieclip.constant.Constants;


/**
 * Created by kumar-5753 on 28/04/18.
 */

public class CursorUtil {
    public static  CursorUtil instance = null;
    SQLiteHelper dbHelper;
    SQLiteDatabase db ;
    public CursorUtil()
    {
        dbHelper = new SQLiteHelper(MovieClipGlobal.movieClipGlobalInstance, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
        instance = this;
    }

    public void insertIntoMovieClipTable(String videoId,String videoTitle,String videoDescription,String channelNamae,String thumbnailURL)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(Constants.MOVIECLIP_TABLE_VIDEO_ID, videoId);
        insertValues.put(Constants.MOVIECLIP_TABLE_VIDEO_TITLE, videoTitle);
        insertValues.put(Constants.MOVIECLIP_TABLE_VIDEO_DESCRIPTION, videoDescription);
        insertValues.put(Constants.MOVIECLIP_TABLE_VIDEO_THUMBNAIL_URL, thumbnailURL);
        insertValues.put(Constants.MOVIECLIP_TABLE_VIDEO_CHANNEL_NAME, channelNamae);
        db.insert(Constants.MOVIECLIP_TABLE_NAME, null, insertValues);
    }

    public Cursor getMovieCursor()
    {
        Cursor cursor;
        String query = "select * from "+Constants.MOVIECLIP_TABLE_NAME;
        cursor = db.rawQuery(query,null);
        return cursor;
    }

    public void deleteClips()
    {
        String query = "delete from "+Constants.MOVIECLIP_TABLE_NAME;
        db.execSQL(query);
    }

}
