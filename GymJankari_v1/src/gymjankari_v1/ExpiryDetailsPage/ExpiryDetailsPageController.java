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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

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
    @FXML
    private TableColumn<Member, Integer> daysTableColumn;

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
        daysTableColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        memberdetailTableView.setRowFactory(tv -> {
            TableRow<Member> row = new TableRow<>();
            StringBinding typeBinding = Bindings.selectString(row.itemProperty(), "day");
            row.backgroundProperty().bind(Bindings.createObjectBinding(()
                    -> new Background(new BackgroundFill(typeToColor(typeBinding.get()), CornerRadii.EMPTY, Insets.EMPTY)), typeBinding));
            return row;
        });

        memberdetailTableView.getColumns();
    }

    public static Color typeToColor(String day) {
        if (day == null) {
            return Color.WHITESMOKE;
        }
        if (Integer.parseInt(day)<=0) {
            return Color.rgb(229,115,115);
        }
        else if(Integer.parseInt(day)>0 && Integer.parseInt(day)<=7){
           return Color.YELLOW;   
        }
        else{
            return Color.WHITE;
        }
    }

    public static LocalDate localDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return localDate;
    }
}
