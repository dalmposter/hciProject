package controllers;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import main.Player;
import javafx.scene.control.TextField;

/**
 * Controller for scene MainMenu.fxml
 * @author Dominic Cousins
 *
 */
public class MenuController extends Controller
{
	
	@FXML
    private ComboBox<String> combo_p2;

    @FXML
    private ComboBox<String> combo_p1;

    @FXML
    private Button btn_quit;

    @FXML
    private Button btn_newPlayer;

    @FXML
    private Button btn_play;
    
    @FXML
    private TextField txt_pointGoal;
    
    @FXML
    private CheckBox chk_tutorial;
    
    @FXML
    Button btn_helpPlay;
    
    /**
     * Method to validate ui data and move to MainGame.fxml while passing the controller its parameters
     * @param tutorial
     */
    private void play(boolean tutorial)
    {
    	int goal = 0;
    	
    	//check for invalid characters in goal field
    	try
    	{
    		goal = Integer.valueOf(txt_pointGoal.getText());
    	}
    	catch (Exception e)
    	{
    		System.out.println("Exception on point goal");
    		e.printStackTrace();
    	}
    	
    	//check goal is greater than 0 (also checks the goal isn't some non-numerical values such as whitespace)
    	if(goal <= 0)
    	{
    		JOptionPane.showMessageDialog(null, "You must choose a whole number greater than 0 for the point goal", "error", JOptionPane.ERROR_MESSAGE);
    	}
    	//check if either player field is empty and that they are both different
    	else if(combo_p1.getValue().equals(combo_p2.getValue()) || "".equals(combo_p1.getValue()) || "".equals(combo_p2.getValue()))
    	{
    		JOptionPane.showMessageDialog(null, "You must select 2 different players to battle it out in Dice Mania!", "error", JOptionPane.ERROR_MESSAGE);
    	}
    	//data validation passes
    	else
    	{
    		Player p1 = null;
    		Player p2 = null;
    		
    		//if either player is the default p1 or p2, create a basic player for that
    		for(int i = 0; i < MainApp.DEFAULT_PLAYERS.length; i++)
    		{
    			if(combo_p1.getValue() == MainApp.DEFAULT_PLAYERS[i]) p1 = new Player(MainApp.DEFAULT_PLAYERS[i], null, i + 1);
    			if(combo_p2.getValue() == MainApp.DEFAULT_PLAYERS[i]) p2 = new Player(MainApp.DEFAULT_PLAYERS[i], null, i + 1);
    		}
    		
    		if(p1 == null)
    		{	
    			//check for player in guest list
    			for(Player p : MainApp.tempPlayers)
    			{
    				if(p.getName().equals(combo_p1.getValue())) 
    				{
    					p1 = p;
    					break;
    				}
    			}
    			//fallback to checking for player on disk
    			if(p1 == null)
    			{
    				p1 = new Player(combo_p1.getValue());
    				p1.readFromDisk();
    			}
    		}
    		if(p2 == null)
    		{
    			//check for player in guest list
    			for(Player p : MainApp.tempPlayers)
    			{
    				if(p.getName().equals(combo_p2.getValue())) 
    				{
    					p2 = p;
    					break;
    				}
    			}
    			//fallback to checking for player on disk
    			if(p2 == null)
    			{
    				p2 = new Player(combo_p2.getValue());
    				p2.readFromDisk();
    			}
    		}
    		
    		//move to MainGame.fxml, passing arguments
    		gotoGame(p1, p2, goal, tutorial);
    	}
    }
    
    @FXML
    void playClicked(ActionEvent event)
    {	
    	play(chk_tutorial.isSelected());
    }

    @FXML
    void newPlayerClicked(ActionEvent event)
    {
    	//go to register player screen
    	gotoRegister();
    }

    @FXML
    void quitClicked(ActionEvent event)
    {
    	System.exit(0);
    }

    /**
     * initialisation method. Sets player combo box values.
     */
    public void initialise()
    {
    	//empty the boxes
    	combo_p1.getItems().clear();
    	combo_p2.getItems().clear();
    	
    	//add the default p1 and p2
    	combo_p1.getItems().addAll(MainApp.DEFAULT_PLAYERS);
    	combo_p2.getItems().addAll(MainApp.DEFAULT_PLAYERS);
    	
    	//add any guest players
    	MainApp.tempPlayers.forEach( p -> {
    		combo_p1.getItems().add(p.getName());
    		combo_p2.getItems().add(p.getName());
    	});
    	
    	//if we can read players from the disk then we should and add them to each list also
    	if(MainApp.canSavePlayers)
    	{
    		File dataFolder = new File(MainApp.dataLocation);
    		File[] possiblePlayers = dataFolder.listFiles();
    	
    		int len = possiblePlayers.length;
    		for(int i = 0; i < len; i++)
    		{
    			String fileName = possiblePlayers[i].getName();
    			if(fileName.substring(fileName.length() - 4).equals(".dat"))
    			{
    				combo_p1.getItems().add(fileName.substring(0, fileName.length() - 4));
    				combo_p2.getItems().add(fileName.substring(0, fileName.length() - 4));
    			}
    		}
    	}
    	
    	//set initial value to the default players
    	combo_p1.setValue(MainApp.DEFAULT_PLAYERS[0]);
    	combo_p2.setValue(MainApp.DEFAULT_PLAYERS[1]);
    	
    	txt_pointGoal.setText("25");
    }
    
    @FXML
    void helpPlayClicked(ActionEvent event)
    {
    	//display help message describing how to start a game
    	JOptionPane.showMessageDialog(null, "Choose 2 players from the drop down lists to do battle!\nSelect an amount of points required to win (recommended 25) and go!\nIf you're new here go ahead and tick that tutorial box", "help", JOptionPane.INFORMATION_MESSAGE);
    }
}
