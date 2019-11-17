package com.repairsys.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * @Author lyr
 * @create 2019/10/26 18:38
 */
public final class GetHttpSessionConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        HttpSession session = (HttpSession) request.getHttpSession();
        if(session!=null)
        {

            sec.getUserProperties().put(HttpSession.class.getName(), session);
        }

    }
}
