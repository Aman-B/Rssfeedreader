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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bewtechnologies.com.bewtechnologies.httptask.HTTPDownloadTask;
import com.bewtechnologies.com.bewtechnologies.httptask.MyProgressDialog;
import com.bewtechnologies.myadapter.postData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

   // private  String[] listData = {"post1","post2","post3","post4","post5","post6"};

    public  postData[] listData;
    public static Context con;
    public static ListView my_listview;
    private Handler updateHandler;
    MyProgressDialog pd;
    public static ProgressBar pb;
    public String Selected_language = "English";
    private Spinner lang;
    private boolean worldwide = false;
    Switch area_selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;

        //launch RSS
        checkIfOnlineAndLaunchRss("English",worldwide);
        my_listview = (ListView) this.findViewById(R.id.postListView);


        //toggle button
     /*   ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Hindi = 1;
                    //launch RSS
                    checkIfOnlineAndLaunchRss(Hindi);

                } else {

                    // The toggle is disabled

                    Hindi = 2;
                    //launch RSS
                    checkIfOnlineAndLaunchRss(Hindi);
                }
            }
        });
*/
        //TODO : Put a condition for regionwise and worldwide,for now checking worldwide


        //Putting language choice using spinnner. Hindi and Enlish only for now.

        lang =(Spinner) findViewById(R.id.language);

        //insert values in Spinner.
        initializeSpinner();

        //setonTouchlistener on item of Spinner
        initializeTouchListener();

        //switch for regional and worldwide news selection.
        area_selection = (Switch) findViewById(R.id.switch1);

        area_selection.setOnCheckedChangeListener(this);




    //end of oncreate
    }

    private void initializeTouchListener() {

        lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selected_language = String.valueOf(lang.getSelectedItem());

                pb.setVisibility(View.VISIBLE);
                checkIfOnlineAndLaunchRss(Selected_language, worldwide);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            //do nothing
            }
        });


    }

    private void initializeSpinner()
    {
        String[] langlist= new String[]{
           "English","Hindi"
        };

        ArrayAdapter<String> setItems = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,langlist);
        lang.setAdapter(setItems);

    }

    private void checkIfOnlineAndLaunchRss(String language,boolean worldwide)
    {


        if (isOnline()) {


            pb= (ProgressBar) findViewById(R.id.pbr);

            RssDataController rc = new RssDataController();

            if((language.equals("Hindi")) &&(!worldwide))
            {
                rc.execute("http://news.google.co.in/news?cf=all&hl=hi&ned=hi_in&output=rss");
            }
            else if((language.equals("Hindi"))&&(worldwide))
            {
                rc.execute("http://news.google.co.in/news?cf=all&hl=hi&ned=hi_in&topic=w&output=rss");

            }
            else if(language.equals("English")&&(!worldwide))
            {

                rc.execute("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");
            }

            else if(language.equals("English")&&(worldwide))
            {
                rc.execute("http://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=w&output=rss");
            }

        }
        else
        {
            Toast.makeText(con, "Damn! No internet connection. ", Toast.LENGTH_SHORT).show();
        }



    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            //do stuff when Switch is ON. (Worldwide news)
            worldwide=true;
            pb.setVisibility(View.VISIBLE);
            checkIfOnlineAndLaunchRss(Selected_language,worldwide);


        } else {
            //do stuff when Switch if OFF (Country-wise news)
            worldwide=false;
            pb.setVisibility(View.VISIBLE);
            checkIfOnlineAndLaunchRss(Selected_language,worldwide);
        }
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
