package com.gdxjam.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gdxjam.Assets;
import com.gdxjam.behaviors.control.CorvetteControlBehavior;
import com.gdxjam.behaviors.control.CruiserControlBehavior;
import com.gdxjam.behaviors.control.FighterControlBehavior;
import com.gdxjam.components.Components;
import com.gdxjam.components.ControlComponent;
import com.gdxjam.components.DecayComponent;
import com.gdxjam.components.FSMComponent;
import com.gdxjam.components.FactionComponent;
import com.gdxjam.components.FactionComponent.Faction;
import com.gdxjam.components.GunportComponent;
import com.gdxjam.components.HealthComponent;
import com.gdxjam.components.IdentifyingComponent;
//import com.gdxjam.components.IdentifyingComponent;
import com.gdxjam.components.ParalaxComponent;
import com.gdxjam.components.ParticleComponent;
import com.gdxjam.components.PhysicsComponent;
import com.gdxjam.components.ProjectileComponent;
import com.gdxjam.components.ResourceComponent;
import com.gdxjam.components.SpriteComponent;
import com.gdxjam.components.SteerableComponent;
import com.gdxjam.components.SteeringBehaviorComponent;
import com.gdxjam.components.TargetComponent;
import com.gdxjam.systems.ParticleSystem;
import com.gdxjam.systems.ParticleSystem.ParticleType;
import com.gdxjam.systems.PhysicsSystem;

/** @author Torin Wiebelt (Twiebs) Creates Ashley entities using a builder */

public class EntityFactory {

	private static final String TAG = "[" + EntityFactory.class.getSimpleName() + "]";

	private static PooledEngine engine;
	private static PhysicsSystem physicsSystem;
	private static EntityBuilder builder = new EntityBuilder();

	private static PhysicsBuilder physicsBuilder = new PhysicsBuilder();

	public static Entity createShip(Faction faction, Vector2 position) {
		Entity entity = builder.createEntity(EntityCategory.UNIT, position).physicsBody(BodyType.DynamicBody).circleCollider(Constants.unitRadius, 1.0f)
				.damping(1.5f, 1.0f).steerable(Constants.unitRadius).steeringBehavior().health(100).faction(faction).target()
				.sprite(Assets.spacecraft.ships.get(faction.ordinal()), Constants.unitRadius * 2, Constants.unitRadius * 2).getWithoutAdding();

		PhysicsComponent physicsComp = Components.PHYSICS.get(entity);

		Components.STEERABLE.get(entity).setIndependentFacing(true);
		return entity;

	}

	public static Entity createPlayer(Faction faction, Vector2 position, long count) {
		Entity entity = EntityFactory.createShip(faction, position);
		builder.entity = entity;
		builder.setCollider(faction);
		builder.control(faction).uuid(count).addToEngine();
		return entity;

	}

	public static Entity createGun(Vector2 position) {
		Entity entity = builder.createEntity(EntityCategory.GUN, position).physicsBody(BodyType.DynamicBody).circleCollider(Constants.unitRadius, 0.5f)
				.getWithoutAdding();
		return entity;
	}

	public static Entity createProjectile(Vector2 position, Vector2 velocity, float radius, Faction faction, int damage) {
		Entity entity = builder.createEntity(EntityCategory.PROJECTILE, position).physicsBody(BodyType.DynamicBody).circleSensor(radius)
				.filter(EntityCategory.PROJECTILE, 0, EntityCategory.UNIT | EntityCategory.RESOURCE | EntityCategory.MOTHERSHIP).faction(faction)
				.sprite(Assets.projectile.projectiles.get(faction.ordinal()), radius * 2, radius * 2).getWithoutAdding();

		ProjectileComponent projectileComp = engine.createComponent(ProjectileComponent.class).init(damage);
		entity.add(projectileComp);

		entity.add(engine.createComponent(DecayComponent.class).init(Constants.projectileDecayTime));

		PhysicsComponent physicsComp = Components.PHYSICS.get(entity);
		physicsComp.getBody().setBullet(true);
		physicsComp.getBody().setLinearVelocity(velocity);
		physicsComp.getBody().setTransform(position, velocity.angle());

		engine.addEntity(entity);
		return entity;
	}

