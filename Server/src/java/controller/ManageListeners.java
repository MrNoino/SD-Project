package controller;

import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import model.Message;
import model.User;

public class ManageListeners {
    private ArrayList<AsyncResponse> chessListeners = new ArrayList<>();
    private ArrayList<AsyncResponse> usersListeners = new ArrayList<>();
    private ArrayList<AsyncResponse> chatListeners = new ArrayList<>();
    
    public ManageListeners(){
        this.chessListeners = new ArrayList<>();
        this.usersListeners = new ArrayList<>();
        this.chatListeners = new ArrayList<>();
    }
    
    public void sendToChessListeners(Chess chess) {
        synchronized (this.chessListeners) {
            for(AsyncResponse asyncResp: this.chessListeners){
                asyncResp.resume(Response.ok(chess.getChessPieces()).build());
            }
            this.chessListeners.clear();
        }
    }
    
    public void sendToUsersListeners(ArrayList<User> users) {
        synchronized (this.usersListeners) {
            for(AsyncResponse asyncResp: this.usersListeners){
                asyncResp.resume(Response.ok(users).build());
            }
            this.usersListeners.clear();
        }
    }
    
    public void sendToChatListeners(Message message) {
        synchronized (this.chatListeners) {
            for(AsyncResponse asyncResp: this.chatListeners){
                asyncResp.resume(Response.ok(message).build());
            }
            this.chatListeners.clear();
        }
    }
    
    public void addChessListener(AsyncResponse async){
        synchronized (this.chessListeners) {
            this.chessListeners.add(async);
        }
    }
    
    public void addUserListener(AsyncResponse async){
        synchronized (this.usersListeners) {
            this.usersListeners.add(async);
        }
    }
    
    public void addChatListener(AsyncResponse async){
        synchronized (this.chatListeners) {
            this.chatListeners.add(async);
        }
    }
}
