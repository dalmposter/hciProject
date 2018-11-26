package controllers;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import main.Player;
import javafx.scene.control.TextField;

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
    private Button btn_tutorial;
    
    private void play(boolean tutorial)
    {
    	int goal = 0;
    	
    	try
    	{
    		goal = Integer.valueOf(txt_pointGoal.getText());
    	}
    	catch (Exception e)
    	{
    		System.out.println("Exception on point goal");
    		e.printStackTrace();
    	}
    	if(goal <= 0)
    	{
    		JOptionPane.showMessageDialog(null, "You must choose a whole number greater than 0 for the point goal", "error", JOptionPane.ERROR_MESSAGE);
    	}
    	else if(combo_p1.getValue().equals(combo_p2.getValue()) || "".equals(combo_p1.getValue()) || "".equals(combo_p2.getValue()))
    	{
    		JOptionPane.showMessageDialog(null, "You must select 2 different players to battle it out in Dice Mania!", "error", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    	{
    		//TODO: GOTO game
    		Player p1 = null;
    		Player p2 = null;
    		
    		for(int i = 0; i < MainApp.DEFAULT_PLAYERS.length; i++)
    		{
    			if(combo_p1.getValue() == MainApp.DEFAULT_PLAYERS[i]) p1 = new Player(MainApp.DEFAULT_PLAYERS[i], null, i + 1);
    			if(combo_p2.getValue() == MainApp.DEFAULT_PLAYERS[i]) p2 = new Player(MainApp.DEFAULT_PLAYERS[i], null, i + 1);
    		}
    		
    		if(p1 == null)
    		{	
    			for(Player p : MainApp.tempPlayers)
    			{
    				if(p.getName().equals(combo_p1.getValue())) 
    				{
    					p1 = p;
    					break;
    				}
    			}
    			
    			if(p1 == null)
    			{
    				p1 = new Player(combo_p1.getValue());
    				p1.readFromDisk();
    			}
    		}
    		if(p2 == null)
    		{
    			for(Player p : MainApp.tempPlayers)
    			{
    				if(p.getName().equals(combo_p2.getValue())) 
    				{
    					p2 = p;
    					break;
    				}
    			}
    			
    			if(p2 == null)
    			{
    				p2 = new Player(combo_p2.getValue());
    				p2.readFromDisk();
    			}
    		}
    		
    		gotoGame(p1, p2, goal, tutorial);
    	}
    }
    
    @FXML
    void tutorialClicked(ActionEvent event)
    {
    	play(true);
    }
    
    @FXML
    void playClicked(ActionEvent event)
    {	
    	play(false);
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
    	//TODO: save changes to disk
    	
    	System.exit(0);
    }

    public void setCombos()
    {
    	combo_p1.getItems().clear();
    	combo_p2.getItems().clear();
    	
    	combo_p1.getItems().addAll(MainApp.DEFAULT_PLAYERS);
    	combo_p2.getItems().addAll(MainApp.DEFAULT_PLAYERS);
    	
    	MainApp.tempPlayers.forEach( p -> {
    		combo_p1.getItems().add(p.getName());
    		combo_p2.getItems().add(p.getName());
    	});
    	
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
    	
    	combo_p1.setValue(MainApp.DEFAULT_PLAYERS[0]);
    	combo_p2.setValue(MainApp.DEFAULT_PLAYERS[1]);
    	
    	txt_pointGoal.setText("25");
    }
}
