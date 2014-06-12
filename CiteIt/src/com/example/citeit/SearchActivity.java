package com.example.citeit;

import java.util.Vector;

import com.example.citeit.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnClickListener{
	
	protected TextView _editText1 = null;
	protected TextView _editText2 = null;
	protected TextView _TextView1 = null;
	protected Button _button1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		_editText1 = (TextView) findViewById (R.id.editText1);
		_editText2 = (TextView) findViewById (R.id.editText2);
		_TextView1 = (TextView) findViewById (R.id.textView1);
		_button1 = (Button) findViewById (R.id.button1);
		_button1.setOnClickListener (this);
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
				//zitateVector = FreebaseSearchForZitat.sucheZitat(_editText1.getText().toString());
				zitateVector = FreebaseSearchForZitat.sucheZitat("art");
				
				//hier die Liste f�llen
				/*
				for (ZitatTopicWrapper zitat : zitateVector) {
					System.out.println( zitat.toString() + "\n" );
					
				}
				*/
				_editText2.setText("hallo",TextView.BufferType.EDITABLE);
				
				 try {
				        Thread.sleep(10000);
				    } catch (InterruptedException e) {
				        // TODO Auto-generated catch block
				        e.printStackTrace();
				    }

				System.out.println("hallo1:" + zitateVector.firstElement());
				System.out.println("hallo2:" + zitateVector.firstElement().toString());
				//System.out.println("hallo3:" + zitateVector.firstElement()._zitatText.toString());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error");
			}	
			
			Toast toast = 
					Toast.makeText(this, "#yolom��ig swaggt hier der Text rein", Toast.LENGTH_LONG);
			toast.show();
		}
		
	}

}
