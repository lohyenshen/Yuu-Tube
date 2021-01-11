package sample.controller;

import app.User;
import database.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChangePassword {

    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField reconfirmPassword;
    @FXML
    private Label changePassword_error;

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
        String oldPassword = Login.loginUser.getPassword();
        String reconfirmPassword = "";
        String newPassword;

        //System.out.print("Enter NEW password: ");
        newPassword = this.newPassword.getText();
        reconfirmPassword = this.reconfirmPassword.getText();

        if (newPassword.equals(oldPassword)) {
            //System.out.println("New password CANNOT BE THE SAME as current password !");
           changePassword_error.setText("New password cannot be same as current password");
        }
        if (newPassword.isEmpty() || newPassword.isBlank()) {
            //System.out.println("NEW password cannot be EMPTY OR BLANK !");
            changePassword_error.setText("Password cannot be empty");
        }
        if (!reconfirmPassword.equals(newPassword)) {
            changePassword_error.setText("Passwords are not match");
        }

        if (!newPassword.isEmpty() && !newPassword.isBlank() && !newPassword.equals(oldPassword) && reconfirmPassword.equals(newPassword)) {
            Login.loginUser.setPassword( newPassword );
            UserQuery.changePassword(Login.loginUser);

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
}
