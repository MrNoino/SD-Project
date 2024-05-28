package controller;

import java.util.ArrayList;
import model.User;

public class ManageUsers {
    private ArrayList<User> users;
    
    public ManageUsers(){
        this.users = new ArrayList<User>();
/*        this.addUser(new User("Mr.Noino", true, (byte) 1));
        this.addUser(new User("Karine", true, (byte) 2));
        this.addUser(new User("Windoh", false, (byte) 0));*/
    }
    
    public synchronized ArrayList<User> getUsers(){
        return this.users;
    }
    
    public User getUser(String username){
        synchronized (this.users) {
            for(User user: this.users){
                if(user.getUsername().equals(username)){
                    return user;
                }
            }
        }
        return null;
    }
    
    public int getNumberOfPlayers(){
        int quantity = 0;
        synchronized (this.users) {
            for(User user: this.users){
                if(user.isPlayer()){
                    quantity++;
                }
            }
        }
        return quantity;
    }
    
    public boolean addUser(User user){
        if(this.getUser(user.getUsername()) != null){
            return false;
        }
        //set if it is player by checking if are less than 2 players
        user.setPlayer(this.getNumberOfPlayers() < 2);
        synchronized(this.users){
            if(user.isPlayer()){
                boolean firstPositionTaken = false;
                for(User c : this.users){
                    if(c.getPosition() == 1){
                        firstPositionTaken = true;
                        break;
                    }
                }
                user.setPosition((firstPositionTaken) ? (byte) 2 : (byte) 1);
            }
            return this.users.add(user);
        }
    }
    
    public boolean removeUser(String username){
        synchronized(this.users){
            for(User user: this.users){
                if(user.getUsername().equals(username)){
                    return this.users.remove(user);
                }
            }
        }
        return false;
    }
    
    public synchronized User changeStatus(String username, boolean isPlayer){
        if(isPlayer){
            if(this.getNumberOfPlayers() == 2){
                return null;
            }
        }
        int indexOfUser = -1;
        byte takenPosition = -1;
        for(int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUsername().equals(username)){
                this.users.get(i).setPlayer(isPlayer);
                indexOfUser = i;
            }
            if(this.users.get(i).getPosition() != 0){
                takenPosition = this.users.get(i).getPosition();
            }
        }
        if(indexOfUser != -1){
            if(!isPlayer){
                this.users.get(indexOfUser).setPosition((byte)0);
            }else if(takenPosition == 1){
                this.users.get(indexOfUser).setPosition((byte)2);
            }else{
                this.users.get(indexOfUser).setPosition((byte)1);
            }
            return this.users.get(indexOfUser);
        }
        return null;
    }
    
    public synchronized User changePosition(String username){
        if(this.getNumberOfPlayers() == 2){
            return null;
        }
        for(int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUsername().equals(username)){
                if(this.users.get(i).getPosition() == 0)
                    return null;
                this.users.get(i).setPosition(this.users.get(i).getPosition() == 1 ? (byte) 2 : (byte) 1);
                return this.users.get(i);
            }
            
        }
        return null;
    }
    
}
