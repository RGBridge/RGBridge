package tk.thesuperlab.rgbridge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CollatzApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(CollatzApplication.class.getResource("collatz-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Collatz Conjecture Visualizer");
		stage.getIcons().add(new Image(CollatzApplication.class.getResourceAsStream("icon.png")));
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}