package com.pranav.movieclip.utils;


import android.util.Log;

import com.pranav.movieclip.constant.Constants;
import com.pranav.movieclip.network.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kumar-5753 on 26/04/18.
 */

public class ApiUtil {

    public static ApiUtil instance = null;


    public static ApiUtil getInstance()
    {
        if(instance == null)
        {
            return new ApiUtil();
        }
        else {
            return instance;
        }
    }


    public  ArrayList<String> getMoviesName(String artist)
    {
        ArrayList<String> movies = new ArrayList<>();
        try
        {
            String url = Constants.TMDB_BASE_URL + "?api_key=" + Constants.TMDB_API_KEY + "&query=" + artist.replaceAll(" ", "+");
            JSONObject response = Request.getRequest(url,"POST");
            JSONArray result = response.optJSONArray("results");
            for(int i = 0;i<result.length();i++)
            {
                JSONArray knownfor = result.getJSONObject(i).getJSONArray("known_for");
                for(int j = 0;j<knownfor.length();j++)
                {
                    movies.add(knownfor.getJSONObject(j).getString("title"));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return movies;

    }
    public List<VideoDetails>  getVideos(String videoName,int maxResult)
    {
        try {
            String url = Constants.YOUTUBE_DATA_BASE_URL + "?key=" + Constants.YOUTUBE_API_KEY + "&maxResults=" + maxResult + "&part=snippet&q=" + videoName;
            JSONObject response = Request.getVideos(videoName, maxResult);
            return getVideosDetailsArray(response,maxResult);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private List<VideoDetails> getVideosDetailsArray(JSONObject response, int size) throws Exception {
        List<VideoDetails> videoDetails = new ArrayList<>();
        JSONArray items = response.getJSONArray("items");
        for(int i = 0;i<items.length();i++)
        {
            JSONObject videoInformation = items.getJSONObject(i);
            VideoDetails details = new VideoDetails();
            details.setVideoId(videoInformation.getJSONObject("id").optString("videoId"));
            details.setVideoTitle(videoInformation.getJSONObject("snippet").optString("title"));
            details.setVideoDescription(videoInformation.getJSONObject("snippet").optString("description"));
            details.setChannelTitle(videoInformation.getJSONObject("snippet").optString("channelTitle"));
            details.setThumbnailURL(videoInformation.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").optString("url"));
            videoDetails.add(details);
        }
        return  videoDetails;
    }
}
