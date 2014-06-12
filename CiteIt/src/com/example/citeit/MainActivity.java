package com.example.citeit;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	
	protected Button _add = null;
	protected Button _delete = null;
	protected ListView _listView1 = null;
	protected TextView _header = null;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        _add = (Button) findViewById (R.id.add);
        _delete = (Button) findViewById (R.id.delete);
		_add.setOnClickListener (this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
		if (view == _add) {
			Intent intent = new Intent (this, SearchActivity.class);
			
			//String textToTransfer = _editText1.getText().toString();
			//intent.putExtra("data_von_1_zu_2", textToTransfer);
			
			startActivity(intent);
		}
		
	}
}
