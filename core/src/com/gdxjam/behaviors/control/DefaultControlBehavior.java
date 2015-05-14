package com.gdxjam.behaviors.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.components.Components;
import com.gdxjam.components.ControlComponent.ControlBehavior;
import com.gdxjam.components.SquadComponent;
import com.gdxjam.components.SteerableComponent;
import com.gdxjam.components.SteeringBehaviorComponent;
import com.gdxjam.utils.Location2;

public class DefaultControlBehavior implements ControlBehavior {

	PooledEngine engine;
	Entity entity;
	SteeringBehaviorComponent steer;

	public DefaultControlBehavior(Entity entity, PooledEngine engine) {
		this.entity = entity;
		this.engine = engine;
		steer = Components.STEERING_BEHAVIOR.get(entity);
	}

	@Override
	public void forward() {
		try{
		// move forward here
		SteerableComponent steerable;
		
		steerable = engine.createComponent(
				SteerableComponent.class).init(
				Components.PHYSICS.get(entity).getBody(), 30.0f);

		// A good rule of thumb is to make the maximum speed of the formation
		// around
		// half that of the members. We also give the anchor point far less
		// acceleration.
		steerable.setMaxLinearSpeed(SteerableComponent.MAX_LINEAR_SPEED / 2);
		steerable
				.setMaxLinearAcceleration(SteerableComponent.MAX_LINEAR_ACCELERATION / 10);

		Arrive<Vector2> arriveSB = new Arrive<Vector2>(steerable)
				.setTarget(new Location2(new Vector2(255, 255)))
				.setTimeToTarget(0.001f).setDecelerationRadius(2f)
				.setArrivalTolerance(0.0001f);

		Components.STEERING_BEHAVIOR.get(entity).setBehavior(arriveSB);

		entity.add(steerable);

		engine.addEntity(entity);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
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
