package client.model;

public class Piece {
    private byte[] piece;
    private byte[] position;
    
    public Piece(){
        this.piece = new byte[2];
        this.position = new byte[2];
    }
    
    public Piece(byte color, byte type, byte x, byte y){
        this.piece = new byte[]{color, type};
        this.position = new byte[]{x, y};
    }

    public byte[] getPiece() {
        return piece;
    }

    public void setPiece(byte[] piece) {
        this.piece = piece;
    }

    public byte[] getPosition() {
        return position;
    }

    public void setPosition(byte[] position) {
        this.position = position;
    }
}
