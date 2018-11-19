package handlers;

import gui.MainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class RollHandler implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent e)
	{
		MainWindow.game.playTurn();
		MainWindow.updatePlayer();
		MainWindow.updateScores();
	}

}
