package com.gdxjam.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public final class Vector2Utils {

	private Vector2Utils() {
	}

	public static float vectorToAngle(Vector2 vector) {
		return vector.angleRad();
	}

	public static Vector2 angleToVector(Vector2 outVector, float angle) {
		outVector.x = -(float) Math.sin(angle);
		outVector.y = (float) Math.cos(angle);
		return outVector;
	}

	/**
	 * Returns a random vector between 0 (inclusive) and max (exclusive)
	 * */
	public static Vector2 random(float maxX, float maxY) {
		return new Vector2(MathUtils.random(maxX), MathUtils.random(maxY));
	}

}
