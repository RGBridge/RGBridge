package org.rgbridge.rgbridge.utils;

import org.rgbridge.commons.Game;

import java.util.HashMap;

public class GameUtils {
	public static HashMap<String, Game> games = new HashMap<String, Game>();

	public GameUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static HashMap<String, Game> getGames() {
		return games;
	}

	public static void addGame(Game game) {
		games.put(game.getGameId(), game);
	}

	public static boolean containsGame(Game game) {
		return games.containsKey(game.getGameId());
	}
}
