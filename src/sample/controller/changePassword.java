package sample.controller;

import app.User;
import database.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class changePassword {

    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField reconfirmPassword;

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

    public void changePassword(ActionEvent event) throws Exception {
        User[] users = UserQuery.getUsers();

        //System.out.println("-----CHANGING TO NEW PASSWORD   -----");
        String oldPassword = login.loginUser.password;
        String reconfirmPassword = "";
        String newPassword;
        do{
            //System.out.print("Enter NEW password: ");
            newPassword = this.newPassword.getText();
            reconfirmPassword = this.reconfirmPassword.getText();

            if (newPassword.equals(oldPassword)) {
                //System.out.println("New password CANNOT BE THE SAME as current password !");
                URL url = new File("src/sample/resource/Error_changePassword_noSame.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(500);
                window.setY(250);
                window.show();
            }
            if (newPassword.isEmpty() || newPassword.isBlank()) {
                //System.out.println("NEW password cannot be EMPTY OR BLANK !");
                URL url = new File("src/sample/resource/Error_changePassword_noEmpty.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(600);
                window.setY(250);
            }
            if (!reconfirmPassword.equals(newPassword)) {
                URL url = new File("src/sample/resource/Error_changePassword_noMatch.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(500);
                window.setY(250);
            }
        } while (newPassword.isEmpty() || newPassword.isBlank() || newPassword.equals(oldPassword) || !reconfirmPassword.equals(newPassword));


        login.loginUser.password = newPassword;
        UserQuery.changePassword(login.loginUser);
//        System.out.println("-----Password changed successfully   -----");
//        System.out.println("-----Please login again          (A)-----");

        URL url = new File("src/sample/resource/Notification_passwordChanged.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(600);
        window.setY(280);
        window.show();
        Main.userOn = true;
    }
}
