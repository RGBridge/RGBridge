module org.rgbridge.rgbridge {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.json;
	requires fx.graph.editor.core;
	requires spark.core;
	requires org.eclipse.jetty.websocket.api;
	requires com.fasterxml.jackson.databind;
	requires org.rgbridge.commons;
	requires javafx.graphics;

	opens org.rgbridge.rgbridge to javafx.fxml;
	exports org.rgbridge.rgbridge;
	exports org.rgbridge.rgbridge.graphskins;
	opens org.rgbridge.rgbridge.graphskins to javafx.fxml;
}