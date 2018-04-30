package com.pranav.movieclip;

import android.app.Application;

import com.pranav.movieclip.database.CursorUtil;
import com.facebook.stetho.Stetho;


/**
 * Created by kumar-5753 on 28/04/18.
 */

public class MovieClipGlobal extends Application{
    public static MovieClipGlobal movieClipGlobalInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        movieClipGlobalInstance = this;
        Stetho.initializeWithDefaults(this);

        initUtilClasses();
    }

    private void initUtilClasses() {
        new CursorUtil();
    }
}
