package com.union.ip.sum.ronniej.showmoviesforchildren;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Created by RonnieJ on 2015-08-07.
 */
public class AdmobFragment extends Fragment {

    private static AdmobFragment instance;

    public static AdmobFragment getInstance() {
        if (instance == null)
            instance = new AdmobFragment();

        return instance;
    }

    private ViewGroup viewGroup;
    private View view;

    private AdView adView;
    private AdRequest adRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.admob_fragment, container, false);
            viewGroup = (ViewGroup)view;

            adView = new AdView(getActivity().getApplicationContext());
            adView.setAdUnitId(DataClass.admobAppId);
            adView.setAdSize(AdSize.BANNER);

            adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            viewGroup.addView(adView);
        }

        return view;
    }

    @Override
    public void onPause() {

        if (adView != null) {

            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {

        if (adView != null) {

            adView.resume();
        }

        super.onResume();
    }

    @Override
    public void onDestroy() {

        if (adView != null) {

            adView.destroy();
            adView = null;
        }

        super.onDestroy();
    }
}
