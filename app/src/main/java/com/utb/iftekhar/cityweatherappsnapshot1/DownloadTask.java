package com.utb.iftekhar.cityweatherappsnapshot1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by syedy on 03-04-2020.
 */



public class DownloadTask extends AsyncTask<String, Void, String> {
    private static final String TAG="DownloadTask";
    private HistoryDatabase historyDatabase;


    //Interface injection in action
    //Interface to send values of onPostExecute
    public interface AsyncResponse{
        void processFinish(String output,String iconString);

    }

    //Interface variable
    public AsyncResponse delegate=null;

    public DownloadTask(AsyncResponse delegate){
        this.delegate=delegate;
    }

//    The url is received as an input to this method to perform the
//    background operation
    @Override
    protected String doInBackground(String... urls) {

        String result="";
        URL url;
        HttpURLConnection urlConnection=null;
        try {
            //String received from the execute() in Tab1Fragment onClick()

            //URL object to access any asset from the internet.
            url=new URL(urls[0]);

            //Represents a URL httpurlconnection instance to a URL on the
            urlConnection=(HttpURLConnection)url.openConnection();
            //Returns an input stream that reads from this open connection.
            InputStream in=urlConnection.getInputStream();
            //An InputStreamReader is a bridge from byte streams to character streams: It reads bytes and decodes
            // them into characters using a specified charset. The charset that it uses may be
            // specified by name or may be given explicitly, or the platform's default charset may be accepted.
            InputStreamReader reader=new InputStreamReader(in);
            //Reads characters into a portion of an array.
            int data=reader.read();

            while (data!=-1){
                char current=(char)data;
                result+=current;
                data=reader.read();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        historyDatabase=MainActivity.historyDatabase;

        try {
//            JSON string is received from the server.
            JSONObject jsonObject=new JSONObject(s);
//            get(String key) – gets the object associated with
//          the supplied key, throws JSONException if the key is not found
            String weatherInfo=jsonObject.getString("weather");
//            String temperature=jsonObject.getString("main");
//            JSONArray has a constructor that creates a Java object directly from a JSON String
            JSONArray jsonArray=new JSONArray(weatherInfo);
            String message="";
            String iconString="";

            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonPart=jsonArray.getJSONObject(i);
                String main=jsonPart.getString("main");
                String description=jsonPart.getString("description");
                iconString=jsonPart.getString("icon");


                if(!main.equals("") && !description.equals("")){

                    JSONObject temp=jsonObject.getJSONObject("main");
                    String currentTemp=temp.getString("temp");

                    String minTemp=temp.getString("temp_min");

                    String maxTemp=temp.getString("temp_max");



                    message+=  "Current Temperature :"+currentTemp+
                             "\nMinimum Temperature :"+minTemp+
                             "\nMaximum Temperature :"+maxTemp
                            +"\nWeather : "+main+
                             "\nDescription : "+description+"\r\n";
                }

            }
            if(!message.equals("")){
                delegate.processFinish(message, iconString);
            }
            historyDatabase.updateListView();
        } catch (Exception e) {
            e.printStackTrace();
            delegate.processFinish("Could not find weather", "");
        }
    }

}