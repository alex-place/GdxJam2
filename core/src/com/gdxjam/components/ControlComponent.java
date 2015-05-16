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

	public void forward(float delta) {
		behavior.forward(delta);
	}

	public void left(float delta) {
		behavior.left(delta);
	}

	public void reverse(float delta) {
		behavior.reverse(delta);
	}

	public void right(float delta) {
		behavior.right(delta);
	}

	public void lookAt(Vector2 position) {
		behavior.lookAt(position);
	}

	public interface ControlBehavior {

		public void forward(float delta);

		public void left(float delta);

		public void reverse(float delta);

		public void right(float delta);

		public void lookAt(Vector2 position);

		public Entity getEntity();

	}

}