	public static Entity createAsteroid(Vector2 position, float radius) {
		Entity entity = builder.createEntity(EntityCategory.RESOURCE, position).physicsBody(BodyType.StaticBody).circleCollider(radius, 50.0f)
				.filter(EntityCategory.RESOURCE, 0, EntityCategory.PROJECTILE | EntityCategory.SQUAD | EntityCategory.UNIT)
				.resource((int) (Constants.baseAsteroidResourceAmt * radius)).steerable(radius).faction(Faction.NONE)
				.sprite(Assets.space.asteroids.random(), radius * 2, radius * 2).addToEngine();
		return entity;
	}

	public static Entity createParticle(Vector2 position, ParticleType type) {
		Entity entity = builder.createEntity(EntityCategory.GRAPHICS, position).particle(type).addToEngine();
		return entity;
	}

	public static Entity createBoundry(Vector2 start, Vector2 end) {
		Entity entity = builder.createEntity(EntityCategory.WALL, new Vector2(0, 0)).physicsBody(BodyType.StaticBody).getWithoutAdding();

		FixtureDef def = new FixtureDef();
		EdgeShape edge = new EdgeShape();
		edge.set(start, end);
		def.shape = edge;
		def.filter.categoryBits = EntityCategory.WALL;

		Components.PHYSICS.get(entity).getBody().createFixture(edge, 1.0f);
		engine.addEntity(entity);
		return entity;
	}

	public static Entity createBackgroundArt(Vector2 position, float width, float height, TextureRegion region, int layer) {
		Entity entity = builder.createEntity(EntityCategory.GRAPHICS, position).sprite(region, width, height).getWithoutAdding();

		entity.add(engine.createComponent(ParalaxComponent.class).init(position.x, position.y, width, height, layer));

		engine.addEntity(entity);
		return entity;
	}

	/**
	 * Called when the GameManager first initializes a new engine
	 * 
	 * @param engine
	 *              The engine that the factory will use to create its
	 *              entities
	 */
	public static void setEngine(PooledEngine engine) {
		EntityFactory.engine = engine;
		physicsSystem = engine.getSystem(PhysicsSystem.class);
	}

	/**
	 * Creates an entity from the engine when first instantiated Exit builder
	 * by calling addToEngine() or getWithoutAdding()
	 */

	public static class EntityBuilder {
		private static final BodyType DEFAULT_BODY = BodyType.DynamicBody;

		public Vector2 position;
		public Entity entity;

		public EntityBuilder createEntity(int categoryBits, Vector2 position) {
			entity = engine.createEntity();
			entity.flags = categoryBits;

			this.position = position;

			return this;
		}

		public EntityBuilder control(Faction faction) {
			control(30, faction);
			return this;
		}

		public EntityBuilder control(float radius, Faction faction) {
			if (faction == Faction.FACTION0) {
				entity.add(engine.createComponent(ControlComponent.class).init(new FighterControlBehavior(entity, engine, radius)));
			}
			if (faction == Faction.FACTION1) {
				entity.add(engine.createComponent(ControlComponent.class).init(new CruiserControlBehavior(entity, engine, radius)));
			}
			if (faction == Faction.FACTION2) {
				entity.add(engine.createComponent(ControlComponent.class).init(new CorvetteControlBehavior(entity, engine, radius)));
			}
			return this;
		}

		public EntityBuilder particle(ParticleType type) {
			PooledEffect effect = engine.getSystem(ParticleSystem.class).createEffect(position, type);
			entity.add(engine.createComponent(ParticleComponent.class).init(effect));
			return this;
		}

		public PhysicsBuilder buildPhysics(BodyType type) {
			return physicsBuilder.reset(type, position, entity);
		}

