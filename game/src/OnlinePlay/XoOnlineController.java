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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private Button btnRematch;
    @FXML
    private Label playeroneLabelScore;
    @FXML
    private Label playertwoLabelScore;
    @FXML
    private Label turnLabel;
    @FXML
    private MediaView mediaView;
    
    Parent root;
    Stage window;
    public String recordd = "";
    public static int myscore;
    public static int hisscore;
    private ObjectInputStream comingStream;
    private ObjectOutputStream goingStream;
    private String oponent;
    private String myname;
    public static boolean myturn;
    private String mySympol;
    private final String win = "Congratulation! You won!";
    public static XoSingleModel board;
    public static int draw = 0;
    private static String nowTurn;
    public static boolean firstleave = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goingStream = GameController.getPlayer().goingStream;
        oponent = TicTacTocClient.getOponent();
        myname = GameController.myname;
        mySympol = TicTacTocClient.mySympol;
        board = new XoSingleModel();
        System.err.println("my s= " + mySympol);
        draw = 0;
        myscore = 0;
        hisscore = 0;
        playeroneLabelScore.setText("" + GameController.myname + " : " + (myscore));
        playertwoLabelScore.setText("" + oponent + " : " + (myscore));
    }
    
    @FXML
    private void btnOnePressed(ActionEvent event) {
        if (myturn) {
            btnOne.setText(TicTacTocClient.mySympol);
            btnOne.setDisable(true);
            board.setarr(0, 0, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "1");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                }
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnTwoPressed(ActionEvent event) {
        
        if (myturn) {
            btnTwo.setText(TicTacTocClient.mySympol);
            btnTwo.setDisable(true);
            board.setarr(0, 1, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "2");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnThreePressed(ActionEvent event) {
        if (myturn) {
            btnThree.setText(TicTacTocClient.mySympol);
            btnThree.setDisable(true);
            board.setarr(0, 2, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "3");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnFourPressed(ActionEvent event) {
        if (myturn) {
            btnFour.setText(TicTacTocClient.mySympol);
            btnFour.setDisable(true);
            board.setarr(1, 0, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "4");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnFivePressed(ActionEvent event) {
        if (myturn) {
            btnFive.setText(TicTacTocClient.mySympol);
            btnFive.setDisable(true);
            board.setarr(1, 1, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "5");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnSixPressed(ActionEvent event) {
        if (myturn) {
            btnSix.setText(TicTacTocClient.mySympol);
            btnSix.setDisable(true);
            board.setarr(1, 2, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "6");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnSevenPressed(ActionEvent event) {
        if (myturn) {
            btnSeven.setText(TicTacTocClient.mySympol);
            btnSeven.setDisable(true);
            board.setarr(2, 0, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "7");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnEightPressed(ActionEvent event) {
        if (myturn) {
            btnEight.setText(TicTacTocClient.mySympol);
            btnEight.setDisable(true);
            board.setarr(2, 1, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "8");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void btnNinePressed(ActionEvent event) {
        if (myturn) {
            btnNine.setText(TicTacTocClient.mySympol);
            btnNine.setDisable(true);
            board.setarr(2, 2, TicTacTocClient.mySympol);
            boolean check = checkWinning();
            Request moverequest = new Request(RequestType.MOVE);
            moverequest.setData("oponent", oponent);
            moverequest.setData("myname", myname);
            moverequest.setData("moveplace", "9");
            myturn = false;
            Request changeTurnReq = changeTurnRequest();
            try {
                goingStream.writeObject(moverequest);
                if (!check && draw < 4) {
                    goingStream.writeObject(changeTurnReq);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
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
    
    @FXML
    private void btnRematchPressed(ActionEvent event) {
        Request rematchRequest = new Request(RequestType.REMATCH);
        rematchRequest.setData("targetname", oponent);
        rematchRequest.setData("myname", myname);
        try {
            goingStream.writeObject(rematchRequest);
        } catch (IOException ex) {
            Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void profilePressed(ActionEvent event) {
        try {
            if (firstleave) {
                Alert a2 = new Alert(Alert.AlertType.CONFIRMATION, GameController.myname + " you will lose if you leave during the game!", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result2 = a2.showAndWait();
                if (result2.get() == ButtonType.YES) {
                    Request notplayingrequest = new Request(RequestType.LEAVE);
                    notplayingrequest.setData("myname", myname);
                    notplayingrequest.setData("oponent", oponent);
                    goingStream.writeObject(notplayingrequest);
                    TicTacTocClient.someoneleft = true;
                } else if (result2.get() == ButtonType.NO) {
                    return;
                }
            } else {
                Request leave = new Request(RequestType.NOTPLAYING);
                leave.setData("myname", myname);
                leave.setData("oponent", oponent);
                goingStream.writeObject(leave);
                TicTacTocClient.someoneleft = true;
            }
            root = FXMLLoader.load(getClass().getResource("/tictactoe/ProfilePage.fxml"));
            Scene profileScene = new Scene(root);
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setResizable(false);
            window.show();
            ListView<String> a = (ListView<String>) window.getScene().lookup("#listViewClients");
            a.setItems(null);
            GameController.getPlayer().askfornames(GameController.myname);
            a.setItems(GameController.getPlayer().getClients());
            firstleave = true;
        } catch (IOException ex) {
            Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void winAlert() {
        Request winningGamesRequest = new Request(RequestType.WINNING_GAMES);
        winningGamesRequest.setData("win", "1");
        winningGamesRequest.setData("username", myname);
        firstleave = false;
        btnRematch.setDisable(false);
        try {
            goingStream.writeObject(winningGamesRequest);
        } catch (IOException ex) {
            Logger.getLogger(XoOnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PauseTransition pause = new PauseTransition(Duration.millis(20));
        pause.setOnFinished(event
                -> {
            // JOptionPane.showMessageDialog(null, xwin);
<<<<<<< HEAD
            String path = "F:\\ITI\\Java\\project2\\win.mp4";
=======
            String path = "src\\Media\\win.mp4";
>>>>>>> ca3e0c7086b2788a9779f22a25a0f57c1c1c8c2f
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
        playeroneLabelScore.setText("" + GameController.myname + " : " + (++myscore));
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
            firstleave = false;
            Request loseRequest = new Request(RequestType.LOSE);
            loseRequest.setData("oponent", oponent);
            loseRequest.setData("myname", myname);
            try {
                goingStream.writeObject(loseRequest);
                
            } catch (IOException ex) {
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(XoOnlineController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    private Request changeTurnRequest() {
        Request changeTurnReq = new Request(RequestType.CHANGETURN);
        changeTurnReq.setData("oponent", oponent);
        changeTurnReq.setData("myname", myname);
        return changeTurnReq;
    }

    private void changeTurn(String nowTurnName) {
        Label nowTurn = (Label) TicTacTocClient.getOnlineStage().getScene().lookup("#turnLabel");
        System.out.println("TurnName:" + nowTurnName);
        nowTurn.setText(nowTurnName);
    }
}
