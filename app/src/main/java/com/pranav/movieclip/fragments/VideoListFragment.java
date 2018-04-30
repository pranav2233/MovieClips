package com.pranav.movieclip.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pranav.movieclip.R;
import com.pranav.movieclip.adapters.VideoListAdapter;
import com.pranav.movieclip.constant.Constants;
import com.pranav.movieclip.database.CursorUtil;
import com.pranav.movieclip.network.Request;
import com.pranav.movieclip.utils.ApiUtil;
import com.pranav.movieclip.utils.VideoDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumar-5753 on 25/04/18.
 */

public class VideoListFragment extends Fragment {
    private View rootView;
    String actor,director;
    RecyclerView recyclerView;
    VideoDetails[] videoDetails = {};
    VideoDetails[] emptyVideoDetails = {};
    VideoListAdapter videoListAdapter;
    ProgressBar progressBar;
    boolean isSearch;
    private VideoListInterface videoListInterface;
    private static final int CLEAR_HISTORY = 1;
    LinearLayout emptyLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            if (getArguments() != null)
            {
                isSearch = getArguments().getBoolean(Constants.IS_SEARCH);
                if(isSearch)
                {
                    actor = getArguments().getString(Constants.ACTOR);
                    director = getArguments().getString(Constants.DIRECTOR);
                }
                new getMovieName(actor,director).execute();

            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_video_list, container, false);
        progressBar = rootView.findViewById(R.id.progress_loader);
        recyclerView = rootView.findViewById(R.id.video_list);
        emptyLayout = rootView.findViewById(R.id.empty_screen);
        rootView.findViewById(R.id.go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListAdapter = new VideoListAdapter(getActivity(),videoDetails);
        videoListAdapter.setOnItemClickListener(videoListItemClickListener);
        recyclerView.setAdapter(videoListAdapter);
        setHasOptionsMenu(true);
        return rootView;

    }
    private VideoListAdapter.VideoListItemClickListener videoListItemClickListener = new VideoListAdapter.VideoListItemClickListener() {
        @Override
        public void onItemClick(VideoListAdapter.VideoHolder holder) {
            CursorUtil.instance.insertIntoMovieClipTable(holder.videoId,holder.title.getText().toString(),holder.description.getText().toString(),holder.channelName.getText().toString(),holder.thumbnailURL);
            videoListInterface.showVideo(holder.videoId);
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        videoListInterface = (VideoListInterface)activity;

    }

    class getMovieName extends AsyncTask<String,String,String> {
        String actor,director;
        String[] movies;
        getMovieName(String actor,String director){
            this.actor = actor;
            this.director = director;
        }

        @Override
        protected String doInBackground(String... strings) {
            if(isSearch) {
                List<VideoDetails> abc = new ArrayList<>();
                ArrayList<String> actorMovies = ApiUtil.getInstance().getMoviesName(actor);
                ArrayList<String> directorMovies = ApiUtil.getInstance().getMoviesName(director);
                List<String> common = new ArrayList<String>(actorMovies);
                common.retainAll(directorMovies);
                movies = common.toArray(new String[common.size()]);
                if (movies.length == 0) {
                    return null;
                }
                else {
                    int numberofMovies = movies.length;
                    for (int i = 0; i < numberofMovies; i++) {
                        if (i == numberofMovies - 1) {

                            abc.addAll(ApiUtil.getInstance().getVideos(movies[i] + " movie clip", (Constants.MAX_COUNT / numberofMovies) + (Constants.MAX_COUNT % numberofMovies)));

                        } else {
                            abc.addAll(ApiUtil.getInstance().getVideos(movies[i] + " movie clip", (Constants.MAX_COUNT / numberofMovies)));

                        }
                    }
                    videoDetails = abc.toArray(new VideoDetails[abc.size()]);
                }
            }
            else
            {
                Cursor cursor = CursorUtil.instance.getMovieCursor();
                videoDetails = new VideoDetails[cursor.getCount()];
                int i = 0;
                for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious())
                {
                    VideoDetails details = new VideoDetails();
                    details.setVideoId(cursor.getString(cursor.getColumnIndex(Constants.MOVIECLIP_TABLE_VIDEO_ID)));
                    details.setVideoTitle(cursor.getString(cursor.getColumnIndex(Constants.MOVIECLIP_TABLE_VIDEO_TITLE)));
                    details.setVideoDescription(cursor.getString(cursor.getColumnIndex(Constants.MOVIECLIP_TABLE_VIDEO_DESCRIPTION)));
                    details.setChannelTitle(cursor.getString(cursor.getColumnIndex(Constants.MOVIECLIP_TABLE_VIDEO_CHANNEL_NAME)));
                    details.setThumbnailURL(cursor.getString(cursor.getColumnIndex(Constants.MOVIECLIP_TABLE_VIDEO_THUMBNAIL_URL)));
                    videoDetails[i++] = details;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(videoDetails.length>0) {
                videoListAdapter.swapData(videoDetails);
            }
            else
            {
                emptyLayout.setVisibility(View.VISIBLE);

            }
            progressBar.setVisibility(View.GONE);

        }
    }

    public interface VideoListInterface
    {
        void showVideo(String videoId);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(!isSearch){
            menu.clear();
            menu.add(0,CLEAR_HISTORY,Menu.NONE, R.string.clear_recents);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == CLEAR_HISTORY) {
            videoListAdapter.swapData(emptyVideoDetails);
            CursorUtil.instance.deleteClips();
            emptyLayout.setVisibility(View.VISIBLE);
        }
        return false;
    }

    void goBack(){
       getActivity().onBackPressed();
    }


}
