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
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author aj33b
 */
public class HomePageController implements Initializable {
    private Main main;
    
    @FXML
    private ImageView searchmemberImageView;
    @FXML
    private ImageView addmemberImageView;
    @FXML
    private ImageView editmemberImageView;
    @FXML
    private ImageView paymentdetailsImageView;
    @FXML
    private ImageView expirydetailsImageView;
    @FXML
    private JFXButton addmemberButton;
    @FXML
    private JFXButton searchmemberButton;
    @FXML
    private JFXButton editmemberButton;
    @FXML
    private JFXButton paymentdetailsButton;
    @FXML
    private JFXButton expirydetailsButton;  
 
    
    @FXML
    private void addmemberclicked() throws IOException{
       main.showaddmemberpage();
    }
    
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
