package com.kf.beaconb1;

import java.util.Date;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class Main2Activity extends ActionBarActivity implements ProximityListener, VisitListener {
	public static TextView textView4;
	public static ImageView v;
	public static ImageView v2;
	public static Handler handler;
	public static int[] r = new int[10];
	public static int x=0;
	public static int y=0;
	public static boolean running=true;
	public static int dest;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for(int i = 0 ; i < 7; i++)
		{
			r[i]=-100;
		}
		setContentView(R.layout.activity_main_activity3);
		dest = getIntent().getIntExtra("area", 0);
		v2 = (ImageView) findViewById(R.id.imageView1);;
		v2.setX(1);
		v2.setY(1);
		ImageView v = (ImageView) findViewById(R.id.imageView2);
		switch(dest)
		{
		case 0:
			v.setX(200);//MAIN DOOR
			v.setY(5);
			break;
		case 1:
			v.setX(140); //KITCHEN
			v.setY(170);
			break;
		case 2:
			v.setX(540);//bedroom1
			v.setY(70);
			break;
		case 3:
			v.setX(170);//HALL
			v.setY(720);
			break;
		case 4:
			v.setX(560);//bedroom2
			v.setY(770);
			break;
		case 5:
			v.setX(580);//BALCONY
			v.setY(930);
			break;
		}
		Proximity.initialize(this,
                "0d0b3cf8bf05bd80b44433dd24dd4657e04bf47b0af0484baa0c466bd1b00b6e",
                "9331e1c04248e50aa6eff804d53fae5a31c1e5dca9f612dade5fc0a363e024a1");
        Proximity.startService(this);
        handler = new Handler();
        Runnable timer1 = new Runnable();
        Thread t1 = new Thread(timer1);
        t1.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main2, menu);
		
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
	   
    @SuppressLint("DefaultLocale") public void startServiceFailed(int errorCode, String message)
    {
        String logMsg = String.format("Proximity Service failed with error code %d, message: %s!", errorCode, message);
        Log.d("Proximity", logMsg);
        if (errorCode == ProximityError.PROXIMITY_BLUETOOTH_IS_OFF.getCode()) {
        }
    }
    public void receivedSighting(Visit visit, Date updateTime, Integer RSSI)
    {
    	if(visit.toString().contains("ZPVU"))
    	{
        	Main2Activity.r[6]=RSSI;      	
        }
    	if(visit.toString().contains("B6"))
    	{
        	Main2Activity.r[5]=RSSI;      	
        }
    	if(visit.toString().contains("B5"))
    	{
        	Main2Activity.r[4]=RSSI;      	
        }
    	if(visit.toString().contains("B4"))
    	{
        	Main2Activity.r[3]=RSSI;      	
        }
    	if(visit.toString().contains("B3"))
    	{
        	Main2Activity.r[2]=RSSI;      	
        }
    	if(visit.toString().contains("B2"))
        {
    		Main2Activity.r[1]=RSSI;
        }
        if(visit.toString().contains("B1"))
        {
        	Main2Activity.r[0]=RSSI;
        }
       
    }
    public void didArrive(Visit visit) {
        }
    @Override
    public void didDepart(Visit visit) {
        }
}
class Runnable extends Thread
{
	int lowesti=0;
	int lowestv=-100;
	@Override
	public void run()
	{
		while (Main2Activity.running)
		{
			Main2Activity.handler.post(new Runnable()
			{
				@Override
				public void run()
				{
					for(int i = 0;i<7;i++)
					{
						if(Main2Activity.r[i]>lowestv)
						{
							lowestv=Main2Activity.r[i];
							lowesti=i;
						}
					}
					switch(lowesti)
					{
					case 0:
						Main2Activity.v2.setX(310);//MAIN DOOR
						Main2Activity.v2.setY(35);
						break;
					case 1:
						Main2Activity.v2.setX(100); //KITCHEN
						Main2Activity.v2.setY(200);
						break;
					case 2:
						Main2Activity.v2.setX(500);//bedroom1
						Main2Activity.v2.setY(100);
						break;
					case 3:
						Main2Activity.v2.setX(80);//HALL1
						Main2Activity.v2.setY(650);
						break;
					case 4:
						Main2Activity.v2.setX(80);//HALL2
						Main2Activity.v2.setY(1000);
						break;
					case 5:
						Main2Activity.v2.setX(510);//bedroom2
						Main2Activity.v2.setY(800);
						break;
					case 6:
						Main2Activity.v2.setX(530);//BALCONY
						Main2Activity.v2.setY(960);
						break;
					}
				}
			});
		}
	}
}