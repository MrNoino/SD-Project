package client.view;

import client.model.Message;
import client.model.Piece;
import client.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ResponseProcessingException;
import java.util.List;

public class ClientFrame extends JFrame {
    
    // <editor-fold defaultstate="collapsed" desc="Variables declaration - View">
    private JPanel mainPainel; 
    
    private JPanel flexPanel;
    private JLabel titleGame;
    private JPanel gameBoard;
    private JPanel chessBoard;
    private JPanel player1SpareBoard;
    private JPanel player2SpareBoard;
    private JLabel namePlayer1;
    private JLabel namePlayer2;
    
    private JPanel observePanel;
    private JScrollPane observeScroll;
    private JTextField observeField;
    private JLabel observeLabel;
    
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
    
    private JPanel panelBtns;
    private JButton observerButton;
    private JButton positionButton;
    private JButton rearrangeButton;
    private JButton cleanButton;
    private JButton flipButton;
    private boolean flipButtonOrganizer;
    private JLabel erroField;
    
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Variables declaration - Controller">
    private SquarePanel[][] board = new SquarePanel[8][8];
    
    static Client client;
    static String baseUri;
    
    private User myUser;
    
    private Thread upChess;
    private Thread upUsers;
    private Thread upChat;
    
    private SquarePanel firstClick;
    private SquarePanel secondClick;
    
    //</editor-fold>

