package com.pranav.movieclip.constant;

/**
 * Created by kumar-5753 on 28/04/18.
 */

public class Constants {
    public static final String TMDB_API_KEY = "ba99f11569fdaade81a03d9262a61a7c";
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/search/person";
    public static final String YOUTUBE_API_KEY = "AIzaSyA8RxdOF5P1G1-ylCoRtkdDutArO7zzje0";
    public static final String YOUTUBE_DATA_BASE_URL = "https://www.googleapis.com/youtube/v3/search";
    public static final int MAX_COUNT = 25;

    public static final String ACTOR ="actor";
    public static final String DIRECTOR ="director";
    public static final String IS_SEARCH ="isSearch";
    public static final String SERACH_ACTION ="search";
    public static final String HISTORY_ACTION ="history";



    //Database
    public static final String DATABASE_NAME = "movieclip.db"; //No I18N
    public static final int DATABASE_VERSION =1;
    public static final String MOVIECLIP_TABLE_NAME = "movieclip";
    public static final String MOVIECLIP_TABLE_ID = "id";
    public static final String MOVIECLIP_TABLE_VIDEO_ID = "videoID";
    public static final String MOVIECLIP_TABLE_VIDEO_TITLE = "videoTitle";
    public static final String MOVIECLIP_TABLE_VIDEO_DESCRIPTION = "videoDescription";
    public static final String MOVIECLIP_TABLE_VIDEO_CHANNEL_NAME = "channelName";
    public static final String MOVIECLIP_TABLE_VIDEO_THUMBNAIL_URL = "thumbnailURL";

}
