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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChangeEmail {

    @FXML private TextField newEmail;
    @FXML private Label changeEmail_error;


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
        changeEmail_error.setText("");
        User[] users = UserQuery.getUsers();

        String oldEmail = Login.loginUser.getEmail();
        String newEmail;
        boolean isUniqueEmail;
        isUniqueEmail = true;
        newEmail = this.newEmail.getText();

        if (this.newEmail.getText() == null) {
            changeEmail_error.setText("Email cannot be empty");
            isUniqueEmail = false;
        }

        if ( !newEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$")){
            //System.out.println("INVALID EMAIL ! ");
            changeEmail_error.setText("Invalid email");
            isUniqueEmail = false;

        }

        for (User user : users) {
            if ( oldEmail.equals(newEmail)){
                //System.out.println("New email CANNOT BE THE SAME as current email !");
                changeEmail_error.setText("New email cannot be the same as current email");
                isUniqueEmail = false;
            } else if (newEmail.equals(user.getEmail())) {
                //System.out.println("THIS EMAIL IS TAKEN! ");
                changeEmail_error.setText("This email has been registered");
                isUniqueEmail = false;
                break;
                }
            }

        if (isUniqueEmail) {
            Login.loginUser.setEmail( newEmail );
            UserQuery.changeEmail(Login.loginUser);

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
}
