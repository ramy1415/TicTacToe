/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacTocClient;

import Facilities.Request;
import Facilities.RequestType;
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
import javafx.scene.control.ButtonType;
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
    public String response;

    //ramy
    static Parent root;
    static String myip;
    static ActionEvent event;
    static Stage window;
    private Stage mystage;
    static String oponent;
    ObservableList<String> clients = FXCollections.observableArrayList();

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

            //cases i added
            case ASKTOPLAY:
                Platform.runLater(() -> {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, req.getData("myname") + " sent you a request .. Do you want to play ?!", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> result = a1.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        oponent = req.getData("myname");
                        Request acceptrequest = new Request(RequestType.ACCEPT);
                        acceptrequest.setData("targetname", req.getData("myname"));
                        acceptrequest.setData("myname", req.getData("targetname"));
                        try {
                            goingStream.writeObject(acceptrequest);
                            //temporary testing
                            Platform.runLater(() -> {
                                try {
                                    root = FXMLLoader.load(getClass().getResource("/tictactoe/XoOnlineView.fxml"));
                                } catch (IOException ex) {
                                    Logger.getLogger(TicTacTocClient.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Scene singleScene = new Scene(root);
                                mystage.setScene(singleScene);
                                mystage.show();
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
                        oponent = req.getData("myname");
                        root = FXMLLoader.load(getClass().getResource("/tictactoe/XoOnlineView.fxml"));
                        Scene singleScene = new Scene(root);
                        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(singleScene);
                        window.show();
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

    public void asktoplay(String myname, String targetname, ActionEvent _event) throws IOException {
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
}
