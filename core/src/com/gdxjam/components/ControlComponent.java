package com.gdxjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class ControlComponent extends Component {

	ControlBehavior behavior;

	private ControlComponent() {

	}

	public ControlComponent init(ControlBehavior behavior) {
		this.behavior = behavior;
		return this;
	}

	public void forward() {
		behavior.forward();
	}

	public void left() {
		behavior.left();
	}

	public void reverse() {
		behavior.reverse();
	}

	public void right() {
		behavior.right();
	}

	public void lookAt(Vector2 position) {
		behavior.lookAt(position);
	}

	public void update(){
		behavior.update();
	}
	
	public interface ControlBehavior {

		public void forward();

		public void left();

		public void reverse();

		public void right();

		public void lookAt(Vector2 position);

		public Entity getEntity();
		
		public void update();


	}

}
