/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.PaymentDetailsPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import gymjankari_v1.Main;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private JFXDatePicker paymentdateDatePicker;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
        MemberService memberService = new MemberServiceImplementation();
        Member member = new Member();
        LocalDate payDate = paymentdateDatePicker.getValue();
        if (payDate == null) {
            LocalDate date = LocalDate.now();
            member.setPayDate(date.toString());
        } else {
            member.setPayDate(paymentdateDatePicker.getValue().toString());
        }
        String payAmount = paymentamountTextField.getText();
        if (payAmount.isEmpty()) {
            amountFieldValidation();
        } else {
            member.setPayAmount(Float.parseFloat(paymentamountTextField.getText()));
            int day = expiryDateCalculation(paymentrateTextField.getText(), payAmount);
            if (day != 0) {

                if (LocalDate.now().compareTo(expiryDateFromDB) < 0) {
                    member.setExpiryDate(calculateExpiryDate(expiryDateFromDB.toString(), day));
                }else{
                    member.setExpiryDate(calculateExpiryDate(paymentdateDatePicker.getValue().toString(), day));
                }

            } else {
                member.setExpiryDate(expiryDateFromDB.toString());
            }
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
        fullnameTextField.setText(member.getFullName());
        memberidTextField.setText(member.getDisplayId());
        paymentrateTextField.setText(String.valueOf(member.getPayRate()));
        expiryDateFromDB = localDate(member.getExpiryDate());
        populateTable();
        paymentdetailTableView.setItems(memberService.getPaymentDetails(displayId));
    }

    public void populateTable() {
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

    public String calculateExpiryDate(String initialDate,int day) {
        Date date = java.sql.Date.valueOf(initialDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        Date expiryDate = calendar.getTime();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(expiryDate);
        return formattedDate;
    }

}
