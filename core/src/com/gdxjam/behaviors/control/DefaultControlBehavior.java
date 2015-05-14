package com.gdxjam.behaviors.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.components.SpriteComponent;
import com.gdxjam.components.ControlComponent.ControlBehavior;

public class DefaultControlBehavior implements ControlBehavior {

	Entity entity;

	public DefaultControlBehavior(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void forward() {
		//move forward here
		
	}

	@Override
	public void left() {
	}

	@Override
	public void reverse() {
	}

	@Override
	public void right() {
	}

	@Override
	public void lookAt(Vector2 position) {
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

}
