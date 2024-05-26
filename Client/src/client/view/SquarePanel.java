package client.view;

import client.model.Piece;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class SquarePanel extends JPanel {

    private Piece piece;
    private ClientFrame cg;
    private JLabel imageLabel;

    private static Image pieceImage[][] = new Image[2][6];
    private static String imageFilename[][] = {
        {"pwp.png", "pwn.png", "pwb.png", "pwr.png", "pwq.png", "pwk.png"},
        {"pbp.png", "pbn.png", "pbb.png", "pbr.png", "pbq.png", "pbk.png"}};

    //colors: 0 - white; 1 - black;
    //pieces: 0 - pawn(peao); 1 - knight(cavalo); 2 - bishop(bispo)
    //        3 - rook(torre); 4 - queen(rainha); 5 - king(rei)
    public SquarePanel(byte x, byte y, ClientFrame c, boolean player) {
        piece = new Piece((byte) -1, (byte) -1, (byte) x, (byte) y);
        cg = c;
        imageLabel = new JLabel();
        add(imageLabel);
        if(player) addMouseListener(new SquareMouseListener());
    }

    public static void loadPieceImages() {
        URL iconURL;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                iconURL = SquarePanel.class.getResource("images/"+imageFilename[i][j]);
//                System.out.println(iconURL);
                pieceImage[i][j] = Toolkit.getDefaultToolkit().getImage(iconURL);
            }
        }
    }

    public void setBackgroundColor(boolean color) {
        if (color) {
            setBackground(new Color(108,108,195));
        } else {
            setBackground(new Color(206,206,255));
        }
    }
    
    public void setBackgroundGray(int color) {
        if (color != 0) {
            setBackground(new Color(108,108,108));
        } else {
            setBackground(new Color(206,206,206));
        }
    }
    
    public Piece getPiece(){
        return piece;
    }

    public void setPiece(int color, int type) {
        if(color == -1 || type == -1){
            removePiece();
        } else{
//            System.out.println(pieceImage[color][type].get(this));
            imageLabel.setIcon(new ImageIcon(pieceImage[color][type]));
            piece.setType(new byte[]{(byte) color, (byte) type});
        }
    }

    public void removePiece() {
        imageLabel.setIcon(null);
        piece.setType(new byte[]{(byte) -1, (byte) -1});
    }

    class SquareMouseListener extends MouseAdapter {

        public void mouseEntered(MouseEvent e) {
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            //repaint();
        }

        public void mouseExited(MouseEvent e) {
            setBorder(null);
            //repaint();
        }

        public void mousePressed(MouseEvent e) {
            cg.selected(SquarePanel.this);
            setBorder(BorderFactory.createLineBorder(Color.RED, 2));
//            setBackground(Color.ORANGE);
        }

    }

}
