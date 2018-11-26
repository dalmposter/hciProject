package main;

import java.util.ArrayList;

public class Game
{
	private int goal, turn;
	public Player p1, p2;
	private boolean p1Leading, tie, p1Turn;
	private Dice[] dice;
	
	//Create a new game with same players and goal as the last one
	public Game(Game lastGame)
	{
		this(lastGame.getP1(), lastGame.getP2(), lastGame.getGoal());
	}
	
	//Standard constructor
	public Game(Player p1, Player p2, int targetScore)
	{
		this.p1 = p1;
		this.p2 = p2;
		p1.reset();
		p2.reset();
		goal = targetScore;
		p1Leading = true;
		p1Turn = true;
		tie = true;
		turn = 1;
		dice = new Dice[3];
		
		for(int i = 0; i < 3; i++)
		{
			dice[i] = new Dice(6);
		}
	}
	
	public Player getCurrentPlayer()
	{
		if(p1Turn) return p1;
		return p2;
	}
	
	public Player getP1()
	{
		return p1;
	}
	
	public Player getP2()
	{
		return p2;
	}
	
	public int getGoal()
	{
		return goal;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	public boolean isTie()
	{
		return tie;
	}
	
	public boolean isP1Leading()
	{
		return p1Leading;
	}
	
	private void doWin(int winner)
	{
		System.out.println("player " + winner + " won! (3 means a draw)");
		System.out.println(p1.getName() + " scored " + p1.getScore());
		System.out.println(p2.getName() + " scored " + p2.getScore());
		System.out.println("The game lasted " + getTurn() + " turns and the goal was " + getGoal() + " points");
	}
	
	//Update booleans that track who's winning
	private void updateLeading()
	{
		if(p1.getScore() > p2.getScore()) p1Leading = true;
		else if(p2.getScore() > p1.getScore()) p1Leading = false;
		else tie = true;
	}
	
	public ArrayList<Integer> playTurn()
	{
		//get current player
		Player currentPlayer = p2;
		if(p1Turn) currentPlayer = p1;
		
		//play turn
		ArrayList<Integer> res = new ArrayList<>();
		System.out.println("rolled: ");
		for(Dice d : dice)
		{
			res.add(d.roll());
			System.out.print(d.lastRoll() + ", ");
		}
		
		int scored;
		
		if(res.get(0) == res.get(1))
		{
			if(res.get(0) == res.get(2))
			{
				//3 of a kind
				scored = 18;
			}
			else
			{
				//pair on 0 and 1
				scored = res.get(0) * 2;
			}
		}
		else if(res.get(0) == res.get(2))
		{
			//pair on 0 and 2
			scored = res.get(0) * 2;
		}
		else if(res.get(1) == res.get(2))
		{
			//pair on 1 and 2
			scored = res.get(1) * 2;
		}
		else
		{
			//no sets
			scored = 1;
		}
		
		System.out.println(currentPlayer.getName() + " got " + scored + " points!");
		currentPlayer.addScore(scored);
		updateLeading();
		
		//change player
		p1Turn = !p1Turn;
		turn++;
		
		if(p1.getScore() == p2.getScore()) tie = true;
		else tie = false;

		//return nice list of rolls to set label
		return res;
	}
	
	/**
	 * Check if we have a winner
	 * @return the player who won, 0 if the game is ongoing or 3 if there was a tie
	 */
	public int checkWin()
	{
		//if we're about to play an even numbered turn there can't be a winner as its mid round
		if(turn % 2 == 0)
		{
			System.out.println("Rejected win check as it is turn: " + turn + " (mid round)");
			return 0;
		}
		if(p1.getScore() >= goal)
		{
			if(p2.getScore() >= goal)
			{
				p1.addWin();
				p2.addWin();
				doWin(3);
				return 3;
			}
			else
			{
				p1.addWin();
				doWin(1);
				return 1;
			}
		}
		else if(p2.getScore() >= goal)
		{
			p2.addWin();
			doWin(2);
			return 2;
		}
		else return 0;
	}
	
	/**
	 * Play next turn
	 * @return points gained by current player
	 */
	public int takeTurn()
	{
		ArrayList<Integer> res = new ArrayList<>();
		System.out.println("rolled: ");
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
