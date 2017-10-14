/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.AddMemberPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author aj33b
 */
public class AddMemberPageController implements Initializable {

    @FXML
    private ImageView photoImageView;
    @FXML
    private JFXButton uploadButton;
    @FXML
    private JFXTextField fullnameTextField;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private JFXTextField heightTextField;
    @FXML
    private JFXTextField weightTextField;
    @FXML
    private JFXTextField streetTextField;
    @FXML
    private JFXTextField vdcmunTextField;
    @FXML
    private JFXTextField wardnoTextField;
    @FXML
    private JFXTextField districtTextField;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXTextField landlineTextField;
    @FXML
    private JFXTextField mobileTextField;
    @FXML
    private JFXTextField memberidTextField;
    @FXML
    private DatePicker membersinceDatePicker;
    @FXML
    private JFXComboBox<?> starttimeComboBox;
    @FXML
    private JFXComboBox<?> starttimeapComboBox;
    @FXML
    private JFXComboBox<?> endtimeComboBox;
    @FXML
    private JFXComboBox<?> endtimeapComboBox;
    @FXML
    private DatePicker paymentdateDatePicker;
    @FXML
    private JFXTextField paymentrateTextField;
    @FXML
    private JFXTextField paymentamountTextField;
    @FXML
    private JFXButton addButton;
    @FXML
    private ToggleGroup gender;
    
    @FXML
    private void uploadButtonClicked() throws IOException{
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files","*.jpg","*.png","*.JPG","*.PNG"),
                                    new ExtensionFilter("JPEG Files (*.jpg)","*.jpg","*.JPG"),
                                    new ExtensionFilter("PNG Files (*.png)","*.png","*.PNG"));
    File selectedFile=fc.showOpenDialog(null); 
      
    if(selectedFile != null){
        BufferedImage bufferedimage= ImageIO.read(selectedFile);
        Image img= SwingFXUtils.toFXImage(bufferedimage, null);
        photoImageView.setImage(img);
    }
    else{
        System.out.println("File is not valid!!");    
    }
    
    }
    
    @FXML
    private void addButtonClicked(){
        }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
}
