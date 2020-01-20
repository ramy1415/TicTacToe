/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlinePlay;

import TicTacTocClient.TicTacTocClient;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    
    private TicTacTocClient me;
    private ObjectInputStream comingStream;
    private ObjectOutputStream goingStream;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        me=GameController.getPlayer();
        me.comingStream=comingStream;
        me.goingStream=goingStream;
    }    

    @FXML
    private void btnOnePressed(ActionEvent event) {
        
    }

    @FXML
    private void btnTwoPressed(ActionEvent event) {
    }

    @FXML
    private void btnThreePressed(ActionEvent event) {
    }

    @FXML
    private void btnFourPressed(ActionEvent event) {
    }

    @FXML
    private void btnFivePressed(ActionEvent event) {
    }

    @FXML
    private void btnSixPressed(ActionEvent event) {
    }

    @FXML
    private void btnSevenPressed(ActionEvent event) {
    }

    @FXML
    private void btnEightPressed(ActionEvent event) {
    }

    @FXML
    private void btnNinePressed(ActionEvent event) {
    }
    
}
