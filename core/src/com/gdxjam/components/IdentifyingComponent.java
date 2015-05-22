package com.gdxjam.components;

import com.badlogic.ashley.core.Component;

public class IdentifyingComponent extends Component {
	long uuid;

	private IdentifyingComponent() {
		// TODO Auto-generated constructor stub
	}

	public IdentifyingComponent init(long uuid) {
		this.uuid = uuid;
		return this;
	}

	public long getUuid() {
		return uuid;
	}
}
