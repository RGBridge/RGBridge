package org.rgbridge.rgbridge.entities;

import java.util.ArrayList;

public class Device {
	private String name;
	private String uuid;
	private ArrayList<Effect> effects;

	public Device(String name, String uuid, ArrayList<Effect> effects) {
		this.name = name;
		this.uuid = uuid;
		this.effects = effects;
	}

	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}
}
