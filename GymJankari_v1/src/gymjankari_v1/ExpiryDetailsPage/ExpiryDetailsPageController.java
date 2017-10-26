/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.ExpiryDetailsPage;

import gymjankari_v1.Main;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import gymjankari_v1.serviceimplementation.MemberServiceImplementation;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author aj33b
 */
public class ExpiryDetailsPageController implements Initializable {

    private Main main;
    @FXML
    private TableView<Member> memberdetailTableView;
    @FXML
    private TableColumn<Member, String> memberidTableColumn;
    @FXML
    private TableColumn<Member, String> fullnameTableColumn;
    @FXML
    private TableColumn<Member, String> startTimeTableColumn;
    @FXML
    private TableColumn<Member, String> endTimeTableColumn;
    @FXML
    private TableColumn<Member, String> phonenoTableColumn;
    @FXML
    private TableColumn<Member, String> expirydateTableColumn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MemberService memberService = new MemberServiceImplementation();
        ObservableList<Member> expiryDetailsList = FXCollections.observableArrayList();
        expiryDetailsList = memberService.getAllMember();
        FXCollections.sort(expiryDetailsList, new Comparator<Member>() {
            @Override
            public int compare(Member o1, Member o2) {
                Date date1 = null;
                Date date2 = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date1 = sdf.parse(o1.getExpiryDate());
                    date2 = sdf.parse(o2.getExpiryDate());
                } catch (ParseException ex) {
                    Logger.getLogger(ExpiryDetailsPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return date1.compareTo(date2);
            }
        });
        populateTable();
        memberdetailTableView.setItems(expiryDetailsList);
    }

    public void populateTable() {
        memberidTableColumn.setCellValueFactory(new PropertyValueFactory<>("displayId"));
        fullnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        startTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        phonenoTableColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        expirydateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
    }

}
