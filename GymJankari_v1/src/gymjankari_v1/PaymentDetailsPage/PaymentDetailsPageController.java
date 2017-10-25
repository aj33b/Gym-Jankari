/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.PaymentDetailsPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author ajeeb
 */
public class PaymentDetailsPageController implements Initializable {

    @FXML
    private JFXTextField fullnameTextField;
    @FXML
    private JFXTextField memberidTextField;
    @FXML
    private JFXTextField paymentrateTextField;
    @FXML
    private JFXDatePicker paymentdateDatePicker;
    @FXML
    private JFXTextField paymentamountTextField;
    @FXML
    private JFXButton adddetailsButton;
    @FXML
    private TableView<?> paymentdetailTableView;
    @FXML
    private TableColumn<?, ?> paymentdateTableColumn;
    @FXML
    private TableColumn<?, ?> paymentamountTableColumn;
    @FXML
    private JFXButton saveButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void adddetailsButtonClicked(ActionEvent event) {
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
    }
    
}
