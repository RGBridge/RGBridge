package org.rgbridge.rgbridge.utils;

import org.rgbridge.rgbridge.entities.Device;

import java.util.HashMap;

public class DeviceUtils {
	public static HashMap<String, Device> devices = new HashMap<String, Device>();

	public DeviceUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static HashMap<String, Device> getDevices() {
		return devices;
	}

	public static void addDevice(Device device) {
		devices.put(device.getDeviceUuid(), device);
	}

	public static boolean containsDevice(Device device) {
		return devices.containsKey(device.getDeviceUuid());
	}
}
