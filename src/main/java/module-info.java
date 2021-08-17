module org.rgbridge.rgbridge {
	requires javafx.controls;
	requires javafx.fxml;
	requires spark.core;
	requires org.eclipse.jetty.websocket.api;
	requires org.json;
	requires fx.graph.editor.core;
	requires org.slf4j;


	opens org.rgbridge.rgbridge to javafx.fxml;
	exports org.rgbridge.rgbridge;
}