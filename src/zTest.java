import app.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class zTest {

    public static void main(String[] args) {
//        boolean isUniqueName;
//        boolean isUniqueEmail;
//        // password.isEmpty() || password.isBlank()
//        //!confirmPassword.equals(password)
//        // while (!isUniqueName && !isUniqueEmail && (password.isEmpty() || password.isBlank()) && !confirmPassword.equals(password))
//        do {
//            isUniqueName = true;
//            isUniqueEmail = true;
//            //System.out.print("Enter name: ");
//            name = usernameSignUp.getText();
//
//            if (name.isEmpty()) {
//                //System.out.println("NAME CANNOT BE EMPTY! ");
//                usernameSignUp.clear();
//
//                isUniqueName = false;
//                continue;
//            }
//            for (User user : users) {
//                if (name.equals(user.name)) {
//                    //System.out.println("THIS NAME IS TAKEN! ");
//                    usernameSignUp.clear();
//
//                    isUniqueName = false;
//                    break;
//                }
//            }
////        } while(!isUniqueName);
//
////        do {
//            //isUniqueEmail = true;
//            //System.out.print("Enter email: ");
//            email = emailSignUp.getText();
//
//            if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$")) {
//                //System.out.println("INVALID EMAIL ! ");
//                emailSignUp.clear();
//
//                isUniqueEmail = false;
//                continue;
//            }
//            for (User user : users) {
//                if (email.equals(user.email)) {
//                    //System.out.println("THIS EMAIL IS TAKEN! ");
//                    emailSignUp.clear();
//
//                    isUniqueEmail = false;
//                    break;
//                }
//            }
////        } while (!isUniqueEmail);
//
//
////        do{
//            //System.out.print("Enter password: ");
//            password = passwordSignUp.getText().toString();
//            if (password.isEmpty() || password.isBlank()) {
//                //System.out.println("password cannot be EMPTY OR BLANK !");
//            }
////        } while (password.isEmpty() || password.isBlank());
//
////        do {
//            //System.out.print("Re-enter password to confirm: ");
//            confirmPassword = reConfirmPW.getText().toString();
//            if (!confirmPassword.equals(password)) {
//                //System.out.println("PASSWORDS DO NOT MATCH !");
//                reConfirmPW.clear();
//                passwordSignUp.clear();
//
//            }
//            if (!isUniqueName || !isUniqueEmail || password.isEmpty() || password.isBlank() || !confirmPassword.equals(password) {
//                URL url = new File("src/sample/resource/Error_toCreateUser.fxml").toURI().toURL();
//                Parent profileParent = FXMLLoader.load(url);
//                Scene profileScene = new Scene(profileParent);
//
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setScene(profileScene);
//                stage.setX(650);
//                stage.setY(300);
//            }
////        } while (!confirmPassword.equals(password));
//        } while (!isUniqueName || !isUniqueEmail || password.isEmpty() || password.isBlank() || !confirmPassword.equals(password));
//    }
    }
}