    public ClientFrame() { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1400,840));
        setResizable(true);
        setTitle("Chess Game");
        
        initComponents();
        
        titleGame.setText("Please, enter a game.");
        titleGame.setForeground(new Color(51, 51, 51));
        sendBtn.setEnabled(false);
        messagesArea.setEnabled(false);
        messageField.setEditable(false);
        messageField.setEnabled(false);
        panelBtns.setVisible(false);
        
        SquarePanel.loadPieceImages();
        chessBoard.setLayout(new GridLayout(8,8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                SquarePanel sqPanel = new SquarePanel((byte) i, (byte) j, this, false);
                sqPanel.setBackgroundGray((i + j) % 2);
                board[i][j] = sqPanel;
                chessBoard.add(sqPanel);
            }
        }
    }// </editor-fold>

    private void initComponents() { // <editor-fold defaultstate="collapsed" desc="collapsed">

        mainPainel = new JPanel();
        
        chatPanel = new JPanel();
        messageField = new JTextField();
        jScrollArea = new JScrollPane();
        messagesArea = new JTextArea();
        msgLabel = new JLabel();
        sendBtn = new JButton();
        infoField = new JLabel();
        
        configPanel = new JPanel();
        ipField = new JTextField();
        ipLabel = new JLabel();
        portField = new JTextField();
        portLabel = new JLabel();
        nameLabel = new JLabel();
        nameField = new JTextField();
        leaveBtn = new JButton();
        joinBtn = new JButton();
        
        titleGame = new JLabel();
        flexPanel = new JPanel();
        gameBoard = new JPanel();
        chessBoard = new JPanel();
        player2SpareBoard = new JPanel();
        player1SpareBoard = new JPanel();
        namePlayer2 = new JLabel();
        namePlayer1 = new JLabel();
        
        observePanel = new JPanel();
        observeLabel = new JLabel();
        observeScroll = new JScrollPane();
        observeField = new JTextField();        
        panelBtns = new JPanel();
        observerButton = new JButton();
        rearrangeButton = new JButton();
        cleanButton = new JButton();
        positionButton = new JButton();
        flipButton = new JButton();
        flipButtonOrganizer = true;
        erroField = new JLabel();
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
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

        portField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                portFieldKeyTyped(evt);
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
                        .addComponent(portField, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addComponent(ipField))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(leaveBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(joinBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(leaveBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        titleGame.setFont(new Font("Gill Sans MT", 0, 18));
        titleGame.setForeground(new Color(51, 51, 51));
        titleGame.setHorizontalAlignment(SwingConstants.CENTER);
        titleGame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        titleGame.setFocusable(false);
        titleGame.setHorizontalTextPosition(SwingConstants.CENTER);

        gameBoard.setBorder(BorderFactory.createEtchedBorder());
        gameBoard.setMaximumSize(new Dimension(680, 468));
        gameBoard.setMinimumSize(new Dimension(680, 468));
        gameBoard.setName("");

        chessBoard.setBorder(BorderFactory.createEtchedBorder());
        chessBoard.setPreferredSize(new Dimension(400, 400));

        player2SpareBoard.setBorder(BorderFactory.createEtchedBorder());
        player2SpareBoard.setPreferredSize(new Dimension(120, 400));

        GridLayout player2SpareBoardLayout = new GridLayout(8,2);
        player2SpareBoard.setLayout(player2SpareBoardLayout);
        

        player1SpareBoard.setBorder(BorderFactory.createEtchedBorder());
        player1SpareBoard.setPreferredSize(new Dimension(120, 400));

        GridLayout player1SpareBoardLayout = new GridLayout(8,2);
        player1SpareBoard.setLayout(player1SpareBoardLayout);
        

        namePlayer2.setFont(new Font("Gill Sans MT", 0, 14));
        namePlayer2.setText("Player 2");

        namePlayer1.setFont(new Font("Gill Sans MT", 0, 14));
        namePlayer1.setHorizontalAlignment(SwingConstants.TRAILING);
        namePlayer1.setText("Player 1");

        GroupLayout gameBoardLayout = new GroupLayout(gameBoard);
        gameBoard.setLayout(gameBoardLayout);
        gameBoardLayout.setHorizontalGroup(
            gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gameBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(gameBoardLayout.createSequentialGroup()
                        .addComponent(player2SpareBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(namePlayer2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(gameBoardLayout.createSequentialGroup()
                        .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(gameBoardLayout.createSequentialGroup()
                                .addGap(132, 132, 132)
                                .addComponent(chessBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(namePlayer1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(player1SpareBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gameBoardLayout.setVerticalGroup(
            gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gameBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(gameBoardLayout.createSequentialGroup()
                        .addComponent(namePlayer2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(gameBoardLayout.createSequentialGroup()
                                .addGap(406, 406, 406)
                                .addComponent(namePlayer1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                            .addComponent(player1SpareBoard, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
                    .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(chessBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(player2SpareBoard, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BorderLayout gameLayout = new BorderLayout();
        flexPanel.setLayout(gameLayout);
        flexPanel.add(titleGame, BorderLayout.NORTH);
        JPanel at = new JPanel();
        at.add(gameBoard);
        flexPanel.add(at, BorderLayout.CENTER);
        
        observeLabel.setFont(new Font("Gill Sans MT", 0, 12));
        observeLabel.setText("Observers: ");

        observeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        observeField.setEditable(false);
        observeField.setFocusable(false);
        observeField.setFont(new Font("Gill Sans MT", 0, 14));
        observeField.setBackground(new Color(220,224,230));
        observeScroll.setViewportView(observeField);
        observeScroll.setEnabled(false);
        observeScroll.setFocusable(false);
        
        GroupLayout observePanelLayout = new GroupLayout(observePanel);
        observePanel.setLayout(observePanelLayout);
        observePanelLayout.setHorizontalGroup(
            observePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, observePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(observeLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(observeScroll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addContainerGap())
        );
        observePanelLayout.setVerticalGroup(
            observePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, observePanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(observePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(observeScroll, GroupLayout.DEFAULT_SIZE,  54, GroupLayout.PREFERRED_SIZE)
                    .addComponent(observeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        flexPanel.add(observePanel, BorderLayout.SOUTH);

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
                .addComponent(flexPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        cleanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cleanButtonActionPerformed(evt);
            }
        });

        positionButton.setText("Change position");
        positionButton.setPreferredSize(new Dimension(120, 30));
        positionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                positionButtonActionPerformed(evt);
            }
        });

        flipButton.setText("Flip board");
        flipButton.setPreferredSize(new Dimension(90, 30));
        flipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                flipButtonActionPerformed(evt);
            }
        });

        erroField.setText("");
        erroField.setHorizontalTextPosition(SwingConstants.CENTER);
        erroField.setForeground(new Color(255,51,51));

        GroupLayout panelBtnsLayout = new GroupLayout(panelBtns);
        panelBtns.setLayout(panelBtnsLayout);
        panelBtnsLayout.setHorizontalGroup(panelBtnsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBtnsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(observerButton, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(erroField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flipButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cleanButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rearrangeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBtnsLayout.setVerticalGroup(panelBtnsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBtnsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBtnsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelBtnsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(observerButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cleanButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(flipButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBtnsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(rearrangeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(positionButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(erroField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");


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

    private void exitMenuItemActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed">
        
        if(myUser != null){
            leaveBtnActionPerformed(null);
//            System.out.println("Log out realizado.");
        }
        
        System.exit(0);
    }// </editor-fold>
    
    private void sendBtnActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed">

        this.sendBtn.setEnabled(false);
        this.infoField.setText("");
        String txt = this.messageField.getText();
        
        if (txt.isEmpty() || txt.isBlank()) {
            this.infoField.setText("Mensagem não deve estar vazio.");
        } else {
            Message msg = new Message();
            msg.setContent(txt);
            msg.setUser(myUser.getUsername());
            
            Response resp = null;
            try {
                resp = client.target(baseUri)
                        .path("chat/")
                        .request()
                        .accept("application/json")
                        .post(Entity.json(msg));
            } catch (ProcessingException ex){
//                System.out.println(ex);
                closeGame("Sorry, enter a game.");
            }
            
            if(resp != null){
            
                int codeResp = resp.getStatus();

//                System.out.println(resp.toString());
                resp.close();

                if(codeResp == 204){
                    messageField.setText("");
                } else{
                    if(codeResp == 400){
                        infoField.setText("Mensagem vazia.");
                    }else{
                        if(codeResp == 404){
                            infoField.setText("Utilizador inválido.");
                            closeGame("Please, enter a game.");
                        } else{
                            System.out.println("Error desconnhecido: "+ codeResp);
                            closeGame("Please, enter a game.");
                        }
                    }
                }
            }
        }
        this.messageField.requestFocus();
            
        this.sendBtn.setEnabled(true);
        this.messageField.requestFocus();
    }// </editor-fold>
    
    private void messageFieldKeyTyped(KeyEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        if (evt.getKeyChar() == '\n') {
            sendBtnActionPerformed(null);
        }
    }// </editor-fold>

    private void portFieldKeyTyped(KeyEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        if ( !Character.isDigit(evt.getKeyChar()) ) {
            evt.consume();
        }
    }// </editor-fold>

    private void flipButtonActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed">
        
        if(flipButtonOrganizer){
            namePlayer1.setHorizontalAlignment(SwingConstants.LEADING);
            namePlayer2.setHorizontalAlignment(SwingConstants.TRAILING);

            GroupLayout gameBoardLayout = new GroupLayout(gameBoard);
            gameBoard.setLayout(gameBoardLayout);
            gameBoardLayout.setHorizontalGroup(
                gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gameBoardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(gameBoardLayout.createSequentialGroup()
                            .addComponent(player1SpareBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(namePlayer1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(gameBoardLayout.createSequentialGroup()
                            .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(gameBoardLayout.createSequentialGroup()
                                    .addGap(132, 132, 132)
                                    .addComponent(chessBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(namePlayer2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(player2SpareBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            gameBoardLayout.setVerticalGroup(
                gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gameBoardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gameBoardLayout.createSequentialGroup()
                            .addComponent(namePlayer1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(gameBoardLayout.createSequentialGroup()
                                    .addGap(406, 406, 406)
                                    .addComponent(namePlayer2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addComponent(player2SpareBoard, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
                        .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(chessBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(player1SpareBoard, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            
            flipBoard();
            flipButtonOrganizer = false;
        }
        else{
            namePlayer1.setHorizontalAlignment(SwingConstants.TRAILING);
            namePlayer2.setHorizontalAlignment(SwingConstants.LEADING);
            
            GroupLayout gameBoardLayout = new GroupLayout(gameBoard);
            gameBoard.setLayout(gameBoardLayout);
            gameBoardLayout.setHorizontalGroup(
                gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gameBoardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(gameBoardLayout.createSequentialGroup()
                            .addComponent(player2SpareBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(namePlayer2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(gameBoardLayout.createSequentialGroup()
                            .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(gameBoardLayout.createSequentialGroup()
                                    .addGap(132, 132, 132)
                                    .addComponent(chessBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(namePlayer1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(player1SpareBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            gameBoardLayout.setVerticalGroup(
                gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gameBoardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gameBoardLayout.createSequentialGroup()
                            .addComponent(namePlayer2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(gameBoardLayout.createSequentialGroup()
                                    .addGap(406, 406, 406)
                                    .addComponent(namePlayer1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addComponent(player1SpareBoard, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
                        .addGroup(gameBoardLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(chessBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(player2SpareBoard, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            
            flipBoard();
            flipButtonOrganizer = true;
        }
    }// </editor-fold>
    
    private synchronized void flipBoard(){ // <editor-fold defaultstate="collapsed" desc="collapsed">
        
        chessBoard.removeAll();
        if(flipButtonOrganizer) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessBoard.add(board[7 - i][7 - j]);
                }
            }
        } else{
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessBoard.add(board[i][j]);
                }
            }
        }
        chessBoard.repaint();
    }// </editor-fold>

    private void observerButtonActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        erroField.setText("");
        User sendUser = new User();
        sendUser.setUsername(myUser.getUsername());
        sendUser.setPlayer(!myUser.isPlayer());
        
        Response resp = null;
        try {
            resp = client.target(baseUri)
                    .path("users/status")
                    .request()
                    .put(Entity.json(sendUser));
        } catch (ProcessingException ex){
//            System.out.println(ex);
            closeGame("Sorry, enter a game.");
        }

        if(resp != null){

            int codeResp = resp.getStatus();

//            System.out.println(resp.toString());

            if(codeResp == 200){
                myUser = resp.readEntity(User.class);

                decideBtns();
                startGame();
                resp.close();
            } else{
                resp.close();
                if(codeResp == 409){
                    erroField.setText("Vagas de jogadores indisponíveis.");
                }
                else{
                    erroField.setText("Erro ao tornar observador|jogador.");
                    closeGame("Please, enter a game.");
                }
            }
        }
    }// </editor-fold>
    
    private void positionButtonActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        
        erroField.setText("");
        User sendUser = new User();
        sendUser.setUsername(myUser.getUsername());
        
        Response resp = null;
        try {
            resp = client.target(baseUri)
                    .path("users/position")
                    .request()
                    .put(Entity.json(sendUser));
        } catch (ProcessingException ex){
//            System.out.println(ex);
            closeGame("Sorry, enter a game.");
        }

        if(resp != null){
        
            int codeResp = resp.getStatus();

//            System.out.println(resp.toString());

            if(codeResp == 200){
                myUser = resp.readEntity(User.class);
                resp.close();
            } else{
                resp.close();
                if(codeResp == 409){
                    erroField.setText("Vaga de jogador indisponível.");
                }
                else{
                    erroField.setText("Erro ao trocar posição.");
                    closeGame("Please, enter a game.");
                }
            }
        }
    }// </editor-fold>
    
    private void cleanButtonActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
                
        erroField.setText("");
        Response resp = null;
        try {
            resp = client.target(baseUri)
                    .path("board/clean")
                    .request()
                    .put(Entity.text(""));
        } catch (ProcessingException ex){
//            System.out.println(ex);
            closeGame("Sorry, enter a game.");
        }

        if(resp != null){
        
            int codeResp = resp.getStatus();

//            System.out.println(resp.toString());
            resp.close();

            if(codeResp != 204){
                erroField.setText("Erro ao limpar tabuleiro.");
                closeGame("Please, enter a game.");
            }
        }
    }// </editor-fold>
    
    private void rearrangeButtonActionPerformed(ActionEvent evt) {// <editor-fold defaultstate="collapsed" desc="collapsed"> 
                
        erroField.setText("");
        Response resp = null;
        try {
            resp = client.target(baseUri)
                    .path("board/rearrange")
                    .request()
                    .put(Entity.text(""));
        } catch (ProcessingException ex){
//            System.out.println(ex);
            closeGame("Sorry, enter a game.");
        }

        if(resp != null){
        
            int codeResp = resp.getStatus();

//            System.out.println(resp.toString());
            resp.close();

            if(codeResp != 204){
                erroField.setText("Erro ao arrumar tabuleiro.");
                closeGame("Please, enter a game.");
            }
        }
        
    }// </editor-fold>

    private void leaveBtnActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed"> 

        erroField.setText("");
        
        Response resp = null;
        try {
            resp = client.target(baseUri)
                    .path("users")
                    .path(myUser.getUsername())
                    .request()
                    .delete();
        } catch (ProcessingException ex){
//            System.out.println(ex);
            closeGame("Sorry, enter a game.");
        }

        if(resp != null){
            int codeResp = resp.getStatus();
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//            System.out.println(resp.toString());
            resp.close();

            if(codeResp == 204 ){
                closeGame("Please, enter a game.");
            } else{
                erroField.setText("Erro :(");
                System.out.println("Erro desconhecido on leave: "+codeResp);
                closeGame("Please, enter a game.");
            }
        }
    }// </editor-fold>
    
    private void closeGame(String message){
        if(upChess != null && upChess.isAlive()){
                upChess.interrupt();
//                if(upChess.isInterrupted()) System.out.println("Thread upChess interrompida.");
            }

            if(upUsers != null && upUsers.isAlive()){
                upUsers.interrupt();
//                if(upUsers.isInterrupted()) System.out.println("Thread upUsers interrompida.");
            }

            if(upChat != null && upChat.isAlive()){
                upChat.interrupt();
//                if(upChat.isInterrupted()) System.out.println("Thread upChat interrompida.");
            }
            
            chessBoard.removeAll();
            player1SpareBoard.removeAll();
            player2SpareBoard.removeAll();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    SquarePanel sqPanel = new SquarePanel((byte) i, (byte) j, this, false);
                    sqPanel.setBackgroundGray((i + j) % 2);
                    board[i][j] = sqPanel;
                    chessBoard.add(sqPanel);

                }
            }

            player1SpareBoard.setBackground(null);
            player2SpareBoard.setBackground(null);
            namePlayer1.setText("Player 1");
            namePlayer2.setText("Player 2");
            observeField.setText(null);

            leaveBtn.setEnabled(false);
            messagesArea.setText("");
            messageField.setText("");
            joinBtn.setEnabled(true);

            ipField.setEditable(true);
            ipField.setEnabled(true);
            portField.setEditable(true);
            portField.setEnabled(true);
            nameField.setEditable(true);
            nameField.setEnabled(true);

            titleGame.setText(message);
            panelBtns.setVisible(false);
            messagesArea.setEnabled(false);
            messageField.setEditable(false);
            messageField.setEnabled(false);
            sendBtn.setEnabled(false);
            
            myUser = null;
            client.close();
    }

    private void joinBtnActionPerformed(ActionEvent evt) { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
                
        infoField.setText("");
        if(ipField.getText().isEmpty() || portField.getText().isEmpty() || nameField.getText().isEmpty()){
            infoField.setText("Todos os campos devem ser preenchidos.");
        } else{            
            client = ClientBuilder.newClient();
            baseUri = "http://"+ ipField.getText() + ":" + portField.getText() + "/chess/api/";

            User newCliente = new User();
            newCliente.setUsername(nameField.getText());
            // inicia conexão
            Response resp = null;
            try {
                resp = client.target(baseUri)
                        .path("users/")
                        .request()
                        .accept("application/json")
                        .post(Entity.json(newCliente));
            } catch (ResponseProcessingException ex){
//                System.out.println(ex);
                titleGame.setText("Erro :(");
                titleGame.setForeground(new Color(255, 51, 51));
            } catch (IllegalArgumentException | ProcessingException ex){
//                System.out.println(ex);
                titleGame.setText("Erro ao encontrar servidor, rever endereço de servidor.");
                titleGame.setForeground(new Color(255, 51, 51));
            }

            if(resp != null){
                int codeResp = resp.getStatus();

//                System.out.println(resp.toString());

                if(codeResp == 200){
                    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                    List<User> users = resp.readEntity(new GenericType<List<User>>(){});
                    setUsers(users, newCliente.getUsername());

                    decideBtns();
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
                    titleGame.setForeground(new Color(51, 51, 51));
                    messagesArea.setEnabled(true);
                    messageField.setEditable(true);
                    messageField.setEnabled(true);
                    sendBtn.setEnabled(true);
                    this.messageField.requestFocus();

                    player1SpareBoard.setBackground(new Color(108,108,195));
                    player2SpareBoard.setBackground(new Color(206,206,255));
                    // pedido de jogo
                    this.startGame();
                    // thread de atualização do jogo
                    upChess = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            threadGameAsync();
                        }
                    });
                    upChess.start();
                    
                    // thread de atualização dos utilizadores
                    upUsers = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            threadUsersAsync();
                        }
                    });
                    upUsers.start();

                    // thread de atualização das mensagens
                    upChat = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            threadChatAsync();
                        }
                    });
                    upChat.start();
                }
                else{
                    if( codeResp == 409){
                        titleGame.setText("Nome de utilizador indisponível.");
                        titleGame.setForeground(new Color(255, 51, 51));
                    } else {
                        titleGame.setText("Erro :(");
                        titleGame.setForeground(new Color(255, 51, 51));
                        System.out.println("Erro desconhecido on join: "+codeResp);
                    }
                }
                resp.close();
            }
        }
    }// </editor-fold>
    
    private void threadUsersAsync() { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        while (true) {
            Response resp = null;
            try{
                resp = client.target(baseUri)
                        .path("users-async")
                        .request()
                        .accept("application/json")
                        .get();
            } catch(Exception ex){
//                System.out.println(ex.getMessage());
                break;
            }
            if(resp != null){
                int codeResp = resp.getStatus();

//                System.out.println(resp.toString());

                if(codeResp == 200){
                    List<User> users = resp.readEntity(new GenericType<List<User>>(){});
                    setUsers(users, myUser.getUsername());
                }
                else {
                    System.out.println("Erro desconhecido na threadUsersAsync: "+codeResp);
                }
                resp.close();
            }
        }
    }// </editor-fold>
    
    private void threadChatAsync() { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        while (true) { 
            Response resp = null;
            try{
                resp = client.target(baseUri)
                        .path("chat-async")
                        .request()
                        .accept("application/json")
                        .get();
            } catch(Exception ex){
//                System.out.println(ex.getMessage());
                break;
            }

            if(resp != null){
                int codeResp = resp.getStatus();

//                System.out.println(resp.toString());

                if(codeResp == 200){
                    Message msg = resp.readEntity(Message.class);
                    messagesArea.append(msg.toString());
                    JScrollBar sb = jScrollArea.getVerticalScrollBar();
                    sb.setValue(sb.getMaximum());
                }
                else {
                    System.out.println("Erro desconhecido na threadChatAsync: "+codeResp);
                }
                resp.close();
            }
        }
    }// </editor-fold>
    
    private void threadGameAsync() { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        while (true) { 
            Response resp = null;
            try{
                resp = client.target(baseUri)
                        .path("chess-async")
                        .request()
                        .accept("application/json")
                        .get();
            } catch(Exception ex){
//                System.out.println(ex.getMessage());
                break;
            }

            if(resp != null){
                int codeResp = resp.getStatus();

//                System.out.println(resp.toString());

                if(codeResp == 200){
                    List<Piece> chess = resp.readEntity(new GenericType<List<Piece>>(){});
                    setBoard(chess);
                }
                else {
                    System.out.println("Erro desconhecido na threadGameAsync: "+codeResp);
                }
                resp.close();
            }
        }
    }// </editor-fold>
    
    private void startGame() { // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        erroField.setText("");
        Response resp = null;
        try {
            resp = client.target(baseUri)
                .request()
                .accept("application/json")
                .get();
        } catch (ProcessingException ex){
//            System.out.println(ex);
            closeGame("Sorry, enter a game.");
        }

        if(resp != null){
            int codeResp = resp.getStatus();

//            System.out.println(resp.toString());

            if(codeResp == 200){
                List<Piece> chess = resp.readEntity(new GenericType<List<Piece>>(){});
                setBoard(chess);
                resp.close();
            } else{
                resp.close();
                erroField.setText("Erro ao receber jogo.");
                System.out.println("Erro desconhecido on startGame: "+codeResp);
                closeGame("Please, enter a game.");
            }
        }
    }// </editor-fold>

    private void decideBtns(){ // <editor-fold defaultstate="collapsed" desc="collapsed"> 
        if(myUser.isPlayer()){
            observerButton.setText("Turn observer");
            cleanButton.setVisible(true);
            flipButton.setVisible(true);
            rearrangeButton.setVisible(true);
            positionButton.setVisible(true);
        }
        else{
            observerButton.setText("Turn player");
            cleanButton.setVisible(false);
            flipButton.setVisible(false);
            rearrangeButton.setVisible(false);
            positionButton.setVisible(false);
        }
    }// </editor-fold>
    
    public void selected(SquarePanel sp) { // <editor-fold defaultstate="collapsed" desc="collapsed">
        erroField.setText("");
        Piece piece = sp.getPiece();
        
        if(firstClick == null){
            if(piece.getType().length > 0 && piece.getType()[0] != -1){
                firstClick = sp;
            }
        } else{
            synchronized (firstClick) {
                if(piece.getPosition().length > 0 && !piece.hasPosition(firstClick.getPiece().getPosition())){
                    Piece[] send = new Piece[]{firstClick.getPiece(), piece};

                    Response resp = null;
                    try {
                        resp = client.target(baseUri)
                            .path("pieces")
                            .request()
                            .accept("application/json")
                            .put(Entity.json(send));
                    } catch (ProcessingException ex){
//                        System.out.println(ex);
                        closeGame("Sorry, enter a game.");
                    }

                    if(resp != null){

                        int codeResp = resp.getStatus();

//                        System.out.println(resp.toString());
                        resp.close();

                        if(codeResp == 200){
                            firstClick = null;
                        }
                        else {
                            if(codeResp == 409){
                                erroField.setText("Movimento inválido.");
                            } else{
                                erroField.setText("Erro ao realizar jogada.");
                                System.out.println("Erro desconhecido move piece: "+codeResp);
                                closeGame("Please, enter a game.");
                            }
                        }
                    }
                }
            }
        }
    }// </editor-fold>
    
    public synchronized void setBoard(List<Piece> chess){ // <editor-fold defaultstate="collapsed" desc="collapsed">
        firstClick = null;
        chessBoard.removeAll();
        player1SpareBoard.removeAll();
        player2SpareBoard.removeAll();
        
        if(flipButtonOrganizer) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    SquarePanel sqPanel = new SquarePanel((byte) i, (byte) j, this, myUser.isPlayer());
                    sqPanel.setBackgroundColor((i + j) % 2 != 0);
                    board[i][j] = sqPanel;
                    chessBoard.add(sqPanel);

                }
            }
        } else{
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // Calcula a posição invertida
                    int invI = 7 - i;
                    int invJ = 7 - j;
                    SquarePanel sqPanel = new SquarePanel((byte) invI, (byte) invJ, this, myUser.isPlayer());
                    sqPanel.setBackgroundColor((invI + invJ) % 2 != 0);
                    board[invI][invJ] = sqPanel;
                    chessBoard.add(sqPanel);
                }
            }
        }
        
        for (Piece piece : chess) {
            byte[] position = piece.getPosition();
            byte[] piecetype = piece.getType();
            if(position[0] >= 0 && position[0] < 8 && position[1] >= 0 && position[1] < 8 ){
                board[position[0]][position[1]].setPiece(piecetype[0], piecetype[1]);
            } else{
                SquarePanel sq = new SquarePanel((byte) -1, (byte) -1,this,myUser.isPlayer());
                sq.setBackground(null);
                sq.setPiece(piecetype[0],piecetype[1]);
                if(piecetype[0] == 0){
                    player1SpareBoard.add(sq);
                } else{
                    player2SpareBoard.add(sq);
                }
            }
        }
        
        if(myUser.isPlayer()){
            SquarePanel sq1 = new SquarePanel((byte) -1, (byte) -1, this, myUser.isPlayer());
            sq1.setBackgroundColor(true);
            if(player1SpareBoard.getComponentCount() < 16){
                player1SpareBoard.add(sq1);
            }
            SquarePanel sq2 = new SquarePanel((byte) -1, (byte) -1, this, myUser.isPlayer());
            sq2.setBackgroundColor(false);
            if(player2SpareBoard.getComponentCount() < 16){
                player2SpareBoard.add(sq2);
            }
        }

        chessBoard.repaint();
        player1SpareBoard.repaint();
        player2SpareBoard.repaint();
    }// </editor-fold>
    
    public void setUsers(List<User> users, String usernameNewCliente){ // <editor-fold defaultstate="collapsed" desc="collapsed">
        String observersName = "";
        namePlayer1.setText("Player 1");
        namePlayer2.setText("Player 2");

        for (User user : users) {
            if(user.isPlayer()){
                if(user.getPosition() == 1){
                    namePlayer1.setText(user.getUsername());
                } else{
                    namePlayer2.setText(user.getUsername());
                }
            } else{
                observersName += "|  "+user.getUsername()+"  |";
            }
            if(user.getUsername().equals(usernameNewCliente)){
                myUser = user;
            }   
        }
        observeField.setText(observersName);
    }//</editor-fold> 
    
    public static void main(String args[]) { // <editor-fold defaultstate="collapsed" desc="collapsed">
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

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }//</editor-fold>       
}
