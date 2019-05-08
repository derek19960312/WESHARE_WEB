package com.websocketconfirm.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.coursereservation.model.CourseReservationService;
import com.coursereservation.model.CourseReservationVO;
import com.google.gson.Gson;
import com.websocketchat.model.ChatMessage;
import com.websocketchat.model.State;



@ServerEndpoint("/ConfirmCourseWS/{userName}")
public class ConfirmCourseWS {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		sessionsMap.put(userName, userSession);
		String text = String.format("Session ID = %s, connected; userName = %s", userSession.getId(), userName);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		CourseReservationVO crVO = gson.fromJson(message, CourseReservationVO.class);
		
		String teacherId = valueGetKey(userSession);
		Session studentSess = sessionsMap.get(crVO.getMemId());
		
		if(teacherId.equals(crVO.getTeacherId())) {
			CourseReservationService crvSvc = new CourseReservationService();
			crvSvc.ConfirmCourse(crVO.getCrvId());
			userSession.getAsyncRemote().sendText("success");
			studentSess.getAsyncRemote().sendText("success");
		}else {
			userSession.getAsyncRemote().sendText("fail");
			studentSess.getAsyncRemote().sendText("fail");
		
		}
		

		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				userNameClose = userName;
				sessionsMap.remove(userName);
				break;
			}
		}

		if (userNameClose != null) {
			State stateMessage = new State("close", userNameClose, userNames);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}
	
	
	 private String valueGetKey(Session userSession) {
		    Set set = sessionsMap.entrySet();
		    Iterator it = set.iterator();
		    while(it.hasNext()) {
		      Map.Entry entry = (Map.Entry)it.next();
		      if(entry.getValue().equals(userSession)) {
		        String s = (String)entry.getKey();
		        return s;
		      }
		    }
		    return "";
	 }
}