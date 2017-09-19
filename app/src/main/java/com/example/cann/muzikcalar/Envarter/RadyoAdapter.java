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
 * Created by Cann on 8.12.2016.
 */

public class RadyoAdapter  extends BaseAdapter {

   ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    private LayoutInflater mInflater;

    public RadyoAdapter(Activity activity, ArrayList<HashMap<String, String>> songsList) {
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
        satirView = mInflater.inflate(R.layout.radyoitem, null);
        TextView radyoTitle = (TextView) satirView.findViewById(R.id.radyoTitle);
        radyoTitle.setText( songsList.get(position).get("radyoTitle") );
        return satirView;
    }
}
