package com.union.ip.sum.ronniej.showmoviesforchildren;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by RonnieJ on 2015-08-02.
 */
public class YouTubePlayerViewFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    static YouTubePlayerViewFragment instance;

    public static YouTubePlayerViewFragment getInstance() {

        if (instance == null) {

            //Log.d("Test", "YouTubePlayerViewFragment.getInstance() called");
            instance = new YouTubePlayerViewFragment();
        }

        return instance;
    }

    private YouTubePlayerView playerView;
    private YouTubePlayer player;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //Log.d("Test", "YouTubePlayerViewFragment.onCreate");

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Log.d("Test", "YouTubePlayerViewFragment.onCreateView");

        if (view == null)
        {
            view = inflater.inflate(R.layout.youtube_player_view, container, false);
            playerView = (YouTubePlayerView)view.findViewById(R.id.youtubePlayerView);
            playerView.initialize(DataClass.youtubeAPIKey, this);

            instance = this;
        }

        else {

            if (player != null) player.loadVideo(DataClass.currentPlayerId);
        }
        //Log.d("Test", "YouTubePlayerViewFragment.onCreateView()");

        return view;
    }

    @Override
    public void onDestroy() {

        view = null;
        playerView = null;
        player.release();
        player = null;

        super.onDestroy();
    }

    // YouTubePlayer.OnInitializedListener
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        //Log.d("Test", "onInitializationSuccess");

        if (player == null) {

            player = youTubePlayer;
            player.setShowFullscreenButton(false);

            //player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
            //player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            //player.setOnFullscreenListener(this);
        }

        player.loadVideo(DataClass.currentPlayerId);

//        if (!b) {
//
//            player = youTubePlayer;
//
//            // loadVideo() will auto play video
//            // Use cueVideo() method, if you don't want to play it automatically
//            youTubePlayer.loadVideo(DataClass.currentPlayerId);
//
//            // Hiding player controls
//            //player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        Log.d("Test", "onInitializationFailure");
    }

    // YouTubePlayer.OnFullscreenListener
//    @Override
//    public void onFullscreen(boolean b) {
//
//    }
}
