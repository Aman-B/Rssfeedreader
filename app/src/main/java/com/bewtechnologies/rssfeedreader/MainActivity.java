package com.bewtechnologies.rssfeedreader;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bewtechnologies.com.bewtechnologies.httptask.HTTPDownloadTask;
import com.bewtechnologies.myadapter.postData;


public class MainActivity extends AppCompatActivity {

   // private  String[] listData = {"post1","post2","post3","post4","post5","post6"};

    public  postData[] listData;
    public static Context con;
    public static ListView my_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=MainActivity.this;

       // this.generateDummyData();

        my_listview= (ListView) this.findViewById(R.id.postListView);
     //   ArrayAdapter<String> itemAdapter =new ArrayAdapter<String>(this,R.layout.postitem,listData);



        HTTPDownloadTask check = new HTTPDownloadTask();
        check.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");

        RssDataController rc = new RssDataController();
        rc.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");


//        PostItemAdapter itemAdapter = new PostItemAdapter(this, R.layout.postitem,listData);



    }
    public enum RSSXMLTag {
        TITLE, DATE, LINK, CONTENT, GUID, IGNORETAG, RSSXMLTag,IMAGE,DESC;
    }

    //generating dummy data
    private void generateDummyData()
    {
        postData data = null;
        listData = new postData[10];

        for(int i=0;i<10;i++)
        {
            data= new postData();
            data.postDate="Oct 28, 2015";
            data.postTitle=" Post " + (i + 1) + " Title: This is the Post Title from RSS Feed";
            data.postThumbUrl=null;
            listData[i]=data;
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
