package com.bewtechnologies.com.bewtechnologies.httptask;

import android.app.ProgressDialog;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.view.View;

import com.bewtechnologies.rssfeedreader.MainActivity;
import com.bewtechnologies.rssfeedreader.RssDataController;

import java.nio.channels.AsynchronousCloseException;

/**
 * Created by aman on 6/11/15.
 */
public class MyProgressDialog extends AsyncTask <String,Integer,String>{

    public static ProgressDialog ringProgressDialog;
    RssDataController rc;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       /* ringProgressDialog= ProgressDialog.show(MainActivity.con, "Please wait...", "Downloading stuff", true);

        ringProgressDialog.setCancelable(true);*/
        rc= new RssDataController();
         rc.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        //ringProgressDialog.setMessage("Almost there.");
    }

    @Override
    protected String doInBackground(String... strings) {

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //MainActivity.pb.setVisibility(View.GONE);
       // ringProgressDialog.dismiss();

    }
}
