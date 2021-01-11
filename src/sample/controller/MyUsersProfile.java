package sample.controller;

import app.*;
import database.UserQuery;
import database.VideoQuery;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import operation.UploadVideo;
import org.apache.commons.io.FilenameUtils;
import sample.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MyUsersProfile{

    @FXML protected Label userID;
    @FXML protected Label numOfSubscribers;
    @FXML protected Label numOfVideos;
    @FXML private Label usernameProfile;
    @FXML private ListView videoID;
    @FXML private ListView videoTitle;
    @FXML private ListView views;
    @FXML private ListView likes;


    // setter
    public void setUserID(Label userID) {
        this.userID = userID;
    }

    public void setNumOfSubscribers(Label numOfSubscribers) {
        this.numOfSubscribers = numOfSubscribers;
    }

    public void setNumOfVideos(Label numOfVideos) {
        this.numOfVideos = numOfVideos;
    }

    public void update() throws Exception {
        if (Main.userOn) {
            UserQuery.getUser(Login.loginUser.getUserID());
            VideoQuery.getVideos(Login.loginUser.getUserID());
            userID.setText(Integer.toString(Login.loginUser.getUserID()));
            numOfSubscribers.setText(Integer.toString(Login.loginUser.getSubscribersCount()));
            numOfVideos.setText(Integer.toString(Login.loginUser.getVideosCount()));
            usernameProfile.setText(Login.loginUser.getName());

        } else {
            userID.setText("");
            numOfSubscribers.setText("");
            numOfVideos.setText("");
            usernameProfile.setText("");
        }
    }

    public void backTo(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
        Main.userOn = true;
    }

    public void logout(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/Confirmation_logout.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(500);
        window.setY(250);
        window.show();
    }

    public void updateData(MouseEvent event) throws Exception {
        int userIDD = 0, sub = 0, video = 0;
        String name = "";
        User[] users = UserQuery.getUsers();

        for (int i = 0; i < users.length; i++) {
            if (Login.loginUser.getUserID() == users[i].getUserID()) {
                userIDD = users[i].getUserID();
                sub = users[i].getSubscribersCount();
                video = users[i].getVideosCount();
                name = users[i].getName();
            }
        }

        if (Main.userOn) {
            userID.setText(Integer.toString(userIDD));
            numOfSubscribers.setText(Integer.toString(sub));
            numOfVideos.setText(Integer.toString(video));
            usernameProfile.setText(name);

        } else {
            userID.setText("");
            numOfSubscribers.setText("");
            numOfVideos.setText("");
            usernameProfile.setText("");
        }
        videoID.getItems().clear();
        videoTitle.getItems().clear();
        views.getItems().clear();
        likes.getItems().clear();

        User uNow = UserQuery.getUser(Login.loginUser.getUserID());
        for (Video videoss : uNow.getVideos()) {
            videoID.getItems().add(videoss.getVideoID());
            videoTitle.getItems().add(videoss.getTitle());
            views.getItems().add(videoss.getViewsCount());
            likes.getItems().add(videoss.getLikesCount());
        }

    }

    public void confirmationToChangeEmail(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/Confirmation_changeEmail.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(500);
        window.setY(250);
        window.show();
    }

    public void toChangePassword(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/Confirmation_changePassword.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(500);
        window.setY(250);
        window.show();
    }

    public void toDeleteAccount(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/Confirmation_deleteAccount.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(500);
        window.setY(250);
        window.show();
    }

    public void uploadVideo(MouseEvent event) throws Exception {
        UploadVideo.main(Login.loginUser);
    }

    public void deleteVideo(MouseEvent event) throws Exception {
        URL url = new File("src/sample/resource/Confirmation_deleteVideo.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(500);
        window.setY(250);
        window.show();
    }
}

