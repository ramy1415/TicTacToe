/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import TicTacTocClient.TicTacTocClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import sun.management.Agent;

/**
 *
 * @author Mahmoud
 */
public class GameController implements Initializable {

    Parent root;
    Stage window;
    String view = "";
    
        String[] views={"","","","","","","","",""};

    //made those static and added ip
    static TicTacTocClient player;
    static Socket socket;
    static String myip;
    static String myname;
    @FXML
    Button btnOne1;
    @FXML
    Button btnTwo2;
    @FXML
    Button btnThree3;
    @FXML
    Button btnFour4;
    @FXML
    Button btnFive5;
    @FXML
    Button btnSix6;
    @FXML
    Button btnSeven7;
    @FXML
    Button btnEight8;
    @FXML
    Button btnNine9;
    @FXML
    Button playBtn;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private TextField registerUsernameTextField;
    @FXML
    private TextField registerPasswordTextField;
    

    //new putton and handler for test
    @FXML
    private Button btnOnlinePlay;
    
    @FXML
    private ListView gamesRecordedList;
    @FXML
    private Button refreshViewListBtn;
    @FXML
    private Button HomeBtn;
    @FXML
    private Button viewGames;
    @FXML
    private ListView<String> listViewClients;
    @FXML
    private Button btnRefreshnames;

