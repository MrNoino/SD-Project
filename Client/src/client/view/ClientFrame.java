package client.view;

import client.model.Chess;
import client.model.Piece;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;
import java.util.List;

import client.model.User;

/**
 *
 * @author Karine Aparecida Braga Florencio - a2021106016 - ESTGOH - IPC
 */
public class ClientFrame extends JFrame {
    
    // Variables declaration - do not modify
    private JPanel mainPainel; 
    
    private JPanel flexPanel;
    private JLabel titleGame;
    private JPanel gameBoard;
    private JPanel chessBoard;
    private JPanel player1SpareBoard;
    private JPanel player2SpareBoard;
    private JLabel namePlayer1;
    private JLabel namePlayer2;
    
    private JPanel chatPanel;
    private JScrollPane jScrollArea;
    private JTextArea messagesArea;
    private JLabel msgLabel;
    private JTextField messageField;
    private JButton sendBtn;
    private JLabel infoField;
    
    private JPanel configPanel;
    private JTextField ipField;
    private JLabel ipLabel;
    private JTextField portField;
    private JLabel portLabel;
    private JTextField nameField;
    private JLabel nameLabel;
    private JButton joinBtn;
    private JButton leaveBtn;
    
    private JMenu fileMenu;
    private JMenuBar menuBar;
    private JMenuItem exitMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem openMenuItem;
    
    private JPanel panelBtns;
    private JButton observerButton;
    private JButton positionButton;
    private JButton rearrangeButton;
    private JButton cleanButton;
    private JButton flipButton;
    
    private SquarePanel[][] board = new SquarePanel[8][8];
    
    static Client client;
    static String baseUri;
    
    private User myUser;
    
    // End of variables declaration     

