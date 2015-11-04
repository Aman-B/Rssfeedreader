package com.bewtechnologies.com.bewtechnologies.httptask;

import android.os.AsyncTask;
import android.util.Log;

import com.bewtechnologies.myadapter.postData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aman  on 10/28/2015.
 */
public class HTTPDownloadTask extends AsyncTask <String,Integer, postData[]> {
    @Override
    protected postData[] doInBackground(String... params)
    {

        String urlStr = params[0];
        InputStream is = null;

        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(10*1000);
            connection.setConnectTimeout(10*1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int response = connection.getResponseCode();
            Log.d("debug","The response is : "+ response);
            is = connection.getInputStream();

            //read string

            final int bufferSize =1024;

            byte[] buffer =new byte[1024];

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            while(true)
            {
                int count =is.read(buffer,0,bufferSize);
                if(count==-1)
                {
                    break;
                }
                os.write(buffer);
            }

            os.close();
            String result = new String(os.toByteArray(),"UTF-8");
            Log.d("debug",result);
        }

        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }


        catch(IOException e)
        {
            e.printStackTrace();
        }


        return null;

    }
}
