package com.hci.krypto;

import java.util.Random;

public class KryptoPuzzle
{
	private Random rand = new Random();
	public int goal;
	public int[] nums = new int[5];
	
	public KryptoPuzzle( int num )
	{
		goal = getRandom( num );
		for ( int i = 0; i < 5; i++ )
		{
			nums[i] = getRandom( num );
		}
	}
	
	public int getRandom( int num )
	{
		return Math.abs( ( rand.nextInt() % num ) + 1 ); 
	}
}