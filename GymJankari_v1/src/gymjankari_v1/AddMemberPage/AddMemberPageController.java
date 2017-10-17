/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.AddMemberPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gymjankari_v1.Main;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import gymjankari_v1.serviceimplementation.MemberServiceImplementation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

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
    private RadioButton otherRadioButton;
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
    private JFXComboBox<Integer> starttimeComboBox;
    @FXML
    private JFXComboBox<String> starttimeapComboBox;
    @FXML
    private JFXComboBox<Integer> endtimeComboBox;
    @FXML
    private JFXComboBox<String> endtimeapComboBox;
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
    
    private Main main;
    
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
        Member member = new Member();
        member.setFullName(fullnameTextField.getText());
        LocalDate dob= dobDatePicker.getValue();
        if(dob==null){
            LocalDate date = LocalDate.now();
            member.setDOB(date.toString());
        }else{
        member.setDOB(dobDatePicker.getValue().toString());
        }
        if(maleRadioButton.isSelected()){
            member.setGender("Male");
        }else if(femaleRadioButton.isSelected()){
            member.setGender("Female");
        }else if(otherRadioButton.isSelected()){
            member.setGender("Other");
        }  
        member.setHeight(heightTextField.getText());
        member.setWeight(weightTextField.getText());
        member.setStreet(streetTextField.getText());
        member.setVdcmun(vdcmunTextField.getText());
        member.setWard(wardnoTextField.getText());
        member.setDistrict(districtTextField.getText());
        member.setEmail(emailTextField.getText());
        member.setLandline(landlineTextField.getText());
        member.setMobile(mobileTextField.getText());
        String memberId = memberidTextField.getText();
        if(memberId.isEmpty())
        {
            Notifications errorNotifications=Notifications.create()
            .title("Failed to Add Member")
            .text("Sorry! Member Id cannot be empty and must be unique!!!")
            .hideAfter(Duration.seconds(5))
            .position(Pos.TOP_RIGHT);
            errorNotifications.showError();
        }
        else{
        member.setmId(memberId);
        LocalDate memberSince= membersinceDatePicker.getValue();
        if(memberSince==null){
            LocalDate date = LocalDate.now();
            member.setMemberSince(date.toString());
        }else{
        member.setMemberSince(membersinceDatePicker.getValue().toString());
        }
        String start = starttimeComboBox.getSelectionModel().getSelectedItem().toString();
        String startap = starttimeapComboBox.getSelectionModel().getSelectedItem();
        String end = endtimeComboBox.getSelectionModel().getSelectedItem().toString();
        String endap = endtimeapComboBox.getSelectionModel().getSelectedItem();
        String shift = start.concat(startap).concat("-").concat(end).concat(endap);
        member.setShift(shift);
        LocalDate payDate= paymentdateDatePicker.getValue();
        if(payDate==null){
            LocalDate date = LocalDate.now();
            member.setPayDate(date.toString());
        }else{
        member.setPayDate(paymentdateDatePicker.getValue().toString());
        }
        String payRate =  paymentrateTextField.getText();
        if(payRate.isEmpty()){
            member.setPayRate(0);
        }else{
        member.setPayRate(Float.parseFloat(paymentrateTextField.getText()));
        }
         String payAmount =  paymentamountTextField.getText();
        if(payAmount.isEmpty()){
            member.setPayAmount(0);
        }else{
        member.setPayAmount(Float.parseFloat(paymentamountTextField.getText()));
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        java.util.Date expiryDate = calendar.getTime();
        member.setExpiryDate(expiryDate.toString());
        MemberService memberService = new MemberServiceImplementation();
        boolean res = memberService.addMember(member);
        if(res){
            Image img=new Image("gymjankari_v1/images/checked_icon.png");
            Notifications addedNotifications = Notifications.create()
            .title("Member Added")
            .text("The information has been added successfully.")
            .graphic(new ImageView(img))
            .hideAfter(Duration.seconds(5))
            .position(Pos.TOP_RIGHT);
            addedNotifications.show();
                    
        }else{
            Notifications errorNotifications=Notifications.create()
            .title("Failed to Add Member")
            .text("Sorry! The information has not been added due to some error.")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.TOP_RIGHT);
            errorNotifications.showError();
        }
        try {
            Main.showviewmemberpage();
        } catch (IOException ex) {
            Logger.getLogger(AddMemberPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Integer> time = FXCollections.observableArrayList();
        for(int i=1;i<13;i++)
        {
            time.add(i);
        }
        ObservableList<String> dayNight = FXCollections.observableArrayList("AM","PM");
        starttimeComboBox.setItems(time);
        starttimeapComboBox.setItems(dayNight);
        endtimeComboBox.setItems(time);
        endtimeapComboBox.setItems(dayNight);
    }
    
}
