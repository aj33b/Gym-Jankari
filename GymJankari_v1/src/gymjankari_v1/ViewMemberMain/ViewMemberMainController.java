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
import com.jfoenix.validation.RequiredFieldValidator;
import static gymjankari_v1.AddMemberPage.AddMemberPageController.encodeImage;
import gymjankari_v1.Main;
import gymjankari_v1.PaymentDetailsPage.PaymentDetailsPageController;
import gymjankari_v1.dateconverter.DateConverter;
import gymjankari_v1.dateconverter.DateConverterInterface;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.controlsfx.control.Notifications;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

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
    private JFXTimePicker startTimePicker;
    @FXML
    private JFXTimePicker endTimePicker;
    @FXML
    private JFXTextField paymentrateTextField;
    @FXML
    private ToggleGroup gender;
    @FXML
    private TableView<Member> paymentdetailTableView;
    @FXML
    private TableColumn<Member, String> paymentdateTableColumn;
    @FXML
    private TableColumn<Member, String> paymentamountTableColumn;
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
    private JFXButton editpdButton;
    @FXML
    private VBox subwindow;
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
    private void saveButtonClicked() {
        MemberService memberService = new MemberServiceImplementation();
        Member member = new Member();
        String name = fullnameTextField.getText();
        if (name.isEmpty()) {
            nameFieldValidation();
        } else {
            member.setFullName(name);
            String dobYear = dobbsyear.getSelectionModel().getSelectedItem().toString();
            String dobMonth = memberService.monthToNumberConversion(dobbsmonth.getSelectionModel().getSelectedItem());
            String dobDay = String.valueOf(dobbsday.getSelectionModel().getSelectedItem());
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
            member.setDisplayId(memberidTextField.getText());
            member.setStartTime(startTimePicker.getValue().toString());
            member.setEndTime(endTimePicker.getValue().toString());
            String memberSinceYear = memsinbsyear.getSelectionModel().getSelectedItem().toString();
            String memberSinceMonth = memberService.monthToNumberConversion(memsinbsmonth.getSelectionModel().getSelectedItem());
            String memberSinceDay = String.valueOf(memsinbsday.getSelectionModel().getSelectedItem());
            String memberSinceBsDate = memberSinceYear.concat("-").concat(memberSinceMonth).concat("-").concat(memberSinceDay);
            member.setMemberSince(dateConverterInterface.convertBsToAd(memberSinceBsDate));
            String payRate = paymentrateTextField.getText();
            if (payRate.isEmpty()) {
                rateFieldValidation();
            } else {
                member.setPayRate(Float.parseFloat(paymentrateTextField.getText()));
                member.setPicture(imageDataString);
                boolean res = memberService.editMember(member, id);
                if (res) {
                    Image img = new Image("gymjankari_v1/images/checked_icon.png");
                    Notifications addedNotifications = Notifications.create()
                            .title("Member Edited")
                            .text("The information has been edited successfully.")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    addedNotifications.show();

                } else {
                    Notifications errorNotifications = Notifications.create()
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
        }
        fullnameTextField.setEditable(false);
        dobbsmonth.setEditable(false);
        dobbsday.setEditable(false);
        dobbsyear.setEditable(false);
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
        memsinbsmonth.setEditable(false);
        memsinbsday.setEditable(false);
        memsinbsyear.setEditable(false);
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
        MemberService memberService = new MemberServiceImplementation();
        for (int i = 1999; i <= 2099; i++) {
            yList.add(i);
        }
        dobbsyear.setItems(yList);
        dobbsmonth.setItems(mList);
        dobbsday.setItems(defaultDays);
        memsinbsyear.setItems(yList);
        memsinbsmonth.setItems(mList);
        memsinbsday.setItems(defaultDays);

        dobbsyear.getSelectionModel().select(yList.get(Integer.parseInt(defaultYear) - 1999));
        dobbsmonth.getSelectionModel().select(mList.get(Integer.parseInt(defaultMonth) - 1));
        dobbsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(defaultDay) - 1));
        memsinbsyear.getSelectionModel().select(yList.get(Integer.parseInt(defaultYear) - 1999));
        memsinbsmonth.getSelectionModel().select(mList.get(Integer.parseInt(defaultMonth) - 1));
        memsinbsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(defaultDay) - 1));

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
    }

    @FXML
    private void editpdButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("PaymentDetailsPage/PaymentDetailsPage.fxml"));
        BorderPane homepageLayout = loader.load();
        subwindow.getChildren().setAll(homepageLayout);
        PaymentDetailsPageController paymentDetailsPageController = loader.getController();
        paymentDetailsPageController.setData(memberidTextField.getText());
    }

    @FXML
    private void editButtonClicked(ActionEvent event) {
        Member member = new Member();
        id = memberidTextField.getText();
        fullnameTextField.setEditable(true);
        dobbsmonth.setEditable(true);
        dobbsday.setEditable(true);
        dobbsyear.setEditable(true);
        heightTextField.setEditable(true);
        weightTextField.setEditable(true);
        streetTextField.setEditable(true);
        vdcmunTextField.setEditable(true);
        wardnoTextField.setEditable(true);
        districtTextField.setEditable(true);
        emailTextField.setEditable(true);
        landlineTextField.setEditable(true);
        mobileTextField.setEditable(true);
        memsinbsmonth.setEditable(true);
        memsinbsday.setEditable(true);
        memsinbsyear.setEditable(true);
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
        if (res) {
            Image img = new Image("gymjankari_v1/images/checked_icon.png");
            Notifications addedNotifications = Notifications.create()
                    .title("Member Edited")
                    .text("The information has been deleted successfully.")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            addedNotifications.show();

        } else {
            Notifications errorNotifications = Notifications.create()
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

    public void setData(String displayId) {
        try {
            MemberService memberService = new MemberServiceImplementation();
            Member member = memberService.getById(displayId);
            fullnameTextField.setText(member.getFullName());
            String dobBsDate[] = dateConverterInterface.convertAdToBs(member.getDOB()).split("-");
            String dobYear = dobBsDate[0];
            String dobMonth = dobBsDate[1];
            String dobDay = dobBsDate[2];
            dobbsyear.getSelectionModel().select(yList.get(Integer.parseInt(dobYear) - 1999));
            dobbsmonth.getSelectionModel().select(mList.get(Integer.parseInt(dobMonth) - 1));
            dobbsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(dobDay) - 1));
            heightTextField.setText(member.getHeight());
            weightTextField.setText(member.getWeight());

            int age = Integer.parseInt(defaultYear) - Integer.parseInt(dobYear);
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
            String memberSinceBsDate[] = dateConverterInterface.convertAdToBs(member.getMemberSince()).split("-");
            String memberSinceYear = memberSinceBsDate[0];
            String memberSinceMonth = memberSinceBsDate[1];
            String memberSinceDay = memberSinceBsDate[2];
            memsinbsyear.getSelectionModel().select(yList.get(Integer.parseInt(memberSinceYear) - 1999));
            memsinbsmonth.getSelectionModel().select(mList.get(Integer.parseInt(memberSinceMonth) - 1));
            memsinbsday.getSelectionModel().select(defaultDays.get(Integer.parseInt(memberSinceDay) - 1));
            paymentrateTextField.setText(String.valueOf(member.getPayRate()));
            imageDataString = member.getPicture();
            if (imageDataString != null) {
                byte[] imageByteArray = decodeImage(imageDataString);
                InputStream inputStream = new ByteArrayInputStream(imageByteArray);
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                photoImageView.setImage(image);
            }
            populateTable();
            paymentdetailTableView.setItems(memberService.getPaymentDetails(displayId));
            saveButton.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(ViewMemberMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LocalDate localDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return localDate;
    }

    public LocalTime localTime(String stringTime) {
        LocalTime time = LocalTime.parse(stringTime);
        return time;
    }

    public void populateTable() {
        paymentdateTableColumn.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        paymentamountTableColumn.setCellValueFactory(new PropertyValueFactory<>("payAmount"));

    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
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
}
