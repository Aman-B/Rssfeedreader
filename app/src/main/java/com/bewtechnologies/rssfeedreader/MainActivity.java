package com.bewtechnologies.rssfeedreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.bewtechnologies.com.bewtechnologies.httptask.HTTPDownloadTask;
import com.bewtechnologies.com.bewtechnologies.httptask.MyProgressDialog;
import com.bewtechnologies.myadapter.postData;


public class MainActivity extends AppCompatActivity {

   // private  String[] listData = {"post1","post2","post3","post4","post5","post6"};

    public  postData[] listData;
    public static Context con;
    public static ListView my_listview;
    private Handler updateHandler;
    MyProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;
        /*updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
*/
        // this.generateDummyData();

     /*   launchRingDialog();*/

        if (isOnline()) {
           /* HTTPDownloadTask check = new HTTPDownloadTask();
            check.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");*/


            pd = new MyProgressDialog();
            pd.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");
/*            RssDataController rc = new RssDataController();
            rc.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");*/


        }
        else
        {
            Toast.makeText(con, "Damn! No internet connection. ", Toast.LENGTH_SHORT).show();
        }


        my_listview = (ListView) this.findViewById(R.id.postListView);
        //   ArrayAdapter<String> itemAdapter =new ArrayAdapter<String>(this,R.layout.postitem,listData);

        //TODO : Check this too condition for god knows what
     /*   if(pd!=null)
        {
        if (pd.getStatus() == AsyncTask.Status.FINISHED) {
            new Thread() {
                @Override
                public void run() {
                    if ((isOnline())) {
                        Log.i("inside?:", "yes");

                    }

                }
            }.start();
        }
//        PostItemAdapter itemAdapter = new PostItemAdapter(this, R.layout.postitem,listData);


        }*/
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private void launchRingDialog()
    {
        final ProgressDialog ringProgressDialog= ProgressDialog.show(MainActivity.this,"Please wait...","Downloading stuff", true);
        ringProgressDialog.setCancelable(true);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try{ //Starting async task



//                    Thread.sleep(2000);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                ringProgressDialog.dismiss(); //Task done, dimiss the dialog.
            }
        }).start();


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
    protected void onResume() {
        super.onResume();
        if(isOnline()) {
            HTTPDownloadTask check = new HTTPDownloadTask();
            check.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");

            RssDataController rc = new RssDataController();
            rc.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");
        }
        else
        {
            Toast.makeText(con,"Darn! No internet connection. ",Toast.LENGTH_SHORT).show();
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
