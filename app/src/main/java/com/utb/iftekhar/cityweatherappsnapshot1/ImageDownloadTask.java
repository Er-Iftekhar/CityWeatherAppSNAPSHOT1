package com.utb.iftekhar.cityweatherappsnapshot1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by syedy on 23-04-2020.
 */

public class ImageDownloadTask extends AsyncTask <String, Void, Bitmap>{
    private Listener mListener;

    public ImageDownloadTask(Listener listener)
    {
        mListener=listener;

    }

    public interface Listener{
        void onImageLoaded(Bitmap bitmap);

    }



    @Override
    protected Bitmap doInBackground(String... args) {

        try {
            URL url = new URL(args[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            Sets the value of the doInput field for this connection to the specified value.
            connection.setDoInput(true);
            connection.connect();
//            Returns an input stream that reads from this open connection.
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(bitmap!=null){
            mListener.onImageLoaded(bitmap);
        }
    }
}
