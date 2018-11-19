package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.*;

import main.Game;
import main.Player;
import handlers.*;

public class MainWindow extends Application
{
	public static Game game;
	private static Label lbl_p1, lbl_p1Wins, lbl_p1Score, lbl_p2, lbl_p2Wins, lbl_p2Score, lbl_objective, lbl_currentTurn;
	private static Button btn_playTurn, btn_newGame;
	private static TextArea txtArea_lastAction;
	private static TextField txt_newGoal;
	
	public static void resetNewGoal()
	{
		txt_newGoal.setText("");
	}
	
	public static int getNewGoal() throws NumberFormatException
	{
		int newGoal = Integer.parseInt(txt_newGoal.getText());
		if(newGoal <= 0) throw new NumberFormatException("Must be greater than 0");
		return newGoal;
	}
	
	public static void setLastAction(List<String> message)
	{
		String out = "";
		
		for(String s : message)
		{
			out += s + "\n";
		}
		
		txtArea_lastAction.setText(out);
	}
	
	public static void updatePlayer()
	{
		lbl_currentTurn.setText(game.getCurrentPlayer().getName() + "'s turn!");
	}
	
	public static void updateObjective()
	{
		lbl_objective.setText("Objective: " + game.getGoal() + " points");
		System.out.println("Set objective lbl to : " + game.getGoal());
	}
	
	public static void updateScores()
	{
		lbl_p1Score.setText("Score: " + game.getP1().getScore());
		lbl_p2Score.setText("Score: " + game.getP2().getScore());
	}
	
	public static void restart(int goal)
	{
		game = new Game(game.getP1(), game.getP2(), goal);
		game.getP1().resetScore();
		game.getP2().resetScore();
		updatePlayer();
		updateScores();
		updateObjective();
	}
	
	@Override
	public void start(Stage mainStage) throws Exception
	{
		//Player 1 information - displayed in left column
				lbl_p1 = new Label("Player 1");
				lbl_p1Wins = new Label("Wins: 0");
				lbl_p1Score = new Label("Score: 0");
				Label p1[] = new Label[]{
								lbl_p1,
								lbl_p1Wins,
								lbl_p1Score
						};
			
				//Player 2 information - displayed in right column
				lbl_p2 = new Label("Player 2");
				lbl_p2Wins = new Label("Wins: 0");
				lbl_p2Score = new Label("Score: 0");
				Label p2[] = new Label[]{
						lbl_p2,
						lbl_p2Wins,
						lbl_p2Score
				};
				
				//Game information - displayed in central column
				lbl_objective = new Label("Objective:");
				lbl_currentTurn = new Label("Player 1's turn!");
				btn_playTurn = new Button("Roll!");
				
				ArrayList<Control> playArea = new ArrayList<>();
				playArea.add(lbl_objective);
				playArea.add(lbl_currentTurn);
				playArea.add(btn_playTurn);
				
				txtArea_lastAction = new TextArea("This area shows the last game event");
				txtArea_lastAction.prefWidthProperty().bind(mainStage.widthProperty());
				txtArea_lastAction.setPrefHeight(100);
				txtArea_lastAction.setEditable(false);
				txtArea_lastAction.setWrapText(true);
				
				btn_newGame = new Button("New Game");
				txt_newGoal = new TextField("");
				txt_newGoal.setMaxWidth(50);
				
				ArrayList<Control> operations = new ArrayList<>();
				operations.add(txtArea_lastAction);
				operations.add(new Label("Goal for next game:"));
				operations.add(txt_newGoal);
				operations.add(btn_newGame);

				//populate boxes
				HBox top = new HBox(5);
				top.getChildren().add(new Label("Welcome to Dice Mania!"));
				
				VBox bottom = new VBox(5);
				VBox left = new VBox(5);
				VBox centre = new VBox(5);
				VBox right = new VBox(5);
				
				for(Label l : p1)
				{
					left.getChildren().add(l);
				}
				
				for(Label l : p2)
				{
					right.getChildren().add(l);
				}
				
				for(Control c : playArea)
				{
					centre.getChildren().add(c);
				}
				
				for(Control c : operations)
				{
					bottom.getChildren().add(c);
				}
				
				//set margins
				Insets margins = new Insets(5, 5, 5, 5);
				centre.setPadding(margins);
				left.setPadding(margins);
				right.setPadding(margins);
				top.setPadding(margins);
				bottom.setPadding(margins);
				
				top.setAlignment(Pos.CENTER);
				centre.setAlignment(Pos.CENTER);
				bottom.setAlignment(Pos.CENTER);
				
				BorderPane bpane = new BorderPane(centre, top, right, bottom, left);
				Scene sc = new Scene(bpane, 600, 400);
				
				mainStage.setScene(sc);
				mainStage.show();
				
				//assign event handlers
				btn_playTurn.setOnAction(new RollHandler());
				btn_newGame.setOnAction(new newGameHandler());
				
				//initial setup
				setup();
	}
	
	private void setup()
	{
		updateObjective();
	}

	public static void main(String[] args)
	{
		game = new Game(new Player("Player 1"), new Player("Player 2"), 25);
		launch(args);
	}

}
