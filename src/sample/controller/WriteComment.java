package sample.controller;

import app.Video;
import database.VideoQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WriteComment {
    @FXML
    private TextArea commentText;

    public void backToLikeComment(ActionEvent event) throws IOException {
        URL url = new File("src/sample/resource/toLike_toComment_Features.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(450);
        window.setY(130);
        window.show();
    }

    public void Notification_commentAdded(ActionEvent event) throws Exception {
        URL url = new File("src/sample/resource/Notification_commentAdded.fxml").toURI().toURL();
        Parent profileParent = FXMLLoader.load(url);
        Scene profileScene = new Scene(profileParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.setX(550);
        window.setY(270);
        window.show();

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
            }
        }
    }
}
