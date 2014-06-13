package com.example.citeit;

import java.util.ArrayList;

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
    private ArrayList<ZitatTopicWrapper> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
 
    public ListViewAdapter(Activity a, ArrayList<ZitatTopicWrapper> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
    
    @Override
    public ZitatTopicWrapper getItem(int position) {
        return data.get(position);
    }

 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);
 
        TextView txtCitation = (TextView) vi.findViewById(R.id.citation); // citation
        TextView txtAuthor = (TextView) vi.findViewById(R.id.author); // name
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.icon); // thumb image
        
        ZitatTopicWrapper citationItem = data.get(position);
        int loader = R.drawable.no_image;
 
        // Setting all values in listview
        txtCitation.setText(citationItem.get_zitatText());
        txtAuthor.setText(citationItem.get_zitatAutor());
        imageLoader.DisplayImage(citationItem.get_imageUrl(), loader, thumb_image);
        return vi;
    }

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
