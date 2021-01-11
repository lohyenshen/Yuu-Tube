package sample.controller;

import app.User;
import database.SearchQuery;
import database.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SearchUser {
    @FXML private TextField userWords;
    @FXML private ListView userID_search;
    @FXML private ListView username_search;
    @FXML private ListView subscribers_search;
    @FXML private TextField toUserID;
    @FXML private ListView video_Search;
    @FXML private Label toUserProfile_error;
    @FXML private Label searchUser_error;

    public static User tempUser;
    boolean search = false;

    public void toSearchVideo(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/searchVideo.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void showUserResult(ActionEvent event) throws Exception {
        searchUser_error.setText("");
        userID_search.getItems().clear();
        username_search.getItems().clear();
        subscribers_search.getItems().clear();
        video_Search.getItems().clear();

        String s;
        s = userWords.getText();
        if (userWords.getText().equalsIgnoreCase("")) {
            searchUser_error.setText("Cannot be empty");
        }

        User[] searchedUsers = SearchQuery.searchUsers(s, Login.loginUser);
        if (searchedUsers.length == 0) {
            searchUser_error.setText("No result found");
        }
        for (int i = 0; i < searchedUsers.length; i++) {
            if (searchedUsers[i].getUserID() == Login.loginUser.getUserID()) {
                continue;
            } else {
                search = true;
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
        String s;
        s = userWords.getText();
        String op = toUserID.getText();
        boolean validID = false;

        User[] searchedUsers = SearchQuery.searchUsers(s, Login.loginUser);

        if (userWords.getText().isEmpty()) {
            toUserProfile_error.setText("Enter keywords to search user");
        }
        for (User su : searchedUsers) {
            if (Integer.parseInt(op) == su.getUserID()) {
                validID = true;
                tempUser = su;
                URL url = new File("src/sample/resource/OtherUsersProfile.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.show();
            }
        }
        if (!validID) {
            toUserProfile_error.setText("Invalid user ID");
        }
    }
}