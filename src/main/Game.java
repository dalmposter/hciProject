package main;

public class Game
{
	private int goal, round;
	public Player p1, p2;
	private boolean p1Leading, tie, p1Turn;
	
	/*public static void main(String args[])
	{
		Game mainGame = new Game(new Player("Player 1"), new Player("Player 2"), 25);
		while(true)
		{
			if(mainGame.playTurn()) break;
		}
	}*/
	
	public Game(Player p1, Player p2, int targetScore)
	{
		this.p1 = p1;
		this.p2 = p2;
		goal = targetScore;
		p1Leading = false;
		p1Turn = true;
		round = 1;
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
	
	public int getRound()
	{
		return round;
	}
	
	public String getLeader()
	{
		if(p1Leading) return p1.getName();
		else return p2.getName();
	}
	
	private void doWin(int winner)
	{
		System.out.println("player " + winner + " won! (3 means a draw)");
		System.out.println(p1.getName() + " scored " + p1.getScore());
		System.out.println(p2.getName() + " scored " + p2.getScore());
		System.out.println("The game lasted " + getRound() + " rounds and the goal was " + getGoal() + " points");
	}
	
	private void updateLeading()
	{
		if(p1.getScore() > p2.getScore()) p1Leading = true;
		else if(p2.getScore() > p1.getScore()) p1Leading = false;
		else tie = true;
	}
	
	public boolean playTurn()
	{
		//get current player
		Player currentPlayer = p2;
		if(p1Turn) currentPlayer = p1;
		
		//play turn
		int scored = currentPlayer.takeTurn();
		System.out.println(currentPlayer.getName() + " got " + scored + " points!");
		currentPlayer.addScore(scored);
		updateLeading();
		
		//if it was p2's turn it's been a whole round so check for a win
		if(!p1Turn)
		{
			if(checkWin() > 0) return true;
			round++;
		}
		
		//change player
		p1Turn = !p1Turn;
		return false;
	}
	
	//returns 0 if no one has won yet, 1 or 2 if p1 or p2 has won, or 3 if there is a tie
	public int checkWin()
	{
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
}
