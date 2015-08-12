package com.union.ip.sum.ronniej.showmoviesforchildren;

import android.app.Fragment;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;

public class MainActivity extends YouTubeBaseActivity {

    public enum FragmentKind {

        None,
        YouTubeClipListFragment,
        YouTubePlayerViewFragment,
    }

    ServiceConnection youtubePlayerServiceConn = null;
    Intent youtubePlayerServiceIntent = null;

    InterstitialAd interstitialAd;

//    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Instantiate AdMob Instance.
//        adView = new AdView(getApplicationContext());
//        adView.setAdUnitId(DataClass.admobAppId);
//        adView.setAdSize(AdSize.BANNER);
//        adView.loadAd(new AdRequest.Builder().build());

        // Ad AdMob View to Layout for showing ads.
//        LinearLayout adLayout = (LinearLayout)findViewById(R.id.adLayout);
//        adLayout.addView(adView);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(DataClass.admobInterstitialAdAppId);
        interstitialAd.loadAd(new AdRequest.Builder().build());
        //interstitialAd.show();

        Log.d("Test", "interstitialAd.isloaded(): " + interstitialAd.isLoaded());

        DataClass.currentFragment = FragmentKind.YouTubeClipListFragment;

        //set fragment to main fragment layout.
        replaceFragment(FragmentKind.YouTubeClipListFragment, FragmentKind.YouTubeClipListFragment.toString());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Log.d("Test", "onPause called");
    }

        @Override
        protected void onStop() {
            super.onStop();
        }

        @Override
        protected void onResume() {
            super.onResume();
    }

    @Override
    protected void onDestroy() {

        //Log.d("Test", "onDestroy called start ");

        if (youtubePlayerServiceIntent != null) {

            super.stopService(youtubePlayerServiceIntent);
            youtubePlayerServiceIntent = null;
            //Log.d("Test", "onDestroy called 1 ");

            if (youtubePlayerServiceConn != null) {

                super.unbindService(youtubePlayerServiceConn);
                youtubePlayerServiceConn = null;
            }
        }

        super.onDestroy();
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {

        //Log.d("Test", "bindService called, " + service.toString() + " , flags: " + flags);
        youtubePlayerServiceConn = conn;
        youtubePlayerServiceIntent = service;

        boolean ret = super.bindService(service, conn, flags);
        //super.unbindService(conn);

        return ret;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        //Log.d("Test", "unbindService called");

        //super.unbindService(conn);
    }

    // replace current fragment to another fragment.
    public void replaceFragment(FragmentKind newFragment, String fragmentTag) {

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, getFragment(newFragment), fragmentTag)
                .commit();

        DataClass.currentFragment = newFragment;
    }

    public Fragment getFragment(FragmentKind fragmentKind) {

        switch (fragmentKind) {

            case YouTubeClipListFragment: return YouTubeClipListFragment.getInstance();
            case YouTubePlayerViewFragment: return YouTubePlayerViewFragment.getInstance();
            default: return null;
            //default: return YouTubeClipListFragment.getInstance();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

//        Log.d("test", "onBackPressed(), currentFragment: " + String.format("%d", DataClass.currentFragment));
        //Log.d("test", "onBackPressed(), currentFragment: " + DataClass.currentFragment);

        switch (DataClass.currentFragment) {

            case YouTubeClipListFragment:

                // ToDo: Show Close Dialog
                finishApp();
                break;

            case YouTubePlayerViewFragment:

                replaceFragment(
                        FragmentKind.YouTubeClipListFragment,
                        FragmentKind.YouTubeClipListFragment.toString());

                break;

            default: break;
        }
    }

    void finishApp() {

        interstitialAd.show();

        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        moveTaskToBack(true);
    }
}