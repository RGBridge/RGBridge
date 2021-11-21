package org.rgbridge.rgbridge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rgbridge.commons.Methods;
import org.rgbridge.commons.effect.EffectParameterDefinition;
import org.rgbridge.commons.effect.TrigerEffectJson;
import org.rgbridge.commons.requests.BasicRequest;
import org.rgbridge.commons.requests.RegisterDevice;
import org.rgbridge.rgbridge.entities.Device;
import org.rgbridge.rgbridge.entities.Effect;
import org.rgbridge.rgbridge.utils.DeviceUtils;
import org.rgbridge.rgbridge.utils.StorageUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.rgbridge.rgbridge.HelloApplication.helloController;

@WebSocket
public class DeviceSocket {
	private static final ArrayList<Session> sessions = new ArrayList<Session>();
	private static final ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.registerSubtypes(RegisterDevice.class);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void turnOnEffect(Device device, Effect effect, HashMap<String, Object> params) {
		parameterCheck(effect, params);

		try {
			TrigerEffectJson obj = new TrigerEffectJson(device.getDeviceUuid(), effect.getName(), effect.getUuid(), params);
			String json = mapper.writeValueAsString(obj);
			device.getSession().getRemote().sendString(json);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void parameterCheck(Effect effect, HashMap<String, Object> params) {
		if(effect == null) {
			System.err.println("Effect is null!!");
		}
		for(EffectParameterDefinition effectParameterDefinition : effect.getParams()) {
			Object param = params.get(effectParameterDefinition.getName());

			if(param == null) {
				System.err.println("Param with name " + effectParameterDefinition.getName() + " is null.");
			} else {
				System.err.println(param.getClass().getName());
				if(!param.getClass().getName().equals(effectParameterDefinition.getType().getName())) {
					System.err.println("Param types do not match " + effectParameterDefinition.getType().getName() + "  " + param.getClass().getName());
				}
			}
		}
	}

	@OnWebSocketConnect
	public void connected(Session session) {
		sessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		sessions.remove(session);
	}

	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println(message);
		Methods method = null;
		try {
			BasicRequest request = mapper.readValue(message, BasicRequest.class);
			System.out.println(request.getMethod());
			System.out.println("req.toString()");
			System.out.println(request);
			method = request.getMethod();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}

		switch(method) {
			case REGISTER_DEVICE:
				registerDevice(mapper.readValue(message, RegisterDevice.class), session);
				break;
		}

		try {
			JSONObject objectRaw = new JSONObject(message);

			switch(objectRaw.getString("method")) {
				case "REG":
					JSONObject deviceObj = objectRaw.getJSONObject("device");
					JSONArray effectJsonArray = deviceObj.getJSONArray("effects");

					ArrayList<Effect> deviceEffects = new ArrayList<Effect>();

					for(int i = 0; i < effectJsonArray.length(); i++) {
						JSONObject effectObj = effectJsonArray.getJSONObject(i);

						deviceEffects.add(
								new Effect(
										effectObj.getString("name"),
										effectObj.getString("uuid"),
										null
								)
						);
					}

					Device device = new Device(
							deviceObj.getString("name"),
							deviceObj.getString("uuid"),
							null
					);


					break;
			}
		} catch(JSONException e) {
			JSONObject errorObj = new JSONObject();
			errorObj.put("status", 400);
			errorObj.put("message", "Bad request");

			session.getRemote().sendString(errorObj.toString());
		}
	}

	private void registerDevice(RegisterDevice req, Session session) {
		System.out.println(req.toString());
		Device device = new Device(req, session);
		if(!StorageUtils.deviceExtists(device.getDeviceUuid())) {
			StorageUtils.createDevice(device);
		}

		if(!DeviceUtils.containsDevice(device)) {
			DeviceUtils.addDevice(device);
		}

		helloController.refreshTree();
		HashMap<String, Object> params = new HashMap<>();
		params.put("red", Integer.valueOf(10));
		turnOnEffect(device, device.getEffects().get("thsa,rthmaropijhzčew59u0šjmw94"), params);
	}
}