package model;

public class Piece {
    private int[] piece;
    private int[] position;
    
    public Piece(){
        this.piece = new int[2];
        this.position = new int[2];
    }

    public int[] getPiece() {
        return piece;
    }

    public void setPiece(int[] piece) {
        this.piece = piece;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
}
