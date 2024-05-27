package application;

import controller.ManageUsers;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import model.Chess;
import model.Message;
import model.User;
import model.Piece;

@Path("")
public class ChessResource{
    private Chess chess;
    private ManageUsers manageUsers;
    
    private ArrayList<AsyncResponse> chessListeners = new ArrayList<>();
    private ArrayList<AsyncResponse> usersListeners = new ArrayList<>();
    private ArrayList<AsyncResponse> chatListeners = new ArrayList<>();
    
    public ChessResource(){
        this.chess = new Chess();
        this.manageUsers = new ManageUsers();
    }
    
    // *** ASYNC CHESS ***
    
    @GET
    @Path("chess-async")
    @Produces(MediaType.APPLICATION_JSON)
    public void getChessAsync(@Suspended AsyncResponse async){
        synchronized (this.chessListeners) {
            this.chessListeners.add(async);
        }
    }
    
    private void sendToChessListeners() {
        synchronized (this.chessListeners) {
            for(AsyncResponse asyncResp: this.chessListeners){
                asyncResp.resume(Response.ok(this.chess.getChessPieces()).build());
            }
            this.chessListeners.clear();
        }
    }
    
    // *** ASYNC USERS ***
    
    @GET
    @Path("users-async")
    @Produces(MediaType.APPLICATION_JSON)
    public void getUsersAsync(@Suspended AsyncResponse async){
        synchronized (this.usersListeners) {
            this.usersListeners.add(async);
        }
    }
    
    private void sendToUsersListeners() {
        synchronized (this.usersListeners) {
            for(AsyncResponse asyncResp: this.usersListeners){
                asyncResp.resume(Response.ok(this.manageUsers.getUsers()).build());
            }
            this.usersListeners.clear();
        }
    }
    
    // *** ASYNC CHAT ***
    
    @GET
    @Path("chat-async")
    @Produces(MediaType.APPLICATION_JSON)
    public void getChatAsync(@Suspended AsyncResponse async){
        synchronized (this.chatListeners) {
            this.chatListeners.add(async);
        }
    }
    
    private void sendToChatListeners(Message message) {
        synchronized (this.chatListeners) {
            for(AsyncResponse asyncResp: this.chatListeners){
                asyncResp.resume(Response.ok(message).build());
            }
            this.chatListeners.clear();
        }
    }
    
    // *** CHESS ***
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Piece> getChess(){
        return this.chess.getChessPieces();
    }
    
    // *** PIECES ***
    
    @Path("pieces")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response movePiece(Piece[] piece){
        this.chess.moveChessPiece(piece[0],piece[1]);
        sendToChessListeners();
        return Response.ok().build();
    }
    
    // *** CLIENTS ***
    
    @Path("users")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> join(User user){
        if(user.getUsername().isEmpty()){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if(!this.manageUsers.addUser(user)){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        sendToUsersListeners();
        return this.manageUsers.getUsers();
    }
    
    @Path("users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> getUsers(){
        return this.manageUsers.getUsers();
    }
    
    @Path("users/{username}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response leave(@PathParam("username") String username){
        if(this.manageUsers.removeUser(username)){
            sendToUsersListeners();
            return Response.noContent().build();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
    @Path("users")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public User changeStatus(User user){
        User u = manageUsers.changeStatus(user.getUsername(), user.isPlayer());
        if(u == null){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        sendToUsersListeners();
        return u;
    }
    
    // *** CHAT ***
    
    @Path("chat")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMessage(Message message){
        if(message.getContent().isEmpty() || message.getUser().isEmpty())
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        if(this.manageUsers.getUser(message.getUser()) == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        sendToChatListeners(message);
        return Response.noContent().build();
    }
}
