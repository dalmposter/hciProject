package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import gui.MainApp;
import javafx.scene.image.Image;

public class Player
{
	private String name;
	private int score, lastScore, wins, careerWins;
	
	//0 = custom picture, 1 = default male, 2 = default female
	private int defaultPicStatus;
	private File pic;
	private boolean temp = true;
	
	//File store:
	/*
	 * name
	 * defaultPicStatus
	 * pic path
	 * careerWins
	 */
	
	//empty constructor, for use when reading player from disk
	//call followed by player.readFromDisk
	public Player(String name)
	{
		this.name = name;
		wins = 0;
		score = 0;
	}
	
	//standard constructor
	public Player(String name, File pic, int defPicStatus)
	{
		this(name);
		this.pic = pic;
		defaultPicStatus = defPicStatus;
	}
	
	public int getDefStat()
	{
		return defaultPicStatus;
	}
	
	/**
	 * Get this players profile picture
	 * @return profile picture as Image object
	 */
	public Image getPicImage()
	{
		if(defaultPicStatus == 0)
		{
			return new Image(pic.toURI().toString());
		}
		else if(defaultPicStatus == 1)
		{
			return new Image("/male.jpg");
		}
		return new Image("female.jpg");
	}
	
	public void reset()
	{
		score = 0;
		lastScore = 0;
	}
	
	public void setCareerWins(int wins)
	{
		careerWins = wins;
		
		//update career wins
		if(!temp) writeToDisk();
	}
	
	public int getCareerWins()
	{
		return careerWins;
	}
	
	public int getLastScore()
	{
		return lastScore;
	}
	
	public void resetWins()
	{
		wins = 0;
	}
	
	public void addWin()
	{
		careerWins++;
		wins++;
		
		//update career wins
		if(!temp) writeToDisk();
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getWins()
	{
		return wins;
	}
	
	/**
	 * Method to add to the players score and set lastScore
	 * @param gained The the amount to increase the score by
	 */
	public void addScore(int gained)
	{
		lastScore = gained;
		score += gained;
	}
	
	public void resetScore()
	{
		score = 0;
	}
	
	public String toString()
	{
		return name;
	}
	
	public File getPic()
	{
		return pic;
	}
	
	public void setPic(File pic)
	{
		this.pic = pic;
	}
	
	/**
	 * Writes this players data to [home directory]/Dice Mania/[name].dat
	 * @return whether the write was successful
	 */
	public boolean writeToDisk()
	{
		try
		{
			System.out.println("Started writing to disk... ");
			
			//create reference to file and delete it if it exists
			File dataFile = new File(MainApp.dataLocation + "/" + name + ".dat");
			System.out.println("Writing to : " + dataFile.getPath().toString());
			if(dataFile.exists()) dataFile.delete();
			
			//create file and writers
			FileWriter fileWriter = new FileWriter(MainApp.dataLocation + "/" + name + ".dat");
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
			//begin printing data
			printWriter.println(name);
			printWriter.println(defaultPicStatus);
			
			//write path to custom picture or n/a if not applicable
			if(defaultPicStatus > 0) printWriter.println("n/a");
			else printWriter.println(pic.getPath().toString());
			
			//write wins
			printWriter.println(careerWins);
			
			printWriter.close();
			
			System.out.println("Successfully wrote to disk");
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Read player file [name].dat and populate this object with it's data
	 * @return whether read was successful
	 */
	public boolean readFromDisk()
	{
		try
		{	
			//create readers
			FileReader fr = new FileReader(MainApp.dataLocation + "/" + name + ".dat");
			BufferedReader br = new BufferedReader(fr);
			
			//read name and pic status
			name = br.readLine();
			defaultPicStatus = Integer.valueOf(br.readLine());
			
			//get custom picture or skip this line if not applicable
			if(defaultPicStatus == 0)
			{
				pic = new File(br.readLine());
			}
			else br.readLine();
			
			//read wins
			careerWins = Integer.valueOf(br.readLine());
			
			//if we were read from disk then we're not a guest player
			temp = false;
		}
		catch(Exception e)
		{
			System.out.println("ERROR reading player from disk: " + name);
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
