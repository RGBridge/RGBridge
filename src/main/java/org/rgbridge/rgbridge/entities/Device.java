package org.rgbridge.rgbridge.entities;

import org.eclipse.jetty.websocket.api.Session;
import org.rgbridge.commons.requests.RegisterDevice;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Device {
	private String name;
	private String deviceUuid;
	private Session session;

	private Map<String, Effect> effects;

	public Device() {
		this.effects = new HashMap<>();
		this.deviceUuid = UUID.randomUUID().toString();
	}

	public Device(String name, String deviceUuid, Map<String, Effect> effects) {
		this.deviceUuid = deviceUuid;
		this.effects = effects;
		this.name = name;
	}

	public Device(RegisterDevice req, Session session) {
		this.deviceUuid = req.getUuid();
		this.name = req.getName();
		this.effects = new HashMap<>();
		this.session = session;
		req.getEffects().forEach((effJson) -> {
			this.effects.put(effJson.getUuid(), new Effect(effJson));
		});
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public void setEffects(Map<String, Effect> effects) {
		this.effects = effects;
	}

	public String getUUID() {
		return this.deviceUuid;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Map<String, Effect> getEffects() {
		return effects;
	}
}
