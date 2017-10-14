/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.HomePage;

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
    private void backbuttonclicked() throws IOException{
    main.showHomePage();
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
