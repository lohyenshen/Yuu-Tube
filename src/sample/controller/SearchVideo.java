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


public class SearchVideo extends HomePage {
    @FXML private TextField videoIDToPlayVideo;
    @FXML private TextField searchVideoText;
    @FXML private Button searchVideoButton;
    @FXML private ListView displayVideoID;
    @FXML private ListView displayVideoTitle;
    @FXML private ListView viewsCount;
    @FXML private ListView likesCount;
    @FXML private ListView userUpload;

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
        String s;
        s = searchVideoText.getText();

        if (s.isBlank() || s.isEmpty()) {
            //System.out.println("search cannot be EMPTY or BLANK!");
            URL url = new File("src/sample/resource/Error_searchVideo_noBlank.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(profileScene);
            stage.setX(500);
            stage.setY(250);
        }

        //Video[] searchedVideos  = SearchQuery.searchVideos( s );
        for (Video video : SearchQuery.searchVideos( s )) {
            displayVideoID.getItems().add(video.getVideoID());
            displayVideoTitle.getItems().add(video.getTitle());
            viewsCount.getItems().add(video.getViewsCount());
            likesCount.getItems().add(video.getLikesCount());
            userUpload.getItems().add(video.getUserID());
        }
    }

    public void playVideo(MouseEvent event) throws Exception {
        String op = videoIDToPlayVideo.getText();


        for (int i = 0; i < VideoQuery.getVideos().length; i++) {
            if (Integer.parseInt(op) == VideoQuery.getVideos()[i].getVideoID()) {
                HomePage.currentVideoPlayingID = VideoQuery.getVideos()[i].getVideoID();
                HomePage.currentVideoPlaying = VideoQuery.getVideos()[i];
            }
        }

        backgroundThread = new Service<Void>() {
            //
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        for (int i = 0; i < VideoQuery.getVideos().length; i++) {
                            if (Integer.parseInt(op) == VideoQuery.getVideos()[i].getVideoID()) {
                                PlayVideo.withLogin( Login.loginUser, VideoQuery.getVideos()[i] );
                            }
                        }
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

    public void toSearchUser(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/searchUser.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void toVideoDetails(MouseEvent event) throws Exception {
        Video[] videos = VideoQuery.getVideos();
        for (int i = 0; i < videos.length; i++) {
            if (videoIDToPlayVideo.getText().equalsIgnoreCase(Integer.toString(videos[i].getVideoID()))) {
                currentVideoPlaying = videos[i];
                currentVideoPlayingID = videos[i].getVideoID();
            }
        }

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
