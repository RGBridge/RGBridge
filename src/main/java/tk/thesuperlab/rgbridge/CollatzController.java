package tk.thesuperlab.rgbridge;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CollatzController {
	@FXML
	private LineChart<Number, Number> collatzChart;
	@FXML
	private TextField testSubject;
	@FXML
	private ListView calculations;
	@FXML
	private Label calculationDetails;
}