package application;

import controller.ManageListeners;
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
import controller.Chess;
import model.Message;
import model.User;
import model.Piece;

@Path("")
public class ChessResource{
    private Chess chess;
    private ManageUsers manageUsers;
    private ManageListeners manageListeners;
    
    public ChessResource(){
        this.chess = new Chess();
        this.manageUsers = new ManageUsers();
        this.manageListeners = new ManageListeners();
    }
    
    // *** ASYNC CHESS ***
    
    @GET
    @Path("chess-async")
    @Produces(MediaType.APPLICATION_JSON)
    public void getChessAsync(@Suspended AsyncResponse async){
        this.manageListeners.addChessListener(async);
    }
    
    // *** ASYNC USERS ***
    
    @GET
    @Path("users-async")
    @Produces(MediaType.APPLICATION_JSON)
    public void getUsersAsync(@Suspended AsyncResponse async){
        this.manageListeners.addUserListener(async);
    }
    
    // *** ASYNC CHAT ***
    
    @GET
    @Path("chat-async")
    @Produces(MediaType.APPLICATION_JSON)
    public void getChatAsync(@Suspended AsyncResponse async){
        this.manageListeners.addChatListener(async);
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
        this.manageListeners.sendToChessListeners(this.chess);
        return Response.ok().build();
    }
    
    // *** BOARD ***
    
    @Path("board")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cleanBoard(){
        this.chess.cleanBoard();
        this.manageListeners.sendToChessListeners(this.chess);
        return Response.noContent().build();
    }
    
    @Path("board")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rearrangeBoard(){
        this.chess.initializeBoard();
        this.manageListeners.sendToChessListeners(this.chess);
        return Response.noContent().build();
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
        this.manageListeners.sendToUsersListeners(this.manageUsers.getUsers());
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
            this.manageListeners.sendToUsersListeners(this.manageUsers.getUsers());
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
        this.manageListeners.sendToUsersListeners(this.manageUsers.getUsers());
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
        this.manageListeners.sendToChatListeners(message);
        return Response.noContent().build();
    }
}
