package gui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controllers.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Player;

public class MainApp extends Application
{
	public static ArrayList<Player> tempPlayers;

	public static final String[] DEFAULT_PLAYERS = {"[Player 1]", "[Player 2]"};
	public static boolean canSavePlayers;
	public static String dataLocation;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		initialise();
		
		MenuController controller = new MenuController();
		
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resources/MainMenu.fxml"));
		loader.setController(controller);
		
		Pane mainPane = (Pane) loader.load();
		
		primaryStage.setTitle("Dice Mania");
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.show();
		
		controller.initialise();
		controller.setStage(primaryStage);
		
		//anonymous inner class defined to run welcome popup as a seperate thread.
		//prevents windows thinking the program is "not responding" and lets the gui load in the backgorund
		Runnable intro = new Runnable()
		{
			public void run()
			{
				JOptionPane.showMessageDialog(null, "Welcome to Dice Mania, the game of unreasonable luck!\nMake sure to tick the tutorial box if you're new, otherwise you know what's up, enjoy!", "welcome", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		new Thread(intro).start();
	}

	public static void main(String[] args)
	{	
		launch(args);
	}

	private static void initialise()
	{
		tempPlayers = new ArrayList<>();
		//localPlayers = new ArrayList<>();
		
		//creating/finding data directory
		dataLocation = System.getProperty("user.home") + "/Dice Mania";
		File dataDir = new File(dataLocation);
		if(dataDir.exists())
		{
			canSavePlayers = true;
			//TODO: read player files and load players
			
		}
		else
		{
			boolean result = false;
			try
			{
				result = dataDir.mkdir();
			}
			catch(Exception e)
			{
				System.out.println("ERROR in making data directory: " + e.getStackTrace());
			}
			if(!result) JOptionPane.showMessageDialog(null, "Dice Mania requires permission to write to " + dataLocation + " in order to save player profiles. You may still play without this but you cannot save players", "error", JOptionPane.ERROR_MESSAGE);
			canSavePlayers = result;
			System.out.println("Set canSavePlayers to: " + canSavePlayers);
		}
	}
	
	/**
	 * Creates the data folder if it doesn't already exist
	 * @return true if it already existed
	 */
	public static boolean checkDataDir()
	{
		File data = new File(dataLocation);
		
		if(data.exists()) return true;
		
		data.mkdirs();
		return false;
	}

}
