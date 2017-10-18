/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.MainView;

import com.jfoenix.controls.JFXButton;
import gymjankari_v1.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author aj33b
 */
public class MainViewController implements Initializable {
    
    private Main main;

    @FXML
    private JFXButton backButton;
    @FXML
    private JFXButton addmemberButton;
    @FXML
    private JFXButton viewmemberButton;
    @FXML
    private JFXButton aboutdevButton;
    @FXML
    private JFXButton expirydetailsButton;
    
    @FXML
    private void backButtonClicked() throws IOException{
    main.showviewmemberpage();
    }
    
    @FXML
    private void addmemberButtonClicked() throws IOException{
    main.showaddmemberpage();
    }
    
    @FXML
    private void viewmemberButtonClicked() throws IOException{
    main.showviewmemberpage();
    }
    
    @FXML
    private void expirydetailsButtonClicked() throws IOException{
    main.showexpirydetailspage();
    }
    
    @FXML
    private void aboutdevButtonClicked() throws IOException{
    main.showaboutdeveloperspage();
    }    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
