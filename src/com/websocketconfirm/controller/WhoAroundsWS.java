package com.websocketconfirm.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.websocketchat.model.State;
import com.websocketconfirm.model.ComfirmState;
import com.websocketconfirm.model.MyLocationVO;
import com.websocketconfirm.model.NearbyState;

@ServerEndpoint("/WhoAroundsWS/{userName}/{Lat}/{Lng}")
public class WhoAroundsWS {
	private static Map<MyLocationVO, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, @PathParam("Lat") Double Lat,
			@PathParam("Lng") Double Lng, Session userSession) throws IOException {
		MyLocationVO userLoc = new MyLocationVO();
		userLoc.setMemberId(userName);
		userLoc.setLat(Lat);
		userLoc.setLng(Lng);

		Set<MyLocationVO> locations = sessionsMap.keySet();

		//通知大家有誰在線上
		for(Session sess:sessionsMap.values()) {
			sess.getAsyncRemote().sendText(gson.toJson(locations));
		}
		// 回傳給自己有誰在線上
		userSession.getAsyncRemote().sendText(gson.toJson(locations));

		sessionsMap.put(userLoc, userSession);

		String text = String.format("Session ID = %s, connected; userName = %s; Lat = %s; Lng = %s",
				userSession.getId(), userName, Lat, Lng);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
//		ComfirmState comfirmState = gson.fromJson(message, ComfirmState.class);
//		String receiver = comfirmState.getReceiver();
//		System.out.println(receiver);
//		
//		Set<MyLocationVO> locations = sessionsMap.keySet();
//		MyLocationVO receiverLocation = null;
//		for(MyLocationVO lo : locations) {
//			if(receiver.equals(lo.getMemberId())) {
//				receiverLocation = lo;
//			}
//		}
//		Session receiverSession = sessionsMap.get(receiverLocation);
//		System.out.println(receiverSession);
//		if (receiverSession != null && receiverSession.isOpen()) {
//			receiverSession.getAsyncRemote().sendText(message);
//
//		}
		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<MyLocationVO> userNames = sessionsMap.keySet();

		for (MyLocationVO userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				sessionsMap.remove(userName);
				break;
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}
}
