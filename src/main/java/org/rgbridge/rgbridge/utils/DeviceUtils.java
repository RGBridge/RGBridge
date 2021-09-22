package org.rgbridge.rgbridge.utils;

import org.rgbridge.rgbridge.entities.Device;

import java.util.HashMap;

public class DeviceUtils {
	private static HashMap<String, Device> devices = new HashMap<String, Device>();

	public DeviceUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void addDevice(Device device) {
		devices.put(device.getUuid(), device);
	}

	public static boolean containsDevice(Device device) {
		return devices.containsKey(device.getUuid());
	}
}
