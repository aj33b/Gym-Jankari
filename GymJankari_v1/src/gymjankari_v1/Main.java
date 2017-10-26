/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author aj33b
 */
public class Main extends Application {
    public static Stage basewindow;
    public static BorderPane mainLayout;
    public static Boolean issplashloaded=false;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
      Main.basewindow=primaryStage;
      Main.basewindow.setTitle("GymJankari");
      showSplashScreen();
    }
    
    public static void showSplashScreen() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainView/SplashScreen.fxml"));
        StackPane splashscreen=loader.load();
        Scene scene=new Scene(splashscreen);
        basewindow.setScene(scene);
        basewindow.initStyle(StageStyle.UNDECORATED);
        basewindow.show();
    }
    
    public static void showMainView() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
        mainLayout=loader.load();
        Scene scene=new Scene(mainLayout);
        basewindow.setScene(scene);
        basewindow.show();
    }
    
    public static void showviewmemberpage()throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("ViewMemberPage/ViewMemberPage.fxml"));
        BorderPane viewmemberpageLayout=loader.load();
        mainLayout.setCenter(viewmemberpageLayout);
    }
    
    public static void showexpirydetailspage()throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("ExpiryDetailsPage/ExpiryDetailsPage.fxml"));
        BorderPane expirydetailspageLayout=loader.load();
        mainLayout.setCenter(expirydetailspageLayout);
    }
    
     public static void showaddmemberpage() throws IOException {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("AddMemberPage/AddMemberPage.fxml"));
        BorderPane addmemberpageLayout=loader.load();
        mainLayout.setCenter(addmemberpageLayout);
    }
     
     public static void showaboutdeveloperspage() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("AboutDevelopersPage/AboutDevelopersPage.fxml"));
        BorderPane aboutdevelopersLayout=loader.load();
        mainLayout.setCenter(aboutdevelopersLayout);
    }
     
     public static void minimizewindow() {
        basewindow.setIconified(true);
    }     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }       
}
