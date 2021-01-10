package sample.controller;

import app.User;
import app.Video;
import database.Query;
import database.SubscriberQuery;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import operation.PlayVideo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class OtherUsersProfile {
    @FXML private Label otherUsername;
    @FXML private Label otherUserID;
    @FXML private Label otherUserNumOfSubscriber;
    @FXML private Label otherUserNumOfVideo;
    @FXML private ListView otherVideoID;
    @FXML private ListView otherVideoTitle;
    @FXML private ListView otherViews;
    @FXML private ListView otherLikes;
    @FXML private TextField chooseVideo;
    @FXML private Button subscribeButton;

    private Service<Void> backgroundThread;

    public void backTo(MouseEvent event) throws IOException {
        URL url = new File("src/sample/resource/homePage.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
    }

    public void showOtherProfileDetails(MouseEvent event) throws Exception {
        int userIDD = 0, sub = 0, video = 0;
        String name = "";
        User[] users = UserQuery.getUsers();
        for (int i = 0; i < users.length; i++) {
            if (SearchUser.tempUser.getUserID() == users[i].getUserID()) {
                userIDD = users[i].getUserID();
                name = users[i].getName();
                sub = users[i].getSubscribersCount();
                video = users[i].getVideosCount();
                otherUsername.setText(name);
                otherUserID.setText(Integer.toString(userIDD));
                otherUserNumOfSubscriber.setText(Integer.toString(sub));
                otherUserNumOfVideo.setText(Integer.toString(video));
            }
        }

        if (SubscriberQuery.subscribedTo(Login.loginUser.getUserID(), SearchUser.tempUser.getUserID())) {
            subscribeButton.setStyle("-fx-background-color: #808080;");
            subscribeButton.setText("Subscribed");
        } else {
            subscribeButton.setStyle("-fx-background-color: #ff0000; ");
            subscribeButton.setText("Subscribe");
        }
    }

    public void showVideo(MouseEvent event) throws Exception {
        int videoIDD = 0, view = 0, like = 0;
        String title = "";
        User[] users = UserQuery.getUsers();
        Video[] videos = VideoQuery.getVideos();
        ArrayList<Video> sv = new ArrayList<Video>();

        for (int i = 0; i < users.length; i++) {
            if (SearchUser.tempUser.getUserID() == users[i].getUserID()) {
                for (int x = 0; x < videos.length; x++) {
                    for (int j = 0; j < SearchUser.tempUser.getVideos().length; j++) {
                        if (videos[x].getVideoID() == SearchUser.tempUser.getVideos()[j].getVideoID()) {
                            videoIDD = videos[x].getVideoID();
                            view = videos[x].getViewsCount();
                            like = videos[x].getLikesCount();
                            title = videos[x].getTitle();
                            otherVideoID.getItems().add(videoIDD);
                            otherVideoTitle.getItems().add(title);
                            otherViews.getItems().add(view);
                            otherLikes.getItems().add(like);
                        }
                    }
                }
            }
        }
    }

    public void playVideo(MouseEvent event) throws Exception {
        String c = chooseVideo.getText();

        for (int i = 0; i < VideoQuery.getVideos().length; i++) {
            if (Integer.parseInt(c) == VideoQuery.getVideos()[i].getVideoID()) {
                HomePage.currentVideoPlaying = VideoQuery.getVideos()[i];
                HomePage.currentVideoPlayingID = VideoQuery.getVideos()[i].getVideoID();
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
                            if (Integer.parseInt(c) == VideoQuery.getVideos()[i].getVideoID()) {
                                PlayVideo.withLogin(Login.loginUser,VideoQuery.getVideos()[i]);
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

    public void toSubscribe(ActionEvent event) throws Exception {
        boolean subscribe = SearchUser.tempUser.getSubscribed();
        if (!SubscriberQuery.subscribedTo(Login.loginUser.getUserID(), SearchUser.tempUser.getUserID())) {

            // insert a new record where "currentUser" subscribes to "searchedUser"
            SubscriberQuery.insertNew(Login.loginUser.getUserID(), SearchUser.tempUser.getUserID());

            // increase subscribersCount of the searchedUser
            Query.increase("user", "subscribersCount", 1, "userID", SearchUser.tempUser.getUserID());

            System.out.println("SUBSCRIBED successfully to " + SearchUser.tempUser.getName());
            subscribeButton.setText("Subscribed");
            subscribeButton.setStyle("-fx-background-color: #808080; ");
            otherUserNumOfSubscriber.setText(Integer.toString(SearchUser.tempUser.getSubscribersCount() + 1));

        } else if (SubscriberQuery.subscribedTo(Login.loginUser.getUserID(), SearchUser.tempUser.getUserID())) {
            // delete the record where "currentUser" subscribes to "searchedUser"
            SubscriberQuery.delete( Login.loginUser.getUserID() , SearchUser.tempUser.getUserID() );

            // decrease subscribersCount of the searchedUser
            Query.decrease("user", "subscribersCount",  1, "userID", SearchUser.tempUser.getUserID());
            System.out.println("UNSUBSCRIBED successfully from " + SearchUser.tempUser.getName());

            subscribeButton.setText("Subscribe");
            subscribeButton.setStyle("-fx-background-color: #ff0000; ");
            otherUserNumOfSubscriber.setText(Integer.toString(SearchUser.tempUser.getSubscribersCount() - 1));
        }
    }
}
