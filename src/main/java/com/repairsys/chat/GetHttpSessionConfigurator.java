package com.repairsys.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;
import java.util.List;
import java.util.Map;

/**
 * @Author lyr
 * @create 2019/10/26 18:38
 */
public class GetHttpSessionConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        HttpSession session  = (HttpSession)request.getHttpSession();
        sec.getUserProperties().put(HttpSession.class.getName(),session);

    }
}
