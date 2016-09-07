package com.caraquri.bookmanager_android.api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectAPI extends AsyncTask<Void,Void,StringBuffer> {
    StringBuffer stringBuffer;
    private String url;
    private String action;
    private String postData;
    private APIListener apiListener;

    public ConnectAPI(String postData,String action){
        this.action = action;
        this.postData = postData;
    }

    private void checkParam(){
        switch (this.action){
            case "getBook":
                this.url = "http://app.com/book/get";
                break;
        }
    }

    public void setAPIListener(APIListener apiListener){
        this.apiListener = apiListener;
    }

    @Override
    protected StringBuffer doInBackground(Void... params){
        HttpURLConnection connection;
        try {
            checkParam();
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(1000);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(this.postData.getBytes());
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            this.stringBuffer = new StringBuffer();
            String temp;
            while ((temp = bufferedReader.readLine()) != null){
                this.stringBuffer.append(temp);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return stringBuffer;
    }

    @Override
    protected void onPostExecute(StringBuffer result){
        try {
            apiListener.succeededAPIConnection(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
