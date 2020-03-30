package top.mine.website.service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/chat/{usrAlias}")
public class ChatWsService {
    private String usrAlias;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    // private static CopyOnWriteArraySet<ChatWsService> webSocketSet = new
    // CopyOnWriteArraySet<ChatWsService>();
    private static ConcurrentHashMap<ChatWsService, Byte> webSocketSet = new ConcurrentHashMap<>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    public ChatWsService() {
        System.out.println("-----------------------------");
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("usrAlias") String usrAlias) {
        this.usrAlias = usrAlias;
        this.session = session;
        webSocketSet.put(this, (byte) 0); // 加入set中
        JSONObject data = new JSONObject();
        data.put("count", getOnlineCount());
        data.put("user", usrAlias);
        data.put("msg", "加入！\n");
        broadcast(data.toJSONString());
    }

    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this); // 从set中删除
        JSONObject data = new JSONObject();
        data.put("count", getOnlineCount());
        data.put("user", usrAlias);
        data.put("msg", "退出。");
        broadcast(data.toJSONString());
    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject data = new JSONObject();
        data.put("count", getOnlineCount());
        data.put("user", usrAlias);
        data.put("msg", message);
        broadcast(data.toJSONString());
    }

    private void broadcast(String message) {
        // webSocketSet.forEach(new Consumer<ChatWsService>() {

        // @Override
        // public void accept(ChatWsService t) {
        // try {
        // t.session.getBasicRemote().sendText(message);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        KeySetView<ChatWsService, Byte> set = webSocketSet.keySet();
        set.forEach(t -> {
            try {
                t.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 发生错误时调用
     * 
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        webSocketSet.remove(this);
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
        return webSocketSet.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(usrAlias);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChatWsService other = (ChatWsService) obj;
        return Objects.equals(usrAlias, other.usrAlias);
    }

}