    @FXML
    private void btnOnlinePlayPressed(ActionEvent event) throws IOException {
        boolean selcted = true;
        if (listViewClients.getSelectionModel().isEmpty()) {
            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, "Please select a player", ButtonType.OK);
            a1.show();
            selcted = false;

        }
        if (selcted) {
            player.asktoplay(myname, listViewClients.getSelectionModel().getSelectedItem(), event);
        }
    }
    @FXML
    private void singlePlayerPressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/xosingle/XoSingleView.fxml"));
        Scene singleScene = new Scene(root);
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(singleScene);
        window.show();
    }
    @FXML
    private void multiPlayerPressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MultiPlayer/XoSingleView.fxml"));
        Scene multiScene = new Scene(root);
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(multiScene);
        window.show();
    }
    @FXML
    private void playOnlinePressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("PlayOnline.fxml"));
        Scene onlineScene = new Scene(root);
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(onlineScene);
        window.show();
    }

    @FXML
    private void homePressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene HomeScene = new Scene(root);
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(HomeScene);
        window.show();
    }
    @FXML
    private void aboutPressed(ActionEvent event) throws IOException {
        // System.exit(0);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This is XO game developed by "
                + "Open Source group in ITI Mansoura branch in 2020");
        alert.showAndWait();

    }
    @FXML
    private void exitPressed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit now?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }
    @FXML
    private void loginPressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene LoginScene = new Scene(root);
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(LoginScene);
        window.show();

    }
    @FXML
    private void registerPressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RegisterPage.fxml"));
        Scene RegisterScene = new Scene(root);
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(RegisterScene);
        window.show();
    }
    @FXML
    private void inLoginPressed(ActionEvent event) throws IOException, InterruptedException {
        socket = new Socket("127.0.0.1", 5005);
        player = new TicTacTocClient(socket, event);
        player.login(usernameTextField.getText().toString(), passwordTextField.getText().toString(), event);
        myname = usernameTextField.getText().toString();
        Thread.sleep(3000);
        if ("success".equals(player.response)) {
            root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
            Scene ProfileScene = new Scene(root);
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(ProfileScene);
            window.show();
            player.passStage(window);
        } else if ("failure".equals(player.response)) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please try again with a valid"
                    + " username and password! ", ButtonType.OK);
            alert.show();
        }

    }
    @FXML
    private void registrationConfirmPressed(ActionEvent event) throws IOException, InterruptedException {
        socket = new Socket("127.0.0.1", 5005);
        player = new TicTacTocClient(socket, event);
        String fullname = fullNameTextField.getText().toString();
        String age = ageTextField.getText().toString();
        String username = registerUsernameTextField.getText().toString();
        String password = registerPasswordTextField.getText().toString();
        player.registration(fullname, age, username, password);
        Thread.sleep(5000);
        if ("success".equals(player.response)) {
            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            Scene NewUserScene = new Scene(root);
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(NewUserScene);
            window.show();
        } else if ("failure".equals(player.response)) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please try again with  valid"
                    + "values ", ButtonType.OK);
            alert.show();
        }
    }

    @FXML 
    private void refreshViewList(ActionEvent e){
    File selectedDirectory=new File("C:\\records");
    File[] flist=selectedDirectory.listFiles();
    if (selectedDirectory!= null) {
        for (int i = 0; i < flist.length; i++) {
            gamesRecordedList.getItems().add(flist[i]);
        }
    } else {
        System.out.println("File is not Valid");
    }
    }
    
    @FXML
    private void viewGamesBtn(ActionEvent e) {
        
        String game=gamesRecordedList.getSelectionModel().getSelectedItem().toString();
      //we should here put the name of the game
        File selectedFile = new File(game);
        byte[] b = new byte[(int) selectedFile.length()];
        if (selectedFile != null) {
        try {
        FileInputStream fis = new FileInputStream(selectedFile);
        fis.read(b);
        view = new String(b);
        System.out.println(view);
        fis.close();
        } catch (FileNotFoundException ex) {
        ex.printStackTrace();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        }
        
        viewgames(view);
       
        try {
            root = FXMLLoader.load(getClass().getResource("viewGames.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene NewUserScene = new Scene(root);
        window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(NewUserScene);
        window.show();

    }

    public static TicTacTocClient getPlayer() {
        return player;
    }

    public void viewgames(String _view) {
        _view = view;
        views = _view.split(";");
        for (int i=0; i<views.length; i++) {
            player.viewRes.add(views[i]);
            //System.out.println(views[i]);
        }
    }
    /*public HashMap<String,Button> mapper=new HashMap<>();
    mapper={
    "1":
    }*/

    public void PlayBtnPressed(ActionEvent e) {
        for (int i=1; i<player.viewRes.size(); i++) {    
            System.out.println(player.viewRes.get(i));
            if(null!=player.viewRes.get(i))
                switch (player.viewRes.get(i)) {
                case "1":
                    PauseTransition pause = new PauseTransition(Duration.seconds(1+i));
                    String s=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnOne1.setText(s));
                    pause.play();
                    break;
                case "2":
                    pause = new PauseTransition(Duration.seconds(1+i));
                    String s2=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnTwo2.setText(s2));
                    pause.play();
                    break;
                case "3":                    
                pause = new PauseTransition(Duration.seconds(1+i));
                    String s3=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnThree3.setText(s3));
                    pause.play();                    
                    break;
                case "4":
                pause = new PauseTransition(Duration.seconds(1+i));
                    String s4=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnFour4.setText(s4));
                    pause.play();                    
                    break;
                case "5":                                       
                pause = new PauseTransition(Duration.seconds(1+i));
                    String s5=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnFive5.setText(s5));
                    pause.play();                    
                    break;
                case "6":
                pause = new PauseTransition(Duration.seconds(1+i));
                    String s6=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnSix6.setText(s6));
                    pause.play();                    
                    break;
                case "7":
                    pause = new PauseTransition(Duration.seconds(1+i));
                    String s7=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnSeven7.setText(s7));
                    pause.play();
                    break;
                case "8":                    
                    pause = new PauseTransition(Duration.seconds(1+i));
                    String s8=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnEight8.setText(s8));
                    pause.play();
                    break;
                case "9":
                    pause = new PauseTransition(Duration.seconds(1+i));
                    String s9=player.viewRes.get(i-1);
                pause.setOnFinished(event ->
                    btnNine9.setText(s9));
                    pause.play();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnRefreshnamesPressed(ActionEvent event) {
        listViewClients.setItems(null);
        player.askfornames(myname);
        listViewClients.setItems(player.getClients());
    }
}