    public ClientFrame() {
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setSize(new Dimension(1460,760));
        setMinimumSize(new Dimension(1460,760));
        setResizable(true);
        
        initComponents();
        
        ipField.setText("localhost");
        portField.setText("8080");
        titleGame.setText("Please, enter a game.");
        sendBtn.setEnabled(false);
        messagesArea.setEnabled(false);
        messageField.setEditable(false);
        messageField.setEnabled(false);
        panelBtns.setVisible(false);
        
        SquarePanel.loadPieceImages();
        chessBoard.setLayout(new GridLayout(8,8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                SquarePanel sqPanel = new SquarePanel(i, j, this);
                sqPanel.setBackgroundGray((i + j) % 2);
                board[i][j] = sqPanel;
                chessBoard.add(sqPanel);

            }
        }
        
//        client = ClientBuilder.newClient();
//        client.close();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        mainPainel = new JPanel();
        chatPanel = new JPanel();
        sendBtn = new JButton();
        messageField = new JTextField();
        jScrollArea = new JScrollPane();
        messagesArea = new JTextArea();
        msgLabel = new JLabel();
        infoField = new JLabel();
        configPanel = new JPanel();
        portField = new JTextField();
        ipField = new JTextField();
        portLabel = new JLabel();
        ipLabel = new JLabel();
        leaveBtn = new JButton();
        joinBtn = new JButton();
        nameLabel = new JLabel();
        nameField = new JTextField();
        flexPanel = new JPanel();
        titleGame = new JLabel();
        gameBoard = new JPanel();
        chessBoard = new JPanel();
        player2SpareBoard = new JPanel();
        player1SpareBoard = new JPanel();
        namePlayer2 = new JLabel();
        namePlayer1 = new JLabel();
        panelBtns = new JPanel();
        observerButton = new JButton();
        rearrangeButton = new JButton();
        cleanButton = new JButton();
        positionButton = new JButton();
        flipButton = new JButton();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();
        saveAsMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();

        mainPainel.setAutoscrolls(true);

        chatPanel.setBorder(BorderFactory.createEtchedBorder());
        chatPanel.setMaximumSize(new Dimension(600, 32767));
        chatPanel.setMinimumSize(new Dimension(300, 400));

        sendBtn.setText("Send");
        sendBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        sendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        messageField.setToolTipText("Write a new message...");
        messageField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                messageFieldKeyTyped(evt);
            }
        });

        messagesArea.setColumns(20);
        messagesArea.setRows(5);
        messagesArea.setFocusable(false);
        messagesArea.setRequestFocusEnabled(false);
        jScrollArea.setViewportView(messagesArea);

        msgLabel.setText("Messages:");

        infoField.setBackground(new Color(255, 153, 0));
        infoField.setForeground(new Color(255, 51, 51));
        infoField.setVerticalAlignment(SwingConstants.TOP);
        infoField.setVerticalTextPosition(SwingConstants.TOP);

        GroupLayout chatPanelLayout = new GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(chatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(messageField)
                    .addComponent(jScrollArea)
                    .addComponent(msgLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, chatPanelLayout.createSequentialGroup()
                        .addComponent(infoField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, chatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(msgLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollArea)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(chatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        configPanel.setBorder(BorderFactory.createEtchedBorder());
        configPanel.setMaximumSize(new Dimension(600, 100));

        portField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                portFieldActionPerformed(evt);
            }
        });

        portLabel.setText("Port:");

        ipLabel.setText("IP:");

        leaveBtn.setText("Leave");
        leaveBtn.setPreferredSize(new Dimension(75, 23));
        leaveBtn.setEnabled(false);
        leaveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                leaveBtnActionPerformed(evt);
            }
        });

        joinBtn.setText("Join");
        joinBtn.setPreferredSize(new Dimension(75, 23));
        joinBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                joinBtnActionPerformed(evt);
            }
        });

        nameLabel.setText("Name:");

        GroupLayout configPanelLayout = new GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(ipLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(portLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(configPanelLayout.createSequentialGroup()
                        .addComponent(portField, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addComponent(ipField))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(leaveBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(joinBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ipLabel)
                    .addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(joinBtn))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(portLabel)
                    .addComponent(leaveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        titleGame.setFont(new Font("Gill Sans MT", 0, 18)); // NOI18N
        titleGame.setForeground(new Color(51, 51, 51));
        titleGame.setHorizontalAlignment(SwingConstants.CENTER);
        titleGame.setText("jLabel1");
        titleGame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        titleGame.setFocusable(false);
        titleGame.setHorizontalTextPosition(SwingConstants.CENTER);

        gameBoard.setBorder(BorderFactory.createEtchedBorder());
        gameBoard.setMaximumSize(new Dimension(680, 468));
        gameBoard.setMinimumSize(new Dimension(680, 468));
        gameBoard.setName(""); // NOI18N

        chessBoard.setBorder(BorderFactory.createEtchedBorder());
        chessBoard.setPreferredSize(new Dimension(400, 400));

        GroupLayout chessBoardLayout = new GroupLayout(chessBoard);
        chessBoard.setLayout(chessBoardLayout);
        chessBoardLayout.setHorizontalGroup(
            chessBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        chessBoardLayout.setVerticalGroup(
            chessBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );

        player2SpareBoard.setBorder(BorderFactory.createEtchedBorder());
        player2SpareBoard.setPreferredSize(new Dimension(120, 400));

        GroupLayout player2SpareBoardLayout = new GroupLayout(player2SpareBoard);
        player2SpareBoard.setLayout(player2SpareBoardLayout);
        player2SpareBoardLayout.setHorizontalGroup(
            player2SpareBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );
        player2SpareBoardLayout.setVerticalGroup(
            player2SpareBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        player1SpareBoard.setBorder(BorderFactory.createEtchedBorder());
        player1SpareBoard.setPreferredSize(new Dimension(120, 400));

        GroupLayout player1SpareBoardLayout = new GroupLayout(player1SpareBoard);
        player1SpareBoard.setLayout(player1SpareBoardLayout);
        player1SpareBoardLayout.setHorizontalGroup(
            player1SpareBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );
        player1SpareBoardLayout.setVerticalGroup(
            player1SpareBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        namePlayer2.setFont(new Font("Gill Sans MT", 0, 14)); // NOI18N
        namePlayer2.setText("Player 2");

        namePlayer1.setFont(new Font("Gill Sans MT", 0, 14)); // NOI18N
        namePlayer1.setHorizontalAlignment(SwingConstants.TRAILING);
        namePlayer1.setText("Player 1");

        GroupLayout gameBoardLayout = new GroupLayout(gameBoard);
        gameBoard.setLayout(gameBoardLayout);
        gameBoardLayout.setHorizontalGroup(
            gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(gameBoardLayout.createSequentialGroup()
                        .addComponent(player2SpareBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(namePlayer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(gameBoardLayout.createSequentialGroup()
                        .addGroup(gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gameBoardLayout.createSequentialGroup()
                                .addGap(132, 132, 132)
                                .addComponent(chessBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(namePlayer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(player1SpareBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gameBoardLayout.setVerticalGroup(
            gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gameBoardLayout.createSequentialGroup()
                        .addComponent(namePlayer2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gameBoardLayout.createSequentialGroup()
                                .addGap(406, 406, 406)
                                .addComponent(namePlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(player1SpareBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
                    .addGroup(gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(chessBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(player2SpareBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BorderLayout gameLayout = new BorderLayout();
        flexPanel.setLayout(gameLayout);
        flexPanel.add(titleGame, BorderLayout.NORTH);
        JPanel at = new JPanel();
        at.add(gameBoard);
        flexPanel.add(at, BorderLayout.CENTER);

        GroupLayout mainPainelLayout = new GroupLayout(mainPainel);
        mainPainel.setLayout(mainPainelLayout);
        mainPainelLayout.setHorizontalGroup(
            mainPainelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPainelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(chatPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(configPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flexPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPainelLayout.setVerticalGroup(
            mainPainelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mainPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPainelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(mainPainelLayout.createSequentialGroup()
                        .addComponent(configPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(7, 7, 7))
                    .addComponent(flexPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        observerButton.setText("Turn observer");
        observerButton.setPreferredSize(new Dimension(90, 30));
        observerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                observerButtonActionPerformed(evt);
            }
        });

        rearrangeButton.setText("Rearrange board");
        rearrangeButton.setPreferredSize(new Dimension(120, 30));
        rearrangeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                rearrangeButtonActionPerformed(evt);
            }
        });

        cleanButton.setText("Clean board");
        cleanButton.setPreferredSize(new Dimension(100, 30));

        positionButton.setText("Change position");
        positionButton.setPreferredSize(new Dimension(120, 30));

        flipButton.setText("Flip board");
        flipButton.setPreferredSize(new Dimension(90, 30));
        flipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                flipButtonActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(panelBtns);
        panelBtns.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(observerButton, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 638, Short.MAX_VALUE)
                .addComponent(flipButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cleanButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rearrangeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(observerButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(flipButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cleanButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(rearrangeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(positionButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainPainel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panelBtns, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPainel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(41, 41, 41))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 515, Short.MAX_VALUE)
                    .addComponent(panelBtns, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>                        

    private void exitMenuItemActionPerformed(ActionEvent evt) {                                             
        System.exit(0);
    }                                            

    private void sendBtnActionPerformed(ActionEvent evt) {                                        

        this.sendBtn.setEnabled(false);
        this.infoField.setText("");
        String msg = this.messageField.getText();
        String name = this.nameField.getText();
        if (name.isBlank() || name.isEmpty()) {
            this.infoField.setText("Name field may not be empty.");
            this.nameField.requestFocus();
        } else {
            if (msg.isEmpty() || msg.isBlank()) {
                this.infoField.setText("Message field may not be empty.");
            } else {

                msg = name + ": " + msg;
                byte[] send = msg.getBytes();

                //send message

            }
            this.messageField.requestFocus();
        }
        this.sendBtn.setEnabled(true);
        this.messageField.requestFocus();
    }                                       

    private void messageFieldKeyTyped(KeyEvent evt) {                                      
        // TODO add your handling code here:
        if (evt.getKeyChar() == '\n') {
            sendBtnActionPerformed(null);
        }
    }                                     

    private void observerButtonActionPerformed(ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                       

    private void rearrangeButtonActionPerformed(ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void flipButtonActionPerformed(ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void portFieldActionPerformed(ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void leaveBtnActionPerformed(ActionEvent evt) {                                         

        // terminar conexão
        Response resp = client.target(baseUri+"users/")
                              .path(myUser.getUsername())
                              .request()
                              .delete();
        int codeResp = resp.getStatus();
        resp.close();
        
        if(codeResp == 204){
            chessBoard.removeAll();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    SquarePanel sqPanel = new SquarePanel(i, j, this);
                    sqPanel.setBackgroundGray((i + j) % 2);
                    board[i][j] = sqPanel;
                    chessBoard.add(sqPanel);

                }
            }

            player1SpareBoard.setBackground(null);
            player2SpareBoard.setBackground(null);

            leaveBtn.setEnabled(false);
            messagesArea.setText("");
            messageField.setText("");
            infoField.setText("");
            joinBtn.setEnabled(true);

            ipField.setEditable(true);
            ipField.setEnabled(true);
            portField.setEditable(true);
            portField.setEnabled(true);
            nameField.setEditable(true);
            nameField.setEnabled(true);

            titleGame.setText("Please, enter a game.");
            panelBtns.setVisible(false);
            messagesArea.setEnabled(false);
            messageField.setEditable(false);
            messageField.setEnabled(false);
            sendBtn.setEnabled(false);
        } else{
            infoField.setText("Erro desconhecido");
        }
        client.close();
    }                                        

    private void joinBtnActionPerformed(ActionEvent evt) { 
        
        client = ClientBuilder.newClient();
        baseUri = "http://"+ ipField.getText() + ":" + portField.getText() + "/chess/api/";

        User newCliente = new User();
        newCliente.setUsername(nameField.getText());
        // inicia conexão
        Response resp = client.target(baseUri+"users/")
            .request()
            .accept("application/json")
            .post(Entity.json(newCliente));
        
        int codeResp = resp.getStatus();
        
        System.out.println(resp.toString());
                
        if(codeResp == 200){
            
            List<User> users = resp.readEntity(new GenericType<List<User>>(){});
            
            for (User user : users) {
                if(user.isPlayer()){
                    if(user.getPosition() == 1){
                        namePlayer1.setText(user.getUsername());
                    } else{
                        namePlayer2.setText(user.getUsername());
                    }
                }
                if(user.getUsername().equals(newCliente.getUsername())){
                    myUser = user;
                }   
            }
            
            if(!myUser.isPlayer()){
                observerButton.setText("Turn player");
                cleanButton.setVisible(false);
                flipButton.setVisible(false);
                rearrangeButton.setVisible(false);
                positionButton.setVisible(false);
            }
            else{
                observerButton.setText("Turn observer");
                cleanButton.setVisible(true);
                flipButton.setVisible(true);
                rearrangeButton.setVisible(true);
                positionButton.setVisible(true);
            }
            panelBtns.setVisible(true);
            
            //bloqueia form de conexão
            this.joinBtn.setEnabled(false);
            this.leaveBtn.setEnabled(true);

            this.ipField.setEditable(false);
            this.ipField.setEnabled(false);
            this.portField.setEditable(false);
            this.portField.setEnabled(false);
            this.nameField.setEditable(false);
            this.nameField.setEnabled(false);

            //Arruma chat
            titleGame.setText("Game on.");
            messagesArea.setEnabled(true);
            messageField.setEditable(true);
            messageField.setEnabled(true);
            sendBtn.setEnabled(true);
            this.messageField.requestFocus();
            
            // pedido de jogo
            this.startGame();
            
        }
        else
            if( codeResp == 409){
                infoField.setText("Nome inválido.");
            } else {
                infoField.setText("Erro desconhecido: "+codeResp);
            }
        
        resp.close();
    }
    
    private void startGame() {
        Response resp = client.target(baseUri)
            .request()
            .accept("application/json")
            .get();
        
        int codeResp = resp.getStatus();
        
        System.out.println(resp.toString());
                
        if(codeResp == 200){
            
            Chess chess = resp.readEntity(Chess.class);

            chessBoard.removeAll();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    SquarePanel sqPanel = new SquarePanel(i, j, this);
                    sqPanel.setBackgroundColor((i + j) % 2);
                    board[i][j] = sqPanel;
                    chessBoard.add(sqPanel);

                }
            }

            player1SpareBoard.setBackground(new Color(108,108,195));
            player2SpareBoard.setBackground(new Color(206,206,255));
            
            for (Piece piece : chess.getChessPieces()) {
                byte[] posit = piece.getPosition();
                byte[] piecetype = piece.getPiece();
                board[posit[0]][posit[1]].setPiece(piecetype[0], piecetype[1]);
            };
        }
        else
            infoField.setText("Erro ao receber jogo.");
        
    }

    public void selected(int x, int y) {
        System.out.printf("mouse pressed at: %d - %d\n", x, y);
    }
    
    public void setBoard(){
        for(int i = 0; i < 8; i++){
            board[6][i].setPiece(0, 0);
        }
        board[7][0].setPiece(0, 3);
        board[7][1].setPiece(0, 1);
        board[7][2].setPiece(0, 2);
        board[7][3].setPiece(0, 4);
        board[7][4].setPiece(0, 5);
        board[7][5].setPiece(0, 2);
        board[7][6].setPiece(0, 1);
        board[7][7].setPiece(0, 3);

        
        for(int i = 0; i < 8; i++){
            board[1][i].setPiece(1, 0);
        }
        board[0][0].setPiece(1, 3);
        board[0][1].setPiece(1, 1);
        board[0][2].setPiece(1, 2);
        board[0][3].setPiece(1, 4);
        board[0][4].setPiece(1, 5);
        board[0][5].setPiece(1, 2);
        board[0][6].setPiece(1, 1);
        board[0][7].setPiece(1, 3);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }              


}
