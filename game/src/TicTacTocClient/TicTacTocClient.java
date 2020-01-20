/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacTocClient;

import Facilities.Request;
import Facilities.RequestType;
<<<<<<< HEAD
import OnlinePlay.XoOnlineController;
=======
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
<<<<<<< HEAD
import javafx.scene.control.Label;
=======
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Mahmoud
 */
public class TicTacTocClient extends Thread {

    Socket serverSocket;
    public ObjectInputStream comingStream;
    public ObjectOutputStream goingStream;
    Request request;
<<<<<<< HEAD
    public String response;
=======
    public static String response;
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481

    //ramy
    static Parent root;
    static String myip;
    static ActionEvent event;
    static Stage window;
    private Stage mystage;
    static String oponent;
    public String mySympol;
    public String hisSympol;
    ObservableList<String> clients = FXCollections.observableArrayList();
    private static Stage onlineStage;
<<<<<<< HEAD
    private final String lose = "Sorry! You lost!";
    private final String tie = "It was a tie!";

=======
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481

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
<<<<<<< HEAD
=======

>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
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

                System.err.println("server disconnected");
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void clientrequestHandler(Request req) {
        switch (req.getType()) {
            case LOGIN_SUCCESS:
                response = "success";
<<<<<<< HEAD
                break;
            case LOGIN_FAILURE:
                response = "failure";
=======
              
                break;
            case LOGIN_FAILURE:
                response = "failure";
                
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
                break;
            case REGISTER_SUCCESS:
                response = "success";
                break;
            case REGISTER_FAILURE:
                response = "failure";
                break;

            //cases i added
            case ASKTOPLAY:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("myname") + " sent you a request .. Do you want to play ?!", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> result = a1.showAndWait();
                    if (result.get() == ButtonType.YES) {
<<<<<<< HEAD
                        mySympol = "X";
                        hisSympol = "O";
                        OnlinePlay.XoOnlineController.myturn = false;
                        
                        oponent = req.getData("myname");
                        Request acceptrequest = new Request(RequestType.ACCEPT);
                        acceptrequest.setData("targetname", req.getData("myname"));
                        
=======
                        mySympol="X";
                        hisSympol="O";
                        OnlinePlay.XoOnlineController.myturn=false;
                        oponent = req.getData("myname");
                        Request acceptrequest = new Request(RequestType.ACCEPT);
                        acceptrequest.setData("targetname", req.getData("myname"));
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
                        acceptrequest.setData("myname", req.getData("targetname"));
                        try {
                            goingStream.writeObject(acceptrequest);
                            //temporary testing
                            Platform.runLater(() -> {
<<<<<<< HEAD
                                
                                try {
                                    root = FXMLLoader.load(getClass().getResource("/OnlinePlay/XoOnlineView.fxml"));
                                    
=======
                                try {
                                    root = FXMLLoader.load(getClass().getResource("/OnlinePlay/XoOnlineView.fxml"));
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
                                } catch (IOException ex) {
                                    Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Scene singleScene = new Scene(root);
                                mystage.setScene(singleScene);
<<<<<<< HEAD
                                onlineStage = mystage;
                                
                                changeTurn(req.getData("myname"));
                                mystage.show();
                                
=======
                                onlineStage=mystage;
                                mystage.show();
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
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
                });
                break;
            case ACCEPT:
                Platform.runLater(() -> {
//                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, "He Accepted", ButtonType.OK);
//                    a1.show();
                    try {
<<<<<<< HEAD
                        mySympol = "X";
                        hisSympol = "O";
                        OnlinePlay.XoOnlineController.myturn = true;
=======
                        mySympol="X";
                        hisSympol="O";
                        OnlinePlay.XoOnlineController.myturn=true;
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
                        oponent = req.getData("myname");
                        root = FXMLLoader.load(getClass().getResource("/OnlinePlay/XoOnlineView.fxml"));
                        Scene singleScene = new Scene(root);
                        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(singleScene);
<<<<<<< HEAD
                        onlineStage = window;
                        window.show();
                        changeTurn(req.getData("targetname"));
=======
                        onlineStage=window;
                        window.show();
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                break;

            case REJECT:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("myname") + " declined!", ButtonType.OK);
                    a1.show();
                });
                break;

            case NAMES:
                askfornamesHandler(req);
                break;

            case NOTFOUND:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("targetname") + " went offline please refresh!", ButtonType.OK);
                    a1.show();
                });
                break;
            case MOVE:
                moveHandler(req);
                break;
<<<<<<< HEAD
            case LOSE:
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
        }

    }

    public void login(String username, String password, ActionEvent _event) throws IOException {
=======
        }
    }

    public void login(String username, String password, ActionEvent _event) throws IOException {
        
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
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

    public void asktoplay(String myname, String targetname, ActionEvent _event) throws IOException {
<<<<<<< HEAD
        mySympol = "X";
        hisSympol = "O";
=======
        mySympol="X";
        hisSympol="O";
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
        Request asktoplayRequest = new Request(RequestType.ASKTOPLAY);
        asktoplayRequest.setData("myname", myname);
        asktoplayRequest.setData("targetname", targetname);
        event = _event;
        goingStream.writeObject(asktoplayRequest);
    }

    //ramy
    public void askfornames(String myname) {
        System.err.println("1");
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
<<<<<<< HEAD
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
                if (req.getData("moveplace").equals("1")) {
                    btnone.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("2")) {
                    btnTwo.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("3")) {
                    btnThree.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("4")) {
                    btnFour.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("5")) {
                    btnFive.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("6")) {
                    btnSix.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("7")) {
                    btnSeven.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("8")) {
                    btnEight.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;
                if (req.getData("moveplace").equals("9")) {
                    btnNine.setText(hisSympol);
                }
                OnlinePlay.XoOnlineController.myturn = true;

            }
        });

=======
        Button btnone=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnOne");
        Button btnTwo=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnTwo");
        Button btnThree=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnThree");
        Button btnFour=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnFour");
        Button btnFive=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnFive");
        Button btnSix=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnSix");
        Button btnSeven=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnSeven");
        Button btnEight=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnEight");
        Button btnNine=(Button)TicTacTocClient.getOnlineStage().getScene().lookup("#btnNine");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(req.getData("moveplace").equals("1"))
                    btnone.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("2"))
                    btnTwo.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("3"))
                    btnThree.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("4"))
                    btnFour.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("5"))
                    btnFive.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("6"))
                    btnSix.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("7"))
                    btnSeven.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("8"))
                    btnEight.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                if(req.getData("moveplace").equals("9"))
                    btnNine.setText(hisSympol);OnlinePlay.XoOnlineController.myturn=true;
                
            }
        });
        
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
    }

    public static Stage getOnlineStage() {
        return onlineStage;
    }
<<<<<<< HEAD

    private void disableAllButtons(Button[] btns) {
        for (int i = 0; i < 9; i++) {
            btns[i].setDisable(true);
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
        JOptionPane.showMessageDialog(null, lose);
        disableAllButtons(getAllButtons());
        // playeroneLabelScore.setText("player One Score : " + (++xscore));
    }

    private void tieAlert() {
        
        disableAllButtons(getAllButtons());
        JOptionPane.showMessageDialog(null, tie);
        // playeroneLabelScore.setText("player One Score : " + (++xscore));
    }

    private void changeTurn(String nowTurnName) {
        Label nowTurn = (Label) TicTacTocClient.getOnlineStage().getScene().lookup("#turnLabel");
        System.out.println("TurnName:" + nowTurnName);
        nowTurn.setText(nowTurnName);

    }
=======
    
    
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
}
