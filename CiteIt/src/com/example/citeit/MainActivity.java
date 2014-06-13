package com.example.citeit;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	static final String KEY_THUMB_URL = "thumb_url";

	DataHelper dataHelper;

	ListView list;
	ListViewAdapter adapter;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Workaraound
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		list = (ListView) findViewById(R.id.lstMyCites);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		dataHelper = new DataHelper(this);

		Log.d("Insert: ", "Inserting ..");
		dataHelper.save(new ZitatTopicWrapper(null, "Banane", "Affe", "url"));
		dataHelper.save(new ZitatTopicWrapper(null, "Alea iacata est", "Julius Cäsar", "url"));

	}

	@Override
	protected void onResume() {
		super.onResume();

		ArrayList<ZitatTopicWrapper> citationList = dataHelper.findAll();

		adapter = new ListViewAdapter(this, citationList);
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);

		// MenuItem searchItem = menu.findItem(R.id.action_search);
		//
		// SearchView searchView = (SearchView)
		// MenuItemCompat.getActionView(searchItem);
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
