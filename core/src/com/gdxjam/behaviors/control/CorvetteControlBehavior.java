package com.gdxjam.behaviors.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.components.Components;
import com.gdxjam.components.ControlComponent.ControlBehavior;
import com.gdxjam.components.SteerableComponent;
import com.gdxjam.components.SteeringBehaviorComponent;

/**
 * This is the movement behavior for the " saucer" ship which i called a "Corvette"
 * 
 * 
 * @author alex-place / Nate Baker
 * */
public class CorvetteControlBehavior extends DefaultControlBehavior  {

	PooledEngine engine;
	Entity entity;
	SteeringBehaviorComponent steer;
	SteerableComponent steerable;
	float speed = 150;
	

	// TODO make parameters (or a param class) for ship classes (speed...)
	public CorvetteControlBehavior(Entity entity, PooledEngine engine, float radius) {
		super(entity, engine, radius);
	}

	@Override
	public void forward(float delta) {
		steerable.getBody().setLinearVelocity(new Vector2(0,0)); //remove all velocity to creat instant speed change. -Nate
		steerable.getBody().applyForce(new Vector2(0, speed * delta), steerable.getBody().getWorldCenter(), true);
	}

	@Override
	public void reverse(float delta) {
		steerable.getBody().setLinearVelocity(new Vector2(0,0)); //remove all velocity to creat instant speed change. -Nate
		steerable.getBody().applyForce(new Vector2(0, -speed * delta), steerable.getBody().getWorldCenter(), true);
	}

	@Override
	public void left(float delta) {
		steerable.getBody().setLinearVelocity(new Vector2(0,0)); //remove all velocity to creat instant speed change. -Nate
		steerable.getBody().applyForce(new Vector2(-speed * delta, 0), steerable.getBody().getWorldCenter(), true);

	}

	@Override
	public void right(float delta) {
		steerable.getBody().setLinearVelocity(new Vector2(0,0)); //remove all velocity to creat instant speed change. -Nate
		steerable.getBody().applyForce(new Vector2(speed * delta, 0), steerable.getBody().getWorldCenter(), true);
	}

	@Override
	public void lookAt(Vector2 position) {
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

}
