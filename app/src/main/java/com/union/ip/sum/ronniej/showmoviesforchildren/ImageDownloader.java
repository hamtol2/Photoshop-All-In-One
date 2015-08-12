package com.union.ip.sum.ronniej.showmoviesforchildren;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Download image and set image to imageview in the listview
public class ImageDownloader extends AsyncTask<String, Integer, Bitmap> {

    public static int downloadCount = 0;
    public static CustomAdapter customAdapter;

    private String url;
    private ImageView imageView;
    private int arrayPosition;

    private int enforceRefeshCount = 10;

    public ImageDownloader(String url, ImageView imageView, int arrayPosition) {

        this.url = url;
        this.imageView = imageView;
        this.arrayPosition = arrayPosition;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try {

            URL urlConnection = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;

        } catch (Exception ex) {

            Log.d("Text", "image load error: " + ex.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        DataClass.bitmapImages[arrayPosition] = bitmap;

        downloadCount++;

        // when image download completed all then, force to refresh listview.
        if (downloadCount >= enforceRefeshCount) {

            customAdapter.notifyDataSetChanged();
            //Log.d("Test", "image download completed");
        }
    }
}