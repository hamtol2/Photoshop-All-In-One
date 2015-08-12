package com.union.ip.sum.ronniej.showmoviesforchildren;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.api.services.youtube.model.MonitorStreamInfo;

import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by RonnieJ on 2015-08-03.
 */
public class DataClass {

    // For YouTubePlayer
    public static String youtubeAPIKey = "AIzaSyCCtDcLXLiAwc6ChAX7PPIkOAdILXcXVEQ";
    public static String youtubeClipId = "X_Dn7z9613Y";
    public static String currentPlayerId = "";
    public static String[] videoList;

    // For Youtube Thumnail.
    public static String thumnailImageBaseURL = "http://img.youtube.com/vi/";
    public static Bitmap[] bitmapImages;
    public static String[] videoListNames;

    // For AdMob
    public static final String admobAppId = "ca-app-pub-8077853857502289/7491761652";
    public static final String admobInterstitialAdAppId = "ca-app-pub-8077853857502289/9271912457";

    // For App
//    public static int currentFragment = 0;
    public static MainActivity.FragmentKind currentFragment = MainActivity.FragmentKind.None;

    // imageSize, 0: 480 x 360, 1 ~ 3: 120 x 90
    public static String getFinalURL(String videoId, int imageSize) {

        String finalURL = thumnailImageBaseURL;
        finalURL += videoId + "/" + String.format("%d", imageSize) + ".jpg";

        return finalURL;
    }
}
