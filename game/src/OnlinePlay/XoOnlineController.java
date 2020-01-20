/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlinePlay;

import Facilities.Request;
import Facilities.RequestType;
import TicTacTocClient.TicTacTocClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import tictactoe.GameController;

/**
 * FXML Controller class
 *
 * @author ramy1
 */
public class XoOnlineController implements Initializable {

    @FXML
    private Button btnOne;
    @FXML
    private Button btnTwo;
    @FXML
    private Button btnThree;
    @FXML
    private Button btnFour;
    @FXML
    private Button btnFive;
    @FXML
    private Button btnSix;
    @FXML
    private Button btnSeven;
    @FXML
    private Button btnEight;
    @FXML
    private Button btnNine;
    @FXML
    private Label playeroneLabelScore;
    @FXML
    private Label playertwoLabelScore;
    @FXML
    private Label turnLabel;

    
    private ObjectInputStream comingStream;
    private ObjectOutputStream goingStream;
    private String oponent;
    private String myname;
    public static boolean myturn;
    private String mySympol;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goingStream=GameController.getPlayer().goingStream;
        oponent = TicTacTocClient.getOponent();
        myname=GameController.myname;
        mySympol=GameController.getPlayer().mySympol;
        System.err.println("my s= "+mySympol);
    }    

    @FXML
    private void btnOnePressed(ActionEvent event) {
        if(myturn){
            btnOne.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "1");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnTwoPressed(ActionEvent event) {
        if(myturn){
            btnTwo.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "2");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnThreePressed(ActionEvent event) {
        if(myturn){
            btnThree.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "3");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnFourPressed(ActionEvent event) {
        if(myturn){
            btnFour.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "4");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnFivePressed(ActionEvent event) {
        if(myturn){
            btnFive.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "5");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnSixPressed(ActionEvent event) {
        if(myturn){
            btnSix.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "6");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnSevenPressed(ActionEvent event) {
        if(myturn){
            btnSeven.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "7");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnEightPressed(ActionEvent event) {
        if(myturn){
            btnEight.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "8");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnNinePressed(ActionEvent event) {
        if(myturn){
            btnNine.setText(mySympol);
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "9");
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
}
