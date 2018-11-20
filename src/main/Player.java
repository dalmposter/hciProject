package main;

import java.util.ArrayList;

public class Player
{
	private static int nextId;
	private String name;
	private int score, wins, id;
	
	public Player(String name)
	{
		this.name = name;
		score = 0;
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
	
	/**
	 * method to add to the players score
	 * @param gained This is the amount to increase the score by
	 */
	public void addScore(int gained)
	{
		score += gained;
	}
	
	public void resetScore()
	{
		Player player = new Player("name");
		
		score = 0;
	}
}
