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

public class RegisterController extends Controller
{
	private boolean maleDefault = false;
	private boolean femaleDefault = false;
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
    		int picStatus = 0;
    		if(maleDefault) picStatus = 1;
    		else if(femaleDefault) picStatus = 2;
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
    		JOptionPane.showMessageDialog(null, "Please enter a username", "error", JOptionPane.ERROR_MESSAGE);
    	}
    }

    @FXML
    void regPlayerClicked(ActionEvent event)
    {
    	if(!txt_username.getText().isEmpty())
    	{
    		int picStatus = 0;
    		if(maleDefault) picStatus = 1;
    		else if(femaleDefault) picStatus = 2;
    		Player newPlayer = new Player(txt_username.getText(), pic, picStatus);
    	
    		MainApp.checkDataDir();
    		newPlayer.writeToDisk();
    	
    		gotoMenu();
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Please enter a username", "error", JOptionPane.ERROR_MESSAGE);
    	}
    }

    @FXML
    void helpRegPlayerClicked(ActionEvent event)
    {
    	JOptionPane.showMessageDialog(null, regHelpMessage, "help", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    void uploadPicClicked(ActionEvent event)
    {
    	FileChooser fc = new FileChooser();
    	pic = fc.showOpenDialog(stage);
    	
    	if(pic != null && pic.exists())
    	{
    		img_profilePic.setImage(new Image(pic.toURI().toString()));
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
    		img_profilePic.setImage(new Image("/female.jpg"));
    		maleDefault = false;
    		femaleDefault = true;
    	}
    	else
    	{
    		img_profilePic.setImage(new Image("/male.jpg"));
    		maleDefault = true;
    		femaleDefault = false;
    	}
    }
    
    public void initialise()
    {
    	if(!MainApp.canSavePlayers)
    	{
    		btn_regPlayer.setDisable(true);
    		regHelpMessage += ". You cannot save local players because Dice Mania needs write permission in your user home directory";
    	}
    }

}
