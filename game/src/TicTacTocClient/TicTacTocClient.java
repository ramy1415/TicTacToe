/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacTocClient;

import Facilities.Request;
import Facilities.RequestType;
import OnlinePlay.XoOnlineController;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import tictactoe.GameController;

/**
 *
 * @author Mahmoud
 */
public class TicTacTocClient extends Thread {

    Socket serverSocket;
    public ObjectInputStream comingStream;
    public ObjectOutputStream goingStream;
    Request request;

    public static String response;

    //ramy
    private boolean busy = false;
    public static boolean someoneleft = false;
    static Parent root;
    static String myip;
    static ActionEvent event;
    static Stage window;
    static Stage mystage;
    static String oponent;
    public static boolean nowplaying;
    public static String winScores;
    public static String lossScores;
    public static String mySympol;
    public static String hisSympol;
    ObservableList<String> clients = FXCollections.observableArrayList();
    private static Stage onlineStage;

    private final String lose = "Sorry! You lost!";
    private final String tie = "It was a tie!";
    @FXML
    private MediaView mediaView;

    public TicTacTocClient(Socket playerSocket, ActionEvent _event) throws IOException {

        //getting ip for this machine
        String s = InetAddress.getLocalHost().toString();
        StringTokenizer s1 = new StringTokenizer(s, "/");
        s1.nextToken();
        myip = "/" + s1.nextToken();

        event = _event;

        serverSocket = playerSocket;
        comingStream = new ObjectInputStream(serverSocket.getInputStream());
        goingStream = new ObjectOutputStream(serverSocket.getOutputStream());
        setDaemon(true);
        start();
    }

    public ArrayList<String> viewRes = new ArrayList<>();

