package sample.controller;

import app.User;
import app.Video;
import database.LikeDislikeQuery;
import database.SubscriberQuery;
import database.UserQuery;
import database.VideoQuery;
import operation.DeleteAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import operation.DeleteVideo;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Confirmation_deleteAccount {
    public void stayProfile(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/MyUserProfile.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(450);
        window.setY(130);
        window.show();
        Main.userOn = true;
    }

    public void deleteAccount(ActionEvent event) throws Exception {
        for (Video video : login.loginUser.getVideos())
            System.out.println(video.toString());

        /**
         * PHASE 1
         *      deletes all records in                   "subscriber" table that contains currentUser's ID
         *      updates subscribersCount of all users in "user" table
         */
        SubscriberQuery.deleteAcc(login.loginUser.getUserID());
        User[] users = UserQuery.getUsers();
        for (User user : users)
            UserQuery.updateSubscribersCount(user.getUserID());

        /**
         * PHASE 2
         *      deletes all records in                             "likedislike" table that contains currentUser's ID
         *      updates likesCount, dislikesCount of all videos in "video" table
         */
        LikeDislikeQuery.deleteAcc(login.loginUser.getUserID());
        Video[] videos = VideoQuery.getVideos();
        for (Video video : videos)
            VideoQuery.updateLikesDislikesCount(video.getVideoID());

        /**
         * PHASE 3
         *      deletes all videos in "video" table       uploaded by currentUser
         *      deletes all videos in DIRECTORY (videos)  uploaded by currentUser
         */
        for (Video video : login.loginUser.getVideos()) {
            // delete a record from "video" table
            VideoQuery.delete(video.getVideoID());
            // delete video from directory
            DeleteVideo.fromDirectory(video);
        }

        /**
         * PHASE 4
         *      delete a record in "user" table based on currentUser's ID
         *      delete the user's directory
         */
        File f = new File(System.getProperty("user.dir") + "\\videos\\" + login.loginUser.getName());
        if (f.delete()) {
            UserQuery.deleteAcc(login.loginUser.getUserID());
            System.out.println("-----Your directory to store video(s) DELETED successfully-----");
        } else
            System.out.println("-----Your directory to store video(s) IS NOT DELETED      -----");

        URL url = new File("src/sample/resource/Notification_accountDeleted.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(600);
        window.setY(250);
        window.show();
        Main.userOn = false;
    }
}
