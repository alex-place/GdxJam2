package com.gdxjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class ControlComponent extends Component {

	ControlBehavior behavior;
	
	public ControlComponent(ControlBehavior behavior) {
		this.behavior = behavior;
	}

	public void forward() {
		behavior.forward();
	}

	public void left() {
	}

	public void reverse() {
	}

	public void right() {
	}

	public void lookAt(Vector2 position) {
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
