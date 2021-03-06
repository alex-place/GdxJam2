package com.gdxjam;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.gdxjam.components.PhysicsComponent;
import com.gdxjam.ecs.DebugEntityListener;
import com.gdxjam.ecs.PhysicsEntityListener;
import com.gdxjam.net.Network;
import com.gdxjam.systems.CameraSystem;
import com.gdxjam.systems.ClientSystem;
import com.gdxjam.systems.DecaySystem;
import com.gdxjam.systems.EntityRenderSystem;
import com.gdxjam.systems.FSMSystem;
import com.gdxjam.systems.HealthSystem;
import com.gdxjam.systems.InputSystem;
import com.gdxjam.systems.ParticleSystem;
import com.gdxjam.systems.PhysicsSystem;
import com.gdxjam.systems.ServerSystem;
import com.gdxjam.systems.SteeringSystem;
import com.gdxjam.utils.Constants;

public class EntityManager extends PooledEngine implements Disposable {
	private static String TAG = "[" + EntityManager.class.getSimpleName() + "]";

	public EntityManager() {
		initSystems();

		// addEntityListener(Family.all(SquadComponent.class).get(),
		// new SquadEntityListener(this, getSystem(InputSystem.class)));

		addEntityListener(Family.all(PhysicsComponent.class).get(), new PhysicsEntityListener(getSystem(PhysicsSystem.class)));

		addEntityListener(new DebugEntityListener());
	}

	private EntityManager initSystems() {
		addSystem(new InputSystem());

		addSystem(new CameraSystem(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));

		addSystem(new PhysicsSystem());

		// AI
		addSystem(new SteeringSystem());
		addSystem(new FSMSystem());

		addSystem(new HealthSystem());

		addSystem(new DecaySystem());

		/**
		 * Running game as a server and client is an experimental feature
		 **/
		if (Network.isServer) {
			addSystem(new ServerSystem());
		}
		addSystem(new ClientSystem());
		// Rendering happens last
		addSystem(new EntityRenderSystem());
		addSystem(new ParticleSystem());

		return this;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public void dispose() {
		Gdx.app.log(TAG, "disposing instance");
		removeAllEntities();
		clearPools();
		for (EntitySystem system : getSystems()) {
			if (system instanceof Disposable) {
				((Disposable) system).dispose();
			}
			system = null;
			removeSystem(system);
		}
	}

}
