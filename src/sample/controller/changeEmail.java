package sample.controller;

import app.User;
import database.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class changeEmail {

    @FXML private TextField newEmail;

    public void stayProfile(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/MyUserProfile.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(450);
        window.setY(130);
        window.show();
        Main.userOn = true;
    }

    public void changeEmail(ActionEvent event) throws Exception {
        User[] users = UserQuery.getUsers();

        System.out.println("-----CHANGING TO NEW EMAIL   -----");
        String oldEmail = login.loginUser.getEmail();
        String newEmail;
        boolean isUniqueEmail;
        do {
            isUniqueEmail = true;
            newEmail = this.newEmail.getText();

            if (this.newEmail.getText() == null) {
                URL url = new File("src/sample/resource/Error_changeEmail_noSame.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(500);
                window.setY(250);
                window.show();
                continue;
            }

            if ( oldEmail.equals(newEmail)){
                //System.out.println("New email CANNOT BE THE SAME as current email !");
                URL url = new File("src/sample/resource/Error_changeEmail_noSame.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(500);
                window.setY(250);
                window.show();
                isUniqueEmail = false;
                continue;
            }
            if ( !newEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$")){
                //System.out.println("INVALID EMAIL ! ");
                URL url = new File("src/sample/resource/Error_changeEmail_invalidEmail.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(600);
                window.setY(250);
                window.show();
                isUniqueEmail = false;
                continue;
            }
            for (User user : users) {
                if (newEmail.equals(user.email)) {
                    //System.out.println("THIS EMAIL IS TAKEN! ");
                    URL url = new File("src/sample/resource/Error_changeEmail_emailTaken.fxml").toURI().toURL();
                    Parent profileParent = FXMLLoader.load(url);
                    Scene profileScene = new Scene(profileParent);

                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(profileScene);
                    window.setX(600);
                    window.setY(250);
                    window.show();
                    isUniqueEmail = false;
                    break;
                }
            }
        } while (!isUniqueEmail) ;

        login.loginUser.email = newEmail;
        UserQuery.changeEmail(login.loginUser);
//        System.out.println("-----Email changed successfully   -----");
//        System.out.println("-----Please login again          (A)-----");
        URL url = new File("src/sample/resource/Notification_emailChanged.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(600);
        window.setY(250);
        window.show();
        Main.userOn = true;
    }
}
