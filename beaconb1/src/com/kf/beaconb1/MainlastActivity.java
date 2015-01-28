package com.kf.beaconb1;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gimbal.proximity.Proximity;
import com.gimbal.proximity.ProximityError;
import com.gimbal.proximity.ProximityFactory;
import com.gimbal.proximity.ProximityListener;
import com.gimbal.proximity.ProximityOptions;
import com.gimbal.proximity.Visit;
import com.gimbal.proximity.VisitListener;
import com.gimbal.proximity.VisitManager;

public class MainlastActivity extends Activity  implements ProximityListener, VisitListener {
	public static Handler handler;
	public static int r1, r2, r3;
	public static int x, y;
	public static TextView textxy;
	public static boolean running= true;
	public static ImageView v12;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainlast);
		v12 = (ImageView) findViewById(R.id.imageV1);;
		textxy = (TextView) findViewById(R.id.textxy);
		Proximity.initialize(this,
                "0d0b3cf8bf05bd80b44433dd24dd4657e04bf47b0af0484baa0c466bd1b00b6e",
                "9331e1c04248e50aa6eff804d53fae5a31c1e5dca9f612dade5fc0a363e024a1");
        Proximity.startService(this);
        handler = new Handler();
        Runnable2 timer1 = new Runnable2();
        Thread t1 = new Thread(timer1);
        t1.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainlast, menu);
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
	public void serviceStarted()
    {
        Log.d("Proximity","Proximity Service successfully started!");
        ProximityOptions options = new ProximityOptions();
        options.setOption(ProximityOptions.VisitOptionSignalStrengthWindowKey,1);
        options.setOption(ProximityOptions.VisitOptionForegroundDepartureIntervalInSecondsKey,
                          1);
        options.setOption(ProximityOptions.VisitOptionBackgroundDepartureIntervalInSecondsKey,
                          1);
        options.setOption(ProximityOptions.VisitOptionArrivalRSSIKey,
                          -120);
        options.setOption(ProximityOptions.VisitOptionDepartureRSSIKey,
                          -121);

        VisitManager visitManager  = ProximityFactory.getInstance().createVisitManager();
        visitManager.setVisitListener(this);
        visitManager.start();
        visitManager.startWithOptions(options);
    }
	   
    public void startServiceFailed(int errorCode, String message)
    {
        String logMsg = String.format("Proximity Service failed with error code %d, message: %s!", errorCode, message);
        Log.d("Proximity", logMsg);
        if (errorCode == ProximityError.PROXIMITY_BLUETOOTH_IS_OFF.getCode()) {
        }
    }
    public void receivedSighting(Visit visit, Date updateTime, Integer RSSI)
    {
    	if(visit.toString().contains("B3"))
    	{
    		if(-65<=RSSI)
        	{
        		r3=0;
        	}
        	else if(-75<=RSSI&&RSSI<-65)
        	{
        		r3=1;
        	}
        	else if(-92<=RSSI&&RSSI<-75)
        	{
        		r3=2;
        	}
        	else if(-98<=RSSI&&RSSI<-92)
        	{
        		r3=3;
        	}
        	else
        	{
        		r3=4;
        	}
    	}
    	if(visit.toString().contains("B2"))
        {
    		if(-65<=RSSI)
        	{
        		r2=0;
        	}
        	else if(-75<=RSSI&&RSSI<-65)
        	{
        		r2=1;
        	}
        	else if(-92<=RSSI&&RSSI<-75)
        	{
        		r2=2;
        	}
        	else if(-98<=RSSI&&RSSI<-92)
        	{
        		r2=3;
        	}
        	else
        	{
        		r2=4;
        	}
    	}
        if(visit.toString().contains("B1"))
        {
        	if(-65<=RSSI)
        	{
        		r1=0;
        	}
        	else if(-75<=RSSI&&RSSI<-65)
        	{
        		r1=1;
        	}
        	else if(-92<=RSSI&&RSSI<-75)
        	{
        		r1=2;
        	}
        	else if(-98<=RSSI&&RSSI<-92)
        	{
        		r1=3;
        	}
        	else
        	{
        		r1=4;
        	}
        }
       
    }
    public void didArrive(Visit visit) {
        }
    @Override
    public void didDepart(Visit visit) {
        }
}
class Runnable2 extends Thread
{
	@Override
	public void run()
	{
		while (MainlastActivity.running)
		{
			MainlastActivity.handler.post(new Runnable()
			{
				@Override
				public void run()
				{
					MainlastActivity.x = ((MainlastActivity.r1*MainlastActivity.r1)-(MainlastActivity.r2*MainlastActivity.r2)+9)/(6);
					MainlastActivity.y = ((MainlastActivity.r1*MainlastActivity.r1)-(MainlastActivity.r3*MainlastActivity.r3)+9)/(6);
					MainlastActivity.textxy.setText(String.valueOf(MainlastActivity.x)+","+String.valueOf(MainlastActivity.y));
					if(MainlastActivity.x == 0)
					{
						MainlastActivity.v12.setX(630);
					}
					else if (MainlastActivity.x == 1)
					{
						MainlastActivity.v12.setX(330);
					}
					else if (MainlastActivity.x >= 2)
					{
						MainlastActivity.v12.setX(30);
					}
					if(MainlastActivity.y == 0)
					{
						MainlastActivity.v12.setY(150);
					}
					else if (MainlastActivity.y == 1)
					{
						MainlastActivity.v12.setY(450);
					}
					else if (MainlastActivity.y >= 2)
					{
						MainlastActivity.v12.setY(750);
					}
				}
			});
		}
	}
}
