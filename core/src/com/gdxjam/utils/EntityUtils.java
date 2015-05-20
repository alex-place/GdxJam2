package com.gdxjam.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.gdxjam.components.Components;
import com.gdxjam.components.FactionComponent.Faction;

public class EntityUtils {

	private static final String TAG = "[" + EntityUtils.class.getSimpleName() + "]";
	private static PooledEngine engine;

	public static void setEngine(PooledEngine engine) {
		EntityUtils.engine = engine;
	}

	/**
	 * Checks to see if two entities are of the same faction
	 * 
	 * @param entityA
	 * @param entityB
	 * @return true if they are the same faction
	 */
	public static boolean isSameFaction(Entity entityA, Entity entityB) {
		if (!Components.FACTION.has(entityA) || !Components.FACTION.has(entityB)) {
			Gdx.app.error(TAG, "entity faction comparision is missing faction component");
			return false;
		}

		Faction factionA = Components.FACTION.get(entityA).getFaction();
		Faction factionB = Components.FACTION.get(entityB).getFaction();

		return factionA == factionB;
	}

	public static void removeEntity(Entity entity) {
		engine.removeEntity(entity);
	}

}
