package application;

import controller.ManageClients;
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
import model.Client;
import model.Piece;

@Path("")
public class ChessResource{
    private Chess chess;
    private ManageClients manageClients;
    
    public ChessResource(){
        this.chess = new Chess();
        this.manageClients = new ManageClients();
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
    public Client join(Client client){
        if(client.getUsername().isEmpty()){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if(!this.manageClients.addClient(client)){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        return this.manageClients.getClient(client.getUsername());
    }
    
    @Path("clients")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Client> getClients(){
        return this.manageClients.getClients();
    }
    
    @Path("clients/{username}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response leave(@PathParam("username") String username){
        System.out.println(username);
        if(this.manageClients.removeClient(username)){
            return Response.noContent().build();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
    @Path("clients")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeStatus(Client client){
        if(manageClients.changeStatus(client.getUsername(), client.isPlayer())){
            return Response.noContent().build();
        }else{
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
    }
    
    
}
