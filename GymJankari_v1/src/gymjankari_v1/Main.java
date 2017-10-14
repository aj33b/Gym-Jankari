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
import javafx.stage.Stage;

/**
 *
 * @author aj33b
 */
public class Main extends Application {
    public static Stage basewindow;
    public static BorderPane mainLayout;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
      Main.basewindow=primaryStage;
      Main.basewindow.setTitle("GymJankari");
      showMainView();
      showHomePage();
    }
    
    private void showMainView() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("HomePage/MainView.fxml"));
        mainLayout=loader.load();
        Scene scene=new Scene(mainLayout);
        basewindow.setScene(scene);
        basewindow.show();
    }
    
    public static void showHomePage()throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("HomePage/HomePage.fxml"));
        BorderPane homepageLayout=loader.load();
        mainLayout.setCenter(homepageLayout);
    }
    
     public static void showaddmemberpage() throws IOException {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Main.class.getResource("AddMemberPage/AddMemberPage.fxml"));
        BorderPane addmemberpageLayout=loader.load();
        mainLayout.setCenter(addmemberpageLayout);
    }
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
}
