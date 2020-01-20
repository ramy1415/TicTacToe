/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlinePlay;

<<<<<<< HEAD
import MultiPlayer.XoSingleModel;
=======
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
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
<<<<<<< HEAD
import javax.swing.JOptionPane;
=======
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
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

<<<<<<< HEAD
=======
    
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
    private ObjectInputStream comingStream;
    private ObjectOutputStream goingStream;
    private String oponent;
    private String myname;
    public static boolean myturn;
    private String mySympol;
<<<<<<< HEAD
    private final String win = "Congratulation! You won!";
    private XoSingleModel board;
    private static int draw = 0;
    private static String nowTurn;

=======
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
<<<<<<< HEAD
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
=======
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
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "1");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();

            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnTwoPressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnTwo.setText(mySympol);
            board.setarr(0, 1, mySympol);
             boolean check = checkWinning();
            
=======
        if(myturn){
            btnTwo.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "2");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnThreePressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnThree.setText(mySympol);
            board.setarr(0, 2, mySympol);
             boolean check = checkWinning();
            
=======
        if(myturn){
            btnThree.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "3");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnFourPressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnFour.setText(mySympol);
            board.setarr(1, 0, mySympol);
             boolean check = checkWinning();
            
=======
        if(myturn){
            btnFour.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "4");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnFivePressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnFive.setText(mySympol);
            board.setarr(1, 1, mySympol);
             boolean check = checkWinning();
            
=======
        if(myturn){
            btnFive.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "5");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnSixPressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnSix.setText(mySympol);
            board.setarr(1, 2, mySympol);
             boolean check = checkWinning();
            
=======
        if(myturn){
            btnSix.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "6");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnSevenPressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnSeven.setText(mySympol);
            board.setarr(2, 0, mySympol);
             boolean check = checkWinning();
            
=======
        if(myturn){
            btnSeven.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "7");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnEightPressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnEight.setText(mySympol);
            board.setarr(2, 1, mySympol);
             boolean check = checkWinning();
            
=======
        if(myturn){
            btnEight.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "8");
<<<<<<< HEAD
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if(!check && draw<4){goingStream.writeObject(changeTurnReq);}
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnNinePressed(ActionEvent event) {
<<<<<<< HEAD
        if (myturn) {
            btnNine.setText(mySympol);
            board.setarr(2, 2, mySympol);
             boolean check = checkWinning();
           
=======
        if(myturn){
            btnNine.setText(mySympol);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "9");
<<<<<<< HEAD
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
=======
            myturn=false;
            try {
                goingStream.writeObject(moverequest);
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
<<<<<<< HEAD
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

=======
    }
    
    
    
    
>>>>>>> 781e1095a0f2a900e2520765a919bcdb766c6481
}
