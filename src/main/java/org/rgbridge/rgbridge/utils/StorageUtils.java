package org.rgbridge.rgbridge.utils;

import org.json.JSONObject;
import org.rgbridge.rgbridge.entities.Event;
import org.rgbridge.rgbridge.entities.Game;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class StorageUtils {
	private static File storageRoot() {
		return new File(System.getenv("APPDATA"));
	}

	public static void initialiseStorage() {
		try {
			if(!Files.exists(Path.of(storageRoot().getAbsolutePath() + "/RGBridge"))) {
				// Create root
				Files.createDirectory(Path.of(storageRoot().getAbsolutePath() + "/RGBridge"));

				// Create subfolders
				Files.createDirectory(Path.of(storageRoot().getAbsolutePath() + "/RGBridge/games"));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean gameExist(String gameId) {
		File gameFile = new File(storageRoot() + "/RGBridge/games/" + gameId + ".json");

		return gameFile.exists() && !gameFile.isDirectory();
	}

	public static boolean createGame(Game game) {
		try {
			File gameFile = new File(storageRoot() + "/RGBridge/games/" + game.getGameId() + ".json");
			JSONObject gameObj = new JSONObject();

			if(gameFile.createNewFile()) {
				gameObj.put("name", game.getName());
				gameObj.put("id", game.getGameId());

				PrintWriter pw = new PrintWriter(gameFile);
				pw.write(gameObj.toString());
				pw.flush();

				return true;
			} else {
				return false;
			}

		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Game> getAllGames() {
		File gameFolder = new File(storageRoot() + "/RGBridge/games/");
		ArrayList<Game> toReturn = new ArrayList<Game>();

		for(File gameFile : Objects.requireNonNull(gameFolder.listFiles())) {
			try {
				Scanner myReader = new Scanner(gameFile);
				StringBuilder gameFileRaw = new StringBuilder();

				while(myReader.hasNextLine()) {
					gameFileRaw.append(myReader.nextLine());
				}
				myReader.close();

				JSONObject gameObj = new JSONObject(gameFileRaw.toString());
				toReturn.add(
						new Game(
								gameObj.getString("name"),
								gameObj.getString("id"),
								new ArrayList<Event>()
						)
				);
			} catch(Exception e) {
				e.printStackTrace();
				return toReturn;
			}
		}

		return toReturn;
	}
}
