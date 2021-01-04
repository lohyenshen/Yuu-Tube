package sample.controller;

import app.*;
import database.*;
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
import sample.controller.login;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class homePage extends MyUsersProfile {

    @FXML
    private TextField searchUser;
    @FXML
    private MediaView mediaView;
    @FXML
    private Button toSearchUser;
    @FXML
    private Label usernameHomePage;


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

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileScene);
        window.show();
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

    public void toListOfUsers(ActionEvent event) throws IOException {
        if (Main.userOn) {
            URL url = new File("src/sample/resource/listOfUsers.fxml").toURI().toURL();
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

    public void displayUsernameAfterLogin(MouseEvent event) {
        if (Main.userOn) {
            usernameHomePage.setText(login.loginUser.getName());
        } else {
            usernameHomePage.setText("Not login");
        }
    }

    public void toSearchUsers(ActionEvent event) {
    }
//
//    public void toSearchUsers(ActionEvent event) throws Exception {
//        Scanner sc = new Scanner(System.in);
//
//        String s;
//        do{
//            //System.out.print("Search NAME of Users: ");
//            s = searchUser.getText();
//
//            if (s.isBlank() || s.isEmpty()) {
//                //System.out.println("search cannot be EMPTY or BLANK!");
//            }
//        } while( s.isBlank() || s.isEmpty() );
//
//        do{
//            User[] searchedUsers  = SearchQuery.searchUsers( s , login.loginUser );
//            //printSearchResult( searchedUsers );
//
//            boolean validID = false;
////            System.out.println();
////            System.out.println("Exit subscribing ,Enter \'e\'");
////            System.out.print("To subscribe to a user, Enter userID: ");
//            String op = sc.next();
//            if (op.equals("e"))
//                return;
//
//            int id;
//            if (isInteger( op )){
//                id = Integer.parseInt( op );
//            }
//            else{
//                System.out.println("Enter a valid ID !");
//                continue;
//            }
//
//            for (User searchedUser : searchedUsers) {
//                if (id == searchedUser.getUserID()) {
//
//                    // currentUser cannot subscribe to himself/herself
//                    if (currentUser.getUserID() == searchedUser.getUserID()) {
//                        System.out.println("Cannot subscribe to yourself !");
//                        break;
//                    }
//                    // currentUser wants to subscribe to the searchedUser
//                    //     (a)                                  (b)
//                    // but has (a)    subscribed to             (b) before ?
//                    else {
//                        validID = true;
//
//                        // if (a) has subscribed to (b) before
//                        // (a) will now unsubscribes to b
//                        if ( searchedUser.getSubscribed() ) {
//
//                            // delete the record where "currentUser" subscribes to "searchedUser"
//                            SubscriberQuery.delete( currentUser.getUserID() , searchedUser.getUserID() );
//                            // decrease subscribersCount of the searchedUser
//                            Query.decrease("user", "subscribersCount",  1, "userID", searchedUser.getUserID());
//
//                            System.out.println("UNSUBSCRIBED successfully from " + searchedUser.getName());
//                            break;
//                        }
//                        // (a) has not subscribed to (b) before
//                        else {
//                            // insert a new record where "currentUser" subscribes to "searchedUser"
//                            SubscriberQuery.insertNew( currentUser.getUserID() , searchedUser.getUserID() );
//                            // increase subscribersCount of the searchedUser
//                            Query.increase("user", "subscribersCount",  1, "userID", searchedUser.getUserID());
//                            System.out.println("SUBSCRIBED successfully to " + searchedUser.getName());
//                            break;
//                        }
//                    }
//                }
//            }
//            if (!validID)
//                System.out.println("Enter a valid ID !");
//        } while (true);
//    }
//    }
}


