package org.rgbridge.rgbridge;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rgbridge.rgbridge.entities.Device;
import org.rgbridge.rgbridge.entities.Effect;
import org.rgbridge.rgbridge.utils.DeviceUtils;

import java.util.ArrayList;

public class HelloController {
	@FXML
	private Label welcomeText;

	@FXML
	protected void onHelloButtonClick() {
		ArrayList<Device> devices = DeviceUtils.getDevices();

		if(devices.size() > 0) {
			Device device = devices.get(0);
			ArrayList<Effect> effects = device.getEffects();

			if(effects.size() > 0) {
				HelloApplication.deviceSocket.turnOnEffect(devices.get(0), effects.get(0));
			}
		}
	}
}