package com.pranav.movieclip.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pranav.movieclip.R;
import com.pranav.movieclip.constant.Constants;
import com.pranav.movieclip.fragments.VideoListFragment;
import com.pranav.movieclip.fragments.VideoPlayerFragment;


public class VideoListActivity extends AppCompatActivity implements VideoListFragment.VideoListInterface {
    String actor,director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null)
        {
            boolean isSearch = getIntent().getExtras().getBoolean(Constants.IS_SEARCH);
            Bundle bundle = new Bundle();
            if(isSearch) {

                actor = getIntent().getExtras().getString(Constants.ACTOR);
                director = getIntent().getExtras().getString(Constants.DIRECTOR);
                bundle.putString(Constants.ACTOR, actor);
                bundle.putString(Constants.DIRECTOR, director);
            }
            else
            {
                getSupportActionBar().setTitle(R.string.recents);
            }
            bundle.putBoolean(Constants.IS_SEARCH, isSearch);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = new VideoListFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void showVideo(String videoId) {
        findViewById(R.id.video_container).setVisibility(View.VISIBLE);
        Bundle bundle = new Bundle();
        bundle.putString("videoId", videoId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new VideoPlayerFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.video_container, fragment);
        fragmentTransaction.commit();

    }

    public interface PlayVideo
    {
        void playVideo(String videoId);
    }
}
