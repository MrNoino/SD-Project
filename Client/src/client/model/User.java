package client.model;

public class User {
    private String username;
    private boolean player;
    private byte position;
    
    public User(){}
    
    public User(String username, boolean player, byte position){
        this.username = username;
        this.player = player;
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }
    
}
