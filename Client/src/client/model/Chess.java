package client.model;

import java.util.ArrayList;

public class Chess {

    ArrayList<int[]> chessPieces;
    ArrayList<int[]> removedPieces;

    public Chess() {
        this.chessPieces = new ArrayList<int[]>();
        this.removedPieces = new ArrayList<int[]>();
        //add the pawns pieces
        for (int i = 0; i < 8; i++) {
            this.chessPieces.add(new int[]{0, 0, 1, i});
            this.chessPieces.add(new int[]{1, 0, 6, i});
        }
        //added white pieces
        this.chessPieces.add(new int[]{0, 3, 7, 0});
        this.chessPieces.add(new int[]{0, 1, 7, 1});
        this.chessPieces.add(new int[]{0, 2, 7, 2});
        this.chessPieces.add(new int[]{0, 4, 7, 3});
        this.chessPieces.add(new int[]{0, 5, 7, 4});
        this.chessPieces.add(new int[]{0, 2, 7, 5});
        this.chessPieces.add(new int[]{0, 1, 7, 6});
        this.chessPieces.add(new int[]{0, 3, 7, 7});
        //add black pieces
        this.chessPieces.add(new int[]{1, 3, 0, 0});
        this.chessPieces.add(new int[]{1, 1, 0, 1});
        this.chessPieces.add(new int[]{1, 2, 0, 2});
        this.chessPieces.add(new int[]{1, 4, 0, 3});
        this.chessPieces.add(new int[]{1, 5, 0, 4});
        this.chessPieces.add(new int[]{1, 2, 0, 5});
        this.chessPieces.add(new int[]{1, 1, 0, 6});
        this.chessPieces.add(new int[]{1, 3, 0, 7});

    }
    
    public synchronized ArrayList<int[]> getChessPieces() {
        return this.chessPieces;
    }
    
    public synchronized ArrayList<int[]> getRemovedPieces() {
        return this.removedPieces;
    }
    
    public int[] getChessPiece(int[] piece) {
        synchronized (this.chessPieces) {
            for(int[] p: this.chessPieces){
                if(p[0] == piece[0] && p[1] == piece[1]){
                    return p;
                }
            }
        }
        return null;
    }
    
    public int[] getRemovedPiece(int[] piece) {
        synchronized (this.removedPieces) {
            for(int[] p: this.removedPieces){
                if(p[0] == piece[0] && p[1] == piece[1]){
                    return p;
                }
            }
        }
        return null;
    }
    
    public boolean setChessPiece() {
        return true;
    }
    
    public boolean setRemovedPiece() {
        return true;
    }

}
