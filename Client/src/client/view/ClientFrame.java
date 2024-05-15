package client.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author Karine Aparecida Braga Florencio - a2021106016 - ESTGOH - IPC
 */
public class ClientFrame extends JFrame {
    
    private Container container;
    private JPanel mainPanel;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    
    private JButton firstBtnBottom;
    private JPanel bottomPanel;
    
    private JPanel gameBoard;
    private JPanel chessBoard;
    private JPanel player1SpareBoard;
    private JPanel player2SpareBoard;
    private JLabel namePlayer1;
    private JLabel namePlayer2;
    
    private JPanel chatPanel;
    private JLabel msgLabel;
    private JScrollPane jScrollArea;
    private JTextArea messagesArea;
    private JLabel nameLabel;
    private JTextField nameField;
    private JTextField messageField;
    private JLabel infoField;
    private JButton sendBtn;
    
//    private JLabel titleGame;
    
    
    
    public static void main(String[] args) {
        new ClientFrame().setVisible(true);
    }
    
    public ClientFrame() {
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280,720));
        setResizable(true);
        
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        container = getContentPane();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        container.add(mainPanel);
        
        firstBtnBottom = new JButton("Turn observer");
        firstBtnBottom.getFont().deriveFont(Font.PLAIN);
        firstBtnBottom.setPreferredSize(new Dimension(120,30));
        FlowLayout bottomPanelLayout = new FlowLayout(FlowLayout.TRAILING);
        bottomPanelLayout.setVgap(10);
        bottomPanelLayout.setHgap(30);
        bottomPanel = new JPanel(bottomPanelLayout);
        bottomPanel.add(firstBtnBottom);
        mainPanel.add(new JSeparator(), BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        chessBoard = new JPanel();
        chessBoard.setBorder(BorderFactory.createEtchedBorder());
        chessBoard.setPreferredSize(new Dimension(400, 400));
        
        player2SpareBoard = new JPanel();
        player2SpareBoard.setBorder(BorderFactory.createEtchedBorder());
        player2SpareBoard.setPreferredSize(new Dimension(150, -1));
        player1SpareBoard = new JPanel();
        player1SpareBoard.setBorder(BorderFactory.createEtchedBorder());
        player1SpareBoard.setPreferredSize(new Dimension(150, -1));
        
        namePlayer2 = new JLabel();
        namePlayer2.setFont(new Font("Gill Sans MT", 0, 14));
        namePlayer2.setText("Player 2");
        namePlayer1 = new JLabel();
        namePlayer1.setFont(new Font("Gill Sans MT", 0, 14));
        namePlayer1.setHorizontalAlignment(SwingConstants.TRAILING);
        namePlayer1.setText("Player 1");
        
        gameBoard = new JPanel();
//        gameBoard.setBorder(BorderFactory.createEtchedBorder());
        BorderLayout gameBorderLayout = new BorderLayout();
        gameBorderLayout.setHgap(10);
        gameBoard.setLayout(gameBorderLayout);
        JPanel centerPanel = new JPanel();
        BorderLayout centerPanelLayout = new BorderLayout();
        centerPanelLayout.setVgap(5);
        centerPanel.setLayout(centerPanelLayout);
        centerPanel.add(namePlayer2,BorderLayout.NORTH);
        centerPanel.add(chessBoard,BorderLayout.CENTER);
        centerPanel.add(namePlayer1,BorderLayout.SOUTH);
        gameBoard.add(centerPanel, BorderLayout.CENTER);
        gameBoard.add(player2SpareBoard, BorderLayout.WEST);
        gameBoard.add(player1SpareBoard, BorderLayout.EAST);
        JPanel j = new JPanel();
//        BoxLayout b = new BoxLayout(j, BoxLayout.PAGE_AXIS);
//        j.setLayout(b);
//        j.setBackground(Color.CYAN);
        j.add(gameBoard);
        mainPanel.add(j, BorderLayout.CENTER);
        
        
        msgLabel = new JLabel("Messages:");
        messagesArea = new JTextArea();
        messagesArea.setFocusable(false);
        messagesArea.setRequestFocusEnabled(false);
        jScrollArea = new JScrollPane();
        jScrollArea.setViewportView(messagesArea);
//        jScrollArea.setPreferredSize(new Dimension(260,400));
        
        nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        nameField.setColumns(25);
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        messageField = new JTextField();
        messageField.setToolTipText("Write a new message...");
        
        infoField = new JLabel();
        infoField.setForeground(new java.awt.Color(255, 51, 51));
        infoField.setVerticalAlignment(SwingConstants.TOP);
        infoField.setVerticalTextPosition(SwingConstants.TOP);
        sendBtn = new JButton("Send");
        sendBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        chatPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        chatPanel.setBorder(BorderFactory.createEtchedBorder());
        BoxLayout chatPanelLayout = new BoxLayout(chatPanel, BoxLayout.Y_AXIS);
        chatPanel.setLayout(chatPanelLayout);
        chatPanel.add(msgLabel);
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(-1,700));
        chatPanel.add(p);
        chatPanel.add(namePanel);
        chatPanel.add(messageField);
        chatPanel.add(sendBtn);
        mainPanel.add(chatPanel, BorderLayout.EAST);
        
        
    
    }
}
