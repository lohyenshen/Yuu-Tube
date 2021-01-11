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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import TwoFactorAuth.*;


public class Login extends HomePage {

    @FXML protected TextField emailLogin;
    @FXML private PasswordField passwordLogin;
    @FXML private Label emailIncorrect;
    @FXML private Label passwordIncorrect;
    @FXML private PasswordField OTPInput;
    @FXML private Label checkEmail;

    public static User loginUser;

    public User getLoginUser() {
        return loginUser;
    }

    public void login(ActionEvent event) throws Exception {
//        boolean userExist = false;
//        User currentUser = null;
//
//        String emailEntered = emailLogin.getText();
//        String passwordEntered = passwordLogin.getText();
//
//        User[] users = UserQuery.getUsers();
//
//        for (User user : users) {
//            if (emailEntered.equals(user.getEmail())) {                  // email    in database
//                emailIncorrect.setText("");
//                if (passwordEntered.equals(user.getPassword())) {        // password in database
//                    passwordIncorrect.setText("");
//                    currentUser = user;
//                    userExist = true;
//                    break;
//                } else {
//                    passwordLogin.clear();
//                    passwordIncorrect.setText("Password incorrect");
//                    passwordIncorrect.setStyle("-fx-background-color: #ff0000; ");
//                }
//            } else {
//                emailLogin.clear();
//                emailIncorrect.setText("Email incorrect");
//                emailIncorrect.setStyle("-fx-background-color: #ff0000; ");
//            }
//        }
        String otpEntered = OTPInput.getText();
        String otpGenerated = OTP.getOTP();

        if ( otpEntered.equals( otpGenerated )){
            URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();
            Main.userOn = true;
        }
        else {
            System.out.println("OTP does not match");

            OTPInput.clear();

            checkEmail.setText("OTP does not match");
            checkEmail.setTextFill(Color.RED);
//            URL url = new File("src/sample/resource/Error_toLogin.fxml").toURI().toURL();
//            Parent profileParent = FXMLLoader.load(url);
//            Scene profileScene = new Scene(profileParent);
//
//            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            window.setScene(profileScene);
//            window.show();
//            window.setX(500);
//            window.setY(280);
        }
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

    public void requestOTP(ActionEvent event) throws Exception {
        boolean userExist = false;
        User currentUser = null;

        String emailEntered = emailLogin.getText();
        String passwordEntered = passwordLogin.getText();

        User[] users = UserQuery.getUsers();

        for (User user : users) {
            if (emailEntered.equals(user.getEmail())) {                  // email    in database
                emailIncorrect.setText("");
                if (passwordEntered.equals(user.getPassword())) {        // password in database
                    passwordIncorrect.setText("");
                    currentUser = user;
                    userExist = true;
                    break;
                } else {
//                    passwordLogin.clear();
                    passwordIncorrect.setText("Password incorrect");
                    break;
                }
            } else {
//                emailLogin.clear();
                emailIncorrect.setText("Email incorrect");
            }
        }

        loginUser = currentUser;

        if (userExist) {
            ////////////////////////////////////////
            OTP.sendEmail(currentUser.getEmail());

            checkEmail.setText("OTP has been sent to your email, please check your email.");
            checkEmail.setTextFill(Color.WHITE);
        }
    }
}
