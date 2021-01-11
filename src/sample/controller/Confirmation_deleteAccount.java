package sample.controller;

import app.User;
import app.Video;
import database.LikeDislikeQuery;
import database.SubscriberQuery;
import database.UserQuery;
import database.VideoQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import operation.DeleteAccount;
import operation.DeleteVideo;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Confirmation_deleteAccount {
    public void stayProfile(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/MyUserProfile.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(450);
        window.setY(130);
        window.show();
        Main.userOn = true;
    }

    public void deleteAccount(ActionEvent event) throws Exception {
        DeleteAccount.main(Login.loginUser);

        URL url = new File("src/sample/resource/Notification_accountDeleted.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(600);
        window.setY(250);
        window.show();
        Main.userOn = false;
    }
}
