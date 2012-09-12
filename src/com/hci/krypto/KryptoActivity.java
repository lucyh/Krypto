package com.hci.krypto;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class KryptoActivity extends Activity 
{

	private KryptoPuzzle 				kp;
	private String       				op;
	private int          				num1   = -1;
	private int          				num2   = -1;
	private int			 				idSel1 = -1;
	private int			 				idSel2 = -1;
//	private KryptoSensor				ks;
//	private SensorManager   			sm;
//	private Sensor 						sen;
//	private SensorEventListener 		listener;

		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krypto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//    	  sm.registerListener(listener, sen, SensorManager.SENSOR_DELAY_NORMAL);
//    	  sen = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//    	  sm.registerListener((SensorEventListener) this, sen, SensorManager.SENSOR_DELAY_NORMAL);
//        ks = new KryptoSensor( sm, sen );
        
        newKryptoPuzzleOnCreate( savedInstanceState );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_krypto, menu);
        return true;
    }
    
    public void selectOperator( View view )
    {
    	op = ((TextView)view).getText().toString();
    	((TextView)findViewById(R.id.selOp)).setText(op);
    }
    
    public void selectNumber( View view )
    {
    	String num = ((TextView)view).getText().toString();
    	if ( num1 == -1 )
    	{
    		num1 = Integer.parseInt(num);
    		idSel1 = view.getId();
    		view.setVisibility( View.INVISIBLE );
    		setText(findViewById(R.id.selNum1), num);
    	}
    	else if ( num2 == -1 )
    	{
    		num2 = Integer.parseInt(num);
    		idSel2 = view.getId();
    		view.setVisibility( View.INVISIBLE );
    		setText(findViewById(R.id.selNum2), num);
    	}
    }
    
    public void clearText( View view )
    {
    	((TextView)view).setText( "" );
    	int id = view.getId();
    	if ( id == R.id.selOp )        
    	{ 
    		op = ""; 
    	}
    	else if ( id == R.id.selNum1 ) 
    	{ 
    		num1 = -1; 
    		if ( idSel1 != -1 )
    		{
	    		findViewById(idSel1).setVisibility( View.VISIBLE );
	    		idSel1 = -1;
    		}
    	}
    	else if ( id == R.id.selNum2 ) 
    	{ 
    		num2 = -1;
    		if ( idSel2 != -1 )
    		{
	    		findViewById(idSel2).setVisibility( View.VISIBLE );
	    		idSel2 = -1;
    		}
    	}
    }

    public void newKryptoPuzzleOnCreate(Bundle savedInstanceState)
    {
    	kp = new KryptoPuzzle( 25 );
    	setText( findViewById(R.id.goal_num), String.valueOf(kp.goal));
    	setText( findViewById(R.id.num0), String.valueOf(kp.nums[0]));    	
    	setText( findViewById(R.id.num1), String.valueOf(kp.nums[1]));    	
    	setText( findViewById(R.id.num2), String.valueOf(kp.nums[2]));    	
    	setText( findViewById(R.id.num3), String.valueOf(kp.nums[3]));    	
    	setText( findViewById(R.id.num4), String.valueOf(kp.nums[4]));    	
    	clearText( findViewById(R.id.selNum1) );
    	clearText(findViewById(R.id.selNum2) );
    	clearText(findViewById(R.id.selOp) );
    }
    
    public void newKryptoPuzzle( View view )
    {
    	kp = new KryptoPuzzle( 25 );
    	setText( findViewById(R.id.goal_num), String.valueOf(kp.goal));
    	setText( findViewById(R.id.num0), String.valueOf(kp.nums[0]));    	
    	setText( findViewById(R.id.num1), String.valueOf(kp.nums[1]));    	
    	setText( findViewById(R.id.num2), String.valueOf(kp.nums[2]));    	
    	setText( findViewById(R.id.num3), String.valueOf(kp.nums[3]));    	
    	setText( findViewById(R.id.num4), String.valueOf(kp.nums[4]));    	
    	clearText( findViewById(R.id.selNum1) );
    	clearText(findViewById(R.id.selNum2) );
    	clearText(findViewById(R.id.selOp) );
    }
    
    public void setText( View view, String text)
    {
    	((TextView)view).setText( text );
    }    
}
