package controller;

import java.util.ArrayList;
import model.Piece;

public class Chess {

    ArrayList<Piece> chessPieces;

    public Chess() {
        this.initializeBoard();
    }
    
    public synchronized void initializeBoard(){
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
    
    private Piece getChessPiece(Piece piece) {
        synchronized (this.chessPieces) {
            for(Piece p: this.chessPieces){
                if(p.getPosition()[0] == piece.getPosition()[0] && p.getPosition()[1] == piece.getPosition()[1] &&
                        p.getType()[0] == piece.getType()[0] && p.getType()[1] == piece.getType()[1]){
                    return p;
                }
            }
        }
        return null;
    }
    
    private Piece getChessPiecebyPosition(byte[] position) {
        synchronized (this.chessPieces) {
            for(Piece p: this.chessPieces){
                if(p.getPosition()[0] == position[0] && p.getPosition()[1] == position[1]){
                    return p;
                }
            }
        }
        return null;
    }
    
    public int moveChessPiece(Piece past, Piece future) {
        synchronized (this.chessPieces) {
            Piece p = getChessPiece(past);
            if(p != null){
                Piece f = getChessPiecebyPosition(future.getPosition());
                if(f != null && future.getPosition()[0] != -1){
                    if(p.getType()[0] != f.getType()[0]){
                         p.setPosition(future.getPosition());
                         f.setPosition(new byte[]{-1,-1});
                         return -1; //OK
                    } else{
                        System.out.println("Error: peças de mesmo jogador");
                        return 400; //BadRequest
                    }
                }else{
                    p.setPosition(future.getPosition());
                    return -1;
                }
            } else{
                System.out.println("Error: primeira peça não encontrada");
                return 400; //BadRequest
            }
        }
    }
    
    public void cleanBoard(){
        synchronized(this.chessPieces){
            for(int i = 0; i < this.chessPieces.size(); i++){
                Piece piece = this.chessPieces.get(i);
                piece.setPosition(new byte[]{(byte) -1, (byte)-1});
                this.chessPieces.set(i, piece);
            }
        }
    }

}
