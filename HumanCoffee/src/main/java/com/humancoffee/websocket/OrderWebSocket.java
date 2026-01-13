package com.humancoffee.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; // ObjectMapper 클래스 임포트 추가
import com.humancoffee.model.*;

@ServerEndpoint("/order-websocket")
public class OrderWebSocket {
	// 이 줄을 추가하여 ObjectMapper 객체를 선언하고 초기화합니다.
    private static final ObjectMapper mapper = new ObjectMapper();
    
	// 모든 클라이언트 세션을 저장하는 스레드 안전한 Set
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    // 클라이언트가 연결되면 호출
    @OnOpen
    public void onOpen(Session session) {
    	
        System.out.println("WebSocket opened: " + session.getId());
        clients.add(session); // 새로운 클라이언트 세션 추가
    }

    // 클라이언트로부터 메시지를 받으면 호출
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from " + session.getId() + ": " + message);
        // 클라이언트에서 받은 메시지 처리 로직 (예: 서버에 주문 요청)
    }

    // 클라이언트 연결이 끊기면 호출
    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket closed: " + session.getId());
        clients.remove(session); // 세션 제거
    }

    // 통신 중 에러가 발생하면 호출
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error on " + session.getId());
        throwable.printStackTrace();
    }

    // 서버에서 모든 연결된 클라이언트에게 메시지 전송
//    public static void broadcast(String message) {
    public static void broadcast(List<OrderHead> T) throws JsonProcessingException {
    	// List<OrderHead> 객체를 JSON 문자열로 직렬화
		String jsonMessage = mapper.writeValueAsString(T);
		System.out.println("1-broadcast:" + jsonMessage);
		synchronized (clients) {
		    for (Session client : clients) {
		        try {
		        	System.out.println("2-broadcast:" + client.getBasicRemote() + ":" + jsonMessage);
		            client.getBasicRemote().sendText(jsonMessage); // 텍스트로 전송
		        } catch (IOException e) {
		            System.err.println("Failed to send message to client: " + client.getId());
		            clients.remove(client);
		            try {
		                client.close();
		            } catch (IOException ex) {
		                // ignore
		            }
		        }
		    }
		}

    }
}
