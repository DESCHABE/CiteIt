package com.example.citeit;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
	// All static variables
    static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_CITATION = "citation"; // parent node
    static final String KEY_AUTHOR = "author";
    static final String KEY_ID = "id";

    static final String KEY_THUMB_URL = "thumb_url";
 
    ListView list;
    ListViewAdapter adapter;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        ArrayList<HashMap<String, String>> citationsList = new ArrayList<HashMap<String, String>>();
 
//        XMLParser parser = new XMLParser();
//        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
//        Document doc = parser.getDomElement(xml); // getting DOM element
// 
//        NodeList nl = doc.getElementsByTagName(KEY_SONG);
//        // looping through all song nodes <song>
//        for (int i = 0; i < nl.getLength(); i++) {
//            // creating new HashMap
//            HashMap<String, String> map = new HashMap<String, String>();
//            Element e = (Element) nl.item(i);
//            // adding each child node to HashMap key => value
//            map.put(KEY_ID, parser.getValue(e, KEY_ID));
//            map.put(KEY_CITATION, parser.getValue(e, KEY_CITATION));
//            map.put(KEY_AUTHOR, parser.getValue(e, KEY_AUTHOR));
//            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
// 
//            // adding HashList to ArrayList
//            songsList.add(map);
//        }
        
      HashMap<String, String> map = new HashMap<String, String>();
      // adding each child node to HashMap key => value
      map.put(KEY_ID, "1");
      map.put(KEY_CITATION, "Ein Zitat");
      map.put(KEY_AUTHOR, "Random");

      // adding HashList to ArrayList
      citationsList.add(map);
 
      map = new HashMap<String, String>();
      // adding each child node to HashMap key => value
      map.put(KEY_ID, "2");
      map.put(KEY_CITATION, "Alea acta est Die sis kdfjlasl kj vklasd k kd k d");
      map.put(KEY_AUTHOR, "Caesar asdfsajfk jkl fjsdkl j fkd lk lk fdl aff lk fdkl ");

      // adding HashList to ArrayList
      citationsList.add(map);
      
        list = (ListView) findViewById(R.id.lstMyCites);
 
        // Getting adapter by passing xml data ArrayList
        adapter=new ListViewAdapter(this, citationsList);
        list.setAdapter(adapter);
 
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
 
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners

        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search_online:
                openFreebaseActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	private void openFreebaseActivity() {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivityForResult(intent, 0);
		
	}
}
