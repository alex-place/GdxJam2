package com.gdxjam.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Components {

	// Entity types
	public static final ComponentMapper<UnitComponent> UNIT = ComponentMapper.getFor(UnitComponent.class);
	public static final ComponentMapper<ResourceComponent> RESOURCE = ComponentMapper.getFor(ResourceComponent.class);
	public static final ComponentMapper<ProjectileComponent> PROJECTILE = ComponentMapper.getFor(ProjectileComponent.class);

	public static final ComponentMapper<PhysicsComponent> PHYSICS = ComponentMapper.getFor(PhysicsComponent.class);
	public static final ComponentMapper<FactionComponent> FACTION = ComponentMapper.getFor(FactionComponent.class);

	// AI
	public static final ComponentMapper<BehaviorTreeComponent> BTREE = ComponentMapper.getFor(BehaviorTreeComponent.class);
	public static final ComponentMapper<SteeringBehaviorComponent> STEERING_BEHAVIOR = ComponentMapper.getFor(SteeringBehaviorComponent.class);
	public static final ComponentMapper<SteerableComponent> STEERABLE = ComponentMapper.getFor(SteerableComponent.class);
	public static final ComponentMapper<FSMComponent> FSM = ComponentMapper.getFor(FSMComponent.class);

	// Graphics
	public static final ComponentMapper<SpriteComponent> SPRITE = ComponentMapper.getFor(SpriteComponent.class);
	public static final ComponentMapper<ParticleComponent> PARTICLE = ComponentMapper.getFor(ParticleComponent.class);
	public static final ComponentMapper<ParalaxComponent> PARALAX = ComponentMapper.getFor(ParalaxComponent.class);

	// Combat

	public static final ComponentMapper<DecayComponent> DECAY = ComponentMapper.getFor(DecayComponent.class);
	public static final ComponentMapper<TargetComponent> TARGET = ComponentMapper.getFor(TargetComponent.class);
	public static final ComponentMapper<HealthComponent> HEALTH = ComponentMapper.getFor(HealthComponent.class);

	// Networking
	public static final ComponentMapper<IdentifyingComponent> ID = ComponentMapper.getFor(IdentifyingComponent.class);

}
