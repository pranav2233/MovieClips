package com.pranav.movieclip.utils;

/**
 * Created by kumar-5753 on 27/04/18.
 */

public class VideoDetails {
    private String videoId,videoTitle,videoDescription,thumbnailURL,channelTitle;


//    VideoDetails(String videoId,String videoTitle,String videoDescription,String thumbnailURL,String channelTitle)
//    {
//        this.videoId = videoId;
//        this.videoTitle = videoTitle;
//        this.videoDescription = videoDescription;
//        this.thumbnailURL = thumbnailURL;
//        this.channelTitle = channelTitle;
//    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
