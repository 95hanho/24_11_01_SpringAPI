package com.hoseongs.apidoc.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chatt")
@Service
public class WebSocketChatting {

	private static Set<Session> CLIENTS = Collections.synchronizedSet(new HashSet<>());
	private static Map<String, Session> SESSIONS = new HashMap<String, Session>();
	private static Map<Session, String> USERIDS = new HashMap<Session, String>();
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println(session.toString());
		if(CLIENTS.contains(session)) {
			System.out.println("이미 연결된 세션입니다." + session);
		} else {
			CLIENTS.add(session);
			System.out.println("새로운 세션입니다." + session);
		}
	}
	
	@OnClose
	public void onClose(Session session) throws Exception {
		CLIENTS.remove(session);
		if(USERIDS.containsKey(session)) {
			String prevId = USERIDS.get(session);
			SESSIONS.remove(prevId);
			USERIDS.remove(session);
			System.out.println("세션을 닫습니다." + session);
			System.out.println("남은인원1:" + SESSIONS.keySet().toString());
			System.out.println("남은인원2:" + USERIDS.values().toString());
		}
		System.out.println("세션닫기 끝.");
	}
	
	@OnMessage
	public void onMessage(String message, Session session) throws Exception {
		String[] msgSplit = message.split(":");
		String act = msgSplit[0];
		String target = msgSplit[1];
		String content = msgSplit[2];
//		System.out.println(act + "-" + target + "-" + content);
		if(act.equals("on")) {
			System.out.println("Socket on = " + content);
			SESSIONS.put(content, session);
			USERIDS.put(session, content);
		} else if(act.equals("off")) {
			System.out.println("Socket off = " + content);
			Session prevSession = SESSIONS.get(content);
			USERIDS.remove(prevSession);
			SESSIONS.remove(content);
		}
		System.out.println("현재인원1:" + SESSIONS.keySet().toString());
		System.out.println("현재인원2:" + USERIDS.values().toString());
		for(Session client : CLIENTS) {
			client.getBasicRemote().sendText(message);
		}
	}
	
}
