package handlers;

import javax.swing.JOptionPane;

import gui.MainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class newGameHandler implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent e)
	{
		try
		{
			int newGoal = MainWindow.getNewGoal();
			MainWindow.restart(newGoal);
		}
		catch(NumberFormatException exc)
		{
			MainWindow.resetNewGoal();
			JOptionPane.showMessageDialog(null, "new goal must be a whole number greater than 0", "input error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
