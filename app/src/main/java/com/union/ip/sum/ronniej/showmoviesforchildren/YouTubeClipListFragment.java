package com.union.ip.sum.ronniej.showmoviesforchildren;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class YouTubeClipListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private CustomAdapter adapter;

    TextView textView;
//    YouTubeThumbnailView thumbnailView;
//    YouTubeThumbnailLoader thumbnailLoader;

    ImageView imageView;


    View view;

    static YouTubeClipListFragment instance;

    public static YouTubeClipListFragment getInstance() {

        if (instance == null) {

            instance = new YouTubeClipListFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.youtube_cliplist, container, false);

        if (adapter == null) {

            adapter = new CustomAdapter();
            DataClass.videoList = readVideolistFromFile();
            DataClass.bitmapImages = new Bitmap[DataClass.videoList.length];
            ImageDownloader.downloadCount = 0;
            ImageDownloader.customAdapter = adapter;

            for (int ix = 0; ix < DataClass.bitmapImages.length; ++ix) {

                imageView = new ImageView(getActivity().getApplicationContext());

                ImageDownloader downloader = new ImageDownloader(DataClass.getFinalURL(DataClass.videoList[ix], 1), imageView, ix);
                downloader.execute();
                imageView.setImageBitmap(DataClass.bitmapImages[ix]);

                adapter.add((imageView));
            }

            //adapter.notifyDataSetChanged();

//            for (int ix = 0; ix < DataClass.videoList.length; ++ix) {
//
//                //thumbnailView = (YouTubeThumbnailView)getActivity().findViewById(R.id.thumbnailView);
//
//                thumbnailView = new YouTubeThumbnailView(getActivity().getApplicationContext());
//                thumbnailView.setTag(adapter.getVideoId(ix));
//                thumbnailView.initialize(DataClass.youtubeAPIKey, adapter);
//
//                adapter.add(thumbnailView);
//            }
        }

        if (listView == null) {

            listView = (ListView)view.findViewById(R.id.youtubeClipListView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

            instance = this;
        }

        return view;
    }

    @Override
    public void onDestroy() {

//        thumbnailView = null;
//        thumbnailLoader = null;
        imageView = null;
        listView = null;
        view = null;
        adapter = null;

        //Log.d("Test", "YouTubeClipListFragment.onDestroy called");

        super.onDestroy();
    }

    // read video list from file.
    private String[] readVideolistFromFile() {

        String[] list = new String[86];
        DataClass.videoListNames = new String[list.length];
        String data;
        InputStream inputStream = getResources().openRawResource(R.raw.videolist);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {

            int count = 0;
            while ((data = reader.readLine()) != null) {

                    if (count < list.length) {

                        list[count] = data.split(":")[0];
                        DataClass.videoListNames[count] = data.split(":")[1];

                        //Log.d("test", "item check: " + list[count] + " : " + DataClass.videoListNames[count]);
                        count++;
                    }
            }

            //Log.d("Test", "Test File Read completed, count: " + count);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return list;
    }

//    // YouTubeThumbnailView.OnInitializedListener
//    @Override
//    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
//
////        thumbnailView = youTubeThumbnailView;
////        thumbnailLoader = youTubeThumbnailLoader;
////        thumbnailLoader.setVideo(DataClass.youtubeClipId);
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//
//    }

    // AdapterView.OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DataClass.currentPlayerId = adapter.getVideoId(position);
        ((MainActivity)getActivity()).replaceFragment(
                MainActivity.FragmentKind.YouTubePlayerViewFragment,
                MainActivity.FragmentKind.YouTubePlayerViewFragment.toString());
    }
}
