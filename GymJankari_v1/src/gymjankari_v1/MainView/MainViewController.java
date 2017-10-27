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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Window;

public class MainViewController implements Initializable {

    private Main main;
    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;
    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;

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
    private JFXButton maximizeButton;

    @FXML
    private void backButtonClicked() throws IOException {
        main.showviewmemberpage();
    }

    @FXML
    private void addmemberButtonClicked() throws IOException {
        Main.showaddmemberpage();
    }

    @FXML
    private void viewmemberButtonClicked() throws IOException {
        Main.showviewmemberpage();
    }

    @FXML
    private void expirydetailsButtonClicked() throws IOException {
        Main.showexpirydetailspage();
    }

    @FXML
    private void aboutdevButtonClicked() throws IOException {
        Main.showaboutdeveloperspage();
    }

    @FXML
    private void closeButtonClicked() throws IOException {
        Platform.exit();
    }

    @FXML
    private void minimizeButtonClicked() {
        Main.minimizewindow();
    }

    @FXML
    private void maximizeButtonClicked(ActionEvent evt) {

        Node n = (Node) evt.getSource();

        Window w = n.getScene().getWindow();

        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if (currentX != bounds.getMinX()
                && currentY != bounds.getMinY()
                && currentWidth != bounds.getWidth()
                && currentHeight != bounds.getHeight()) {

            w.setX(bounds.getMinX());
            w.setY(bounds.getMinY());
            w.setWidth(bounds.getWidth());
            w.setHeight(bounds.getHeight());

            lastX = currentX; // save old dimensions
            lastY = currentY;
            lastWidth = currentWidth;
            lastHeight = currentHeight;

        } else {

            // de-maximize the window (not same as minimize)
            w.setX(lastX);
            w.setY(lastY);
            w.setWidth(lastWidth);
            w.setHeight(lastHeight);
        }

        evt.consume(); // don't bubble up to title bar

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
        moveTrackingPopup.setOnHidden((e) -> resetMoveOperation());
    }

    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }
}