		public EntityBuilder physicsBody(BodyType type) {
			BodyDef def = new BodyDef();
			def.type = type;
			def.position.set(position);

			Body body = physicsSystem.createBody(def);
			body.setUserData(entity);

			PhysicsComponent physics = engine.createComponent(PhysicsComponent.class).init(body);
			entity.add(physics);
			return this;
		}

		public EntityBuilder damping(float angular, float linear) {
			if (Components.PHYSICS.has(entity)) {
				PhysicsComponent physics = Components.PHYSICS.get(entity);
				physics.getBody().setAngularDamping(angular);
				physics.getBody().setLinearDamping(linear);
			} else {
				Gdx.app.error(TAG, "entity is missing physics component!");
			}
			return this;
		}

		public EntityBuilder stateMachine() {
			FSMComponent stateMachineComp = engine.createComponent(FSMComponent.class).init(entity);
			entity.add(stateMachineComp);
			return this;
		}

		public EntityBuilder steeringBehavior() {
			SteeringBehaviorComponent behaviorComp = engine.createComponent(SteeringBehaviorComponent.class);
			entity.add(behaviorComp);
			return this;
		}

		public EntityBuilder resource(int amount) {
			ResourceComponent resourceComp = engine.createComponent(ResourceComponent.class).init(amount);
			entity.add(resourceComp);

			return this;
		}

		public EntityBuilder category(int categoryBits) {
			entity.flags = categoryBits;

			return this;
		}

		public EntityBuilder filter(int categoryBits, int groupIndex, int maskBits) {
			entity.flags = categoryBits;

			Filter filter = new Filter();
			filter.categoryBits = (short) categoryBits;
			filter.groupIndex = (short) groupIndex;
			filter.maskBits = (short) maskBits;

			// TODO make EntityBuilder filter beter
			Components.PHYSICS.get(entity).getBody().getFixtureList().get(0).setFilterData(filter);
			return this;
		}

		public EntityBuilder target() {
			entity.add(engine.createComponent(TargetComponent.class));
			return this;
		}

		public EntityBuilder gunport(Vector2 origin) {
			entity.add(engine.createComponent(GunportComponent.class).init(origin));
			return this;
		}

		public EntityBuilder faction(Faction faction) {
			entity.add(engine.createComponent(FactionComponent.class).init(faction));
			return this;
		}

		public EntityBuilder steerable(float radius) {
			PhysicsComponent physics = Components.PHYSICS.get(entity);
			if (physics == null) {
				Gdx.app.error(TAG, "cannot create a steerable without physics!");
				return this;
			}
			SteerableComponent steerable = engine.createComponent(SteerableComponent.class).init(physics.getBody(), radius);
			entity.add(steerable);
			return this;
		}
		
		public EntityBuilder setCollider(Faction faction){
			if(faction == Faction.FACTION0){
				return circleCollider(1, 1);
			}
			if(faction == Faction.FACTION1 || faction == Faction.FACTION2){
				return boxCollider(1, 1, 1);
			}
			else{return null;}
		}

		public EntityBuilder circleCollider(float radius, float density) {
			CircleShape shape = new CircleShape();
			shape.setRadius(radius);
			PhysicsComponent physics = Components.PHYSICS.get(entity);
			if (physics == null) {
				physicsBody(DEFAULT_BODY);
			}

			FixtureDef def = new FixtureDef();
			def.shape = shape;
			def.density = density;
			def.isSensor = true;

			physics.getBody().createFixture(def);

			shape = new CircleShape();
			shape.setRadius(radius - 2);
			shape.setPosition(new Vector2(2, 0));

			FixtureDef fixture = new FixtureDef();
			fixture.shape = shape;
			fixture.density = density;
			fixture.isSensor = true;

			physics.getBody().createFixture(shape, density);

			return this;
		}

