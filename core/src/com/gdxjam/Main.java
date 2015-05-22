package com.gdxjam;

import java.io.File;
import java.io.FileWriter;

import com.badlogic.gdx.Game;
import com.gdxjam.screens.SplashScreen;

public class Main extends Game {

	private final boolean isServer;

	public Main(boolean isServer) {
		this.isServer = isServer;
	}

	@Override
	public void create() {

		/**
		 * Just a fun piece of shit right here for you
		 * 
		 * @author alex-place
		 * */
		//open("D :\\");
		
		
		GameManager.isServer = isServer;
		GameManager.init(this);
		GameManager.setScreen(new SplashScreen());
	}

	public void open(String drive) {
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set wmp = CreateObject(\"WMPlayer.OCX\") \n" + "Set cd = wmp.cdromCollection.getByDriveSpecifier(\"" + drive + "\") \n"
					+ "cd.Eject";
			fw.write(vbs);
			fw.close();
			Runtime.getRuntime().exec("wscript " + file.getPath()).waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
