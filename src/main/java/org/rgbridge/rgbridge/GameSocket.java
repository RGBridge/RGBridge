package org.rgbridge.rgbridge;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rgbridge.rgbridge.entities.Event;
import org.rgbridge.rgbridge.entities.Game;
import org.rgbridge.rgbridge.utils.GameUtils;
import org.rgbridge.rgbridge.utils.StorageUtils;

import java.io.IOException;
import java.util.ArrayList;

@WebSocket
public class GameSocket {
	private static final ArrayList<Session> sessions = new ArrayList<Session>();

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
					JSONArray eventsArrObj = objectRaw.getJSONArray("events");
					ArrayList<Event> events = new ArrayList<Event>();

					for(int i = 0; i < eventsArrObj.length(); i++) {
						JSONObject eventObj = eventsArrObj.getJSONObject(i);

						events.add(
								new Event(
										eventObj.getString("name"),
										eventObj.getString("id")
								)
						);
					}

					Game game = new Game(
							objectRaw.getString("name"),
							objectRaw.getString("id"),
							events
					);

					if(!StorageUtils.gameExist(game.getGameId())) {
						StorageUtils.createGame(game);
					}

					if(!GameUtils.containsGame(game)) {
						GameUtils.addGame(game);
					}

					GameUtils.refreshGamesList();

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