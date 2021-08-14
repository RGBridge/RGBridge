package org.rgbridge.rgbridge.utils;

import org.rgbridge.rgbridge.entities.Device;

import java.util.ArrayList;

public class DeviceUtils {
	private static ArrayList<Device> devices = new ArrayList<Device>();

	public static void addDevice(Device device) {
		devices.add(device);
	}

	public static ArrayList<Device> getDevices() {
		return devices;
	}
}
