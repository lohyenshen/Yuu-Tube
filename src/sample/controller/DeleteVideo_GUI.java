package sample.controller;

import app.User;
import app.Video;
import database.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import operation.DeleteVideo;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DeleteVideo_GUI {
    @FXML private ListView videoID;
    @FXML private ListView videoTitle;
    @FXML private TextField deleteVideoSelect;

    public void backToProfile(ActionEvent event) throws IOException {
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

    public void showVideoDetails(MouseEvent event) throws Exception {
        videoID.getItems().clear();
        videoTitle.getItems().clear();

        User uNow = UserQuery.getUser(Login.loginUser.getUserID());
        for (Video videos : uNow.getVideos()) {
            videoID.getItems().add(videos.getVideoID());
            videoTitle.getItems().add(videos.getTitle());
        }
    }

    public void deleteVideo(MouseEvent event) throws Exception {

        // modified to support multiple videos deletion
        Login.loginUser = UserQuery.getUser( Login.loginUser.getUserID() );          // extract latest details
        String[] selections_String = deleteVideoSelect.getText().split("\\s+");

        for (String s : selections_String){
            int id = Integer.parseInt( s );

            for (Video video : Login.loginUser.getVideos()){
                if (id == video.getVideoID()) {

                    DeleteVideo.delete( Login.loginUser, video);
                    break;
                }
            }
        }


        URL url = new File("src/sample/resource/Notification_videoDeleted.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(520);
        window.setY(250);
        window.show();
        Main.userOn = true;
    }
}
