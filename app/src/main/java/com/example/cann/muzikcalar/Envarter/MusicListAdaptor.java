package com.example.cann.muzikcalar.Envarter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cann.muzikcalar.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cann on 30.11.2016.
 */

public class MusicListAdaptor extends BaseAdapter {
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private LayoutInflater mInflater;

    public MusicListAdaptor(Activity activity, ArrayList<HashMap<String, String>> songsList) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.songsList = songsList;
    }

    public int getCount() {
      return   songsList.size();
    }

    @Override
    public Object getItem(int position) {
        return songsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.playlistitem, null);
        TextView songtitle = (TextView) satirView.findViewById(R.id.songTitle);
        ImageView rsx = (ImageView) satirView.findViewById(R.id.simge);
        songtitle.setText(   songsList.get(position).get("songTitle"));
        rsx.setImageResource(R.drawable.playblue);
        return satirView;
    }
}