    @Override
    public void run() {
        while (true) {
            try {
                request = (Request) comingStream.readObject();
                clientrequestHandler(request);
            } catch (IOException ex) {
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.ERROR, "Server disconnected please try to log in later!", ButtonType.OK);
                    a1.showAndWait();
                    Platform.exit();
                });
                return;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void clientrequestHandler(Request req) throws IOException {
        switch (req.getType()) {
            case LOGIN_SUCCESS:
                response = "success";
                break;
            case LOGIN_FAILURE:
                response = "failure";
                break;
            case REGISTER_SUCCESS:
                response = "success";
                break;
            case REGISTER_FAILURE:
                response = "failure";
                break;
            case ALREADY_LOGINNED:
                response = "loginned";
                break;

            //cases i added
            case ASKTOPLAY:
                if (!busy) {
                    Platform.runLater(() -> {
                        busy = true;
                        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("myname") + " sent you a request .. Do you want to play ?!", ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> result = a1.showAndWait();
                        if (result.get() == ButtonType.YES) {
                            Request playingrequest = new Request(RequestType.PLAYING);
                            playingrequest.setData("myname", GameController.myname);
                            try {
                                goingStream.writeObject(playingrequest);
                            } catch (IOException ex) {
                                Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            OnlinePlay.XoOnlineController.myturn = false;
                            oponent = req.getData("myname");
                            Request acceptrequest = new Request(RequestType.ACCEPT);
                            acceptrequest.setData("targetname", req.getData("myname"));
                            acceptrequest.setData("myname", req.getData("targetname"));
                            try {
                                goingStream.writeObject(acceptrequest);
                                //temporary testing
                                Platform.runLater(() -> {

                                    try {
                                        root = FXMLLoader.load(getClass().getResource("/OnlinePlay/XoOnlineView.fxml"));

                                    } catch (IOException ex) {
                                        Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    Scene singleScene = new Scene(root);
                                    mystage.setScene(singleScene);
                                    onlineStage = mystage;
                                    nowplaying=true;

                                    changeTurn(req.getData("myname"));
                                    mystage.setResizable(false);
                                    mystage.show();
                                    mystage.setOnCloseRequest((event) -> {
                                        try {
                                            if (nowplaying) {
                                                if (XoOnlineController.firstleave) {
                                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION, GameController.myname + " you will lose if you leave during the game!", ButtonType.OK);
                                                    Optional<ButtonType> result2 = a2.showAndWait();
                                                        Request notplayingrequest = new Request(RequestType.LEAVE);
                                                        notplayingrequest.setData("myname", GameController.myname);
                                                        notplayingrequest.setData("oponent", oponent);
                                                        goingStream.writeObject(notplayingrequest);
                                                        TicTacTocClient.someoneleft = true;                   
                                                } else {
                                                    Request leave = new Request(RequestType.NOTPLAYING);
                                                    leave.setData("myname", GameController.myname);
                                                    leave.setData("oponent", oponent);
                                                    goingStream.writeObject(leave);
                                                    TicTacTocClient.someoneleft = true;
                                                }
                                            }
                                            Platform.exit();
                                        } catch (IOException ex) {
                                            Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                                    TicTacTocClient.someoneleft = false;
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION, GameController.myname + " Do you want to play X ?", ButtonType.YES, ButtonType.NO);
                                    Optional<ButtonType> result2 = a2.showAndWait();
                                    if (result2.get() == ButtonType.YES) {
                                        TicTacTocClient.mySympol = "X";
                                        TicTacTocClient.hisSympol = "O";
                                    }
                                    if (result2.get() == ButtonType.NO) {
                                        TicTacTocClient.mySympol = "O";
                                        TicTacTocClient.hisSympol = "X";
                                    }
                                });
                            } catch (IOException ex) {
                                Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (result.get() == ButtonType.NO) {
                            Request rejectrequest = new Request(RequestType.REJECT);
                            rejectrequest.setData("myname", req.getData("targetname"));
                            rejectrequest.setData("targetname", req.getData("myname"));
                            try {
                                goingStream.writeObject(rejectrequest);
                            } catch (IOException ex) {
                                Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        busy = false;
                    });
                } else {
                    Request busyrequest = new Request(RequestType.BUSY);
                    busyrequest.setData("myname", req.getData("targetname"));
                    busyrequest.setData("targetname", req.getData("myname"));
                    goingStream.writeObject(busyrequest);
                }
                break;
            case ACCEPT:
                Platform.runLater(() -> {
                    Request playingrequest = new Request(RequestType.PLAYING);
                    playingrequest.setData("myname", GameController.myname);
                    try {

                        nowplaying = true;
                        OnlinePlay.XoOnlineController.myturn = true;
                        oponent = req.getData("myname");
                        root = FXMLLoader.load(getClass().getResource("/OnlinePlay/XoOnlineView.fxml"));
                        Scene singleScene = new Scene(root);
                        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(singleScene);
                        onlineStage = window;
                        window.setResizable(false);
                        window.show();
                        window.setOnCloseRequest((event) -> {

                                        try {
                                            if (nowplaying) {
                                                if (XoOnlineController.firstleave) {
                                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION, GameController.myname + " you will lose if you leave during the game!", ButtonType.OK);
                                                    Optional<ButtonType> result2 = a2.showAndWait();
                                                        Request notplayingrequest = new Request(RequestType.LEAVE);
                                                        notplayingrequest.setData("myname", GameController.myname);
                                                        notplayingrequest.setData("oponent", oponent);
                                                        goingStream.writeObject(notplayingrequest);
                                                        TicTacTocClient.someoneleft = true;                   
                                                } else {
                                                    Request leave = new Request(RequestType.NOTPLAYING);
                                                    leave.setData("myname", GameController.myname);
                                                    leave.setData("oponent", oponent);
                                                    goingStream.writeObject(leave);
                                                    TicTacTocClient.someoneleft = true;
                                                }
                                            }
                                            Platform.exit();
                                        } catch (IOException ex) {
                                            Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                        
                        
                        
                        TicTacTocClient.someoneleft = false;
                        Alert a2 = new Alert(Alert.AlertType.CONFIRMATION, GameController.myname + " Do you want to play X ?", ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> result2 = a2.showAndWait();
                        if (result2.get() == ButtonType.YES) {
                            TicTacTocClient.mySympol = "X";
                            TicTacTocClient.hisSympol = "O";
                        }
                        if (result2.get() == ButtonType.NO) {
                            TicTacTocClient.mySympol = "O";
                            TicTacTocClient.hisSympol = "X";
                        }
                        changeTurn(req.getData("targetname"));
                        busy = false;
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                break;

            case REJECT:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("myname") + " declined!", ButtonType.OK);
                    a1.showAndWait();
                    busy = false;
                });
                break;

            case NAMES:
                askfornamesHandler(req);
                break;

            case NOTFOUND:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("targetname") + " went offline please refresh!", ButtonType.OK);
                    a1.showAndWait();
                    busy = false;
                });
                break;
            case MOVE:
                moveHandler(req);
                break;
            case LOSE:
                Request losingGamesRequest = new Request(RequestType.LOSING_GAMES);
                losingGamesRequest.setData("username", GameController.myname);
                 {
                    try {
                        goingStream.writeObject(losingGamesRequest);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Platform.runLater(() -> {
                    loseAlert();
                    changeTurn("Game Over!");
                });
                break;
            case TIE:
                Platform.runLater(() -> {
                    tieAlert();
                    changeTurn("Game Over!");
                });

                break;
            case CHANGETURN:
                Platform.runLater(() -> {
                    changeTurn(req.getData("oponent"));
                });
                break;
            case PLAYING:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("oponent") + " is playing now", ButtonType.OK);
                    a1.show();
                });
                break;
            case LEAVE:
                leaveHandler(req);
                break;
            case RETURNNING_SCORES:
                System.out.println(request.getData("wins"));
                Platform.runLater(() -> {
                    winScores = request.getData("wins");
                    lossScores = request.getData("losses");
                });
                break;
            case REMATCH:
                Platform.runLater(() -> {
                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION, GameController.myname + " Do you want A rematch ?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> result2 = a2.showAndWait();
                    if (result2.get() == ButtonType.YES) {
                        try {
                            Request rematchAcceptedRequest = new Request(RequestType.REMATCHACCEPT);
                            rematchAcceptedRequest.setData("targetname", oponent);
                            rematchAcceptedRequest.setData("myname", GameController.myname);
                            goingStream.writeObject(rematchAcceptedRequest);
                            XoOnlineController.board.newGame();
                            Platform.runLater(() -> {
                                enableAllButtons(getAllButtons());
                                resetAllButtons(getAllButtons());
                                XoOnlineController.draw = 0;
                                XoOnlineController.firstleave = true;
                                Button btnrematch = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnRematch");
                                btnrematch.setDisable(true);
                                nowplaying=true;

                            });
                        } catch (IOException ex) {
                            Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (result2.get() == ButtonType.NO) {
                        Request rejectrequest = new Request(RequestType.REJECT);
                        rejectrequest.setData("myname", GameController.myname);
                        rejectrequest.setData("targetname", oponent);
                        try {
                            goingStream.writeObject(rejectrequest);
                        } catch (IOException ex) {
                            Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                break;

            case REMATCHACCEPT:
                XoOnlineController.board.newGame();
                Platform.runLater(() -> {
                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION, GameController.myname + "! He accepted", ButtonType.OK);
                    a2.show();
                    enableAllButtons(getAllButtons());
                    resetAllButtons(getAllButtons());
                    XoOnlineController.draw = 0;
                    XoOnlineController.firstleave = true;
                    Button btnrematch = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnRematch");
                    btnrematch.setDisable(true);
                    nowplaying=true;

                });
                break;
            case NOTPLAYING:
                if (!someoneleft) {
                    Button rematch = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnRematch");
                    rematch.setDisable(true);
                }
                break;
            case BUSY:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("myname") + " Cant recieve invites now!", ButtonType.OK);
                    a1.show();
                });
                break;
        }
    
    }
    public void login(String username, String password, ActionEvent _event) throws IOException {
        event = _event;
        Request loginRequest = new Request(RequestType.LOGIN);
        loginRequest.setData("username", username);
        loginRequest.setData("password", password);
        goingStream.writeObject(loginRequest);
    }

    public void registration(String fullname, String age, String username, String password) throws IOException {
        Request registerationRequest = new Request(RequestType.REGISTER);
        registerationRequest.setData("fullname", fullname);
        registerationRequest.setData("age", age);
        registerationRequest.setData("username", username);
        registerationRequest.setData("password", password);
        goingStream.writeObject(registerationRequest);
    }

    public void updateScores(String name) {
        try {
            Request updateScoreRequest = new Request(RequestType.UPDATE_SCORES);
            updateScoreRequest.setData("username", name);
            goingStream.writeObject(updateScoreRequest);
        } catch (IOException ex) {
            Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void asktoplay(String myname, String targetname, ActionEvent _event) throws IOException {
        busy = true;
        Request asktoplayRequest = new Request(RequestType.ASKTOPLAY);
        asktoplayRequest.setData("myname", myname);
        asktoplayRequest.setData("targetname", targetname);
        event = _event;
        goingStream.writeObject(asktoplayRequest);
    }

    //ramy
    public void askfornames(String myname) {
        Request askfornames = new Request(RequestType.NAMES);
        askfornames.setData("myname", myname);
        try {
            goingStream.writeObject(askfornames);
        } catch (IOException ex) {
            Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void askfornamesHandler(Request req) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<String> names = Collections.synchronizedList(new ArrayList<String>());
                names = req.getNames("names");
                clients.clear();
                for (String t1 : names) {
                    clients.add(t1);
                }
            }
        });
    }

    public static String getOponent() {
        return oponent;
    }

    public ObservableList<String> getClients() {
        return clients;
    }

    public void passStage(Stage window) {
        mystage = window;
    }

    private void moveHandler(Request req) {
        Button btnone = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnOne");
        Button btnTwo = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnTwo");
        Button btnThree = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnThree");
        Button btnFour = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnFour");
        Button btnFive = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnFive");
        Button btnSix = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnSix");
        Button btnSeven = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnSeven");
        Button btnEight = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnEight");
        Button btnNine = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnNine");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (req.getData("moveplace")) {
                    case "1":
                        // btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnone.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "1" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnone.setDisable(true);
                        break;
                    case "2":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnTwo.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "2" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnTwo.setDisable(true);
                        break;
                    case "3":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnThree.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "3" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnThree.setDisable(true);
                        break;
                    case "4":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnFour.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "4" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnFour.setDisable(true);
                        break;
                    case "5":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnFive.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "5" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnFive.setDisable(true);
                        break;
                    case "6":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnSix.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "6" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnSix.setDisable(true);
                        break;
                    case "7":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnSeven.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "7" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnSeven.setDisable(true);
                        break;
                    case "8":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnEight.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "8" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnEight.setDisable(true);
                        break;
                    case "9":
                        //btnone.setStyle("-fx-text-fill: #ffc200;");
                        btnNine.setText(TicTacTocClient.hisSympol);
                        XoOnlineController.recordd=XoOnlineController.recordd+TicTacTocClient.hisSympol+ ";" + "9" + ";";
                        OnlinePlay.XoOnlineController.myturn = true;
                        btnNine.setDisable(true);
                        break;
                }
//                if(req.getData("moveplace").equals("1")){
//                    btnone.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnone.setDisable(true);}
//                if(req.getData("moveplace").equals("2")){
//                    btnTwo.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnTwo.setDisable(true);}
//                if(req.getData("moveplace").equals("3")){
//                    btnThree.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnThree.setDisable(true);}
//                if(req.getData("moveplace").equals("4")){
//                    btnFour.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnFour.setDisable(true);}
//                if(req.getData("moveplace").equals("5")){
//                    btnFive.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnFive.setDisable(true);}
//                if(req.getData("moveplace").equals("6"))
//                    btnSix.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnSix.setDisable(true);
//                if(req.getData("moveplace").equals("7")){
//                    btnSeven.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnSeven.setDisable(true);}
//                if(req.getData("moveplace").equals("8")){
//                    btnEight.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnEight.setDisable(true);}
//                if(req.getData("moveplace").equals("9")){
//                    btnNine.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;btnNine.setDisable(true);}
            }
        });

    }

    public static Stage getOnlineStage() {
        return onlineStage;
    }

    private void disableAllButtons(Button[] btns) {
        for (int i = 0; i < 9; i++) {
            btns[i].setDisable(true);
        }
    }

    private void enableAllButtons(Button[] btns) {
        for (int i = 0; i < 9; i++) {
            btns[i].setDisable(false);
        }
    }

    private void resetAllButtons(Button[] btns) {
        for (int i = 0; i < 9; i++) {
            btns[i].setText("");
        }
    }

    private Button[] getAllButtons() {
        Button[] buttons = new Button[9];
        buttons[0] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnOne");
        buttons[1] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnTwo");
        buttons[2] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnThree");
        buttons[3] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnFour");
        buttons[4] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnFive");
        buttons[5] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnSix");
        buttons[6] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnSeven");
        buttons[7] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnEight");
        buttons[8] = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnNine");
        return buttons;
    }

    private void loseAlert() {
        PauseTransition pause = new PauseTransition(Duration.millis(20));
        pause.setOnFinished(event
                -> {
            // JOptionPane.showMessageDialog(null, xwin);
            String path = "src\\Media\\lose.mp4";
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaView = new MediaView();
            mediaView.setMediaPlayer(mediaPlayer);
            Group root1 = new Group();
            root1.getChildren().add(mediaView);
            Scene multiScene = new Scene(root1);
            Stage stage1 = new Stage();
            stage1.setScene(multiScene);
            stage1.initModality(Modality.APPLICATION_MODAL);
             stage1.setMinWidth(700);
            stage1.setMinHeight(500);
            stage1.setMaxHeight(500);
            stage1.setMaxWidth(700);
            stage1.centerOnScreen();
            mediaPlayer.setOnEndOfMedia(() -> {
                stage1.close();
            });
            stage1.setResizable(false);
            stage1.show();
            Button btnrematch = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnRematch");
            btnrematch.setDisable(false);
            nowplaying=false;

            XoOnlineController.firstleave = false;
        });
        pause.play();
        disableAllButtons(getAllButtons());
        Label hisscorelabel = (Label) TicTacTocClient.getOnlineStage().getScene().lookup("#playertwoLabelScore");
        hisscorelabel.setText("" + oponent + " : " + (++XoOnlineController.hisscore));
    }

    private void tieAlert() {

        disableAllButtons(getAllButtons());
        JOptionPane.showMessageDialog(null, tie);
        XoOnlineController.firstleave = false;
        Button btnrematch = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnRematch");
        btnrematch.setDisable(false);

        nowplaying = false;
        // playeroneLabelScore.setText("player One Score : " + (++xscore));
    }

    private void changeTurn(String nowTurnName) {
        Label nowTurn = (Label) TicTacTocClient.getOnlineStage().getScene().lookup("#turnLabel");
        System.out.println("TurnName:" + nowTurnName);
        nowTurn.setText(nowTurnName);

    }

    private void leaveHandler(Request req) {
        Platform.runLater(() -> {
            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("leaver") + " left sorry! you won", ButtonType.OK);
            a1.showAndWait();
            disableAllButtons(getAllButtons());
            Button rematch = (Button) TicTacTocClient.getOnlineStage().getScene().lookup("#btnRematch");
            rematch.setDisable(true);
            XoOnlineController.firstleave = false;
        });
    }
}
