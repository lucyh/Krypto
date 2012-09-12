package com.hci.krypto;

import com.hci.krypto.R.color;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class KryptoActivity extends Activity 
{
	private KryptoPuzzle 				kp;
	private char       					op;
	private int[]						selectedNumbers = {0, 0};
	private int[]						displayNumbers = new int[5];
	private int			 				idSel0 = 0;
	private int			 				idSel1 = 0;
	private int							max;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.menu, menu);
        return true;
    }
    
    public void selectPuzzle( View view )
    {
    	setContentView(R.layout.activity_krypto);
    	if ( view.getId() == R.id.mainMenuButton10 )
    	{
    		max = 10;
    	}
    	else if ( view.getId() == R.id.mainMenuButton25 )
    	{
    		max = 25;
    	}
        newPuzzle( findViewById(R.id.new_puzzle) );
    }
    
    public void returnToMenu( View view )
    {
    	setContentView(R.layout.menu);
    }
    
    public void selectOperator( View view )
    {
    	op = ((TextView)view).getText().charAt(0);
    	((TextView)findViewById(R.id.selOp)).setText(String.valueOf(op));
    	resetOpButtonColors( view );
    	view.setBackgroundColor(getResources().getColor(R.color.selected_button_color));
    }
    
    public void selectNumber( View view )
    {
    	String strNum = ((TextView)view).getText().toString();
    	if ( selectedNumbers[0] == 0 )
    	{
    		selectedNumbers[0] = Integer.parseInt(strNum);
    		idSel0 = view.getId();
    		view.setVisibility( View.INVISIBLE );
    		setText(findViewById(R.id.selectedNum0), strNum);
    		setFontSelectedNum( (TextView)findViewById(R.id.selectedNum0), selectedNumbers[0]);
    	}
    	else if ( selectedNumbers[1] == 0 )
    	{
    		selectedNumbers[1] = Integer.parseInt(strNum);
    		idSel1 = view.getId();
    		view.setVisibility( View.INVISIBLE );
    		setText(findViewById(R.id.selectedNum1), strNum);
    		setFontSelectedNum( (TextView)findViewById(R.id.selectedNum1), selectedNumbers[1]);
    	}
    }
    
    public void clearText( View view )
    {
    	((TextView)view).setText( "" );
    	int id = view.getId();
    	if ( id == R.id.selOp )        
    	{ 
    		op = 'n'; 
    		resetOpButtonColors( view );

    	}
    	else if ( id == R.id.selectedNum0 ) 
    	{ 
    		selectedNumbers[0] = 0; 
    		if ( idSel0 > 0 )
    		{
	    		findViewById(idSel0).setVisibility( View.VISIBLE );
	    		idSel0 = 0;
    		}
    	}
    	else if ( id == R.id.selectedNum1 ) 
    	{ 
    		selectedNumbers[1] = 0;
    		if ( idSel1 > 0 )
    		{
	    		findViewById(idSel1).setVisibility( View.VISIBLE );
	    		idSel1 = 0;
    		}
    	}
    }

    public void loadPuzzle( View view )
    {
    	setText( findViewById(R.id.goal_num), String.valueOf(kp.getGoal()));
    	setText( findViewById(R.id.num0), String.valueOf(kp.getNum(0)));    	
    	setText( findViewById(R.id.num1), String.valueOf(kp.getNum(1)));    	
    	setText( findViewById(R.id.num2), String.valueOf(kp.getNum(2)));    	
    	setText( findViewById(R.id.num3), String.valueOf(kp.getNum(3)));    	
    	setText( findViewById(R.id.num4), String.valueOf(kp.getNum(4)));    	
    	clearText( findViewById(R.id.selectedNum0) );
    	clearText( findViewById(R.id.selectedNum1) );
    	clearText( findViewById(R.id.selOp) );
    	resetOpButtonColors( view );
    	setFontSizesDefault( view );
    	displayNumbers( view );
    }
    
    public void setText( View view, String text)
    {
    	((TextView)view).setText( text );
    }
    
    public void newPuzzle( View view )
    {
    	kp = new KryptoPuzzle( max );
    	for (int i = 0; i < 5; i++ )
    	{
    		displayNumbers[i] = kp.getNum(i);
    	}
    	loadPuzzle( view );
    }
    
    public void calculate( View view )
    {
    	int newNum = 0;
    	
    	char add = ((TextView)findViewById(R.id.add)).getText().charAt(0);
    	char sub = ((TextView)findViewById(R.id.subtract)).getText().charAt(0);
    	char mul = ((TextView)findViewById(R.id.multiply)).getText().charAt(0);
    	char div = ((TextView)findViewById(R.id.divide)).getText().charAt(0);
    	if ( selectedNumbers[0] > 0 && selectedNumbers[1] > 0 && op != 'n')
    	{
    		if ( op == add)
    		{
    			newNum = selectedNumbers[0] + selectedNumbers[1];
    		}
    		else if ( op == sub)
    		{
    			newNum = selectedNumbers[0] - selectedNumbers[1];
    			if ( newNum < 0 )
    			{
    				return;
    			}
    		}
    		else if ( op == mul)
    		{
    			newNum = selectedNumbers[0] * selectedNumbers[1];
    		}
    		else if ( op == div )
    		{
    			if ( selectedNumbers[0] % selectedNumbers[1] == 0 )
    			{
    				newNum = selectedNumbers[0] / selectedNumbers[1];
    			}
    			else
    			{
    				return;
    			}
    		}
    		
    		((TextView)findViewById( R.id.selectedNum0 )).setText("");
    		((TextView)findViewById( R.id.selectedNum1 )).setText("");
    		clearText( findViewById( idSel0 ) );
    		clearText( findViewById( idSel1 ) );
    		idSel0 = 0;
    		idSel1 = 0;
    		((TextView)findViewById( R.id.selOp )).setText("");
    		selectedNumbers[0] = 0;
    		selectedNumbers[1] = 0;
    		op = 'n';
    		addToDisplay( newNum );
    		displayNumbers( view );
    		resetOpButtonColors( view );
    	}
    }
    
    public void displayNumbers( View view )
    {
    	for (int i = 0; i < 5; i++ )
    	{
    		if ( !((TextView)findViewById( R.id.num0 )).getText().equals( "" ) )
    		{
    			findViewById( R.id.num0 ).setVisibility( View.VISIBLE );
    		}
    		if ( !((TextView)findViewById( R.id.num1 )).getText().equals( "" ) )
    		{
    			findViewById( R.id.num1 ).setVisibility( View.VISIBLE );
    		}
    		if ( !((TextView)findViewById( R.id.num2 )).getText().equals( "" ) )
    		{
    			findViewById( R.id.num2 ).setVisibility( View.VISIBLE );
    		}
    		if ( !((TextView)findViewById( R.id.num3 )).getText().equals( "" ) )
    		{
    			findViewById( R.id.num3 ).setVisibility( View.VISIBLE );
    		}
    		if ( !((TextView)findViewById( R.id.num4 )).getText().equals( "" ) )
    		{
    			findViewById( R.id.num4 ).setVisibility( View.VISIBLE );
    		}
    	}
    }
    
    public void addToDisplay( int num )
    {
    	String strNum = String.valueOf( num );
    	if ( ((TextView)findViewById( R.id.num0 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num0 )).setText( strNum );
    		setFontDisplayNum( (TextView)findViewById( R.id.num0 ), num );
    		displayNumbers[0] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num1 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num1 )).setText( strNum );
    		setFontDisplayNum( (TextView)findViewById( R.id.num1 ), num );
    		displayNumbers[1] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num2 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num2 )).setText( strNum );
    		setFontDisplayNum( (TextView)findViewById( R.id.num2 ), num );
    		displayNumbers[2] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num3 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num3 )).setText( strNum );
    		setFontDisplayNum( (TextView)findViewById( R.id.num3 ), num );
    		displayNumbers[3] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num4 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num4 )).setText( strNum );
    		setFontDisplayNum( (TextView)findViewById( R.id.num4 ), num );
    		displayNumbers[4] = num;
    	}
    }
    
    public void setFontSizesDefault( View view )
    {
    	int dp = TypedValue.COMPLEX_UNIT_DIP;
    	((TextView)findViewById(R.id.num0)).setTextSize(dp, 80);    	
    	((TextView)findViewById(R.id.num1)).setTextSize(dp, 80);
    	((TextView)findViewById(R.id.num2)).setTextSize(dp, 80);
    	((TextView)findViewById(R.id.num3)).setTextSize(dp, 80);
    	((TextView)findViewById(R.id.num4)).setTextSize(dp, 80);  	
    }
    
    public void setFontDisplayNum( TextView tv, int num )
    {
    	int dp = TypedValue.COMPLEX_UNIT_DIP;
    	if 		( num > 999999 ) 	{ tv.setTextSize(dp, 30); }
    	else if ( num > 99999 ) 	{ tv.setTextSize(dp, 35); }
    	else if ( num > 9999 )		{ tv.setTextSize(dp, 45); }
    	else if ( num > 999 ) 		{ tv.setTextSize(dp, 55); }
    	else if ( num > 99 ) 		{ tv.setTextSize(dp, 75); }
    	else 						{ tv.setTextSize(dp, 80); }
    }
    
    public void setFontSelectedNum( TextView tv, int num )
    {
    	int dp = TypedValue.COMPLEX_UNIT_DIP;
    	if 		( num > 999999 ) 	{ tv.setTextSize(dp, 40); }
    	else if ( num > 99999 ) 	{ tv.setTextSize(dp, 45); }
    	else if ( num > 9999 )		{ tv.setTextSize(dp, 55); }
    	else if ( num > 999 ) 		{ tv.setTextSize(dp, 70); }
    	else if ( num > 99 ) 		{ tv.setTextSize(dp, 95); }
    	else 						{ tv.setTextSize(dp, 125); }
    }
    
    public void resetOpButtonColors( View view )
    {
    	findViewById(R.id.add).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    	findViewById(R.id.subtract).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    	findViewById(R.id.multiply).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    	findViewById(R.id.divide).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    }
}
