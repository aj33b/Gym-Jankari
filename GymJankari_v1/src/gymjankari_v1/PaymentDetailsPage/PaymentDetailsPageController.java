/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.PaymentDetailsPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import gymjankari_v1.Main;
import gymjankari_v1.dateconverter.DateConverter;
import gymjankari_v1.dateconverter.DateConverterInterface;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import gymjankari_v1.serviceimplementation.MemberServiceImplementation;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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
    private JFXTextField paymentamountTextField;
    @FXML
    private TableView<Member> paymentdetailTableView;
    @FXML
    private TableColumn<Member, String> paymentdateTableColumn;
    @FXML
    private TableColumn<Member, Float> paymentamountTableColumn;
    @FXML
    private JFXButton saveButton;

    private LocalDate expiryDateFromDB;
    @FXML
    private JFXComboBox<String> paybsmonth;
    @FXML
    private JFXComboBox<String> paybsday;
    @FXML
    private JFXComboBox<Integer> paybsyear;

    DateConverterInterface dateConverterInterface = new DateConverter();
    LocalDate default_date = LocalDate.now();
    String default_bs_date = dateConverterInterface.convertAdToBs(default_date.toString());
    String defaultBsDate[] = default_bs_date.split("-");
    String defaultYear = defaultBsDate[0];
    String defaultMonth = defaultBsDate[1];
    String defaultDay = defaultBsDate[2];

    ObservableList<String> mList = FXCollections.observableArrayList("Baisakh", "Jestha", "Asadh", "Shrawan", "Bhadra", "Asoj", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra");
    ObservableList<Integer> yList = FXCollections.observableArrayList();
    ObservableList<String> defaultDays = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32");
    @FXML
    private JFXButton payDeleteButton;
    @FXML
    private TableColumn<Member, Integer> idTableColumn;
    @FXML
    private TableColumn<Member, String> memberIdTableColumn;

    int id = -1;
    String displayId = null;
    @FXML
    private JFXRadioButton cdp;
    @FXML
    private ToggleGroup payment;
    @FXML
    private JFXRadioButton np;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        payDeleteButton.setDisable(true);
        MemberService memberService = new MemberServiceImplementation();
        for (int i = 1999; i <= 2099; i++) {
            yList.add(i);
        }
        paybsyear.setItems(yList);
        paybsmonth.setItems(mList);
        paybsday.setItems(defaultDays);

        paybsyear.getSelectionModel().select(yList.get(Integer.parseInt(defaultYear) - 1999));
        paybsmonth.getSelectionModel().select(mList.get(Integer.parseInt(defaultMonth) - 1));
        paybsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(defaultDay) - 1));

        paybsyear.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<String> dList = FXCollections.observableArrayList();
                String selectedYear = String.valueOf(paybsyear.getSelectionModel().getSelectedItem());
                int selectedMonth = Integer.parseInt(memberService.monthToNumberConversion(paybsmonth.getSelectionModel().getSelectedItem()));
                dList = memberService.dayValues(selectedYear, selectedMonth);
                paybsday.setItems(dList);
            }

        });
        paybsmonth.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> dList = FXCollections.observableArrayList();
                String selectedYear = String.valueOf(paybsyear.getSelectionModel().getSelectedItem());
                int selectedMonth = Integer.parseInt(memberService.monthToNumberConversion(paybsmonth.getSelectionModel().getSelectedItem()));
                dList = memberService.dayValues(selectedYear, selectedMonth);
                paybsday.setItems(dList);

            }

        });
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
        MemberService memberService = new MemberServiceImplementation();
        Member member = new Member();
        String payYear = paybsyear.getSelectionModel().getSelectedItem().toString();
        String payMonth = String.valueOf(memberService.monthToNumberConversion(paybsmonth.getSelectionModel().getSelectedItem()));
        String payDay = String.valueOf(paybsday.getSelectionModel().getSelectedItem());
        String payBsDate = payYear.concat("-").concat(payMonth).concat("-").concat(payDay);
        member.setPayDate(dateConverterInterface.convertBsToAd(payBsDate));
        member.setPayRate(Float.parseFloat(paymentrateTextField.getText()));
        String payAmount = paymentamountTextField.getText();
        if (payAmount.isEmpty()) {
            amountFieldValidation();
        } else {
            member.setPayAmount(Float.parseFloat(paymentamountTextField.getText()));
            int day = expiryDateCalculation(paymentrateTextField.getText(), payAmount);
            if (day != 0) {

                if (LocalDate.now().compareTo(expiryDateFromDB) < 0) {
                    member.setExpiryDate(calculateExpiryDate(expiryDateFromDB.toString(), day));
                } else {
                    if(cdp.isSelected()){
                        member.setExpiryDate(calculateExpiryDate(expiryDateFromDB.toString(), day));
                    }else if(np.isSelected()){
                        member.setExpiryDate(calculateExpiryDate(dateConverterInterface.convertBsToAd(payBsDate), day));
                    }
                }

            } else {
                member.setExpiryDate(expiryDateFromDB.toString());
            }
            int daysRemaining = memberService.calculateDays(member.getExpiryDate());
            member.setDay(daysRemaining);
            if (memberService.updatePaymentDetails(member, memberidTextField.getText())) {
                Image img = new Image("gymjankari_v1/images/checked_icon.png");
                Notifications addedNotifications = Notifications.create()
                        .title("Payment Updated")
                        .text("The payment has been updated successfully.")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                addedNotifications.show();
            } else {
                Notifications errorNotifications = Notifications.create()
                        .title("Failed to Update Payment")
                        .text("Sorry! The Payment has not been updated due to some error.")
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                errorNotifications.showError();
            }
            try {
                Main.showviewmemberpage();
            } catch (IOException ex) {
                Logger.getLogger(PaymentDetailsPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setData(String displayId) {
        MemberService memberService = new MemberServiceImplementation();
        Member member = memberService.getById(displayId);
        expiryDateFromDB = localDate(member.getExpiryDate());
        String defaultPaymentDate[] = dateConverterInterface.convertAdToBs(expiryDateFromDB.toString()).split("-");
        String defaultPaymentYear = defaultPaymentDate[0];
        String defaultPaymentMonth = defaultPaymentDate[1];
        String defaultPaymentDay = defaultPaymentDate[2];
        fullnameTextField.setText(member.getFullName());
        memberidTextField.setText(member.getDisplayId());
        paymentrateTextField.setText(String.valueOf(member.getPayRate()));

        populateTable();
        paymentdetailTableView.setItems(memberService.getPaymentDetails(displayId));
    }

    public void populateTable() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("displayId"));
        paymentdateTableColumn.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        paymentamountTableColumn.setCellValueFactory(new PropertyValueFactory<>("payAmount"));
    }

    public void amountFieldValidation() {
        RequiredFieldValidator amountFieldValidator = new RequiredFieldValidator();
        paymentamountTextField.getValidators().add(amountFieldValidator);
        amountFieldValidator.setMessage("Cannot be empty");
        paymentamountTextField.validate();
    }

    public int expiryDateCalculation(String payRate, String payAmount) {
        float ratePerDay = Float.parseFloat(payRate) / 30;
        float validDays = Float.parseFloat(payAmount) / ratePerDay;
        return (int) validDays;
    }

    public LocalDate localDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return localDate;
    }

    public String calculateExpiryDate(String initialDate, int day) {
        Date date = java.sql.Date.valueOf(initialDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        Date expiryDate = calendar.getTime();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(expiryDate);
        return formattedDate;
    }

    @FXML
    private void payDeleteButtonClicked() {
        MemberService memberService = new MemberServiceImplementation();
        if (displayId != null && id != -1) {
            if (memberService.deletePaymentDetails(id, displayId)) {
                Image img = new Image("gymjankari_v1/images/checked_icon.png");
                Notifications addedNotifications = Notifications.create()
                        .title("Payment Detail Deleted")
                        .text("The Payment Detail has been deleted successfully.")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                addedNotifications.show();
            } else {
                Notifications errorNotifications = Notifications.create()
                        .title("Failed to Delete Payment Detail")
                        .text("Sorry! The Payment Detail has not been deleted due to some error.")
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                errorNotifications.showError();
            }
            try {
                Main.showviewmemberpage();
            } catch (IOException ex) {
                Logger.getLogger(PaymentDetailsPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getClickCount() == 1) {
            id = paymentdetailTableView.getSelectionModel().getSelectedItem().getId();
            displayId = paymentdetailTableView.getSelectionModel().getSelectedItem().getDisplayId();
            if (displayId != null && id != -1) {
                payDeleteButton.setDisable(false);
            }
        }
    }
}
