module tk.thesuperlab.rgbridge {
	requires javafx.controls;
	requires javafx.fxml;

	opens tk.thesuperlab.rgbridge to javafx.fxml;
	exports tk.thesuperlab.rgbridge;
}