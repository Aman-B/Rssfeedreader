package com.bewtechnologies.myadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bewtechnologies.rssfeedreader.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aman  on 10/28/2015.
 */
public class PostItemAdapter extends ArrayAdapter<postData> {

    private Activity myContext;
    private  postData[] data;


    public PostItemAdapter(Context context, int textViewResourceId, postData[] objects)
    {

        super(context, textViewResourceId, objects);

        myContext= (Activity)context;

        data=objects;


    }

    static class ViewHolder{
        TextView postTitleView;
        TextView postDataView;
        ImageView postThumbView;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if(convertView==null)
        {   //convertView saves the whole view once it's instantiated
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.postitem, null);
            viewHolder= new ViewHolder();


            viewHolder.postThumbView = (ImageView)  convertView.findViewById(R.id.postThumb);
            viewHolder.postTitleView = (TextView) convertView.findViewById(R.id.postTitleLabel);

            viewHolder.postDataView= (TextView) convertView.findViewById(R.id.postDateLabel);

            convertView.setTag(viewHolder); //setting tag  which saves views that have been already initialized but are out of sight.

        }

        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }


        if(data[position].postThumbUrl!=null)
        {
            DownloadImages dim = new DownloadImages();
            Bitmap bmp= null;
            try {
                bmp = dim.execute(data[position].postThumbUrl).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            viewHolder.postThumbView.setImageBitmap(bmp);
        }


        if(data[position].postThumbUrl == null)
        {
            viewHolder.postThumbView.setImageResource(R.drawable.ic_launcher);
        }



        viewHolder.postTitleView.setText(data[position].postTitle);
        /*
            set textview value in from postData to postitem.xml and data[position] gets
            it value in MainActivity, see the declaration there.
        */



        return convertView;
    }
}




