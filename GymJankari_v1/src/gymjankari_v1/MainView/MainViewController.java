package gymjankari_v1.MainView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import gymjankari_v1.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;

public class MainViewController implements Initializable {
    
    private Main main;
    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;

    @FXML
    private JFXButton backButton;
    @FXML
    private JFXButton addmemberButton;
    @FXML
    private JFXButton viewmemberButton;
    @FXML
    private JFXButton aboutdevButton;
    @FXML
    private JFXButton expirydetailsButton;
    @FXML
    private JFXButton closeButton;
    @FXML
    private JFXToolbar titlebar;
    @FXML
    private BorderPane mainwindow;
    @FXML
    private JFXButton minimizeButton;
    
    @FXML
    private void backButtonClicked() throws IOException{
        main.showviewmemberpage();
    }
    
    @FXML
    private void addmemberButtonClicked() throws IOException{
        main.showaddmemberpage();
    }
    
    @FXML
    private void viewmemberButtonClicked() throws IOException{
        main.showviewmemberpage();
    }
    
    @FXML
    private void expirydetailsButtonClicked() throws IOException{
        main.showexpirydetailspage();
    }
    
    @FXML
    private void aboutdevButtonClicked() throws IOException{
        main.showaboutdeveloperspage();
    }
    
    @FXML
    private void closeButtonClicked() throws IOException{
        Platform.exit();
    }
    
    @FXML
    private void minimizeButtonClicked() {
        main.minimizewindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    //The Drag Action of the window by dragging the title bar starts here.
    @FXML
    private void endMoveWindow(MouseEvent evt) {
        if (dragging) {
        double endMoveX = evt.getScreenX();
        double endMoveY = evt.getScreenY();

        Window w = mainwindow.getScene().getWindow();

        double stageX = w.getX();
        double stageY = w.getY();

        w.setX(stageX + (endMoveX - startMoveX));
        w.setY(stageY + (endMoveY - startMoveY));

        if (moveTrackingPopup != null) {
        moveTrackingPopup.hide();
        moveTrackingPopup = null;
    }
  }

  resetMoveOperation();
    }

    @FXML
    private void moveWindow(MouseEvent evt) {
        if (dragging) {

        double endMoveX = evt.getScreenX();
        double endMoveY = evt.getScreenY();

        Window w = mainwindow.getScene().getWindow();

        double stageX = w.getX();
        double stageY = w.getY();

        moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
        moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
        }
    }
    

    @FXML
    private void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;

        moveTrackingRect = new Rectangle();
        moveTrackingRect.setWidth(mainwindow.getWidth());
        moveTrackingRect.setHeight(mainwindow.getHeight());
        moveTrackingRect.setOpacity(0.1);

        moveTrackingPopup = new Popup();
        moveTrackingPopup.getContent().add(moveTrackingRect);
        moveTrackingPopup.show(mainwindow.getScene().getWindow());
        moveTrackingPopup.setOnHidden( (e) -> resetMoveOperation());
    }
    
    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }    
}
