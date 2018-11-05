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
	
	public Dice(int sides)
	{
		this.sides = sides;
		roll = -1;
		rand = new Random();
	}
	
	public int roll()
	{
		roll = rand.nextInt(sides) + 1;
		return roll;
	}
	
	public int lastRoll()
	{
		return roll;
	}
}
