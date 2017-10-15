/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.ViewMemberPage;

import com.jfoenix.controls.JFXButton;
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
 * @author aj33b
 */
public class ViewMemberPageController implements Initializable {

    @FXML
    private JFXTextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private TableView<?> memberdetailTableView;
    @FXML
    private TableColumn<?, ?> memberidTableColumn;
    @FXML
    private TableColumn<?, ?> fullnameTableColumn;
    @FXML
    private TableColumn<?, ?> shiftTableColumn;
    @FXML
    private TableColumn<?, ?> phonenoTableColumn;
    @FXML
    private TableColumn<?, ?> expirydateTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchButtonClicked(ActionEvent event) {
    }
    
}
