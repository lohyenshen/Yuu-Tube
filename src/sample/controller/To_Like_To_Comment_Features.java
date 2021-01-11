package sample.controller;

import app.User;
import app.Video;
import database.LikeDislikeQuery;
import database.UserQuery;
import database.VideoQuery;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import operation.PlayVideo;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class To_Like_To_Comment_Features {
    @FXML private Button toSearchVideo;
    @FXML private Button toSearchUser;
    @FXML private ImageView profileEnter;
    @FXML private Label usernameHomePage;
    @FXML private Label like_dislike_comment;
    @FXML private ListView comments;
    @FXML private Label videoTitle;
    @FXML private Label views;
    @FXML private Label likes;
    @FXML private Label userID;
    @FXML private Label dislikes;
    @FXML private TextField commentText;

    private Service<Void> backgroundThread;

    public void displayUsernameAfterLogin_trendingVideos(MouseEvent event) throws Exception {
        usernameHomePage.setText(Login.loginUser.getName());

        comments.getItems().clear();

        Video now = VideoQuery.getVideo(HomePage.currentVideoPlaying.getVideoID());

        videoTitle.setText(now.getTitle());
        views.setText(Integer.toString(now.getViewsCount()));
        likes.setText(Integer.toString(now.getLikesCount()));
        userID.setText(Integer.toString(now.getUserID()));
        dislikes.setText(Integer.toString(now.getDislikesCount()));

        String[] coms = now.getComments();
        for (int i = 1; i < coms.length; i++) {
            comments.getItems().add(coms[i]);
        }
    }

    public void backToHomePage(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
        Main.userOn = true;
    }

    public void toSearchUsers(ActionEvent event) throws IOException {
        if (Main.userOn) {
            URL url = new File("src/sample/resource/searchUser.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
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

    public void toSearchVideo(ActionEvent event) throws IOException {
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

    public void changeSceneToProfile(MouseEvent event) throws IOException {
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

    public void pressLike(MouseEvent event) throws Exception {
        boolean past = LikeDislikeQuery.likedDisliked(Login.loginUser.getUserID(), HomePage.currentVideoPlayingID);

        if (past) {
            boolean status = LikeDislikeQuery.getStatus( Login.loginUser.getUserID() , HomePage.currentVideoPlayingID);
            if (status) {     // user liked the video before, so cannot like again, only dislike
//                System.out.println("You can't like the video as you liked it before");
                like_dislike_comment.setText("You already liked it ");
                like_dislike_comment.setStyle("-fx-background-color: #FF0000; ");

            } else {        // user disliked the video before, so cannot dislike again, only like
                // update dislike to like
                LikeDislikeQuery.update(Login.loginUser.getUserID(), HomePage.currentVideoPlayingID, 1);
                like_dislike_comment.setText("You successfully liked the video!!! :)");
                like_dislike_comment.setStyle("-fx-background-color: #00FF00; ");
                VideoQuery.updateLikesDislikesCount(HomePage.currentVideoPlayingID);

                User[] users = UserQuery.getUsers();
                Video[] videos = VideoQuery.getVideos();
                int like = 0;
                for (int i = 0; i < users.length; i++) {
                    if (HomePage.currentVideoPlaying.getUserID() == users[i].getUserID()) {
                        for (int x = 0; x < videos.length; x++) {
                            if (videos[x].getVideoID() == HomePage.currentVideoPlaying.getVideoID()) {
                                like = videos[x].getLikesCount();
                                likes.setText(Integer.toString(like));
                            }
                        }
                    }
                }
            }
        } else {
            LikeDislikeQuery.insertNew( Login.loginUser.getUserID() , HomePage.currentVideoPlayingID, 1);
            like_dislike_comment.setText("You successfully liked the video!!! :)");
            like_dislike_comment.setStyle("-fx-background-color: #00FF00; ");

            User[] users = UserQuery.getUsers();
            Video[] videos = VideoQuery.getVideos();
            int like = 0;
            for (int i = 0; i < users.length; i++) {
                if (HomePage.currentVideoPlaying.getUserID() == users[i].getUserID()) {
                    for (int x = 0; x < videos.length; x++) {
                        if (videos[x].getVideoID() == HomePage.currentVideoPlaying.getVideoID()) {
                            like = videos[x].getLikesCount();
                            likes.setText("1");
                        }
                    }
                }
            }
        }
        VideoQuery.updateLikesDislikesCount(HomePage.currentVideoPlayingID);
    }

    public void pressDislike(MouseEvent event) throws Exception {
        like_dislike_comment.setText("You have just disliked the video!!! :(");
        boolean past = LikeDislikeQuery.likedDisliked(Login.loginUser.getUserID(), HomePage.currentVideoPlayingID);

        if (past) {
            boolean status = LikeDislikeQuery.getStatus( Login.loginUser.getUserID() , HomePage.currentVideoPlayingID);
            if (!status) {     // user liked the video before, so cannot like again, only dislike
                System.out.println("You can't dislike the video as you disliked it before");
                like_dislike_comment.setText("You already disliked it ");
                like_dislike_comment.setStyle("-fx-background-color: #FF0000; ");

            } else {        // user disliked the video before, so cannot dislike again, only like
                // update dislike to like
                LikeDislikeQuery.update(Login.loginUser.getUserID(), HomePage.currentVideoPlayingID, 0);
                like_dislike_comment.setText("You have just disliked the video!!! :(");
                like_dislike_comment.setStyle("-fx-background-color: #00FF00; ");
                VideoQuery.updateLikesDislikesCount(HomePage.currentVideoPlayingID);

                User[] users = UserQuery.getUsers();
                Video[] videos = VideoQuery.getVideos();
                int like = 0;
                for (int i = 0; i < users.length; i++) {
                    if (HomePage.currentVideoPlaying.getUserID() == users[i].getUserID()) {
                        for (int x = 0; x < videos.length; x++) {
                            if (videos[x].getVideoID() == HomePage.currentVideoPlaying.getVideoID()) {
                                like = videos[x].getLikesCount();
                                likes.setText(Integer.toString(like));
                            }
                        }
                    }
                }
            }
        } else {
            LikeDislikeQuery.insertNew( Login.loginUser.getUserID() , HomePage.currentVideoPlayingID, 0);
            like_dislike_comment.setText("You have just disliked the video!!! :(");
            like_dislike_comment.setStyle("-fx-background-color: #00FF00; ");

            User[] users = UserQuery.getUsers();
            Video[] videos = VideoQuery.getVideos();
            int like = 0;
            for (int i = 0; i < users.length; i++) {
                if (HomePage.currentVideoPlaying.getUserID() == users[i].getUserID()) {
                    for (int x = 0; x < videos.length; x++) {
                        if (videos[x].getVideoID() == HomePage.currentVideoPlaying.getVideoID()) {
                            like = videos[x].getLikesCount();
                            likes.setText(Integer.toString(like));
                        }
                    }
                }
            }
        }
        VideoQuery.updateLikesDislikesCount(HomePage.currentVideoPlayingID);
    }

    public void pressComment(MouseEvent event) throws Exception {

        Video[] videos = VideoQuery.getVideos();

        for (int j = 0; j < videos.length; j++) {
            if (videos[j].getVideoID() == HomePage.currentVideoPlaying.getVideoID()) {
                String[] oldComments = videos[j].getComments();

                // StringBuilder is faster than String concatenation
                StringBuilder s = new StringBuilder();
                for (String oldComment : oldComments) {
                    s.append(oldComment);
                    s.append("\n");
                }
                String newComments = s.toString() + commentText.getText() + "\n";
                VideoQuery.updateComments(newComments, HomePage.currentVideoPlaying.getVideoID());
                commentText.clear();
            }
        }

    }
}
