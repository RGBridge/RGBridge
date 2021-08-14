package org.rgbridge.rgbridge;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rgbridge.rgbridge.entities.Device;
import org.rgbridge.rgbridge.entities.Effect;
import org.rgbridge.rgbridge.utils.DeviceUtils;

import java.io.IOException;
import java.util.ArrayList;

@WebSocket
public class DeviceSocket {
	private static final ArrayList<Session> sessions = new ArrayList<Session>();

	public void turnOnEffect(Device device, Effect effect) {
		for(Session session : sessions) {
			try {
				JSONObject errorObj = new JSONObject();
				errorObj.put("method", "EFFON");
				errorObj.put("device", device.getUuid());
				errorObj.put("effect", effect.getUuid());

				session.getRemote().sendString(errorObj.toString());
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void turnOnEffect(String message) {
		for(Session session : sessions) {
			try {
				session.getRemote().sendString(message);
			} catch(IOException e) {
				e.printStackTrace();
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
										effectObj.getString("uuid")
								)
						);
					}

					DeviceUtils.addDevice(
							new Device(
									deviceObj.getString("name"),
									deviceObj.getString("uuid"),
									deviceEffects
							)
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
}