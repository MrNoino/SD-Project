package controller;

import java.util.ArrayList;
import model.User;

public class ManageUsers {
    private ArrayList<User> clients;
    
    public ManageUsers(){
        this.clients = new ArrayList<User>();
        this.addUser(new User("Mr.Noino", true, (byte) 1));
        this.addUser(new User("Karine", true, (byte) 2));
        this.addUser(new User("Windoh", false, (byte) 0));
    }
    
    public synchronized ArrayList<User> getUsers(){
        return this.clients;
    }
    
    public User getUser(String username){
        synchronized (this.clients) {
            for(User client: this.clients){
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
            for(User client: this.clients){
                if(client.isPlayer()){
                    quantity++;
                }
            }
        }
        return quantity;
    }
    
    public boolean addUser(User client){
        if(this.getUser(client.getUsername()) != null){
            return false;
        }
        //set if it is player by checking if are less than 2 players
        client.setPlayer(this.getNumberOfPlayers() < 2);
        synchronized(this.clients){
            if(client.isPlayer()){
                boolean firstPositionTaken = false;
                for(User c : this.clients){
                    if(c.getPosition() == 1){
                        firstPositionTaken = true;
                        break;
                    }
                }
                client.setPosition((firstPositionTaken) ? (byte) 2 : (byte) 1);
            }
            return this.clients.add(client);
        }
    }
    
    public boolean removeUser(String username){
        synchronized(this.clients){
            for(User client: this.clients){
                if(client.getUsername().equals(username)){
                    return this.clients.remove(client);
                }
            }
        }
        return false;
    }
    
    public synchronized boolean changeStatus(String username, boolean isPlayer){
        if(isPlayer){
            if(this.getNumberOfPlayers() == 2){
                return false;
            }
        }
        
        for(int i = 0; i < this.clients.size(); i++){
            if(this.clients.get(i).getUsername().equals(username)){
                this.clients.get(i).setPlayer(isPlayer);
                return true;
            }
        }
        
        return false;
    }
    
}
