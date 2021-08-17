package org.rgbridge.rgbridge.entities;

public class Event {
	private String name;
	private String id;

	public Event(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
}
