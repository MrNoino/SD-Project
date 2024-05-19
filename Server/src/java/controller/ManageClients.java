package controller;

import java.util.ArrayList;
import model.Client;

public class ManageClients {
    private ArrayList<Client> clients;
    
    public ManageClients(){
        this.clients = new ArrayList<Client>();
        this.addClient(new Client("Nuno", true, (byte) 1));
        this.addClient(new Client("Karine", true, (byte) 2));
        this.addClient(new Client("Windoh", false, (byte) 0));
    }
    
    public synchronized ArrayList<Client> getClients(){
        return this.clients;
    }
    
    public Client getClient(String username){
        synchronized (this.clients) {
            for(Client client: this.clients){
                if(client.getUsername().equals(username)){
                    return client;
                }
            }
        }
        return null;
    }
    
    public int getNumberOfPlayers(){
        int quantity = 0;
        synchronized (this.clients) {
            for(Client client: this.clients){
                if(client.isPlayer()){
                    quantity++;
                }
            }
        }
        return quantity;
    }
    
    public boolean addClient(Client client){
        if(this.getClient(client.getUsername()) != null){
            return false;
        }
        //set if it is player by checking if are less than 2 players
        client.setPlayer(this.getNumberOfPlayers() < 2);
        synchronized(this.clients){
            return this.clients.add(client);
        }
    }
    
    public boolean removeClient(String username){
        synchronized(this.clients){
            for(Client client: this.clients){
                if(client.getUsername().equals(username)){
                    return this.clients.remove(client);
                }
            }
        }
        return false;
    }
    
}
