package com.pranav.movieclip.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pranav.movieclip.R;
import com.pranav.movieclip.utils.MovieClipUtil;
import com.pranav.movieclip.utils.VideoDetails;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kumar-5753 on 27/04/18.
 */


public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity activity;
    VideoDetails[] videoDetails;
    private VideoListItemClickListener videoListItemClickListener;

    public VideoListAdapter(Activity activity, VideoDetails[] videoDetails)
    {
        this.activity =activity;
        this.videoDetails =videoDetails;
        setHasStableIds(true);
    }
    public void swapData(VideoDetails[] videoDetails)
    {
        this.videoDetails = videoDetails;
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        VideoHolder v = new VideoHolder(inflater.inflate(R.layout.video_list_layout, null,false));
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        VideoHolder holder = (VideoHolder) genericHolder;
        holder.title.setText(videoDetails[position].getVideoTitle());
        holder.description.setText(videoDetails[position].getVideoDescription());
        holder.channelName.setText(videoDetails[position].getChannelTitle());
        holder.videoId = videoDetails[position].getVideoId();
        holder.thumbnailURL = videoDetails[position].getThumbnailURL();
        if(MovieClipUtil.isNetworkConnectionAvailable()) {
            new ImageLoadTask(videoDetails[position].getThumbnailURL(), holder.thumbnail).execute();
        }


    }

    @Override
    public int getItemCount() {
        return videoDetails.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface VideoListItemClickListener
    {
        void onItemClick(VideoHolder holder);
    }

    public void setOnItemClickListener(VideoListItemClickListener listener)
    {
        this.videoListItemClickListener = listener;
    }


    public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       public ImageView thumbnail;
       public TextView title,description,channelName;
       public String videoId,thumbnailURL;

        public VideoHolder(final View view) {
            super(view);

            thumbnail = (ImageView) view.findViewById(R.id.action_image);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.descrption);
            channelName = (TextView) view.findViewById(R.id.channelName);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            videoListItemClickListener.onItemClick(this);
        }
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }


}
