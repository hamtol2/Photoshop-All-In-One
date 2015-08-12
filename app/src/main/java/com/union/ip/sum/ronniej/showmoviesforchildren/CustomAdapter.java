package com.union.ip.sum.ronniej.showmoviesforchildren;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.api.services.youtube.model.Playlist;

import java.util.ArrayList;

/**
 * Created by RonnieJ on 2015-07-31.
 */
public class CustomAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener {

    //private ArrayList<YouTubeThumbnailView> list;
    private ArrayList<ImageView> list;

    public CustomAdapter() {

        list = new ArrayList<ImageView>();

        //list = new ArrayList<YouTubeThumbnailView>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ImageView imageView;
    YouTubeThumbnailView tView;
    TextView textView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        String videoId = getVideoId(position);
        String thumnailURL = DataClass.getFinalURL(videoId, 0);

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.youtube_cliplist_item, parent, false);
            view = convertView;

            textView = (TextView)convertView.findViewById(R.id.thumbnailListText);
            textView.setText(DataClass.videoListNames[position]);
            convertView.setTag(R.id.thumbnailListText, textView);

            imageView = (ImageView)convertView.findViewById(R.id.youtubeThumnailImageView);
            imageView.setImageBitmap(DataClass.bitmapImages[position]);

            convertView.setTag(R.id.youtubeThumnailImageView, imageView);

            //tView = list.get(position);

//            tView = (YouTubeThumbnailView)convertView.findViewById(R.id.thumbnailView);
//            tView.setTag(videoId);
//            tView.initialize(DataClass.youtubeAPIKey, this);

            //convertView.setTag(R.id.thumbnailView, tView);

        } else {

            //textView = (TextView)convertView.findViewById(R.id.thumbnailListText);
            textView = (TextView)convertView.getTag(R.id.thumbnailListText);
            textView.setText(DataClass.videoListNames[position]);

            imageView = (ImageView)convertView.getTag(R.id.youtubeThumnailImageView);
            //imageView = (ImageView)convertView.getTag(R.id.youtubeThumnailImageView);
            imageView.setImageBitmap(DataClass.bitmapImages[position]);

            //tView = list.get(position);
            //Log.d("Test", "list value check, position: " + position + " , videoid: " + list.get(position).getTag());

            //tView = (YouTubeThumbnailView)convertView.findViewById(R.id.thumbnailView);
//            tView = (YouTubeThumbnailView)convertView.getTag(R.id.thumbnailView);
//            tView.setTag(videoId);
            //tView.initialize(DataClass.youtubeAPIKey, this);

            //Log.d("Critical Test", "getView() called, position :" + position + " , videoId: " + videoId);
        }

        return view;
    }

    public void add(ImageView imageView) {

        list.add(imageView);
    }

//    public void add(YouTubeThumbnailView thumbnailView) {
//        list.add(thumbnailView);
//    }

    public void remove(int position) {
        list.remove(position);
    }

    public String getVideoId(int position) {

//        return pororoSeasonOneList[position];
        return DataClass.videoList[position];
    }

    // YouTubeThumbnailView.OnInitializedListener
    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

//        String videoId = (String)youTubeThumbnailView.getTag();
//        youTubeThumbnailLoader.setVideo(videoId);
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
