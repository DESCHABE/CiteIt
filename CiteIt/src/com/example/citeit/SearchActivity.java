package com.example.citeit;

import java.util.ArrayList;
import java.util.Vector;

import com.example.citeit.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity implements OnClickListener{
	
	protected TextView _editText1 = null;
	protected TextView _TextView1 = null;
	protected Button _button1 = null;
	protected ListView _listView1 = null;
	
	ListViewAdapter adapter;
	DataHelper dataHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		_editText1 = (TextView) findViewById (R.id.editText1);
		_button1 = (Button) findViewById (R.id.button1);
		_listView1 = (ListView) findViewById (R.id.listView1);
		_button1.setOnClickListener (this);
		
		dataHelper = new DataHelper(this);
		
		_listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				ZitatTopicWrapper item = ((ListViewAdapter) _listView1.getAdapter()).getItem(position);
				dataHelper.save(item);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		if (view == _button1) {
			try {
				Vector<ZitatTopicWrapper> zitateVector = null;
				zitateVector = FreebaseSearchForZitat.sucheZitat(_editText1.getText().toString());
				ArrayList<ZitatTopicWrapper> citationList = new ArrayList<ZitatTopicWrapper>();
				
				if(zitateVector.size() < 1) {
					Toast toast = Toast.makeText(this, "Es wurden keine passenden #Zitate gefunden.", Toast.LENGTH_LONG);
					toast.show();
				} else {
					//Liste füllen
					for (ZitatTopicWrapper zitat : zitateVector) {
						
						if(zitat._autorDesZitats != null && zitat._autorDesZitats.getName() != null) {
							zitat.set_zitatAutor(zitat.get_autorDesZitats().getName());
						} else {
							zitat.set_zitatAutor("Unbekannt");
						}
						
						if(zitat._autorDesZitats != null) {
							Vector<PersonTopicWrapper> personenVector = FreebaseSearchForPerson.suchePerson(zitat.get_zitatAutor(), false, false);
							if(personenVector.size() > 0) {
								zitat.set_imageUrl(personenVector.elementAt(0).getBildURL());
							}
						}
						
						citationList.add(zitat);
					}
					
					adapter = new ListViewAdapter(this, citationList);
					_listView1.setAdapter(adapter);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}

}
