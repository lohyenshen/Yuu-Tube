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
import java.lang.String;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SignUp {
    @FXML private TextField usernameSignUp;
    @FXML private TextField emailSignUp;
    @FXML private PasswordField passwordSignUp;
    @FXML private PasswordField reConfirmPW;

    // "Create" button
    public void saveNewUser(ActionEvent event) throws Exception {
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
        do {
            isUniqueName = true;
            isUniqueEmail = true;
            name = usernameSignUp.getText();

            if (name.isEmpty()) {
                usernameSignUp.clear();

                isUniqueName = false;

                URL url = new File("src/sample/resource/Error_toCreateUser.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(profileScene);
                stage.setX(650);
                stage.setY(300);
                continue;
            }

            email = emailSignUp.getText();

            if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$")) {
                emailSignUp.clear();

                isUniqueEmail = false;

                URL url = new File("src/sample/resource/Error_toCreateUser.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(profileScene);
                stage.setX(650);
                stage.setY(300);
                continue;
            }
            for (User user : users) {
                if (name.equals(user.getName())) {
                    usernameSignUp.clear();

                    isUniqueName = false;

                    URL url = new File("src/sample/resource/Error_toCreateUser.fxml").toURI().toURL();
                    Parent profileParent = FXMLLoader.load(url);
                    Scene profileScene = new Scene(profileParent);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(profileScene);
                    stage.setX(650);
                    stage.setY(300);
                    break;
                }
            }
            for (User user : users) {
                if (email.equals(user.getEmail())) {
                    emailSignUp.clear();

                    isUniqueEmail = false;

                    URL url = new File("src/sample/resource/Error_toCreateUser.fxml").toURI().toURL();
                    Parent profileParent = FXMLLoader.load(url);
                    Scene profileScene = new Scene(profileParent);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(profileScene);
                    stage.setX(650);
                    stage.setY(300);
                    break;
                }
            }

            password = passwordSignUp.getText().toString();
            if (password.isEmpty() || password.isBlank()) {
                URL url = new File("src/sample/resource/Error_toCreateUser.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(profileScene);
                stage.setX(650);
                stage.setY(300);
           }
            confirmPassword = reConfirmPW.getText();
            if (!confirmPassword.equals(password)) {
                reConfirmPW.clear();
                passwordSignUp.clear();

                URL url = new File("src/sample/resource/Error_toCreateUser.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(profileScene);
                stage.setX(650);
                stage.setY(300);
            }

        } while (!isUniqueName || !isUniqueEmail || password.isEmpty() || password.isBlank() || !confirmPassword.equals(password));

        created = true;
        User uniqueUser = new User(0,  name, email, password, 0,0,null);
        UserQuery.insertNew(uniqueUser);

        if (created) {
            File f = new File( System.getProperty("user.dir") + "\\videos\\" + uniqueUser.getName() );
            if (f.mkdir()) {
                System.out.println("-----Your directory to store video(s) created successfully-----");
                System.out.println("-----Account created successfully -----");
                System.out.println("-----Please login now          (A)-----");
            }
            else{
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
