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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import model.Chess;
import model.User;
import model.Piece;

@Path("")
public class ChessResource{
    private Chess chess;
    private ManageUsers manageUsers;
    
    public ChessResource(){
        this.chess = new Chess();
        this.manageUsers = new ManageUsers();
    }
    
    // *** CHESS ***
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Chess getChess(){
        return this.chess;
    }
    
    // *** PIECES ***
    @Path("pieces")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPiece(Piece piece){
        
        return Response.ok().build();
    }
    
    @Path("pieces")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response movePiece(Piece piece){
        return Response.ok().build();
    }
    
    @Path("pieces")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removePiece(Piece piece){
        return Response.ok().build();
    }
    
    
    
    // *** CLIENTS ***
    @Path("clients")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User join(User user){
        if(user.getUsername().isEmpty()){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if(!this.manageUsers.addUser(user)){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        return this.manageUsers.getUser(user.getUsername());
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
        System.out.println(username);
        if(this.manageUsers.removeUser(username)){
            return Response.noContent().build();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
    @Path("users")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeStatus(User user){
        if(manageUsers.changeStatus(user.getUsername(), user.isPlayer())){
            return Response.noContent().build();
        }else{
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
    }
    
    
}
