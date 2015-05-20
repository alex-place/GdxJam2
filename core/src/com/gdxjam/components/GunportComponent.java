package com.gdxjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class GunportComponent extends Component {

	private Vector2 origin;

	private GunportComponent() {

	}

	public Component init(Vector2 origin) {
		this.origin = origin;
		return this;
	}

	public interface WeaponBehavior {

	}

}
