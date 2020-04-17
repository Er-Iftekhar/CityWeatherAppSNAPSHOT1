package com.utb.iftekhar.cityweatherappsnapshot1;

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


    //Interface to send values of onPostExecute
    public interface AsyncResponse{
        void processFinish(String output);
    }

    //Interface variable
    public AsyncResponse delegate=null;

    public DownloadTask(AsyncResponse delegate){
        this.delegate=delegate;
    }


    @Override
    protected String doInBackground(String... urls) {

        String result="";
        URL url;
        HttpURLConnection urlConnection=null;
        try {
            Log.i("url [0]",urls[0]);
            url=new URL(urls[0]);
            Log.i("url received",url.toString());
            urlConnection=(HttpURLConnection)url.openConnection();
            InputStream in=urlConnection.getInputStream();
            InputStreamReader reader=new InputStreamReader(in);

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
            JSONObject jsonObject=new JSONObject(s);
            String weatherInfo=jsonObject.getString("weather");
            String temperature=jsonObject.getString("main");
            JSONArray jsonArray=new JSONArray(weatherInfo);

            String message="";
            String temperatureMessage="";

            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonPart=jsonArray.getJSONObject(i);
                String main=jsonPart.getString("main");
                String description=jsonPart.getString("description");

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
                delegate.processFinish(message);
            }
            historyDatabase.updateListView();


        } catch (Exception e) {
            e.printStackTrace();
            delegate.processFinish("Could not find weather");
        }
    }

}