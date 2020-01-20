/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlinePlay;

import MultiPlayer.XoSingleModel;
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
import javax.swing.JOptionPane;
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
    private final String win = "Congratulation! You won!";
    private XoSingleModel board;
    private static int draw = 0;
    private static String nowTurn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goingStream = GameController.getPlayer().goingStream;
        oponent = TicTacTocClient.getOponent();
        myname = GameController.myname;
        mySympol = GameController.getPlayer().mySympol;
        board = new XoSingleModel();
        System.err.println("my s= " + mySympol);

    }

    @FXML
    private void btnOnePressed(ActionEvent event) {
        if (myturn) {
            btnOne.setText(mySympol);
            board.setarr(0, 0, mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "1");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();

            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnTwoPressed(ActionEvent event) {
        if (myturn) {
            btnTwo.setText(mySympol);
            board.setarr(0, 1, mySympol);
             boolean check = checkWinning();
            
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "2");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnThreePressed(ActionEvent event) {
        if (myturn) {
            btnThree.setText(mySympol);
            board.setarr(0, 2, mySympol);
             boolean check = checkWinning();
            
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "3");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnFourPressed(ActionEvent event) {
        if (myturn) {
            btnFour.setText(mySympol);
            board.setarr(1, 0, mySympol);
             boolean check = checkWinning();
            
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "4");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnFivePressed(ActionEvent event) {
        if (myturn) {
            btnFive.setText(mySympol);
            board.setarr(1, 1, mySympol);
             boolean check = checkWinning();
            
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "5");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnSixPressed(ActionEvent event) {
        if (myturn) {
            btnSix.setText(mySympol);
            board.setarr(1, 2, mySympol);
             boolean check = checkWinning();
            
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "6");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnSevenPressed(ActionEvent event) {
        if (myturn) {
            btnSeven.setText(mySympol);
            board.setarr(2, 0, mySympol);
             boolean check = checkWinning();
            
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "7");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnEightPressed(ActionEvent event) {
        if (myturn) {
            btnEight.setText(mySympol);
            board.setarr(2, 1, mySympol);
             boolean check = checkWinning();
            
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "8");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnNinePressed(ActionEvent event) {
        if (myturn) {
            btnNine.setText(mySympol);
            board.setarr(2, 2, mySympol);
             boolean check = checkWinning();
           
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "9");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void winAlert() {
        JOptionPane.showMessageDialog(null, win);
        disableAllbtns();
        // playeroneLabelScore.setText("player One Score : " + (++xscore));
    }

    private void disableAllbtns() {
        btnOne.setDisable(true);
        btnTwo.setDisable(true);
        btnThree.setDisable(true);
        btnFour.setDisable(true);
        btnFive.setDisable(true);
        btnSix.setDisable(true);
        btnSeven.setDisable(true);
        btnEight.setDisable(true);
        btnNine.setDisable(true);
    }

    private boolean checkWinning() {
        if (board.checkwinner()) {
            //board.printCurrentBoard();
            
            winAlert();
            Request loseRequest = new Request(RequestType.LOSE);
            loseRequest.setData("oponent", oponent);
            loseRequest.setData("myname", myname);
            try {
                goingStream.writeObject(loseRequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
            turnLabel.setText("Game Over!");
            //changeTurn("Game Over!");
            
            return true;
        } else {
            draw++;

        }
        if (draw > 4) {
            // System.out.println("draw happend at  : "+draw);
            Request tieRequest = new Request(RequestType.TIE);
            tieRequest.setData("oponent", oponent);
            tieRequest.setData("myname", myname);
            try {
                goingStream.writeObject(tieRequest);
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    private Request changeTurnRequest()
    {
        Request changeTurnReq = new Request(RequestType.CHANGETURN);
            changeTurnReq.setData("oponent", oponent);
            changeTurnReq.setData("myname", myname);
            return changeTurnReq;
    }
    private void changeTurn(String nowTurnName )
    {
        Label nowTurn =(Label)TicTacTocClient.getOnlineStage().getScene().lookup("#turnLabel");
        System.out.println("TurnName:"+nowTurnName);
        nowTurn.setText(nowTurnName);
        
    }

}
