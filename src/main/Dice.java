package main;

import java.util.Random;

public class Dice
{
	private int sides, roll;
	private Random rand;
	
	public Dice()
	{
		this(6);
	}
	
	/**
	 * Constructor. Creates Random to be used for the lifetime of this dice for more "random" results
	 * @param sides
	 */
	public Dice(int sides)
	{
		this.sides = sides;
		roll = -1;
		rand = new Random();
	}
	
	//roll this dice
	public int roll()
	{
		roll = rand.nextInt(sides) + 1;
		return roll;
	}
	
	//get the last result this dice created
	public int lastRoll()
	{
		return roll;
	}
}
