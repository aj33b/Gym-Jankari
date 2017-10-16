/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.ViewMemberPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import gymjankari_v1.serviceimplementation.MemberServiceImplementation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author aj33b
 */
public class ViewMemberPageController implements Initializable {
    
    @FXML
    private RadioButton memberIdRadioButton;
    @FXML
    private RadioButton nameRadioButton;
    @FXML
    private JFXTextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private TableView<Member> memberdetailTableView;
    @FXML
    private TableColumn<Member, String> memberidTableColumn;
    @FXML
    private TableColumn<Member, String> fullnameTableColumn;
    @FXML
    private TableColumn<Member, String> shiftTableColumn;
    @FXML
    private TableColumn<Member, String> phonenoTableColumn;
    @FXML
    private TableColumn<Member, String> expirydateTableColumn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MemberService memberService = new MemberServiceImplementation();
        populateTable();
        memberdetailTableView.setItems(memberService.getAllMember());
            
    }    

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        MemberService memberService = new MemberServiceImplementation();
        populateTable();
        if(memberIdRadioButton.isSelected()){  
            memberdetailTableView.setItems(memberService.searchById(searchTextField.getText()));
        }else if(nameRadioButton.isSelected()){
            memberdetailTableView.setItems(memberService.searchByName(searchTextField.getText()));
        }else{ 
            memberdetailTableView.setItems(memberService.getAllMember());
        }  
    }
    
    public void populateTable(){
        memberidTableColumn.setCellValueFactory(new PropertyValueFactory<>("displayId"));
        fullnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        shiftTableColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));
        phonenoTableColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        expirydateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
    }
    
}
