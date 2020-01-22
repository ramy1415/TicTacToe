/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import DataBase.DB;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Facilities.Request;
import Facilities.RequestType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Mahmoud
 */
public class TicTacToeServer extends Thread {

    Socket playerSocket;
    public ObjectInputStream comingStream;
    public ObjectOutputStream goingStream;
    Request request;
    DB dataBase;

    //passed ip and vector
    String ip;
    String name;
    boolean playing =false;
    static List<TicTacToeServer> clientslist = Collections.synchronizedList(new ArrayList<TicTacToeServer>());
    static ArrayList<String> onlineNames = new ArrayList<>();
    //static Vector<TicTacToeServer> clientsvector = new Vector<TicTacToeServer>();

    public TicTacToeServer(Socket s, ObjectOutputStream oos, ObjectInputStream ois, String _ip) throws IOException {
        playerSocket = s;
        goingStream = oos;
        //ip
        comingStream = ois;
        ip = _ip;
        
        System.err.println("");
        dataBase = new DB();
        //adding to vector
        System.err.println("new client ip:" + ip);
        clientslist.add(this);
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                request = (Request) comingStream.readObject();
                requestHandler(request);
            } catch (IOException ex) {
                clientslist.remove(this);
                onlineNames.remove(this.name);
                if (this.name != null) {
                    System.err.println("client " + this.name + " left");
                }

                return;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void requestHandler(Request req) throws SQLException, IOException {
        switch (req.getType()) {
            case LOGIN:
                loginHandler(req);
                break;
            case REGISTER:
                registerHandler(req);
                break;
            case ASKTOPLAY:
                asktoplayHandler(req);
                break;
            case ACCEPT:
                acceptHandler(req);
                break;
            case REJECT:
                rejectHandler(req);
                break;
            case NAMES:
                askfornamesHandler(req);
                break;
            case MOVE:
                moveHandler(req);
                break;
            case LOSE:
                loseHandler(req);
                break;
            case TIE:
                tieHandler(req);
                break;
            case CHANGETURN:
                changeTurnHandler(req);
                break;
            case WINNING_GAMES:
                winningGamesHandler(req);
                break;
            case LOSING_GAMES:
                losingGamesHandler(req);
                break;
            case PLAYING:
                playingHandler(req);
                break;
            case NOTPLAYING:
                notplayingHandler(req);
                break;
            case LEAVE:
                leave(req);
                break;

        }
    }

    public void loginHandler(Request req) throws SQLException, IOException {
        String user_name = req.getData("username");
        String password = req.getData("password");
        if (!onlineNames.contains(user_name)) {
            boolean check = dataBase.checkForLogin(user_name, password);
            if (check == true) {
                this.name = req.getData("username");
                onlineNames.add(user_name);
                Request r = new Request(RequestType.LOGIN_SUCCESS);
                goingStream.writeObject(r);
            } else {
                Request r = new Request(RequestType.LOGIN_FAILURE);
                goingStream.writeObject(r);
            }
        } else {
            Request r = new Request(RequestType.ALREADY_LOGINNED);
                goingStream.writeObject(r);
        }
    }

    public void registerHandler(Request req) throws SQLException, IOException {
        String fullname = req.getData("fullname");
        String age = req.getData("age");
        String username = req.getData("username");
        String password = req.getData("password");
        if (fullname == null || age == null || username == null || password == null) {
            boolean valid = dataBase.checkForValidation(fullname, age, username, password);
            if (valid == true) {
                Request r = new Request(RequestType.REGISTER_SUCCESS);
                goingStream.writeObject(r);
            } else {
                Request r = new Request(RequestType.REGISTER_FAILURE);
                goingStream.writeObject(r);
            }
        } else {
            Request r = new Request(RequestType.REGISTER_FAILURE);
            goingStream.writeObject(r);
        }
    }
    //new handlers

    private void asktoplayHandler(Request req) {
        boolean found = false;
        for (TicTacToeServer t1 : clientslist) {
            if (t1.name.equals(req.getData("targetname"))) {
                try {
                    if(t1.playing==true){
                        playingNowHandler(req);
                        return;
                    }
                    t1.goingStream.writeObject(req);
                    found = true;
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!found) {
            for (TicTacToeServer t2 : clientslist) {
                if (t2.name.equals(req.getData("myname"))) {
                    try {
                        Request r = new Request(RequestType.NOTFOUND);
                        r.setData("targetname", req.getData("targetname"));
                        t2.goingStream.writeObject(r);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }

    private void acceptHandler(Request req) {

        for (TicTacToeServer t1 : clientslist) {
            if (t1.name.equals(req.getData("targetname"))) {
                try {
                    t1.goingStream.writeObject(req);
                    t1.playing=true;
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void rejectHandler(Request req) {
        for (TicTacToeServer t1 : clientslist) {
            System.err.println("reject t1.name :" + t1.name);
        }
        for (TicTacToeServer t1 : clientslist) {
            if (t1.name.equals(req.getData("targetname"))) {
                try {
                    t1.goingStream.writeObject(req);
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //ramy
    synchronized private void askfornamesHandler(Request req) {
        List<String> names = Collections.synchronizedList(new ArrayList<String>());
        for (TicTacToeServer t1 : clientslist) {
            if (t1.name != null) {
                if (!t1.name.equals(req.getData("myname"))) {
                    names.add(t1.name);
                }
            }
        }
        for (TicTacToeServer t1 : clientslist) {
            if (t1.name != null) {
                if (t1.name.equals(req.getData("myname"))) {
                    try {
                        req.setNames("names", names);
                        t1.goingStream.writeObject(req);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void moveHandler(Request req) {
        for (TicTacToeServer t1 : clientslist) {
            System.err.println("hi");
            System.err.println(t1.name);
            System.err.println(req.getData("oponent"));
            if (t1.name != null) {
                if (t1.name.equals(req.getData("oponent"))) {
                    try {
                        t1.goingStream.writeObject(req);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void loseHandler(Request req) {
        for (TicTacToeServer t1 : clientslist) {

            if (t1.name != null) {
                if (t1.name.equals(req.getData("oponent"))) {
                    try {
                        t1.goingStream.writeObject(req);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void tieHandler(Request req) {
        for (TicTacToeServer t1 : clientslist) {
            if (t1.name != null) {
                if (t1.name.equals(req.getData("oponent")) || t1.name.equals(req.getData("myname"))) {
                    try {
                        t1.goingStream.writeObject(req);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }

    private void changeTurnHandler(Request req) {

        for (TicTacToeServer t1 : clientslist) {
            if (t1.name != null) {
                if (t1.name.equals(req.getData("oponent")) || t1.name.equals(req.getData("myname"))) {
                    try {
                        t1.goingStream.writeObject(req);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }
    
    private void winningGamesHandler(Request req){
        String username=req.getData("username");
        dataBase.addingNewWins(username);
    }

    private void losingGamesHandler(Request req) {
        String username=req.getData("username");
        dataBase.addingNewLoses(username);
    }

    private void playingHandler(Request req) {
        for (TicTacToeServer t1 : clientslist) {
            if(t1.name.equals(req.getData("myname"))){
                t1.playing=true;
            }
        }
    }

    private void playingNowHandler(Request req) {
        for (TicTacToeServer t1 : clientslist) {
            if(t1.name.equals(req.getData("myname"))){
                Request playingnowrequest=new Request(RequestType.PLAYING);
                playingnowrequest.setData("oponent", req.getData("targetname"));
                try {
                    t1.goingStream.writeObject(playingnowrequest);
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }

    private void notplayingHandler(Request req) {
        for (TicTacToeServer t1 : clientslist) {
            if(t1.name.equals(req.getData("myname"))){
                t1.playing=false;
            }
        }
    }
    
    private void leave(Request req) {
        for (TicTacToeServer t1 : clientslist) {
            if(t1.name.equals(req.getData("myname"))){
                t1.playing=false;
                dataBase.addingNewLoses(t1.name);
            }
            if(t1.name.equals(req.getData("oponent"))){
                Request left2=new Request(RequestType.LEAVE);
                left2.setData("leaver", req.getData("myname"));
                dataBase.addingNewWins(t1.name);
                try {
                    t1.goingStream.writeObject(left2);
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
