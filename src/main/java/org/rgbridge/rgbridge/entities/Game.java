package org.rgbridge.rgbridge.entities;

import java.util.ArrayList;

public class Game {
	private String name;
	private String gameId;
	private ArrayList<Event> gameEvents;

	public Game(String name, String gameId, ArrayList<Event> gameEvents) {
		this.name = name;
		this.gameId = gameId;
		this.gameEvents = gameEvents;
	}

	public String getName() {
		return name;
	}

	public String getGameId() {
		return gameId;
	}

	public ArrayList<Event> getGameEvents() {
		return gameEvents;
	}
}
