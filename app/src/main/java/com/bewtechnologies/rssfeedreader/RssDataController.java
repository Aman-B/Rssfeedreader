package com.bewtechnologies.rssfeedreader;

import android.os.AsyncTask;
import android.util.Log;

import com.bewtechnologies.myadapter.PostItemAdapter;
import com.bewtechnologies.myadapter.postData;

import org.apache.http.ParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aman  on 10/28/2015.
 */
class RssDataController extends
        AsyncTask <String,Integer,ArrayList> {

    private MainActivity.RSSXMLTag currentTag;
    public  postData[] listData;
    int i=0;

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected ArrayList doInBackground(String... params) {
// TODO Auto-generated method stub
        String urlStr = params[0];
        InputStream is = null;
        ArrayList postDataList = new ArrayList();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d("debug", "The response is : " + response);
            is = connection.getInputStream();

// parse xml after getting the data
            XmlPullParserFactory factory = XmlPullParserFactory
                    .newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);

            int eventType = xpp.getEventType();
            postData pdData = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, DD MMM yyyy HH:MM:SS");
            while (eventType != XmlPullParser.END_DOCUMENT)
            {

                if (eventType == XmlPullParser.START_DOCUMENT)
                {

                }
                else if (eventType == XmlPullParser.START_TAG)
                {
                    Log.i("Name : ",xpp.getName());
                    if (xpp.getName().equals("item"))
                    {
                        pdData = new postData();
                        currentTag = MainActivity.RSSXMLTag.IGNORETAG;
                    }
                    else if (xpp.getName().equals("title"))
                    {
                        currentTag = MainActivity.RSSXMLTag.TITLE;
                    }
                    else if (xpp.getName().equals("link"))
                    {
                        currentTag = MainActivity.RSSXMLTag.LINK;
                    }
                    else if (xpp.getName().equals("pubDate"))
                    {

                        currentTag = MainActivity.RSSXMLTag.DATE;
                    }
                    else if(xpp.getName().equalsIgnoreCase(("img")))
                    {
                        currentTag=MainActivity.RSSXMLTag.IMAGE;
                        Log.d("Image","found");
                    }
                    else if(xpp.getName().equals("description"))
                    {
                        currentTag=MainActivity.RSSXMLTag.DESC;
                    }
                }
                else if (eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equals("item"))
                    {
// format the data here, otherwise format data in
// Adapter
                        Date postDate = dateFormat.parse(pdData.postDate);
                        pdData.postDate = dateFormat.format(postDate);
                        postDataList.add(pdData);
                    }
                    else
                    {
                        currentTag = MainActivity.RSSXMLTag.IGNORETAG;
                    }
                }


                else if (eventType == XmlPullParser.TEXT)
                {

                    String content = xpp.getText();
                    content = content.trim();
                    Log.d("Content ","content"+(i++)+content);
                    Log.d("debug", content);
                    if (pdData != null)
                    {
                        switch (currentTag)
                        {
                            case DESC:
                                if((content.length()>0) &&(content.contains("<img src"))){ //fetching img url from inside description tag...

                                    String closer_to_url=getSubString(content,"alt","src");
                                    String almost_got_imgurl=closer_to_url.substring(closer_to_url.indexOf("//"));
                                    Log.i("almost url",almost_got_imgurl);
                                    String imgurl= almost_got_imgurl.substring(almost_got_imgurl.indexOf("//"),almost_got_imgurl.indexOf("\""));
                                    imgurl="https:"+imgurl;
                                    Log.i("new url",imgurl);
                                Log.i("Ab lo : ", content);

                                    if(pdData.postThumbUrl!=null)
                                    {
                                        pdData.postThumbUrl+=imgurl;
                                    }
                                    else
                                    {
                                        pdData.postThumbUrl=imgurl;
                                    }
                                }




                                break;



                            case IMAGE:
                                if(content.length()!=0)
                                {
                                    if(pdData.postThumbUrl!=null)
                                    {
                                        Log.d("Image url:", content);
                                    }
                                    else
                                        Log.d("Image url:", content);
                                }

                            case TITLE:
                                if (content.length() != 0)
                                {
                                    if (pdData.postTitle != null)
                                    {
                                        pdData.postTitle += content;
                                    } else
                                    {
                                        pdData.postTitle = content;
                                    }
                                }
                                break;
                            case LINK:
                                if (content.length() != 0)
                                {
                                    if (pdData.postLink != null)
                                    {
                                        pdData.postLink += content;
                                        Log.d("link", content);
                                    } else
                                    {
                                        pdData.postLink = content;
                                    }
                                }
                                break;

                            case DATE:
                                if (content.length() != 0)
                                {
                                    if (pdData.postDate != null)
                                    {
                                        pdData.postDate += content;
                                    } else
                                    {
                                        pdData.postDate = content;
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

                eventType = xpp.next();
            }
            Log.v("tst", String.valueOf((postDataList.size())));
        } catch (MalformedURLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Log.i("Lel ", MainActivity.con + "---" + R.layout.postitem + "--  " + listData);
        return postDataList;
    }

    private String getSubString(String mainString, String  lastString, String startString) {
        String endString = "";
        int endIndex = mainString.indexOf(lastString);
        int startIndex = mainString.indexOf(startString);
        Log.d("message" + i++, "" + mainString.substring(startIndex, endIndex));
        endString = mainString.substring(startIndex, endIndex);
        Log.d("Here: ", endString);
        return endString;

    }


    @Override
    protected void onPostExecute(ArrayList result) {
// TODO Auto-generated method stub

        Log.i("Lel ", MainActivity.con+"---"+R.layout.postitem+"---"+listData);
        listData=new postData[10];

        for (int i = 0; i < result.size(); i++) {

            System.out.println("data : " + result.get(i).toString());
            listData[i]= (postData)result.get(i);

            System.out.println("data : " + result.get(i).toString());
        }

        PostItemAdapter itemAdapter= new PostItemAdapter(MainActivity.con,R.layout.postitem,listData);


        MainActivity.my_listview.setAdapter(itemAdapter);

    }
}