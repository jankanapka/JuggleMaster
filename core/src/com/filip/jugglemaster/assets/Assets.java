package com.filip.jugglemaster.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets
{
	public static AssetManager manager;

	//------TEXTURES------
	public static String ball = "SoccerBall.png";
	public static String background = "footballpitchscaled.jpg";
	public static String coin = "coin-sheet-scaled.png" ;

	//------SOUNDS------
	public static String kick = "smackSound.wav";
	public static String ding = "ding.mp3";

	public static void load()
	{
		manager  = new AssetManager();
		//-------------------------------------
		manager.load(ball, Texture.class);
		manager.load(background, Texture.class);
		manager.load(coin, Texture.class);
		//--------------------------------------
		manager.load(kick, Sound.class);
		manager.load(ding, Sound.class);
	}

	public static void dispose()
	{
		manager.dispose();
	}

}