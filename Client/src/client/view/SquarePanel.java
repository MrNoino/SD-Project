package client.view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class SquarePanel extends JPanel {

    private int row, column;
    private ChessGUI cg;
    private JLabel imageLabel;

    private static Image pieceImage[][] = new Image[2][6];
    private static String imageFilename[][] = {
        {"pwp.png", "pwn.png", "pwb.png", "pwr.png", "pwq.png", "pwk.png"},
        {"pbp.png", "pbn.png", "pbb.png", "pbr.png", "pbq.png", "pbk.png"}};

    //colors: 0 - white; 1 - black;
    //pieces: 0 - pawn(peao); 1 - knight(cavalo); 2 - bishop(bispo)
    //        3 - rook(torre); 4 - queen(rainha); 5 - king(rei)
    public SquarePanel(int x, int y, ChessGUI c) {
        row = x;
        column = y;
        cg = c;
//        setPreferredSize(new Dimension(70, 70));
        imageLabel = new JLabel();
//        imageLabel.setPreferredSize(new Dimension(60, 60));
        add(imageLabel);
        //loadPieceImages();
        addMouseListener(new SquareMouseListener());
    }

    public static void loadPieceImages() {
        URL iconURL;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
//                SquarePanel.class.getResource("images/wb.gif");
//                ClassLoader.getSystemResource("images/wb.gif");
                iconURL = SquarePanel.class.getResource("images/"+imageFilename[i][j]);
                System.out.println(iconURL);
                pieceImage[i][j] = Toolkit.getDefaultToolkit().getImage(iconURL);
            }
        }
    }

    public void setBackColor(int color) {
        if (color != 0) {
            setBackground(new Color(108,108,195));
        } else {
            setBackground(new Color(206,206,255));
        }
    }

    public void setPiece(int color, int type) {
//        System.out.println(pieceImage[color][type].get(this));
        imageLabel.setIcon(new ImageIcon(pieceImage[color][type]));
    }

    public void removePiece() {
        imageLabel.setIcon(null);
    }

    class SquareMouseListener extends MouseAdapter {

        public void mouseEntered(MouseEvent e) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            //repaint();
        }

        public void mouseExited(MouseEvent e) {
            setBorder(null);
            //repaint();
        }

        public void mousePressed(MouseEvent e) {
            cg.selected(row, column);
            setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

    }

}
