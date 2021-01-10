package sample.controller;

import app.User;
import database.Query;
import database.SearchQuery;
import database.SubscriberQuery;
import database.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class searchUser {
    @FXML private TextField userWords;
    @FXML private ListView userID_search;
    @FXML private ListView username_search;
    @FXML private ListView subscribers_search;
    @FXML private TextField toUserID;
    @FXML private ListView video_Search;

    public static User tempUser;

    public void toSearchVideo(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/searchVideo.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void showUserResult(ActionEvent event) throws Exception {
        String s;
        s = userWords.getText();
        //s.isBlank() || s.isEmpty() ||
        if (userWords.getText().equalsIgnoreCase("")) {
            URL url = new File("src/sample/resource/Error_searchUser_noBlank.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(profileScene);
            stage.setX(530);
            stage.setY(250);
        }

        User[] searchedUsers = SearchQuery.searchUsers(s, login.loginUser);
        for (int i = 0; i < searchedUsers.length; i++) {
            if (searchedUsers[i].getUserID() == login.loginUser.getUserID()) {
                continue;
            } else {
                userID_search.getItems().add(searchedUsers[i].getUserID());
                username_search.getItems().add(searchedUsers[i].getName());
                subscribers_search.getItems().add(searchedUsers[i].getSubscribersCount());
                video_Search.getItems().add(searchedUsers[i].getVideosCount());
            }
        }
    }

    public void backTo(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }


    public void toUserProfile(MouseEvent event) throws Exception {
        String op = toUserID.getText();

        for (int i = 0; i < UserQuery.getUsers().length; i++) {
            if (Integer.parseInt(op) == UserQuery.getUsers()[i].getUserID()) {
                tempUser = UserQuery.getUsers()[i];
                URL url = new File("src/sample/resource/OtherUsersProfile.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.show();
            }
        }
    }
}