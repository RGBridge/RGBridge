package org.rgbridge.rgbridge.entities;

public class Effect {
	private String name;
	private String uuid;

	public Effect(String name, String uuid) {
		this.name = name;
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}
}
