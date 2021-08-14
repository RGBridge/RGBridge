package org.rgbridge.rgbridge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import spark.Spark;

import java.io.IOException;

public class HelloApplication extends Application {
	public static DeviceSocket deviceSocket;

	public static void main(String[] args) {
		deviceSocket = new DeviceSocket();

		Spark.port(32230);
		Spark.webSocket("/device", deviceSocket);
		Spark.init();

		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}
}