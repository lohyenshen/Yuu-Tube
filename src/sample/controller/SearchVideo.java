package sample.controller;

import app.Video;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import operation.*;
import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class SearchVideo extends HomePage{
    @FXML private TextField videoIDToPlayVideo;
    @FXML private TextField searchVideoText;
    @FXML private Button searchVideoButton;
    @FXML private ListView displayVideoID;
    @FXML private ListView displayVideoTitle;
    @FXML private ListView viewsCount;
    @FXML private ListView likesCount;
    @FXML private ListView userUpload;
    @FXML private Label searchVideo_error;
    @FXML private Label invalidVideoID;

    private Service<Void> backgroundThread;

    public void backTo(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void toUsers(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/OtherUsersProfile.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void searchVideo(ActionEvent event) throws Exception {
        searchVideo_error.setText("");

        displayVideoID.getItems().clear();
        displayVideoTitle.getItems().clear();
        viewsCount.getItems().clear();
        likesCount.getItems().clear();
        userUpload.getItems().clear();

        String s;
        s = searchVideoText.getText();

        if (s.equalsIgnoreCase("")) {
            //System.out.println("search cannot be EMPTY or BLANK!");
            searchVideo_error.setText("Cannot be empty");
        }

        Video[] searchedVideos  = SearchQuery.searchVideos( s );
        for (Video video : searchedVideos) {
            if ( searchedVideos.length == 0) {
                searchVideo_error.setText("No result found");
            } else {
                displayVideoID.getItems().add(video.getVideoID());
                displayVideoTitle.getItems().add(video.getTitle());
                viewsCount.getItems().add(video.getViewsCount());
                likesCount.getItems().add(video.getLikesCount());
                userUpload.getItems().add(video.getUserID());
            }
        }
    }

    public void playVideo(MouseEvent event) throws Exception {
        boolean isValid = false;
        invalidVideoID.setText("");

        String s = searchVideoText.getText();
        String op = videoIDToPlayVideo.getText();

        if (videoIDToPlayVideo.getText().equalsIgnoreCase("")) {
            invalidVideoID.setText("No video to play");
        }

        Video[] searchedVideos  = SearchQuery.searchVideos( s );
        if (searchedVideos.length == 0) {
            invalidVideoID.setText("Enter keywords to search");
        }
        for (Video video : searchedVideos) {
            if (Integer.parseInt(op) == video.getVideoID()) {
                isValid = true;
                HomePage.currentVideoPlayingID = video.getVideoID();
                HomePage.currentVideoPlaying = video;

                backgroundThread = new Service<Void>() {
                    @Override
                    protected Task<Void> createTask() {
                        return new Task<Void>() {

                            @Override
                            protected Void call() throws Exception {
                                PlayVideo.withLogin(Login.loginUser, video);
                                return null;
                            }
                        };
                    }
                };
                backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        System.out.println("Done");
                    }
                });
                backgroundThread.start();

                URL url = new File("src/sample/resource/toLike_toComment_Features.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.show();
            }
        }
        if (!isValid) {
            invalidVideoID.setText("Invalid video ID");
        }
    }

    public void toSearchUser(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/searchUser.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void toVideoDetails(MouseEvent event) throws Exception {
        String op = videoIDToPlayVideo.getText();
        Video[] searchedVideos = SearchQuery.searchVideos(op);
        boolean isValid = false;

        if (videoIDToPlayVideo.getText().isEmpty()) {
            invalidVideoID.setText("No video");
        }

        for (Video video : searchedVideos) {
            if (Integer.parseInt(op) == video.getVideoID()) {
                isValid = true;
                currentVideoPlaying = video;
                currentVideoPlayingID = video.getVideoID();
                URL url = new File("src/sample/resource/toLike_toComment_Features.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(450);
                window.setY(130);
                window.show();
            }
        }
        if (!isValid) {
            invalidVideoID.setText("Invalid video ID");
        }
    }
}
