package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.Game;
import main.Player;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.DiceDisplay;
import javafx.event.ActionEvent;

public class GameController extends Controller
{
	Game currGame;
	boolean tutorial = false;
	
	@FXML
    private ImageView img_p1;

    @FXML
    private ImageView img_p2;

    @FXML
    private Label lbl_goal;

    @FXML
    private Label lbl_winsP1;

    @FXML
    private Label lbl_p1;

    @FXML
    private Button btn_rollP1;

    @FXML
    private Label lbl_p2;

    @FXML
    private Button btn_rollP2;

    @FXML
    private Label lbl_careerWinsP1;

    @FXML
    private Label lbl_resultMsg;

    @FXML
    private Label lbl_winsP2;

    @FXML
    private ImageView img_dice1;

    @FXML
    private Label lbl_result;

    @FXML
    private ImageView img_dice2;

    @FXML
    private ImageView img_dice0;

    @FXML
    private Button btn_gotoMenu;

    @FXML
    private Label lbl_lastScoreP2;

    @FXML
    private Label lbl_lastScoreP1;

    @FXML
    private Label lbl_resultP2;

    @FXML
    private Label lbl_careerWinsP2;

    @FXML
    private Label lbl_resultP1;

    @FXML
    private Label lbl_scoreP2;

    @FXML
    private Label lbl_scoreP1;
    
    @FXML
    private Button btn_restart;
    
    @FXML
    private ImageView img_d0;
    
    @FXML
    private ImageView img_d1;
    
    @FXML
    private ImageView img_d2;
    
    @FXML
    private ImageView img_p1Leading;
    
    @FXML
    private ImageView img_p2Leading;
    
    @FXML
    private ImageView img_tie;
    
    @FXML
    private Label lbl_tutorial;
    
    @FXML
    private VBox vbox_dice;
    
    @FXML
    private VBox vbox_p1;
    
    @FXML
    private VBox vbox_p2;
    
    @FXML
    void restartClicked(ActionEvent event)
    {
    	initialise(currGame.getP1(), currGame.getP2(), currGame.getGoal(), tutorial);
    }

    @FXML
    void gotoMenuClicked(ActionEvent event)
    {
    	gotoMenu();
    }
	
