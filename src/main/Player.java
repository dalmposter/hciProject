package main;

import java.util.ArrayList;

public class Player
{
	private String name;
	private int score, wins;
	private Dice[] dice;
	
	public Player(String name)
	{
		this.name = name;
		score = 0;
		dice = new Dice[3];
		
		for(int i = 0; i < 3; i++)
		{
			dice[i] = new Dice();
		}
	}
	
	public void addWin()
	{
		wins++;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getWins()
	{
		return wins;
	}
	
	public void addScore(int gained)
	{
		score += gained;
	}
	
	public void resetScore()
	{
		score = 0;
	}
	
	public int takeTurn()
	{
		ArrayList<Integer> res = new ArrayList<>();
		System.out.println(name + " rolled: ");
		for(Dice d : dice)
		{
			res.add(d.roll());
			System.out.print(d.lastRoll() + ", ");
		}
		
		if(res.get(0) == res.get(1))
		{
			if(res.get(0) == res.get(2))
			{
				//3 of a kind
				return 18;
			}
			else
			{
				//pair on 0 and 1
				return res.get(0) * 2;
			}
		}
		else if(res.get(0) == res.get(2))
		{
			//pair on 0 and 2
			return res.get(0) * 2;
		}
		else if(res.get(1) == res.get(2))
		{
			//pair on 1 and 2
			return res.get(1) * 2;
		}
		else
		{
			//no sets
			return 1;
		}
	}
}
