package com.kf.beaconb1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity{
	public int dest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main0);
		addButton();
		Button Location = (Button) findViewById(R.id.button2);
    	Location.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, MainlastActivity.class));
			}
		});
    }
	public void addButton() {
   	 
    	final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
    	Button onSubmit = (Button) findViewById(R.id.button1);
    	onSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(String.valueOf(spinner1.getSelectedItem()).trim())
		    	{
		    		case "Main Door":
	    				dest=0;
	    				break;
	    			case "Kitchen":
		    			dest=1;
		    			break;
		    		case "Bedroom 1":
		    			dest=2;
		    			break;
		    		case "Hall":
		    			dest=3;
		    			break;
		    		case "Bedroom 2":
		    			dest=4;
		    			break;
		    		case "Balcony":
		    			dest=5;
		    			break;
		    		case "Select Area":
		    			dest=6;
		    			break;
		    	}
		    	if(dest!=6)
		    	{
		    		Intent intent = new Intent(MainActivity.this, Main2Activity.class);
					intent.putExtra("area", dest);
					startActivity(intent);
				}
			}
		});
      }
	    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}