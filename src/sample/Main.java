package sample;

import app.*;
import database.VideoQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import sample.*;

public class Main extends Application {
    public static boolean userOn;

    @Override
    public void start(Stage stage) throws Exception {
        User currentUser = null;
        userOn = false;
        URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        //Parent root = FXMLLoader.load(getClass().getResource("src/sample/resource/login.fxml"));

        stage.getIcons().add(new Image("/sample/Photo/111.png"));
        stage.setTitle("Yuu-tube");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
