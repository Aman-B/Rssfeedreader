package com.bewtechnologies.myadapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by aman on 5/11/15.
 */
public class DownloadImages extends AsyncTask <String[],URI,Bitmap[]>{

static boolean  image= false;
    public static String[] img_urls= new String[10];
    @Override
    protected void onPostExecute(Bitmap[] bitmap) {

        super.onPostExecute(bitmap);

        Log.i("Insider:1", "yes");

    }

    @Override
    protected Bitmap[] doInBackground(String[]... strings) {
        URL url = null;
        Bitmap[] bmp = new Bitmap[10];
        img_urls=strings[0];
        for(int i=0;i<10;i++) {
            try {
                if(img_urls[i]!=null) {
                    url = new URL(img_urls[i]);
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try { if(img_urls[i]!=null) {
                bmp[i] = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            ;
        }

        return bmp;
    }
}
