package sample.controller;

import app.*;
import database.VideoQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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

public class MyUsersProfile {

    @FXML
    protected Label userID;
    @FXML
    protected Label numOfSubscribers;
    @FXML
    protected Label numOfVideos;
    @FXML
    private Label usernameProfile;


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

    public void update() {
        if (Main.userOn) {
            userID.setText(Integer.toString(login.loginUser.getUserID()));
            numOfSubscribers.setText(Integer.toString(login.loginUser.getSubscribersCount()));
            numOfVideos.setText(Integer.toString(login.loginUser.getVideosCount()));
            usernameProfile.setText(login.loginUser.getName());

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

    public void updateData(MouseEvent event) {
        if (Main.userOn) {
            userID.setText(Integer.toString(login.loginUser.getUserID()));
            numOfSubscribers.setText(Integer.toString(login.loginUser.getSubscribersCount()));
            numOfVideos.setText(Integer.toString(login.loginUser.getVideosCount()));
            usernameProfile.setText(login.loginUser.getName());
        } else {
            userID.setText("");
            numOfSubscribers.setText("");
            numOfVideos.setText("");
            usernameProfile.setText("");
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
        //UploadVideo.main(login.loginUser);

        File[] fs = null;
        boolean isUniqueTitle = true;


            JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileNameExtensionFilter("FLV files", "flv"));
            fc.setFileFilter(new FileNameExtensionFilter("WMV files", "wmv"));
            fc.setFileFilter(new FileNameExtensionFilter("AVI files", "avi"));
            fc.setFileFilter(new FileNameExtensionFilter("MOV files", "mov"));
            fc.setFileFilter(new FileNameExtensionFilter("MKV files", "mkv"));
            fc.setFileFilter(new FileNameExtensionFilter("MP4 files", "mp4"));
//                File wd = new File(System.getProperty("user.dir"));
            /////////// alter the pathname to change JFileChooser open location ///////////////////
//            File cd = new File("C:\\Users\\lohye\\Desktop\\videossss\\");
            File cd = new File(System.getProperty("user.home"));
            //////////////////////////////////////////////////////////////////////////////////////
            fc.setCurrentDirectory(cd);
            fc.setMultiSelectionEnabled(true);
            fc.setDialogTitle("Choose video file(s) to upload");
            fc.setApproveButtonText("Upload video(s)");


            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.CANCEL_OPTION) {
                fs = null;
            }
            if (result != JFileChooser.APPROVE_OPTION)

            fs = fc.getSelectedFiles();
            Video[] videos = VideoQuery.getVideos();
            isUniqueTitle = true;
            stop: {
                for (File f : fs) {
                    String title = FilenameUtils.removeExtension(f.getName());
                    for (Video video : videos) {
                        if (title.equals(video.getTitle()) ) {
                            isUniqueTitle = false;
//                        System.out.println("Title = " + title);
//                        System.out.println("Video of the SAME TITLE has already been uploaded !");
                            fc.cancelSelection();
                            fs = null;
                            break stop;
                        }
                    }
                }
            }


        if (!isUniqueTitle) {
            //System.out.println("-----No video(s) uploaded , returning to main page. ---------");

            URL url = new File("src/sample/resource/Error_uploadVideo_sameTitle.fxml").toURI().toURL();
            Parent profileParent = FXMLLoader.load(url);
            Scene profileScene = new Scene(profileParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(profileScene);
            window.show();
            window.setX(500);
            window.setY(280);
        } else {
            if (fs == null) {
                System.out.println("No videos choose");
            } else {
                for (File f : fs) {
                    // videoID, userID, title, likesCount, dislikesCount, viewsCount, comments, path
                    // all new videos has 0 likesCount, 0 dislikesCount, 0 viewsCount, "" comment
                    // videoID's ACTUAL VALUE will be handled by MySQL auto increment feature
                    int videoID = 0;
                    int userID = login.loginUser.getUserID();
                    String title = FilenameUtils.removeExtension(f.getName());
                    int likesCount = 0;
                    int dislikesCount = 0;
                    int viewsCount = 0;
                    String[] comments = null;
                    String path = login.loginUser.getName() + "\\" + f.getName();

                    Video video = new Video(videoID, userID, title, likesCount, dislikesCount, viewsCount, comments, path);
                    // insert video details into database
                    VideoQuery.insertNew(video);
                    // copy the video file
                    String from = f.getAbsolutePath();
                    String to = System.getProperty("user.dir") + "\\videos\\" + path;
                    UploadVideo.toDirectory(from, to);

                    System.out.println();
                    System.out.println("Upload video done! ");
                    System.out.println("Title             = " + title);
                    System.out.println("Uploaded by       = " + login.loginUser.getName());
                }
            }
        }
    }
}
