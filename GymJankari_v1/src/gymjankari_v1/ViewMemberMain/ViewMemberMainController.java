/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.ViewMemberMain;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import static gymjankari_v1.AddMemberPage.AddMemberPageController.encodeImage;
import gymjankari_v1.Main;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import gymjankari_v1.serviceimplementation.MemberServiceImplementation;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author aj33b
 */
public class ViewMemberMainController implements Initializable {

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
    private JFXTimePicker startTimePicker;
    @FXML
    private JFXTimePicker endTimePicker;
    @FXML
    private JFXTextField paymentrateTextField;
    @FXML
    private ToggleGroup gender;
    @FXML
    private TableView<String> paymentdetailTableView;
    @FXML
    private TableColumn<String, String> paymentdateTableColumn;
    @FXML
    private TableColumn<String, String> paymentamountTableColumn;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton saveButton;
    @FXML
    private ImageView uploadImageView;
    @FXML 
    private JFXTextField ageTextField;
    
    private String id;
    private Main main;
    private String imageDataString = null;
    
    @FXML
    private void uploadButtonClicked() throws IOException{
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files","*.jpg","*.png","*.JPG","*.PNG"),
                                    new ExtensionFilter("JPEG Files (*.jpg)","*.jpg","*.JPG"),
                                    new ExtensionFilter("PNG Files (*.png)","*.png","*.PNG"));
    File selectedFile=fc.showOpenDialog(null); 
      
