package com.example.citeit;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
//    public ImageLoader imageLoader;
 
    public ListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);
 
        TextView txtCitation = (TextView)vi.findViewById(R.id.citation); // citation
        TextView txtAuthor = (TextView)vi.findViewById(R.id.author); // name
        ImageView thumb_image = (ImageView)vi.findViewById(R.id.icon); // thumb image
        
        
        HashMap<String, String> citation = new HashMap<String, String>();
        citation = data.get(position);
 
        // Setting all values in listview
        txtCitation.setText(citation.get(MainActivity.KEY_CITATION));
        txtAuthor.setText(citation.get(MainActivity.KEY_AUTHOR));
//        imageLoader.DisplayImage(citation.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}
