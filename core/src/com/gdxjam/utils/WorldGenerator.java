package com.gdxjam.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.Assets;
import com.gdxjam.components.FactionComponent.Faction;

/**
 * Generates world bounds
 * 
 * @author Torin Wiebelt (Twiebs)
 * @author alex-place
 */

public class WorldGenerator {

	private int width;
	private int height;

	public WorldGenerator(int width, int height, long seed) {
		this.width = width;
		this.height = height + 1; // Plus one hides missing band at the top
							// of
		// the world
	}

	public void generate() {
		createBackground();
		createWorldBounds();
	}

	public Entity createUnit(Faction faction, Vector2 position) {
		return EntityFactory.createUnit(faction, position);
	}

	public void createWorldBounds() {
		EntityFactory.createBoundry(new Vector2(0, 0), new Vector2(0, height));
		EntityFactory.createBoundry(new Vector2(0, height), new Vector2(width, height));
		EntityFactory.createBoundry(new Vector2(width, height), new Vector2(width, 0));
		EntityFactory.createBoundry(new Vector2(width, 0), new Vector2(0, 0));

	}

	public void createBackground() {
		EntityFactory.createBackgroundArt(new Vector2(0, 0), Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, Assets.space.background, 0);

		// Make this random
		int planetCount = 4;
		float planetRadius;
		for (int i = 0; i < planetCount; i++) {
			planetRadius = MathUtils.random(1, 10);
			int index = (int) (MathUtils.random(Assets.space.planets.size));
			if (index == Assets.space.planets.size)
				index--;
			EntityFactory.createBackgroundArt(new Vector2(Constants.VIEWPORT_WIDTH * MathUtils.random(), Constants.VIEWPORT_HEIGHT * MathUtils.random()),
					planetRadius, planetRadius, Assets.space.planets.get(index), 1);
		}
	}
}
