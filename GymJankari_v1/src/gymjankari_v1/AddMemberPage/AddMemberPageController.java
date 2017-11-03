/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.AddMemberPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import gymjankari_v1.Main;
import gymjankari_v1.dateconverter.DateConverter;
import gymjankari_v1.dateconverter.DateConverterInterface;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import gymjankari_v1.serviceimplementation.MemberServiceImplementation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;
import org.apache.commons.codec.binary.Base64;

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
    private JFXDatePicker dobDatePicker;
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
    private JFXDatePicker membersinceDatePicker;
    @FXML
    private JFXTimePicker startTimePicker;
    @FXML
    private JFXTimePicker endTimePicker;
    @FXML
    private JFXDatePicker paymentdateDatePicker;
    @FXML
    private JFXTextField paymentrateTextField;
    @FXML
    private JFXTextField paymentamountTextField;
    @FXML
    private JFXButton addButton;
    @FXML
    private ToggleGroup gender;

    private Main main;
    private String imageDataString;
    @FXML
    private ImageView uploadImageView;
    @FXML
    private JFXComboBox<String> dobbsmonth;
    @FXML
    private JFXComboBox<String> dobbsday;
    @FXML
    private JFXComboBox<Integer> dobbsyear;
    @FXML
    private JFXComboBox<String> memsinbsmonth;
    @FXML
    private JFXComboBox<String> memsinbsday;
    @FXML
    private JFXComboBox<Integer> memsinbsyear;
    @FXML
    private JFXComboBox<String> paybsmonth;
    @FXML
    private JFXComboBox<String> paybsday;
    @FXML
    private JFXComboBox<Integer> paybsyear;
    @FXML
    private ListView<String> monthList;
    @FXML
    private ListView<String> dayList;
    @FXML
    private ListView<Integer> yearList;

    DateConverterInterface dateConverterInterface = new DateConverter();
    LocalDate default_date = LocalDate.now();
    String default_bs_date = dateConverterInterface.convertAdToBs(default_date.toString());
    String defaultBsDate[] = default_bs_date.split("-");
    String defaultYear = defaultBsDate[0];
    String defaultMonth = defaultBsDate[1];
    String defaultDay = defaultBsDate[2];

    @FXML
    private void uploadButtonClicked() throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg", "*.png", "*.JPG", "*.PNG"),
                new ExtensionFilter("JPEG Files (*.jpg)", "*.jpg", "*.JPG"),
                new ExtensionFilter("PNG Files (*.png)", "*.png", "*.PNG"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image img = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(img);
            FileInputStream imageInFile = new FileInputStream(selectedFile);
            byte imageData[] = new byte[(int) selectedFile.length()];
            imageInFile.read(imageData);
            imageDataString = encodeImage(imageData);
        } else {
            System.out.println("File is not valid!!");
        }

    }

    @FXML
    private void addButtonClicked() {
        MemberService memberService = new MemberServiceImplementation();
        Member member = new Member();
        String name = fullnameTextField.getText();
        if (name.isEmpty()) {
            nameFieldValidation();
        } else {
            member.setFullName(name);
            String dobYear = dobbsyear.getSelectionModel().getSelectedItem().toString();
            String dobMonth = memberService.monthToNumberConversion(dobbsmonth.getSelectionModel().getSelectedItem());
            String dobDay = dobbsday.getSelectionModel().getSelectedItem();
            String dobBsDate = dobYear.concat("-").concat(dobMonth).concat("-").concat(dobDay);
            member.setDOB(dateConverterInterface.convertBsToAd(dobBsDate));
            if (maleRadioButton.isSelected()) {
                member.setGender("Male");
            } else if (femaleRadioButton.isSelected()) {
                member.setGender("Female");
            } else if (otherRadioButton.isSelected()) {
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
            if (memberId.isEmpty()) {
                memberIdFieldValidation();
            } else {
                if (memberService.checkDuplicate(memberId)) {
                    Notifications errorNotifications = Notifications.create()
                            .title("Failed to Add Member")
                            .text("Sorry! MemberId already in use!!!")
                            .graphic(null)
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    errorNotifications.showError();
                } else {
                    member.setmId(memberId);
                    String memberSinceYear = memsinbsyear.getSelectionModel().getSelectedItem().toString();
                    String memberSinceMonth = String.valueOf(memberService.monthToNumberConversion(memsinbsmonth.getSelectionModel().getSelectedItem()));
                    String memberSinceDay = memsinbsday.getSelectionModel().getSelectedItem();
                    String memberSinceBsDate = memberSinceYear.concat("-").concat(memberSinceMonth).concat("-").concat(memberSinceDay);
                    member.setMemberSince(dateConverterInterface.convertBsToAd(memberSinceBsDate));
                    LocalTime startTime = startTimePicker.getValue();
                    if (startTime == null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        LocalDateTime dateTime = LocalDateTime.now();
                        String formattedDateTime = dateTime.format(formatter);
                        member.setStartTime(formattedDateTime);
                    } else {
                        member.setStartTime(String.valueOf(startTimePicker.getValue()));
                    }
                    LocalTime endTime = endTimePicker.getValue();
                    if (endTime == null) {
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        LocalDateTime dateTime = LocalDateTime.now();
                        String formattedDateTime = dateTime.format(formatter);
                        member.setEndTime(formattedDateTime);
                    } else {
                        member.setEndTime(String.valueOf(endTimePicker.getValue()));
                    }
                    String payYear = paybsyear.getSelectionModel().getSelectedItem().toString();
                    String payMonth = String.valueOf(memberService.monthToNumberConversion(paybsmonth.getSelectionModel().getSelectedItem()));
                    String payDay = paybsday.getSelectionModel().getSelectedItem();
                    String payBsDate = payYear.concat("-").concat(payMonth).concat("-").concat(payDay);
                    member.setPayDate(dateConverterInterface.convertBsToAd(payBsDate));
                    String payRate = paymentrateTextField.getText();
                    if (payRate.isEmpty()) {
                        rateFieldValidation();
                    } else {
                        member.setPayRate(Float.parseFloat(paymentrateTextField.getText()));
                        String payAmount = paymentamountTextField.getText();
                        if (payAmount.isEmpty()) {
                            amountFieldValidation();
                        } else {
                            member.setPayAmount(Float.parseFloat(paymentamountTextField.getText()));
                            int day = expiryDateCalculation(payRate, payAmount);
                            if (day != 0) {
                                Date date = java.sql.Date.valueOf(dateConverterInterface.convertBsToAd(payBsDate));
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.DAY_OF_MONTH, day);
                                Date expiryDate = calendar.getTime();
                                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(expiryDate);
                                member.setExpiryDate(formattedDate);
                            } else {
                                member.setExpiryDate(LocalDate.now().toString());
                            }
                            member.setPicture(imageDataString);
                            int daysRemaining = memberService.calculateDays(member.getExpiryDate());
                            member.setDay(daysRemaining);
                            boolean res = memberService.addMember(member);
                            if (res) {
                                Image img = new Image("gymjankari_v1/images/checked_icon.png");
                                Notifications addedNotifications = Notifications.create()
                                        .title("Member Added")
                                        .text("The information has been added successfully.")
                                        .graphic(new ImageView(img))
                                        .hideAfter(Duration.seconds(5))
                                        .position(Pos.TOP_RIGHT);
                                addedNotifications.show();

                            } else {
                                Notifications errorNotifications = Notifications.create()
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
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MemberService memberService = new MemberServiceImplementation();
        ObservableList<String> mList = FXCollections.observableArrayList("Baisakh", "Jestha", "Asadh", "Shrawan", "Bhadra", "Asoj", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra");
        ObservableList<Integer> yList = FXCollections.observableArrayList();
        for (int i = 1999; i <= 2099; i++) {
            yList.add(i);
        }
        ObservableList<String> defaultDays = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
                "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32");
        dobbsyear.setItems(yList);
        dobbsmonth.setItems(mList);
        dobbsday.setItems(defaultDays);
        memsinbsyear.setItems(yList);
        memsinbsmonth.setItems(mList);
        memsinbsday.setItems(defaultDays);
        paybsyear.setItems(yList);
        paybsmonth.setItems(mList);
        paybsday.setItems(defaultDays);

        dobbsyear.getSelectionModel().select(yList.get(Integer.parseInt(defaultYear) - 1999));
        dobbsmonth.getSelectionModel().select(mList.get(Integer.parseInt(defaultMonth) - 1));
        dobbsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(defaultDay) - 1));
        memsinbsyear.getSelectionModel().select(yList.get(Integer.parseInt(defaultYear) - 1999));
        memsinbsmonth.getSelectionModel().select(mList.get(Integer.parseInt(defaultMonth) - 1));
        memsinbsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(defaultDay) - 1));
        paybsyear.getSelectionModel().select(yList.get(Integer.parseInt(defaultYear) - 1999));
        paybsmonth.getSelectionModel().select(mList.get(Integer.parseInt(defaultMonth) - 1));
        paybsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(defaultDay) - 1));

        dobbsyear.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<String> dList = FXCollections.observableArrayList();
                String selectedYear = String.valueOf(dobbsyear.getSelectionModel().getSelectedItem());
                int selectedMonth = Integer.parseInt(memberService.monthToNumberConversion(dobbsmonth.getSelectionModel().getSelectedItem()));
                dList = memberService.dayValues(selectedYear, selectedMonth);
                dobbsday.setItems(dList);
            }

        });
        dobbsmonth.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> dList = FXCollections.observableArrayList();
                String selectedYear = String.valueOf(dobbsyear.getSelectionModel().getSelectedItem());
                int selectedMonth = Integer.parseInt(memberService.monthToNumberConversion(dobbsmonth.getSelectionModel().getSelectedItem()));
                dList = memberService.dayValues(selectedYear, selectedMonth);
                dobbsday.setItems(dList);

            }

        });
        memsinbsyear.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<String> dList = FXCollections.observableArrayList();
                String selectedYear = String.valueOf(memsinbsyear.getSelectionModel().getSelectedItem());
                int selectedMonth = Integer.parseInt(memberService.monthToNumberConversion(memsinbsmonth.getSelectionModel().getSelectedItem()));
                dList = memberService.dayValues(selectedYear, selectedMonth);
                memsinbsday.setItems(dList);
            }

        });
        memsinbsmonth.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> dList = FXCollections.observableArrayList();
                String selectedYear = String.valueOf(memsinbsyear.getSelectionModel().getSelectedItem());
                int selectedMonth = Integer.parseInt(memberService.monthToNumberConversion(memsinbsmonth.getSelectionModel().getSelectedItem()));
                dList = memberService.dayValues(selectedYear, selectedMonth);
                memsinbsday.setItems(dList);

            }

        });
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

        final Circle clip1 = new Circle(100, 100, 100);
        final Circle clip2 = new Circle(100, 100, 100);
        photoImageView.setClip(clip1);
        uploadImageView.setClip(clip2);
        RequiredFieldValidator idFieldValidator = new RequiredFieldValidator();
        memberidTextField.getValidators().add(idFieldValidator);
        idFieldValidator.setMessage("Cannot be empty");
        memberidTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    memberidTextField.validate();
                }

            }
        });
        RequiredFieldValidator nameFieldValidator = new RequiredFieldValidator();
        fullnameTextField.getValidators().add(nameFieldValidator);
        nameFieldValidator.setMessage("Cannot be empty");
        fullnameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    fullnameTextField.validate();
                }

            }
        });
        RequiredFieldValidator rateFieldValidator = new RequiredFieldValidator();
        paymentrateTextField.getValidators().add(rateFieldValidator);
        rateFieldValidator.setMessage("Cannot be empty");
        paymentrateTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    paymentrateTextField.validate();
                }

            }
        });
        RequiredFieldValidator amountFieldValidator = new RequiredFieldValidator();
        paymentamountTextField.getValidators().add(amountFieldValidator);
        amountFieldValidator.setMessage("Cannot be empty");
        paymentamountTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    paymentamountTextField.validate();
                }

            }
        });
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    public void memberIdFieldValidation() {
        RequiredFieldValidator idFieldValidator = new RequiredFieldValidator();
        memberidTextField.getValidators().add(idFieldValidator);
        idFieldValidator.setMessage("Cannot be empty");
        memberidTextField.validate();
    }

    public void nameFieldValidation() {
        RequiredFieldValidator nameFieldValidator = new RequiredFieldValidator();
        fullnameTextField.getValidators().add(nameFieldValidator);
        nameFieldValidator.setMessage("Cannot be empty");
        fullnameTextField.validate();
    }

    public void rateFieldValidation() {
        RequiredFieldValidator rateFieldValidator = new RequiredFieldValidator();
        paymentrateTextField.getValidators().add(rateFieldValidator);
        rateFieldValidator.setMessage("Cannot be empty");
        paymentrateTextField.validate();
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
}