   	/**
   	 * Updates UI if we have a winner
   	 * @param winner the player who won or 3 if there was a tie
   	 */
    private void doWin(int winner)
    {
    	btn_rollP1.setDisable(true);
    	btn_rollP2.setDisable(true);
    	
    	if(winner == 1)
    	{
    		JOptionPane.showMessageDialog(null, currGame.getP1().getName() + " wins!", "winner!", JOptionPane.INFORMATION_MESSAGE);
    		lbl_careerWinsP1.setText("Career wins: " + currGame.getP1().getCareerWins());
    		lbl_winsP1.setText("Wins: " + currGame.getP1().getWins());
    	}
    	else if(winner == 2)
    	{
    		JOptionPane.showMessageDialog(null, currGame.getP2().getName() + " wins!", "winner!", JOptionPane.INFORMATION_MESSAGE);
    		lbl_careerWinsP2.setText("Career wins: " + currGame.getP2().getCareerWins());
    		lbl_winsP2.setText("Wins: " + currGame.getP2().getWins());
    	}
    	else JOptionPane.showMessageDialog(null, "The game ended in a tie", "tie!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @FXML
    void rollP1Clicked(ActionEvent event)
    {
    	if(tutorial)
    	{
    		lbl_tutorial.setText("You score points based on your roll.\n3-of-a-kind: 18 points, pair: sum of the pair\notherwise: 1 point. Click '" + btn_rollP2.getText() + "' to continue");
    	}
    	
    	//Take turn in currGame
    	ArrayList<Integer> result = currGame.playTurn();
    	
    	btn_rollP1.setDisable(true);
    	
    	ArrayList<DiceDisplay> displays = new ArrayList<>();
    	String resString = result.get(0) + ", " + result.get(1) + ", " + result.get(2);
    	
    	lbl_resultP1.setText("?, ?, ?");
    	
    	//Create dice rolling threads for display purposes
    	displays.add(new DiceDisplay(0, result.get(0), btn_rollP2, img_d0, lbl_resultP1, resString));
    	displays.add(new DiceDisplay(1, result.get(1), btn_rollP2, img_d1, lbl_resultP1, resString));
    	displays.add(new DiceDisplay(2, result.get(2), btn_rollP2, img_d2, lbl_resultP1, resString));
    	
    	displays.forEach(d -> d.start());
    	
    	updateP1();
    	updateLeader();
    	
    	lbl_p2.setStyle("-fx-font-weight: bold");
    	lbl_p1.setStyle("-fx-font-weight: regular");
    }

    @FXML
    void rollP2Clicked(ActionEvent event)
    {
    	if(tutorial)
    	{
    		lbl_tutorial.setText("Whoever's turn it is has their name in bold\nThe current leader has a crown over their name\nThis concludes the tutorial, Have fun!");
    		tutorial = false;
    	}
    	
    	//Take turn in currGame
    	ArrayList<Integer> result = currGame.playTurn();
    	
    	btn_rollP2.setDisable(true);
    	
    	ArrayList<DiceDisplay> displays = new ArrayList<>();
    	String resString = result.get(0) + ", " + result.get(1) + ", " + result.get(2);
    	
    	lbl_resultP2.setText("?, ?, ?");
    	
    	//Create dice rolling threads for display purposes
    	displays.add(new DiceDisplay(0, result.get(0), btn_rollP1, img_d0, lbl_resultP2, resString));
    	displays.add(new DiceDisplay(1, result.get(1), btn_rollP1, img_d1, lbl_resultP2, resString));
    	displays.add(new DiceDisplay(2, result.get(2), btn_rollP1, img_d2, lbl_resultP2, resString));
    	
    	displays.forEach(d -> d.start());
    	
    	updateP2();
    	updateLeader();
    	
    	//check if we have a winner
    	int res = currGame.checkWin();
    	if(res > 0) doWin(res);
    	else
    	{
    		lbl_p2.setStyle("-fx-font-weight: regular");
        	lbl_p1.setStyle("-fx-font-weight: bold");
    	}
    }

    public void initialise(Player p1, Player p2, int goal, boolean tutorial)
    {
    	initialise(p1, p2, goal);
    	this.tutorial = tutorial;
    	
    	if(tutorial)
    	{
    		lbl_tutorial.setText("In Dice Mania you and your opponent will take turns\nrolling 3 dice. Click '" + btn_rollP1.getText() + "' to do so.");
    	}
    	else
    	{
    		lbl_tutorial.setText("");
    	}
    }
    
    public void initialise(Player p1, Player p2, int goal)
    {
    	//TODO: init
    	currGame = new Game(p1, p2, goal);
    	p1.resetWins();
    	p2.resetWins();
    	
    	btn_rollP1.setText("Roll " + p1.getName());
    	btn_rollP2.setText("Roll " + p2.getName());
    	
    	btn_rollP1.setDisable(false);
    	btn_rollP2.setDisable(true);
    	
    	lbl_p1.setText(p1.getName());
    	lbl_p2.setText(p2.getName());
    	
    	updateLeader();
    	img_p1.setImage(p1.getPicImage());
    	img_p2.setImage(p2.getPicImage());
    	
    	lbl_careerWinsP1.setText("Career wins: " + p1.getCareerWins());
    	lbl_careerWinsP2.setText("Career wins: " + p2.getCareerWins());
    	
    	lbl_goal.setText("First to: " + goal);
    	
    	img_p1Leading.setImage(new Image("/leading.png"));
    	img_p2Leading.setImage(new Image("/leading.png"));
    	img_tie.setImage(new Image("/leading.png"));
    	updateLeader();
    	
    	lbl_p1.setStyle("-fx-font-weight: bold");

    	BackgroundImage BI = new BackgroundImage(new Image("/table.jpeg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    	vbox_dice.setBackground(new Background(BI));
    	vbox_p1.setBackground(new Background(BI));
    	vbox_p2.setBackground(new Background(BI));
    }

    //Update data displays for p1
    private void updateP1()
    {
    	lbl_scoreP1.setText("Total score: " + currGame.getP1().getScore());
    	lbl_lastScoreP1.setText("Scored: " + currGame.getP1().getLastScore());
    }
    
    //Update data displays for p2
    private void updateP2()
    {
    	lbl_scoreP2.setText("Total score: " + currGame.getP2().getScore());
    	lbl_lastScoreP2.setText("Scored: " + currGame.getP2().getLastScore());
    }
    
    /**
     * Updates visual queues as to who has the most points
     */
    private void updateLeader()
    {	
    	System.out.println("Checking leader: ");
    	if(!currGame.isTie())
    	{
    		img_tie.setVisible(false);
    		
	    	if(currGame.isP1Leading())
	    	{
	    		img_p1Leading.setVisible(true);
	    		img_p2Leading.setVisible(false);
	    	}
	    	else
	    	{
	    		img_p1Leading.setVisible(false);
	    		img_p2Leading.setVisible(true);
	    	}
    	}
    	else
    	{
    		img_p1Leading.setVisible(false);
    		img_p2Leading.setVisible(false);
    		img_tie.setVisible(true);
    		System.out.println("Tie");
    	}
    }
}
