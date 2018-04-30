package com.pranav.movieclip.network;


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


import com.google.api.client.extensions.android.http.AndroidHttp;


import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;

import com.google.api.services.youtube.model.*;



/**
 * Created by kumar-5753 on 26/04/18.
 */

public class Request {
    public static JSONObject getRequest(String urls,String method) {
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String response = sb.toString();
            JSONObject myResponse = new JSONObject(response.toString());
            return myResponse;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static JSONObject getVideos(String query, int maxResult)  {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        YouTube youtube = new com.google.api.services.youtube.YouTube.Builder(
                transport, jsonFactory, null)
                .setApplicationName("Movie Clip")
                .build();;
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet");
            parameters.put("maxResults", maxResult+"");
            parameters.put("q", query);
            parameters.put("type", "");

            YouTube.Search.List searchListByKeywordRequest = youtube.search().list(parameters.get("part").toString());
            if (parameters.containsKey("maxResults")) {
                searchListByKeywordRequest.setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
            }

            if (parameters.containsKey("q") && parameters.get("q") != "") {
                searchListByKeywordRequest.setQ(parameters.get("q").toString());
            }

            if (parameters.containsKey("type") && parameters.get("type") != "") {
                searchListByKeywordRequest.setType(parameters.get("type").toString());
            }
            searchListByKeywordRequest.setKey("AIzaSyA8RxdOF5P1G1-ylCoRtkdDutArO7zzje0");

            SearchListResponse response = searchListByKeywordRequest.execute();
            JSONObject abc = new JSONObject(response.toString());
            return  abc;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;

        }
    }

}
