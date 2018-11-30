package controllers;

import gui.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Player;

public class Controller
{
	protected Stage stage;
	
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
	
	//Functions to change scene, inherited by all controllers
	//-----------------------------------------------------------------------------------------------------
	
	protected void gotoMenu()
	{
		MenuController controller = new MenuController();
		
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resources/MainMenu.fxml"));
		loader.setController(controller);
		
		try
		{
			Pane mainPane = (Pane) loader.load();
		
			stage.setScene(new Scene(mainPane));
			stage.show();
		
			controller.initialise();
			controller.setStage(stage);
		}
		catch(Exception e)
		{
			System.out.println("ERROR moving to MainMenu.fxml :");
			e.printStackTrace();
		}
	}
	
	protected void gotoRegister()
	{
		RegisterController controller = new RegisterController();
		
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/RegisterPlayer.fxml"));
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resources/RegisterPlayer.fxml"));
		loader.setController(controller);
		
		try
		{
			Pane mainPane = (Pane) loader.load();
		
			stage.setScene(new Scene(mainPane));
			stage.show();
		
			controller.setStage(stage);
			
			controller.initialise();
		}
		catch(Exception e)
		{
			System.out.println("ERROR switching to RegisterPlayer.fxml: ");
			e.printStackTrace();
		}
	}
	
	protected void gotoGame(Player p1, Player p2, int goal, boolean tutorial)
	{
		GameController controller = new GameController();
		
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resources/MainGame.fxml"));
		loader.setController(controller);
		
		try
		{
			Pane mainPane = (Pane) loader.load();
		
			stage.setScene(new Scene(mainPane));
			stage.show();
		
			controller.setStage(stage);
			
			controller.initialise(p1, p2, goal, tutorial);
		}
		catch(Exception e)
		{
			System.out.println("ERROR switching to MainGame.fxml: ");
			e.printStackTrace();
		}
	}
}
