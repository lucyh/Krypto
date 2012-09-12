package com.hci.krypto;

import java.util.Random;

public class KryptoPuzzle
{
	private Random rand = new Random();
	private int goal;
	private int[] numbers = new int[5];
	
	public KryptoPuzzle( int max )
	{
		goal = getRandom( max );
		for ( int i = 0; i < 5; i++ )
		{
			numbers[i] = getRandom( max );
		}
	}
	
	public int getRandom( int max )
	{
		return ( Math.abs( rand.nextInt() % max ) + 1 ); 
	}
	
	public int getGoal()
	{
		return goal;
	}
	
	public int getNum(int i)
	{
		return numbers[i];
	}
}