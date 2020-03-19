package mine.learn.javawebajax.servlet;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * WSAppoint
 */
@ServerEndpoint("/ws/{user}")
public class WSAppoint {
    private static CopyOnWriteArrayList<WSAppoint> list = new CopyOnWriteArrayList<>();
    private Session session;
    private String user;

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String user) {
        this.user = user;
        this.session = session;

    }

    private void broadcast(String msg) throws IOException {
        session.getBasicRemote().sendText(msg);
    }

}