package application;

import controller.ManageClients;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import model.Chess;
import model.Client;

@Path("")
public class ChessResource{
    private Chess chess;
    private ManageClients manageClients;
    
    public ChessResource(){
        this.chess = new Chess();
        this.manageClients = new ManageClients();
    }
    
    @Path("clients")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client join(Client client){
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
}
