/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.ViewMemberMain;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import gymjankari_v1.serviceimplementation.MemberServiceImplementation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
    private JFXComboBox<?> starttimeComboBox;
    @FXML
    private JFXComboBox<?> starttimeapComboBox;
    @FXML
    private JFXComboBox<?> endtimeComboBox;
    @FXML
    private JFXComboBox<?> endtimeapComboBox;
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
    private void saveButtonClicked(){
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
        saveButton.setVisible(false);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void editButtonClicked(ActionEvent event) {
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
        saveButton.setVisible(true);
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
    }
    
    public void setData(String displayId){
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
        streetTextField.setText(member.getStreet());
        vdcmunTextField.setText(member.getVdcmun());
        wardnoTextField.setText(member.getWard());
        districtTextField.setText(member.getDistrict());
        emailTextField.setText(member.getEmail());
        landlineTextField.setText(member.getLandline());
        mobileTextField.setText(member.getMobile());
        memberidTextField.setText(member.getDisplayId());
        membersinceDatePicker.setValue(localDate(member.getMemberSince()));
        paymentrateTextField.setText(String.valueOf(member.getPayRate()));
        saveButton.setVisible(true);
    }
    
    public LocalDate localDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return localDate;
    }
    
    public void populateTable(){
        paymentdateTableColumn.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        //paymentamountTableColumn.setCellValueFactory(new PropertyValueFactory<>("payAmount"));
    }
 }
