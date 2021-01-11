package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Error_searchVideo_notInList {
    public void backToSearchVideo(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/searchVideo.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(400);
        stage.setY(150);
        stage.setScene(profileScene);
    }
}
