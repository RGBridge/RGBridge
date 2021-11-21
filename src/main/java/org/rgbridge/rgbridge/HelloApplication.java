package org.rgbridge.rgbridge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.rgbridge.commons.Game;
import org.rgbridge.rgbridge.entities.Device;
import org.rgbridge.rgbridge.utils.DeviceUtils;
import org.rgbridge.rgbridge.utils.GameUtils;
import org.rgbridge.rgbridge.utils.StorageUtils;
import spark.Spark;

import java.io.IOException;

public class HelloApplication extends Application {
	public static DeviceSocket deviceSocket;
	public static GameSocket gameSocket;

	public static HelloController helloController;

	public static void main(String[] args) {
		deviceSocket = new DeviceSocket();
		gameSocket = new GameSocket();

		Spark.port(32230);
		Spark.webSocket("/device", deviceSocket);
		Spark.webSocket("/game", gameSocket);
		Spark.init();

		StorageUtils.initialiseStorage();
		launch();
	}

	public static void loadSavedDevices() {
		for(Device device : StorageUtils.getAllDevices()) {
			DeviceUtils.addDevice(device);
		}

		helloController.refreshTree();
	}

	public static void loadSavedGames() {
		for(Game game : StorageUtils.getAllGames()) {
			GameUtils.addGame(game);
		}

		helloController.refreshTree();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		scene.getStylesheets().add(getClass().getResource("titledskins.css").toExternalForm());

		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();

		helloController = fxmlLoader.getController();
		/*loadSavedDevices();
		loadSavedGames();*/
	}
}