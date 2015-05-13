package com.gdxjam.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class DeveloperInputProcessor extends InputAdapter{
	
	@Override
	public boolean keyDown (int keycode) {
		switch(keycode){
		case Keys.F5:
			return true;
		case Keys.F6:
			return true;
		}
		return false;
	}

}
