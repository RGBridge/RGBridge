package org.rgbridge.rgbridge.utils;

import org.rgbridge.rgbridge.entities.Game;

import java.util.HashMap;

import static org.rgbridge.rgbridge.HelloApplication.helloController;

public class GameUtils {
	private static HashMap<String, Game> games = new HashMap<String, Game>();

	public GameUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void addGame(Game game) {
		games.put(game.getGameId(), game);
	}

	public static void refreshGamesList() {
		helloController.clearTree();

		for(String gameId : games.keySet()) {
			helloController.addGame(games.get(gameId));
		}
	}

	public static boolean containsGame(Game game) {
		return games.containsKey(game.getGameId());
	}
}
