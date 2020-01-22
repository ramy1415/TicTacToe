/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xosingle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class XoSingleController implements Initializable {

    private final XoSingleModel m1 = new XoSingleModel();
    private final String player1 = "X";
    private final String player2 = "O";
    private final String xwin = "The Winner Is X";
    private final String owin = "The Winner Is O";
    Stage s1;
    Scene c1;
    Parent root;
    int draw = 0;
    private int xscore = 0;
    private int oscore = 0;
    //private int flag = 0;
    @FXML
    Button btnOne;
    @FXML
    Button btnTwo;
    @FXML
    Button btnThree;
    @FXML
    Button btnFour;
    @FXML
    Button btnFive;
    @FXML
    Button btnSix;
    @FXML
    Button btnSeven;
    @FXML
    Button btnEight;
    @FXML
    Button btnNine;
    @FXML
    Label playeroneLabelScore;
    @FXML
    Label playertwoLabelScore;
    @FXML
    Label turnLabel;

    private Button[][] buttons;

    public XoSingleController() {

    }
    public void initializeButtonArray() {
        buttons = new Button[3][3];
        buttons[0][0] = btnOne;
        buttons[0][1] = btnTwo;
        buttons[0][2] = btnThree;
        buttons[1][0] = btnFour;
        buttons[1][1] = btnFive;
        buttons[1][2] = btnSix;
        buttons[2][0] = btnSeven;
        buttons[2][1] = btnEight;
        buttons[2][2] = btnNine;

    }

    @FXML
    private void btnOnePressed(ActionEvent e) {
        anyButtonAction(e, 0, 0);
        compMove();
       

    }

    @FXML
    private void btnTwoPressed(ActionEvent e) {
        anyButtonAction(e, 0, 1);
        compMove();
        
    }

    @FXML
    private void btnThreePressed(ActionEvent e) {
        anyButtonAction(e, 0, 2);
        compMove();
    }

    @FXML
    private void btnFourPressed(ActionEvent e) {
        anyButtonAction(e, 1, 0);
        compMove();
    }

    @FXML
    private void btnFivePressed(ActionEvent e) {
        anyButtonAction(e, 1, 1);
        compMove();
    }

    @FXML
    private void btnSixPressed(ActionEvent e) {
        anyButtonAction(e, 1, 2);
        compMove();
    }

    @FXML
    private void btnSevenPressed(ActionEvent e) {
        anyButtonAction(e, 2, 0);
        compMove();
    }

    @FXML
    private void btnEightPressed(ActionEvent e) {
        anyButtonAction(e, 2, 1);
        compMove();
    }

    @FXML
    private void btnNinePressed(ActionEvent e) {
        anyButtonAction(e, 2, 2);
        compMove();
    }

    @FXML
    private void btnResetGamePressed(ActionEvent e) {
        resetButtons();
        xscore = 0;
        oscore = 0;
        playeroneLabelScore.setText("player One Score : " + xscore);
        playertwoLabelScore.setText("player Two Score : " + oscore);
    }

    @FXML
    private void btnNewgamePressed(ActionEvent e) {
        resetButtons();
    }

    @FXML
    public void ChoosePlayerPressed(ActionEvent e) {
        s1 = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("XoChoice.fxml"));
            c1 = new Scene(root);
            s1.setScene(c1);
            s1.initModality(Modality.WINDOW_MODAL);
        } catch (IOException ex) {
            Logger.getLogger(XoSingleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.show();

    }

    @FXML
    public void ChooseXPressed(ActionEvent e) {
        Button b = (Button) e.getSource();
        Stage s = (Stage) b.getScene().getWindow();
        s.close();
    }

    @FXML
    public void ChooseOPressed(ActionEvent e) {
        Button b = (Button) e.getSource();
        Stage s = (Stage) b.getScene().getWindow();
        s.close();
    }

    private int getRandomMove() {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(XoSingleController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        int move = (int) (Math.random() * 9);
        if ((m1.getarr(move / 3, move % 3).equals(player1))
                || (m1.getarr(move / 3, move % 3).equals(player2))) {
            return getRandomMove();
        } else {
            return move;
        }
    }

    private  void compMove() {
        
        if ((!m1.checkwinner()) && (draw <= 7)) {
            int move = getRandomMove();
            m1.setarr((move / 3), (move % 3), player2);
            buttons[move / 3][move % 3].setText(player2);
            if (m1.checkwinner()) {
                oWon();
            } else {
                draw++;
            }
            if (draw > 8) {
                JOptionPane.showMessageDialog(null, "draw");
            }
            buttons[move / 3][move % 3].setDisable(true);
            turnLabel.setText("X");
            /*for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(" " + m1.getarr(i, j));
                }
            }*/
        }

    }
   

    private void anyButtonAction(ActionEvent a, int x, int y) {
        Button b = (Button) a.getSource();
        m1.setarr(x, y, player1);
        b.setStyle("-fx-text-fill: red;");
        b.setText(player1);
        if (m1.checkwinner()) {
            xWon();
        } else {
            draw++;
        }
        if (draw > 8) {
            JOptionPane.showMessageDialog(null, "draw");
        }
        b.setDisable(true);
        turnLabel.setText("O");
        

    }

    private void xWon() {
        JOptionPane.showMessageDialog(null, xwin);
        disableAllbtns();
        playeroneLabelScore.setText("player One Score : " + (++xscore));
    }

    private void oWon() {
        JOptionPane.showMessageDialog(null, owin);
        disableAllbtns();
        playertwoLabelScore.setText("player Two Score : " + (++oscore));
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

    private void resetButtons() {

        btnOne.setDisable(false);
        btnTwo.setDisable(false);
        btnThree.setDisable(false);
        btnFour.setDisable(false);
        btnFive.setDisable(false);
        btnSix.setDisable(false);
        btnSeven.setDisable(false);
        btnEight.setDisable(false);
        btnNine.setDisable(false);
        btnOne.setText("");
        btnTwo.setText("");
        btnThree.setText("");
        btnFour.setText("");
        btnFive.setText("");
        btnSix.setText("");
        btnSeven.setText("");
        btnEight.setText("");
        btnNine.setText("");
        m1.newGame();
        draw = 0;
        turnLabel.setText("X");
    }

    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonArray();
    }
}
