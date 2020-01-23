/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiPlayer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tictactoe.GameController;

/**
 * FXML Controller class
 *
 * @author ramy1
 */
public class NamesModalController implements Initializable {

    @FXML
    private TextArea textPlayerOne;
    @FXML
    private TextArea textPlayertwo;
    @FXML
    private Button btnOk;
    private Stage window;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnOkPressed(ActionEvent event) {
        Label name1=(Label) GameController.multiplayerpage.getScene().lookup("#playeroneLabelScore");
        Label name2=(Label) GameController.multiplayerpage.getScene().lookup("#playertwoLabelScore");
        if(!textPlayerOne.getText().equals("")&&!textPlayertwo.getText().equals("")){
//            name1.setText(textPlayerOne.getText());
//            name2.setText(textPlayertwo.getText());
            XoSingleController.player1name=textPlayerOne.getText();
            XoSingleController.player2name=textPlayertwo.getText();
            name1.setText(XoSingleController.player1name+" Score : 0");
            name2.setText(XoSingleController.player2name+" Score : 0");

            
            window= (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
        }else{
            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, "Please enter names !", ButtonType.OK);
            a1.show();
        }
        
    }
    
}
