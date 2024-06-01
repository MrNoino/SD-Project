package model;

public class Piece {
    private byte[] type;
    private byte[] position;
    
    public Piece(){
        this.type = new byte[2];
        this.position = new byte[2];
    }
    
    public Piece(byte color, byte type, byte row, byte column){
        this.type = new byte[]{color, type};
        this.position = new byte[]{row, column};
    }

    public byte[] getType() {
        return type;
    }

    public void setType(byte[] type) {
        this.type = type;
    }

    public byte[] getPosition() {
        return position;
    }

    public void setPosition(byte[] position) {
        this.position = position;
    }
    
    public boolean hasPosition(byte [] position){
        return this.position[0] == position[0] && this.position[1] == position[1];
    }

    @Override
    public String toString() {
        return "At:["+position[0]+position[1]+"]: "+ type[0]+type[1];
    }
}
