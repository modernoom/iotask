package com.hjy.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * @author huangjinyong
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        InputStream is = Main.class.getClassLoader().getResourceAsStream("page.fxml");
        if(is!=null){
            Parent root  =loader.load(is);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
