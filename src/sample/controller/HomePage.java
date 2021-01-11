package sample.controller;

import app.*;
import database.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import operation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HomePage extends MyUsersProfile {
    @FXML protected Label video1;
    @FXML private Label video2;
    @FXML private Label video3;
    @FXML private Label video4;
    @FXML private Label video5;
    @FXML private Button toSearchUser;
    @FXML private Label usernameHomePage;
    @FXML private Button searchVideoButton;
    @FXML private ImageView playTrendingVideo;
    @FXML protected TextField playTrendingVideoText;
    @FXML private ImageView loginImage;
    @FXML private Label loginWord;
    @FXML private Label invalidTreadingID;

    public static int currentVideoPlayingID;
    public static Video currentVideoPlaying;
    public int tempID;
    private Service<Void> backgroundThread;

    public void changeSceneToProfile(MouseEvent event) throws Exception {
        if (Main.userOn) {
            URL url = new File("src/sample/resource/MyUserProfile.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();

        } else {
            URL url = new File("src/sample/resource/Error_loginFirst.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setX(600);
            window.setY(250);
            window.show();
        }
    }

    public void toUsers(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/OtherUsersProfile.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void toSearchVideo(ActionEvent event) throws Exception {
        if (Main.userOn) {
            URL url = new File("src/sample/resource/searchVideo.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();
        } else {
            URL url = new File("src/sample/resource/Error_loginFirst.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setX(600);
            window.setY(250);
            window.show();
        }
    }


    public void displayUsernameAfterLogin_trendingVideos(MouseEvent event) throws Exception {
        if (VideoQuery.getTrendingVideos().length == 0) {
            video1.setText("");
            video2.setText("");
            video3.setText("");
            video4.setText("");
            video5.setText("");
        } else if (VideoQuery.getTrendingVideos().length == 1) {
            video1.setText(VideoQuery.getTrendingVideos()[0].getTitle());
        } else if (VideoQuery.getTrendingVideos().length == 2) {
            video1.setText(VideoQuery.getTrendingVideos()[0].getTitle());
            video2.setText(VideoQuery.getTrendingVideos()[1].getTitle());
        } else if (VideoQuery.getTrendingVideos().length == 3) {
            video1.setText(VideoQuery.getTrendingVideos()[0].getTitle());
            video2.setText(VideoQuery.getTrendingVideos()[1].getTitle());
            video3.setText(VideoQuery.getTrendingVideos()[2].getTitle());
        } else if (VideoQuery.getTrendingVideos().length == 4) {
            video1.setText(VideoQuery.getTrendingVideos()[0].getTitle());
            video2.setText(VideoQuery.getTrendingVideos()[1].getTitle());
            video3.setText(VideoQuery.getTrendingVideos()[2].getTitle());
            video4.setText(VideoQuery.getTrendingVideos()[3].getTitle());
        } else {
            video1.setText(VideoQuery.getTrendingVideos()[0].getTitle());
            video2.setText(VideoQuery.getTrendingVideos()[1].getTitle());
            video3.setText(VideoQuery.getTrendingVideos()[2].getTitle());
            video4.setText(VideoQuery.getTrendingVideos()[3].getTitle());
            video5.setText(VideoQuery.getTrendingVideos()[4].getTitle());
        }

        if (Main.userOn) {
            usernameHomePage.setText(Login.loginUser.getName());
            loginWord.setText("");
            loginImage.setVisible(false);
        } else {
            usernameHomePage.setText("Not login");
        }
    }

    public void toSearchUsers(ActionEvent event) throws IOException {
        if (Main.userOn) {
            URL url = new File("src/sample/resource/searchUser.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();
        } else {
            URL url = new File("src/sample/resource/Error_loginFirst.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setX(600);
            window.setY(250);
            window.show();
        }

    }

    public void playTrendingVideo(MouseEvent event) throws Exception {
        invalidTreadingID.setText("");
        String s = playTrendingVideoText.getText();
        int i;
        if (Main.userOn) {
            Video[] trendingVideos = VideoQuery.getTrendingVideos();
            i =  Integer.parseInt(s)  - 1;
            if (i>=0 && i<trendingVideos.length) {
                backgroundThread = new Service<Void>() {
                    //
                    @Override
                    protected Task<Void> createTask() {
                        return new Task<Void>() {

                            @Override
                            protected Void call() throws Exception {
                                PlayVideo.withLogin(Login.loginUser, trendingVideos[i]);
                                return null;
                            }
                        };
                    }
                };

                backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        System.out.println("Done");
                        backgroundThread.getValue();
                        backgroundThread.valueProperty();
                    }
                });
                backgroundThread.start();

                currentVideoPlaying = trendingVideos[i];
                currentVideoPlayingID = trendingVideos[i].getVideoID();

                URL url = new File("src/sample/resource/toLike_toComment_Features.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.show();

            } else {
                invalidTreadingID.setText("Invalid");
                invalidTreadingID.setTextFill(Color.RED);
            }

        } else {
            Video[] trendingVideos = VideoQuery.getTrendingVideos();
            i =  Integer.parseInt(s)  - 1;
            if (i>=0 && i<trendingVideos.length) {
                backgroundThread = new Service<Void>() {

                    @Override
                    protected Task<Void> createTask() {
                        return new Task<Void>() {

                            @Override
                            protected Void call() throws Exception {
                                PlayVideo.withoutLogin(trendingVideos[i]);
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

                URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(450);
                window.setY(130);
                window.show();
            } else {
                invalidTreadingID.setText("Invalid");
                invalidTreadingID.setTextFill(Color.RED);
            }
        }
    }

    public void toVideoInfo(MouseEvent event) throws Exception {
        Video[] trendingVideos = VideoQuery.getTrendingVideos();
        String s = playTrendingVideoText.getText();
        int i;
        if (Main.userOn) {
            i =  Integer.parseInt(s)  - 1;
            if (i>=0 && i<trendingVideos.length) {
                currentVideoPlaying = trendingVideos[i];
                currentVideoPlayingID = trendingVideos[i].getVideoID();

                URL url = new File("src/sample/resource/toLike_toComment_Features.fxml").toURI().toURL();
                Parent profileParent = FXMLLoader.load(url);
                Scene profileScene = new Scene(profileParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(profileScene);
                window.setX(450);
                window.setY(130);
                window.show();
            }
        } else {
            URL url = new File("src/sample/resource/Error_loginFirst.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setX(600);
            window.setY(250);
            window.show();
        }
    }

    public void toLogin(MouseEvent event) throws IOException {
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


