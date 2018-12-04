package controllers;

import javafx.stage.FileChooser;
import main.Player;

import java.io.File;

import javax.swing.JOptionPane;

import gui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for scene RegisterPlayer.fxml
 * @author Dominic Cousins
 *
 */
public class RegisterController extends Controller
{
	//booleans for working out if the user has selected either of the default profile pictures
	//need both as false in both would mean a user defined picture
	private boolean maleDefault = false;
	private boolean femaleDefault = false;
	
	//pointer to uploaded profile picture
	private File pic;
	
	@FXML
    private Button btn_regPlayer;

    @FXML
    private Button btn_helpRegGuest;

    @FXML
    private Button btn_regGuest;

    @FXML
    private Button btn_toggleDef;

    @FXML
    private Button btn_helpRegPlayer;

    @FXML
    private ImageView img_profilePic;

    @FXML
    private TextField txt_username;

    @FXML
    private Button btn_uploadPic;
    
    @FXML
    private Button btn_cancel;
    
    private String regHelpMessage = "Choosing to make a local player means the player (name and picture) will persist even if you close Dice Mania";

    @FXML
    void helpRegGuestClicked(ActionEvent event)
    {
    	//help message about guest players
    	JOptionPane.showMessageDialog(null, "Choosing to make a guest player means the player (name and picture) will be lost when you close Dice Mania", "help", JOptionPane.INFORMATION_MESSAGE);
    }

    //Creates new instance of Player with values from gui
    //adds this to tempPlayers
    @FXML
    void regGuestClicked(ActionEvent event)
    {
    	//Username must be non empty
    	if(!txt_username.getText().isEmpty())
    	{
    		//set picStatus which stores whether this player chose a default or custom picture
    		int picStatus = 0;
    		if(maleDefault) picStatus = 1;
    		else if(femaleDefault) picStatus = 2;
    		
    		//create new player and add them to tempPlayers as this is a guest player
    		Player newPlayer = new Player(txt_username.getText(), pic, picStatus);
    		MainApp.tempPlayers.add(newPlayer);
    	
    		System.out.println("Just registered temp user with:");
    		System.out.println("Name:" + newPlayer.getName());
    		if(newPlayer.getPic() != null) System.out.println("pic path: " + newPlayer.getPic().getPath().toString());
    		System.out.println("defaultPicStatus: " + newPlayer.getDefStat());
    	
    		gotoMenu();
    	}
    	else
    	{
    		//username field is empty, show error message
    		JOptionPane.showMessageDialog(null, "Please enter a username", "error", JOptionPane.ERROR_MESSAGE);
    	}
    }

    //Creates new instance of Player with values form gui
    //writes this player to file
    @FXML
    void regPlayerClicked(ActionEvent event)
    {
    	if(!txt_username.getText().isEmpty())
    	{
    		//set picStatus which stores whether this player chose a default or custom picture
    		int picStatus = 0;
    		if(maleDefault) picStatus = 1;
    		else if(femaleDefault) picStatus = 2;
    		
    		//Create new player
    		Player newPlayer = new Player(txt_username.getText(), pic, picStatus);
    		//Create save file if not exists
    		MainApp.checkDataDir();
    		//write player to this file
    		newPlayer.writeToDisk();
    	
    		gotoMenu();
    	}
    	else
    	{
    		//username field empty, show error message
    		JOptionPane.showMessageDialog(null, "Please enter a username", "error", JOptionPane.ERROR_MESSAGE);
    	}
    }

    @FXML
    void helpRegPlayerClicked(ActionEvent event)
    {
    	//help message regarding saving players to disk
    	JOptionPane.showMessageDialog(null, regHelpMessage, "help", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    void uploadPicClicked(ActionEvent event)
    {
    	//open a file chooser
    	FileChooser fc = new FileChooser();
    	pic = fc.showOpenDialog(stage);
    	
    	//avoid null pointer exception by calling != null first in an and
    	if(pic != null && pic.exists())
    	{
    		//show selected image to user and set necessary flags for saving this user
    		img_profilePic.setImage(new Image(pic.toURI().toString()));
    		femaleDefault = false;
    		maleDefault = false;
    	}
    }
    
    @FXML
    void cancelClicked(ActionEvent event)
    {
    	gotoMenu();
    }
    
    @FXML
    void toggleDefClicked(ActionEvent event)
    {
    	if(maleDefault)
    	{
    		//show blank female picture and set flags
    		img_profilePic.setImage(new Image("/female.jpg"));
    		maleDefault = false;
    		femaleDefault = true;
    	}
    	else
    	{
    		//show blank male picture and set flags
    		img_profilePic.setImage(new Image("/male.jpg"));
    		maleDefault = true;
    		femaleDefault = false;
    	}
    }
    
    public void initialise()
    {
    	if(!MainApp.canSavePlayers)
    	{
    		//if we can't save players to disk for some reason, disable the save player button and add reason for this to help message
    		btn_regPlayer.setDisable(true);
    		regHelpMessage += ". You cannot save local players because Dice Mania needs write permission in your user home directory";
    	}
    }

}
