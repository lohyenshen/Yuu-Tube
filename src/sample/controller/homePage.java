package sample.controller;

import app.*;
import database.*;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import operation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class homePage extends MyUsersProfile {
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
            usernameHomePage.setText(login.loginUser.getName());
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
        if (Main.userOn) {
            backgroundThread = new Service<Void>() {
                //
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {

                        @Override
                        protected Void call() throws Exception {
                            if (playTrendingVideoText.getText().equalsIgnoreCase("1")) {
                                PlayVideo.withLogin(login.loginUser, VideoQuery.getTrendingVideos()[0]);
                                tempID = VideoQuery.getTrendingVideos()[0].getVideoID();
                                currentVideoPlaying = VideoQuery.getTrendingVideos()[0];

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("2")) {
                                PlayVideo.withLogin(login.loginUser, VideoQuery.getTrendingVideos()[1]);
                                tempID = VideoQuery.getTrendingVideos()[1].getVideoID();
                                currentVideoPlaying = VideoQuery.getTrendingVideos()[1];

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("3")) {
                                PlayVideo.withLogin(login.loginUser, VideoQuery.getTrendingVideos()[2]);
                                tempID = VideoQuery.getTrendingVideos()[2].getVideoID();
                                currentVideoPlaying = VideoQuery.getTrendingVideos()[2];

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("4")) {
                                PlayVideo.withLogin(login.loginUser, VideoQuery.getTrendingVideos()[3]);
                                tempID = VideoQuery.getTrendingVideos()[3].getVideoID();
                                currentVideoPlaying = VideoQuery.getTrendingVideos()[3];

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("5")) {
                                PlayVideo.withLogin(login.loginUser, VideoQuery.getTrendingVideos()[4]);
                                tempID = VideoQuery.getTrendingVideos()[4].getVideoID();
                                currentVideoPlaying = VideoQuery.getTrendingVideos()[4];

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("")) {
                                System.out.println("Please enter something");
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
                    backgroundThread.getValue();
                    backgroundThread.valueProperty();
                }
            });
            backgroundThread.start();
            if (playTrendingVideoText.getText().equalsIgnoreCase("1")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[0].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[0];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("2")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[1].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[1];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("3")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[2].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[2];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("4")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[3].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[3];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("5")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[4].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[4];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("")) {
                System.out.println("Please enter something");
            }

            URL url = new File("src/sample/resource/toLike_toComment_Features.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();

        } else {
            backgroundThread = new Service<Void>() {

                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {

                        @Override
                        protected Void call() throws Exception {
                            if (playTrendingVideoText.getText().equalsIgnoreCase("1")) {
                                PlayVideo.withoutLogin(VideoQuery.getTrendingVideos()[0]);
                                currentVideoPlayingID = VideoQuery.getTrendingVideos()[0].getVideoID();

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("2")) {
                                PlayVideo.withoutLogin(VideoQuery.getTrendingVideos()[1]);
                                currentVideoPlayingID = VideoQuery.getTrendingVideos()[1].getVideoID();

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("3")) {
                                PlayVideo.withoutLogin(VideoQuery.getTrendingVideos()[2]);
                                currentVideoPlayingID = VideoQuery.getTrendingVideos()[1].getVideoID();

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("4")) {
                                PlayVideo.withoutLogin(VideoQuery.getTrendingVideos()[3]);
                                currentVideoPlayingID = VideoQuery.getTrendingVideos()[3].getVideoID();

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("5")) {
                                PlayVideo.withoutLogin(VideoQuery.getTrendingVideos()[4]);
                                currentVideoPlayingID = VideoQuery.getTrendingVideos()[4].getVideoID();

                            } else if (playTrendingVideoText.getText().equalsIgnoreCase("")) {
                                System.out.println("Please enter something");
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

            URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setX(450);
            window.setY(130);
            window.show();
        }
    }

    public void toVideoInfo(MouseEvent event) throws Exception {
        if (Main.userOn) {
            if (playTrendingVideoText.getText().equalsIgnoreCase("1")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[0].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[0];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("2")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[1].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[1];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("3")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[2].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[2];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("4")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[3].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[3];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("5")) {
                currentVideoPlayingID = VideoQuery.getTrendingVideos()[4].getVideoID();
                currentVideoPlaying = VideoQuery.getTrendingVideos()[4];

            } else if (playTrendingVideoText.getText().equalsIgnoreCase("")) {
                System.out.println("Please enter something");
            }
            URL url = new File("src/sample/resource/toLike_toComment_Features.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.setX(450);
            window.setY(130);
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