		// Working on this not done yet but had to go - Nate
		public EntityBuilder polygonCollider(Vector2[] points, float density) {
			PolygonShape shape = new PolygonShape(); //
			shape.set(points);
			PhysicsComponent physics = Components.PHYSICS.get(entity);
			if (physics == null) {
				physicsBody(DEFAULT_BODY);
			}

			FixtureDef def = new FixtureDef();
			def.shape = shape;
			def.density = density;
			def.isSensor = true;

			physics.getBody().createFixture(def);

			shape = new PolygonShape();
			shape.set(points);

			FixtureDef fixture = new FixtureDef();
			fixture.shape = shape;
			fixture.density = density;
			fixture.isSensor = true;

			physics.getBody().createFixture(shape, density);

			return this;
		}

		public EntityBuilder boxCollider(float height, float width, float density) {
			PolygonShape shape = new PolygonShape(); //
			shape.setAsBox(height, width);

			PhysicsComponent physics = Components.PHYSICS.get(entity);
			if (physics == null) {
				physicsBody(DEFAULT_BODY);
			}

			FixtureDef def = new FixtureDef();
			def.shape = shape;
			def.density = density;
			def.isSensor = true;

			physics.getBody().createFixture(def);

			shape = new PolygonShape();
			shape.setAsBox(height, width);

			FixtureDef fixture = new FixtureDef();
			fixture.shape = shape;
			fixture.density = density;
			fixture.isSensor = true;

			physics.getBody().createFixture(shape, density);

			return this;
		}

		public EntityBuilder circleSensor(float radius) {
			CircleShape shape = new CircleShape();
			shape.setRadius(radius);

			PhysicsComponent physics = Components.PHYSICS.get(entity);
			if (physics == null) {
				physicsBody(DEFAULT_BODY);
			}

			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.isSensor = true;
			fixtureDef.shape = shape;

			physics.getBody().createFixture(fixtureDef);
			return this;
		}

		public EntityBuilder rangeSensor(float range, float arc) {
			Body body;
			if (Components.PHYSICS.has(entity)) {
				body = Components.PHYSICS.get(entity).getBody();
			} else {
				Gdx.app.error(TAG, "can not add range sensor : entity does not have a physics component!");
				return this;
			}

			Vector2 vertices[] = new Vector2[8];

			for (int i = 0; i <= 7; i++) {
				vertices[i] = new Vector2(0, 0);
			}

			for (int i = 0; i < 7; i++) {
				float angle = (i / 6.0f * arc * MathUtils.degRad) - (90 * MathUtils.degRad);
				vertices[i + 1].set(range * MathUtils.cos(angle), range * MathUtils.sin(angle));
			}

			PolygonShape poly = new PolygonShape();
			poly.set(vertices);

			FixtureDef sensorDef = new FixtureDef();
			sensorDef.shape = poly;
			sensorDef.isSensor = true;
			body.createFixture(sensorDef);
			poly.dispose();
			return this;
		}

		public EntityBuilder health(int value) {
			HealthComponent health = engine.createComponent(HealthComponent.class);
			health.max = value;
			health.value = value;
			entity.add(health);
			return this;
		}

		public EntityBuilder sprite(TextureRegion region, float width, float height) {
			SpriteComponent spriteComp = engine.createComponent(SpriteComponent.class).init(region, position.x, position.y, width, height);
			entity.add(spriteComp);
			return this;
		}

		public EntityBuilder uuid(long uuid) {
			entity.add(engine.createComponent(IdentifyingComponent.class).init(uuid));
			return this;
		}

		public Entity addToEngine() {
			engine.addEntity(entity);
			return entity;
		}

		public Entity getWithoutAdding() {
			return entity;
		}

	}

	public static class PhysicsBuilder {
		private Body body;

		public PhysicsBuilder reset(BodyType type, Vector2 position, Entity entity) {
			BodyDef def = new BodyDef();
			def.type = type;
			def.position.set(position);
			body = physicsSystem.createBody(def);
			body.setUserData(entity);
			return this;
		}

		public EntityBuilder getBody() {
			return builder;
		}

		//
	}

}