    if(selectedFile != null){
        BufferedImage bufferedImage= ImageIO.read(selectedFile);
        Image img= SwingFXUtils.toFXImage(bufferedImage, null);
        photoImageView.setImage(img);
        FileInputStream imageInFile = new FileInputStream(selectedFile);
        byte imageData[] = new byte[(int) selectedFile.length()];
        imageInFile.read(imageData);
        imageDataString = encodeImage(imageData);
    }
    else{
        System.out.println("File is not valid!!");    
    }
    
    }
    
    @FXML
    private void saveButtonClicked(){
  
        Member member = new Member();
        member.setFullName(fullnameTextField.getText());
        member.setDOB(dobDatePicker.getValue().toString());
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
        member.setDisplayId(memberidTextField.getText());
        member.setStartTime(startTimePicker.getValue().toString());
        member.setEndTime(endTimePicker.getValue().toString());
        member.setMemberSince(membersinceDatePicker.getValue().toString());
        member.setPayRate(Float.parseFloat(paymentrateTextField.getText()));
        member.setPicture(imageDataString);
        MemberService memberService = new MemberServiceImplementation();
        boolean res = memberService.editMember(member,id);
        if(res){
            Image img=new Image("gymjankari_v1/images/checked_icon.png");
            Notifications addedNotifications = Notifications.create()
            .title("Member Edited")
            .text("The information has been edited successfully.")
            .graphic(new ImageView(img))
            .hideAfter(Duration.seconds(5))
            .position(Pos.TOP_RIGHT);
            addedNotifications.show();
                    
        }else{
            Notifications errorNotifications=Notifications.create()
            .title("Failed to Edit Member")
            .text("Sorry! The information has not been edited due to some error.")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.TOP_RIGHT);
            errorNotifications.showError();
        }
        try {
            Main.showviewmemberpage();
        } catch (IOException ex) {
            Logger.getLogger(ViewMemberMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        fullnameTextField.setEditable(false);
        dobDatePicker.setEditable(false);
        heightTextField.setEditable(false);
        weightTextField.setEditable(false);
        streetTextField.setEditable(false);
        vdcmunTextField.setEditable(false);
        wardnoTextField.setEditable(false);
        districtTextField.setEditable(false);
        emailTextField.setEditable(false);
        landlineTextField.setEditable(false);
        mobileTextField.setEditable(false);
        memberidTextField.setEditable(false);
        membersinceDatePicker.setEditable(false);
        paymentrateTextField.setEditable(false);
        paymentdateTableColumn.setEditable(false);
        paymentamountTableColumn.setEditable(false);
        startTimePicker.setEditable(false);
        endTimePicker.setEditable(false);
        saveButton.setVisible(false);
        uploadImageView.setVisible(false);
        uploadButton.setVisible(false);
        maleRadioButton.setDisable(true);
        femaleRadioButton.setDisable(true);
        otherRadioButton.setDisable(true);
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void editButtonClicked(ActionEvent event) {
        Member member = new Member();
        id = memberidTextField.getText();
        fullnameTextField.setEditable(true);
        dobDatePicker.setEditable(true);
        heightTextField.setEditable(true);
        weightTextField.setEditable(true);
        streetTextField.setEditable(true);
        vdcmunTextField.setEditable(true);
        wardnoTextField.setEditable(true);
        districtTextField.setEditable(true);
        emailTextField.setEditable(true);
        landlineTextField.setEditable(true);
        mobileTextField.setEditable(true);
        memberidTextField.setEditable(true);
        membersinceDatePicker.setEditable(true);
        paymentrateTextField.setEditable(true);
        paymentdateTableColumn.setEditable(true);
        paymentamountTableColumn.setEditable(true);
        startTimePicker.setEditable(false);
        endTimePicker.setEditable(false);
        saveButton.setVisible(true);
        uploadImageView.setVisible(true);
        uploadButton.setVisible(true);
        maleRadioButton.setDisable(false);
        femaleRadioButton.setDisable(false);
        otherRadioButton.setDisable(false);
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        id = memberidTextField.getText();
        MemberService memberService = new MemberServiceImplementation();
        boolean res = memberService.deleteMember(id);
        if(res){
            Image img=new Image("gymjankari_v1/images/checked_icon.png");
            Notifications addedNotifications = Notifications.create()
            .title("Member Edited")
            .text("The information has been deleted successfully.")
            .graphic(new ImageView(img))
            .hideAfter(Duration.seconds(5))
            .position(Pos.TOP_RIGHT);
            addedNotifications.show();
                    
        }else{
            Notifications errorNotifications=Notifications.create()
            .title("Failed to Edit Member")
            .text("Sorry! The information has not been deleted due to some error.")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.TOP_RIGHT);
            errorNotifications.showError();
        }
        try {
            Main.showviewmemberpage();
        } catch (IOException ex) {
            Logger.getLogger(ViewMemberMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setData(String displayId){
        try {
            MemberService memberService = new MemberServiceImplementation();
            Member member = memberService.getById(displayId);
            ObservableList<String> paymentList = FXCollections.observableArrayList();
            //String[] paymentInfo = {member.getPayDate()};
            paymentList.add(member.getPayDate());
            populateTable();
            paymentdetailTableView.setItems(paymentList);
            fullnameTextField.setText(member.getFullName());
            dobDatePicker.setValue(localDate(member.getDOB()));
            heightTextField.setText(member.getHeight());
            weightTextField.setText(member.getWeight());
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int birthYear = dobDatePicker.getValue().getYear();
            int age = year-birthYear;
            ageTextField.setText(String.valueOf(age));
            streetTextField.setText(member.getStreet());
            vdcmunTextField.setText(member.getVdcmun());
            wardnoTextField.setText(member.getWard());
            districtTextField.setText(member.getDistrict());
            emailTextField.setText(member.getEmail());
            landlineTextField.setText(member.getLandline());
            mobileTextField.setText(member.getMobile());
            memberidTextField.setText(member.getDisplayId());
            startTimePicker.setValue(localTime(member.getStartTime()));
            endTimePicker.setValue(localTime(member.getEndTime()));
            membersinceDatePicker.setValue(localDate(member.getMemberSince()));
            paymentrateTextField.setText(String.valueOf(member.getPayRate()));
            imageDataString = member.getPicture();
            if(imageDataString!=null){
            byte[] imageByteArray = decodeImage(imageDataString);
            InputStream inputStream = new ByteArrayInputStream(imageByteArray);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            Image image= SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(image);
            }
            saveButton.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(ViewMemberMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public LocalDate localDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return localDate;
    }
    
    public LocalTime localTime(String stringTime){
        LocalTime time = LocalTime.parse(stringTime);
        return time;
    }
    
    public void populateTable(){
        paymentdateTableColumn.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        //paymentamountTableColumn.setCellValueFactory(new PropertyValueFactory<>("payAmount"));
    }
    
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
 }
