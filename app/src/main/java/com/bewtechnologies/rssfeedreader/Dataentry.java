package com.bewtechnologies.rssfeedreader;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bewtechnologies.database.FeedReaderContract;
import com.bewtechnologies.database.FeedReaderDbHelper;
import com.bewtechnologies.myadapter.PostItemAdapter;
import com.bewtechnologies.myadapter.postData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by aman on 6/11/15.
 */


public class Dataentry extends AsyncTask<ArrayList,Integer,String>{
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(MainActivity.con,"Data Saved! here : "+newrowid,Toast.LENGTH_SHORT).show();

    }

    long newrowid;
    ArrayList got_results;
    postData[] listData_fordb;
    byte[] image;
    @Override
    protected String doInBackground(ArrayList... params)
    {
            got_results= new ArrayList();
            got_results=params[0];
            int size= got_results.size();
            listData_fordb= new postData[size];

        for (int i = 0; i < got_results.size(); i++) {

            System.out.println("data  for db: " + got_results.get(i));
            listData_fordb[i]= (postData)got_results.get(i);

        }

        putinDb(listData_fordb,size);


        return null;
    }

    private void putinDb(postData[] listData_fordb,int size)
    {
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(MainActivity.con);

        SQLiteDatabase news =mDbHelper.getWritableDatabase();


        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        for(int i=0;i<size;i++)
        {
            Log.i("bitmap","here:"+PostItemAdapter.bitmaps[i]);
            if(PostItemAdapter.bitmaps[i]!=null) {
              image = getImageBytes(PostItemAdapter.bitmaps[i]);
            }

           // values.put(FeedReaderContract.FeedEntry._ID ,i);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NEWS_DATA, listData_fordb[i].postTitle);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NEWS_DATE, listData_fordb[i].postDate);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NEWS_URL, listData_fordb[i].postLink);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NEWS_IMAGE, image);



            newrowid= news.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        }



    }

    private byte[] getImageBytes(Bitmap bitmap) {
        // convert from bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();

    }
}
