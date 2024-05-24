package client.model;

import client.model.Piece;
import java.util.ArrayList;

public class Chess {

    ArrayList<Piece> chessPieces;

    public Chess() {
        this.chessPieces = new ArrayList<Piece>();
        //add the pawns pieces
        for (int i = 0; i < 8; i++) {
            this.chessPieces.add(new Piece((byte) 0, (byte) 0, (byte) 6, (byte) i));
            this.chessPieces.add(new Piece((byte) 1, (byte) 0, (byte) 1, (byte) i));
        }
        //added white pieces
        this.chessPieces.add(new Piece((byte) 0, (byte) 3, (byte) 7, (byte) 0));
        this.chessPieces.add(new Piece((byte) 0, (byte) 1, (byte) 7, (byte) 1));
        this.chessPieces.add(new Piece((byte) 0, (byte) 2, (byte) 7, (byte) 2));
        this.chessPieces.add(new Piece((byte) 0, (byte) 4, (byte) 7, (byte) 3));
        this.chessPieces.add(new Piece((byte) 0, (byte) 5, (byte) 7, (byte) 4));
        this.chessPieces.add(new Piece((byte) 0, (byte) 2, (byte) 7, (byte) 5));
        this.chessPieces.add(new Piece((byte) 0, (byte) 1, (byte) 7, (byte) 6));
        this.chessPieces.add(new Piece((byte) 0, (byte) 3, (byte) 7, (byte) 7));
        //add black pieces
        this.chessPieces.add(new Piece((byte) 1, (byte) 3, (byte) 0, (byte) 0));
        this.chessPieces.add(new Piece((byte) 1, (byte) 1, (byte) 0, (byte) 1));
        this.chessPieces.add(new Piece((byte) 1, (byte) 2, (byte) 0, (byte) 2));
        this.chessPieces.add(new Piece((byte) 1, (byte) 4, (byte) 0, (byte) 3));
        this.chessPieces.add(new Piece((byte) 1, (byte) 5, (byte) 0, (byte) 4));
        this.chessPieces.add(new Piece((byte) 1, (byte) 2, (byte) 0, (byte) 5));
        this.chessPieces.add(new Piece((byte) 1, (byte) 1, (byte) 0, (byte) 6));
        this.chessPieces.add(new Piece((byte) 1, (byte) 3, (byte) 0, (byte) 7));

    }
    
    public synchronized ArrayList<Piece> getChessPieces() {
        return this.chessPieces;
    }
    
    public Piece getChessPiece(byte[] piece) {
        synchronized (this.chessPieces) {
            for(Piece p: this.chessPieces){
                if(p.getPiece() == piece){
                    return p;
                }
            }
        }
        return null;
    }
    
    public boolean setChessPiece(Piece piece) {
        return true;
    }

}
