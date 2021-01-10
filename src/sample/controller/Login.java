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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Login extends HomePage {

    @FXML protected TextField emailLogin;
    @FXML private PasswordField passwordLogin;

    public static User loginUser;

    public User getLoginUser() {
        return loginUser;
    }

    public void login(ActionEvent event) throws Exception {
        boolean userExist = false;
        User currentUser = null;

        String emailEntered = emailLogin.getText();
        String passwordEntered = passwordLogin.getText();

        User[] users = UserQuery.getUsers();

        for (User user : users) {
            if (emailEntered.equals(user.getEmail())) {                  // email    in database
                if (passwordEntered.equals(user.getPassword())) {        // password in database
                    currentUser = user;
                    userExist = true;
                    break;
                }
            }
        }

        if (userExist){
            URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();
            Main.userOn = true;
            loginUser = currentUser;
        }
        else{
            emailLogin.clear();
            passwordLogin.clear();

            URL url = new File("src/sample/resource/Error_toLogin.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();
            window.setX(500);
            window.setY(280);
        }
        loginUser = currentUser;
    }

    public void toSignUp(MouseEvent event) throws Exception {
        URL url = new File("src/sample/resource/signUp_1.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void toHomePage(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
        Main.userOn = false;
    }
}
