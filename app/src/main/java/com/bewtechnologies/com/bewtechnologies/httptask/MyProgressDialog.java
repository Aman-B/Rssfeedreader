package com.bewtechnologies.com.bewtechnologies.httptask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.bewtechnologies.rssfeedreader.MainActivity;
import com.bewtechnologies.rssfeedreader.RssDataController;

import java.nio.channels.AsynchronousCloseException;

/**
 * Created by aman on 6/11/15.
 */
public class MyProgressDialog extends AsyncTask <String,Integer,String>{

    private ProgressDialog ringProgressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ringProgressDialog= ProgressDialog.show(MainActivity.con, "Please wait...", "Downloading stuff", true);

        ringProgressDialog.setCancelable(true);
        RssDataController rc = new RssDataController();
        rc.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");
    }

    @Override
    protected String doInBackground(String... strings) {


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ringProgressDialog.dismiss();

    }
}
