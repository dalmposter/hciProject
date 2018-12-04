package gui;

import java.util.Random;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Display thread for a single dice
 * 
 * @author Dominic Cousins
 *
 */
public class DiceDisplay extends Thread
{
	public static boolean[] done = { false, false, false };
	private static final int ROLL_DELAY = 150;
	
	private int id, result;
	private Button nextEnable;
	private ImageView myView;
	private Label lbl;
	private String res;
	
	/**
	 * 
	 * @param id this dice's number (0-2)
	 * @param result the number this dice should end on
	 * @param nextEnable the play turn button that should be enabled when the dice are rolled
	 * @param myView the view to be populated with sprites by this dice
	 * @param lbl the label to display the results in when all dice are finished
	 * @param res the text to be put in lbl
	 */
	public DiceDisplay(int id, int result, Button nextEnable, ImageView myView, Label lbl, String res)
	{
		done[id] = false;
		this.id = id;
		this.result = result;
		this.nextEnable = nextEnable;
		this.myView = myView;
		this.res = res;
		this.lbl = lbl;
	}
	
	/**
	 * Cycles through numbers randomly on a dice, eventually landing on [result]
	 */
	public void run()
	{
		int thisShow = 0;
		Random rand = new Random();
		
		System.out.println("Created DiceDisplay instance with id: " + id + " and result: " + result);
		
		//update gui repeatedly to make it look like the dice are scrolling through random numbers
		for(int i = 0; i < 10; i++)
		{
			thisShow = rand.nextInt(6) + 1;
			
			String imgName = "/d" + id + "-" + thisShow + ".png";
			
			Platform.runLater(new Runnable() {
				@Override
				public void run()
				{
					myView.setImage(new Image(imgName));
				}
			});
			
			System.out.println(id + ": set image to: " + "/d" + id + "-" + thisShow + ".png");
			
			try
			{
				sleep(ROLL_DELAY);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
		
		//update gui with final result
		Platform.runLater(new Runnable() {
			@Override
			public void run()
			{
				myView.setImage(new Image("/d" + id + "-" + result + ".png"));
			}
		});
		
		System.out.println(id + ": set image to: " + "/d" + id + "-" + result + ".png");
		
		//if all DiceDisplay threads are done we need to reveal the result to the players
		done[id] = true;
		if(done[0] && done[1] && done[2])
		{
			nextEnable.setDisable(false);
			Platform.runLater(new Runnable() {
				@Override
				public void run()
				{
					lbl.setText(res);
				}
			});
		}
	}
}
