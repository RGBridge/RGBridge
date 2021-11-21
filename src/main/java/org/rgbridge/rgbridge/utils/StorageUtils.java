package org.rgbridge.rgbridge.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.rgbridge.commons.Event;
import org.rgbridge.commons.Game;
import org.rgbridge.rgbridge.entities.Device;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class StorageUtils {
	private static final ObjectMapper om;

	static {
		om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public StorageUtils() {
		throw new IllegalStateException("Utility class");
	}

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
				Files.createDirectory(Path.of(storageRoot().getAbsolutePath() + "/RGBridge/devices"));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// GAMES
	public static boolean gameExists(String gameId) {
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

	// Devices
	public static boolean deviceExtists(String deviceId) {
		File deviceFile = new File(storageRoot() + "/RGBridge/devices/" + deviceId + ".json");

		return deviceFile.exists() && !deviceFile.isDirectory();
	}

	public static boolean createDevice(Device device) {
		try {
			File deviceFile = new File(storageRoot() + "/RGBridge/devices/" + device.getDeviceUuid() + ".json");
			JSONObject deviceObj = new JSONObject();

			if(deviceFile.createNewFile()) {
				deviceObj.put("name", device.getName());
				deviceObj.put("id", device.getDeviceUuid());

				PrintWriter pw = new PrintWriter(deviceFile);
				pw.write(deviceObj.toString());
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

	public static ArrayList<Device> getAllDevices() {
		File deviceFolder = new File(storageRoot() + "/RGBridge/devices/");
		ArrayList<Device> toReturn = new ArrayList<Device>();

		for(File deviceFile : Objects.requireNonNull(deviceFolder.listFiles())) {
			try {
				Scanner myReader = new Scanner(deviceFile);
				StringBuilder deviceFileRaw = new StringBuilder();

				while(myReader.hasNextLine()) {
					deviceFileRaw.append(myReader.nextLine());
				}

				myReader.close();

				JSONObject deviceObj = new JSONObject(deviceFileRaw.toString());
				toReturn.add(
						new Device(
								deviceObj.getString("name"),
								deviceObj.getString("id"),
								new HashMap<>()
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
