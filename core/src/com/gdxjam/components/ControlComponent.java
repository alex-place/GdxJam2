package com.gdxjam.components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class ControlComponent {

	public ControlComponent(ControlBehavior behavior) {
	}

	public interface ControlBehavior {
		
		public void forward();

		public void left();

		public void reverse();

		public void right();

		public void lookAt(Vector2 position);
		
		public Entity getEntity();
		
		
	}

}
