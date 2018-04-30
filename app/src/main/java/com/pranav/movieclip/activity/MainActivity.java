package com.pranav.movieclip.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.pranav.movieclip.R;
import com.pranav.movieclip.constant.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner actorSpinner,directorSpinner;
    Button searchButton;
    String[] actors,directors;
    private static final int MENU_HISTORY = 1;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar();
        actorSpinner = findViewById(R.id.actor_spinner);
        actorSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        directorSpinner = findViewById(R.id.director_spinner);
        directorSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        actors = getResources().getStringArray(R.array.actor_array);
        directors = getResources().getStringArray(R.array.director_array);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_button)
        {
            if(isNetworkConnectionAvailable()) {
                Intent intent = new Intent(this, VideoListActivity.class);
                intent.putExtra(Constants.ACTOR, actors[actorSpinner.getSelectedItemPosition()]);
                intent.putExtra(Constants.DIRECTOR, directors[directorSpinner.getSelectedItemPosition()]);
                intent.putExtra(Constants.IS_SEARCH, true);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0,MENU_HISTORY,Menu.NONE,getResources().getString(R.string.recents));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent = new Intent(this,VideoListActivity.class);
        intent.putExtra(Constants.IS_SEARCH,false);
        startActivity(intent);
        return false;

    }

    boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }
}
