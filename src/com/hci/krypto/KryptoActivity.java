package com.hci.krypto;

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
	private char       					op = 'n';
	private int[]						selectedNumbers = {-1, -1};
	private int[]						displayedNumbers = new int[5];
	private int			 				idSel0 = 0;
	private int			 				idSel1 = 0;
	private int							max;
	private int							iter = 0;
	private int[][]						state = new int[5][5];
		
    public void addToDisplay( int num )
    {
    	String strNum = String.valueOf( num );
    	if ( ((TextView)findViewById( R.id.num0 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num0 )).setText( strNum );
    		displayedNumbers[0] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num1 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num1 )).setText( strNum );
    		displayedNumbers[1] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num2 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num2 )).setText( strNum );
    		displayedNumbers[2] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num3 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num3 )).setText( strNum );
    		displayedNumbers[3] = num;
    	}
    	else if ( ((TextView)findViewById( R.id.num4 )).getText().equals( "" ) )
    	{
    		((TextView)findViewById( R.id.num4 )).setText( strNum );
    		displayedNumbers[4] = num;
    	}
    	
    	adjustFontSizes();
    }

    public void adjustFontSizes()
    {
    	setFontDisplayNum( (TextView)findViewById(R.id.num0), displayedNumbers[0] );
    	setFontDisplayNum( (TextView)findViewById(R.id.num1), displayedNumbers[1] );
    	setFontDisplayNum( (TextView)findViewById(R.id.num2), displayedNumbers[2] );
    	setFontDisplayNum( (TextView)findViewById(R.id.num3), displayedNumbers[3] );
    	setFontDisplayNum( (TextView)findViewById(R.id.num4), displayedNumbers[4] );
    }
    
    public void calculate( View view )
    {
    	int newNum = 0;
    	
    	char add = ((TextView)findViewById(R.id.add)).getText().charAt(0);
    	char sub = ((TextView)findViewById(R.id.subtract)).getText().charAt(0);
    	char mul = ((TextView)findViewById(R.id.multiply)).getText().charAt(0);
    	char div = ((TextView)findViewById(R.id.divide)).getText().charAt(0);
    	if ( selectedNumbers[0] != -1 && selectedNumbers[1] != -1 && op != 'n')
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
    			if ( selectedNumbers[1] != 0 && selectedNumbers[0] % selectedNumbers[1] == 0 )
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
    		selectedNumbers[0] = -1;
    		selectedNumbers[1] = -1;
    		op = 'n';
    		addToDisplay( newNum );
    		displayNumbers();
    		iter++;
    		setState( iter );
    		resetOpButtonColors();
    	}
    }
    
    public void clearSelected()
    {
    	clearText(findViewById(R.id.selectedNum0));
    	clearText(findViewById(R.id.selectedNum1));
    	clearText(findViewById(R.id.selOp));
    }
    
    public void clearState()
    {
    	iter = 0;
    	for (int i = 0; i < 5; i++ )
    	{
    		for (int j = 0; j < 5; j++)
    		{
    			state[i][j] = 0;
    		}
    	}
    }
    
    public void clearText( View view )
    {
    	((TextView)view).setText( "" );
    	int id = view.getId();
    	if ( id == R.id.selOp )        
    	{ 
    		op = 'n'; 
    		resetOpButtonColors();

    	}
    	else if ( id == R.id.selectedNum0 ) 
    	{ 
    		selectedNumbers[0] = -1; 
    		if ( idSel0 > 0 )
    		{
	    		findViewById(idSel0).setVisibility( View.VISIBLE );
	    		idSel0 = 0;
    		}
    	}
    	else if ( id == R.id.selectedNum1 ) 
    	{ 
    		selectedNumbers[1] = -1;
    		if ( idSel1 > 0 )
    		{
	    		findViewById(idSel1).setVisibility( View.VISIBLE );
	    		idSel1 = 0;
    		}
    	}
    }
    
    public void displayNumbers()
    {
		hideOrDisplayNumber( findViewById( R.id.num0 ) );
		hideOrDisplayNumber( findViewById( R.id.num1 ) );
		hideOrDisplayNumber( findViewById( R.id.num2 ) );
		hideOrDisplayNumber( findViewById( R.id.num3 ) );
		hideOrDisplayNumber( findViewById( R.id.num4 ) );
    }
    
    public void hideOrDisplayNumber( View view )
    {
    	if ( ((TextView)view).getText().equals("") )
    	{
    		view.setVisibility( View.INVISIBLE );
    	}
    	else
    	{
    		view.setVisibility( View.VISIBLE );
    	}
    }
    
    public void loadPuzzle( View view )
    {
    	for (int i = 0; i < 5; i++ )
    	{
    		displayedNumbers[i] = kp.getNum(i);
    	}    	
    	setText( findViewById(R.id.goal_num), String.valueOf(kp.getGoal()));
    	setText( findViewById(R.id.num0), String.valueOf(kp.getNum(0)));    	
    	setText( findViewById(R.id.num1), String.valueOf(kp.getNum(1)));    	
    	setText( findViewById(R.id.num2), String.valueOf(kp.getNum(2)));    	
    	setText( findViewById(R.id.num3), String.valueOf(kp.getNum(3)));    	
    	setText( findViewById(R.id.num4), String.valueOf(kp.getNum(4)));    	
    	clearText( findViewById(R.id.selectedNum0) );
    	clearText( findViewById(R.id.selectedNum1) );
    	clearText( findViewById(R.id.selOp) );
    	resetOpButtonColors();
    	setFontSizesDefault();
    	displayNumbers( );
    	clearState();
    	setState( iter );
    }
    
    public void loadState( int i )
    {
    	for (int j = 0; j < 5; j++ )
    	{
    		displayedNumbers[j] = state[i][j];
    	}
    	setText(findViewById(R.id.num0), state[i][0] < 0 ? "" : String.valueOf(state[i][0]));
    	setText(findViewById(R.id.num1), state[i][1] < 0 ? "" : String.valueOf(state[i][1]));
    	setText(findViewById(R.id.num2), state[i][2] < 0 ? "" : String.valueOf(state[i][2]));
    	setText(findViewById(R.id.num3), state[i][3] < 0 ? "" : String.valueOf(state[i][3]));
    	setText(findViewById(R.id.num4), state[i][4] < 0 ? "" : String.valueOf(state[i][4]));
    	adjustFontSizes();
    	
    	displayNumbers();
    }

    public void newPuzzle( View view )
    {
    	kp = new KryptoPuzzle( max );
    	loadPuzzle( view );
    }
    
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
    
    public void resetOpButtonColors()
    {
    	findViewById(R.id.add).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    	findViewById(R.id.subtract).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    	findViewById(R.id.multiply).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    	findViewById(R.id.divide).setBackgroundColor(getResources().getColor(R.color.normal_button_color));
    }
    
    public void returnToMenu( View view )
    {
    	setContentView(R.layout.menu);
    }
    
    public void selectNumber( View view )
    {
    	String strNum = ((TextView)view).getText().toString();
    	if ( selectedNumbers[0] == -1 )
    	{
    		selectedNumbers[0] = Integer.parseInt(strNum);
    		idSel0 = view.getId();
    		view.setVisibility( View.INVISIBLE );
    		setText(findViewById(R.id.selectedNum0), strNum);
    		setFontSelectedNum( (TextView)findViewById(R.id.selectedNum0), selectedNumbers[0]);
    	}
    	else if ( selectedNumbers[1] == -1 )
    	{
    		selectedNumbers[1] = Integer.parseInt(strNum);
    		idSel1 = view.getId();
    		view.setVisibility( View.INVISIBLE );
    		setText(findViewById(R.id.selectedNum1), strNum);
    		setFontSelectedNum( (TextView)findViewById(R.id.selectedNum1), selectedNumbers[1]);
    	}
    }
    
    public void selectOperator( View view )
    {
    	op = ((TextView)view).getText().charAt(0);
    	((TextView)findViewById(R.id.selOp)).setText(String.valueOf(op));
    	resetOpButtonColors();
    	view.setBackgroundColor(getResources().getColor(R.color.selected_button_color));
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
    
    public void setFontSizesDefault()
    {
    	int dp = TypedValue.COMPLEX_UNIT_DIP;
    	((TextView)findViewById(R.id.num0)).setTextSize(dp, 80);    	
    	((TextView)findViewById(R.id.num1)).setTextSize(dp, 80);
    	((TextView)findViewById(R.id.num2)).setTextSize(dp, 80);
    	((TextView)findViewById(R.id.num3)).setTextSize(dp, 80);
    	((TextView)findViewById(R.id.num4)).setTextSize(dp, 80);  	
    }
    
    public void setState( int i )
    {    	
    	state[i][0] = findViewById(R.id.num0).isShown() ? displayedNumbers[0] : -1;
    	state[i][1] = findViewById(R.id.num1).isShown() ? displayedNumbers[1] : -1;
    	state[i][2] = findViewById(R.id.num2).isShown() ? displayedNumbers[2] : -1;
    	state[i][3] = findViewById(R.id.num3).isShown() ? displayedNumbers[3] : -1;
    	state[i][4] = findViewById(R.id.num4).isShown() ? displayedNumbers[4] : -1;
    }
    
    public void setText( View view, String text)
    {
    	((TextView)view).setText( text );
    }
    
    public void showInstructions( View view )
    {
    	max = view.getId() == R.id.mainMenuButton10 ? 10 : 25;
    	setContentView(R.layout.instructions);
    }
        
    public void showPuzzle( View view )
    {
    	setContentView(R.layout.activity_krypto);
        newPuzzle( findViewById(R.id.new_puzzle) );
    }
    
    public void undo( View view )
    {
    	if ( selectedNumbers[0] != -1 || selectedNumbers[1] != -1 || op != 'n')
    	{
    		clearSelected();
    	}
    	else
    	{
    		if (iter > 0)
    		{
    			iter--;
    			loadState( iter );
    			displayNumbers();
    		}
    	}
    }
}
