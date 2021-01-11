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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.lang.String;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SignUp {
    @FXML private TextField usernameSignUp;
    @FXML private TextField emailSignUp;
    @FXML private PasswordField passwordSignUp;
    @FXML private PasswordField reConfirmPW;
    @FXML private Label passwordNotMatch;
    @FXML private Label invalidEmail;
    @FXML private Label usernameNoEmpty;
    @FXML private Label passwordNoEmpty;

    // "Create" button
    public void saveNewUser(ActionEvent event) throws Exception {
        passwordNotMatch.setText("");
        invalidEmail.setText("");
        usernameNoEmpty.setText("");
        passwordNoEmpty.setText("");

        User[] users = UserQuery.getUsers();
        String name;
        name = usernameSignUp.getText();
        String email;
        email = emailSignUp.getText();
        String password;
        password = passwordSignUp.getText();
        String confirmPassword;
        confirmPassword = reConfirmPW.getText();

        boolean isUniqueName;
        boolean isUniqueEmail;
        boolean created = false;
        isUniqueName = true;
        isUniqueEmail = true;

            if (usernameSignUp.getText().equalsIgnoreCase("")) {
                usernameNoEmpty.setText("Username cannot be empty");
                usernameNoEmpty.setTextFill(Color.RED);

                isUniqueName = false;
            }

            email = emailSignUp.getText();

            if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$")) {
                emailSignUp.clear();

                invalidEmail.setText("Invalid email");
                invalidEmail.setTextFill(Color.RED);

                isUniqueEmail = false;

            } else if (email.equalsIgnoreCase("")) {
                invalidEmail.setText("Email cannot be empty");
                invalidEmail.setTextFill(Color.RED);
            }

            for (User user : users) {
                if (name.equals(user.getName())) {
//                    usernameSignUp.clear();
                    usernameNoEmpty.setText("The username has been registered");
                    usernameNoEmpty.setTextFill(Color.RED);

                    isUniqueName = false;
                    break;
                }
            }
            for (User user : users) {
                if (email.equals(user.getEmail())) {
//                    emailSignUp.clear();
                    invalidEmail.setText("The email has been registered");
                    invalidEmail.setTextFill(Color.RED);

                    isUniqueEmail = false;

                    break;
                }
            }

            password = passwordSignUp.getText().toString();
            if (password.isEmpty() || password.isBlank()) {
                passwordNoEmpty.setText("Password cannot be empty");
                passwordNoEmpty.setTextFill(Color.RED);

           }
            confirmPassword = reConfirmPW.getText();
            if (!confirmPassword.equals(password)) {
                passwordNotMatch.setText("Passwords do not match");
                passwordNotMatch.setTextFill(Color.RED);

//                reConfirmPW.clear();
//                passwordSignUp.clear();
            }

        if (isUniqueName && isUniqueEmail && !password.isEmpty() && !password.isBlank() && confirmPassword.equals(password)) {
            created = true;
            User uniqueUser = new User(0, name, email, password, 0, 0, null);
            UserQuery.insertNew(uniqueUser);
            File f = new File(System.getProperty("user.dir") + "\\videos\\" + uniqueUser.getName());
            if (f.mkdir()) {
                System.out.println("-----Your directory to store video(s) created successfully-----");
                System.out.println("-----Account created successfully -----");
                System.out.println("-----Please login now          (A)-----");
            } else {
                System.out.println("Your directory to store video(s) is NOT CREATED");
                System.out.println("-----Account NOT CREATED -----");
            }

            URL url = new File("src/sample/resource/Notification_accountCreated.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setX(600);
            window.setY(250);
            window.show();
        }

    }

    public void toLogin(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/login.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(450);
        window.setY(130);
        window.show();
    }
}
