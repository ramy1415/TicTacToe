/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class XoSingleController implements Initializable {

    private final XoSingleModel m1 = new XoSingleModel();
    private final String player1 = "X";
    private final String player2 = "O";
    private final String xwin = "The Winner Is X";
    private final String owin = "The Winner Is O";
    public static String player1name;
    public static String player2name;
    public String recordd = "";
    int buttonNum;
    Stage s1;
    Scene c1;
    Parent root;
    Stage window;
    int draw = 0;
    private int xscore = 0;
    private int oscore = 0;
    private int flag = 0;
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
    Button record;
    @FXML
    Label playertwoLabelScore;
    @FXML
    Label turnLabel;
    @FXML
    private Button btnNewgame;
    @FXML
    private Button btnResetGame;
    @FXML
    private Button btnHome;

    @FXML
    private MediaView mediaView;

    public XoSingleController() {
        player1name="Player 1";
        player2name="Player 2";
    }

    @FXML
    private void btnOnePressed(ActionEvent e) {

        buttonNum = 1;
        anyButtonAction(e, 0, 0);

    }

    @FXML
    private void btnTwoPressed(ActionEvent e) {
        buttonNum = 2;

        anyButtonAction(e, 0, 1);
    }

    @FXML
    private void btnThreePressed(ActionEvent e) {
        buttonNum = 3;
        anyButtonAction(e, 0, 2);
    }

    @FXML
    private void btnFourPressed(ActionEvent e) {
        buttonNum = 4;

        anyButtonAction(e, 1, 0);
    }

    @FXML
    private void btnFivePressed(ActionEvent e) {
        buttonNum = 5;

        anyButtonAction(e, 1, 1);
    }

    @FXML
    private void btnSixPressed(ActionEvent e) {
        buttonNum = 6;

        anyButtonAction(e, 1, 2);
    }

    @FXML
    private void btnSevenPressed(ActionEvent e) {
        buttonNum = 7;

        anyButtonAction(e, 2, 0);
    }

    @FXML
    private void btnEightPressed(ActionEvent e) {
        buttonNum = 8;

        anyButtonAction(e, 2, 1);
    }

    @FXML
    private void btnNinePressed(ActionEvent e) {
        buttonNum = 9;

        anyButtonAction(e, 2, 2);
    }

    @FXML
    private void btnResetGamePressed(ActionEvent e) {
        resetButtons();
        xscore = 0;
        oscore = 0;
        playeroneLabelScore.setText(player1name+" Score : " + xscore);
        playertwoLabelScore.setText(player2name+" Score : " + oscore);
    }

    @FXML
    private void btnNewgamePressed(ActionEvent e) {
        playeroneLabelScore.setText(player1name+" Score : " + xscore);
        playertwoLabelScore.setText(player2name+" Score : " + oscore);
        resetButtons();
        recordd = "";

    }

    public void ChooseXPressed(ActionEvent e) {
        Button b = (Button) e.getSource();
        Stage s = (Stage) b.getScene().getWindow();
        s.close();
    }

    public void ChooseOPressed(ActionEvent e) {
        Button b = (Button) e.getSource();
        Stage s = (Stage) b.getScene().getWindow();
        s.close();
    }

    private void anyButtonAction(ActionEvent a, int x, int y) {
        Button b = (Button) a.getSource();
        if (flag == 0) {
            m1.setarr(x, y, player1);
            b.setStyle("-fx-text-fill: red;");
            b.setText(player1);
            recordd = recordd + m1.getarr(x, y) + ";" + Integer.toString(buttonNum) + ";";

            if (m1.checkwinner()) {
                xWon();
            } else {
                draw++;
            }
            if (draw > 8) {
                JOptionPane.showMessageDialog(null, "draw");
            }
            b.setDisable(true);
            flag = 1;
            turnLabel.setText(player2name);
        } else {
            m1.setarr(x, y, player2);

            b.setText(player2);

            recordd = recordd + m1.getarr(x, y) + ";" + Integer.toString(buttonNum) + ";";

            if (m1.checkwinner()) {
                oWon();
            } else {
                draw++;
            }
            if (draw > 8) {
                JOptionPane.showMessageDialog(null, "draw");
            }
            b.setDisable(true);
            flag = 0;
            turnLabel.setText(player1name);
        }
    }

    public void recordPressed(ActionEvent e) {
        Date date = new Date();
        Instant instant = date.toInstant();

        StringTokenizer inst = new StringTokenizer(instant.toString(), ".");
        String name = "username";
        File file = new File("C:\\records\\" + name + inst.nextToken().replace(":", "-") + ".txt");
        Path records = Paths.get("C:\\records");
        if (file != null) {
            byte[] b2 = recordd.getBytes();
            try {
                Files.createDirectories(records);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(b2);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void xWon() {

        PauseTransition pause = new PauseTransition(Duration.millis(20));
        pause.setOnFinished(event
                -> {
            // JOptionPane.showMessageDialog(null, xwin);
            
             String path ="src\\Media\\win.mp4";
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
            stage1.setMinWidth(550);
            stage1.setMinHeight(450);
            stage1.setMaxHeight(450);
            stage1.setMaxWidth(550);
            stage1.centerOnScreen();
            mediaPlayer.setOnEndOfMedia(() -> {
                stage1.close();
            });
            stage1.setResizable(false);
            stage1.show();
        });
        pause.play();
        disableAllbtns();
        
        playeroneLabelScore.setText(player1name+" Score : " + (++xscore));
    }

    private void oWon() {

        PauseTransition pause = new PauseTransition(Duration.millis(20));
        pause.setOnFinished(event
                -> {
            // JOptionPane.showMessageDialog(null, xwin);
            
             String path ="src\\Media\\win.mp4";
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
            stage1.setMinWidth(550);
            stage1.setMinHeight(450);
            stage1.setMaxHeight(450);
            stage1.setMaxWidth(550);
            stage1.centerOnScreen();
            mediaPlayer.setOnEndOfMedia(() -> {
                stage1.close();
            });
            stage1.setResizable(false);
            stage1.show();
        });
        pause.play();
        disableAllbtns();
        playertwoLabelScore.setText(player2name+" Score : " + (++oscore));
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
        btnOne.setStyle("-fx-text-fill: #ffc200;");
        btnTwo.setStyle("-fx-text-fill: #ffc200;");
        btnThree.setStyle("-fx-text-fill: #ffc200;");
        btnFour.setStyle("-fx-text-fill: #ffc200;");
        btnFive.setStyle("-fx-text-fill: #ffc200;");
        btnSix.setStyle("-fx-text-fill: #ffc200;");
        btnSeven.setStyle("-fx-text-fill: #ffc200;");
        btnEight.setStyle("-fx-text-fill: #ffc200;");
        btnNine.setStyle("-fx-text-fill: #ffc200;");
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
        flag = 0;
        turnLabel.setText(player1name);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Stage stage1=new Stage();
            root = FXMLLoader.load(getClass().getResource("/MultiPlayer/namesModal.fxml"));
            Scene HomeScene = new Scene(root);
            stage1.setScene(HomeScene);
            stage1.setResizable(false);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.show();
        } catch (IOException ex) {
            Logger.getLogger(XoSingleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnHomePressed(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/tictactoe/HomePage.fxml"));
            Scene HomeScene = new Scene(root);
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(HomeScene);
            window.setResizable(false);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(XoSingleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
