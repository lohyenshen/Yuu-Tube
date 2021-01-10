package app;
import database.*;

import java.io.File;
import java.util.Scanner;
public class User {
    private int userID;               // unique, auto_increased, PRIMARY KEY in database

    private String name;
    private String email;
    private String password;
    private int videosCount;
    private int subscribersCount;
    private Video[] videos;

    // for SearchQuery only
    private boolean subscribed;


    // constructor
    public User(int userID, String name, String email, String password, int videosCount, int subscribersCount, Video[] videos) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
        this.videos = videos;
    }
    // special constructor dedicated for SearchQuery
    public User(int userID, String name, String email, String password, int videosCount, int subscribersCount, Video[] videos, boolean subscribed) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.videosCount = videosCount;
        this.subscribersCount = subscribersCount;
        this.videos = videos;
        this.subscribed = subscribed;
    }
    // getters
    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getVideosCount() {
        return videosCount;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public Video[] getVideos() {
        return videos;
    }

    public boolean getSubscribed() {
        return subscribed;
    }

    // setters


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString
    public String toString(){
        // for fun, depends on GUI

        // userID, name, email, password, videosCount, subscribersCount
        String header  = String.format("%20s|%20s|%20s|%20s|%20s|%20s|\n", "User ID", "Name", "Email", "Password", "Videos Count", "Subscribers Count");
        String details = String.format("%20d|%20s|%20s|%20s|%20d|%20d|\n", userID, name, email, password, videosCount, subscribersCount);
        String m = "";
        for (int i = 0; i < 126; i++)
            m += "-";
        m += "\n";

        return m+ header + m + details;
    }

    // methods
    public static void register() throws Exception{
        // obtain required details of a new user

        System.out.println("\nCreating new user account..... Please enter your credentials as below\n");

        User uniqueUser = createUniqueUser();
        UserQuery.insertNew( uniqueUser );

        // create a directory to store all videos by this user in the future
        File f = new File( System.getProperty("user.dir") + "\\videos\\" + uniqueUser.getName() );
        if (f.mkdir()) {
            System.out.println("-----Your directory to store video(s) created successfully-----");
            System.out.println("-----Account created successfully -----");
            System.out.println("-----Please login now          (A)-----");
        }
        else{
            System.out.println("Your directory to store video(s) is NOT CREATED");
            System.out.println("-----Account NOT CREATED -----");
        }
    }
    private static User createUniqueUser() throws Exception{
        // this method ensures all new users are unique (name, email) are not duplicated
        // by comparing (entered details) with (details extracted from database0

        Scanner sc = new Scanner(System.in);
        User[] users = UserQuery.getUsers();

        String name;
        boolean isUniqueName;
        do{
            isUniqueName = true;
            System.out.print("Enter name: ");
            name = sc.nextLine();

            if ( name.isEmpty() ){
                System.out.println("NAME CANNOT BE EMPTY! ");
                isUniqueName = false;
                continue;
            }
            for (User user : users) {
                if (name.equals(user.name)) {
                    System.out.println("THIS NAME IS TAKEN! ");
                    isUniqueName = false;
                    break;
                }
            }
        } while(!isUniqueName);

        String email;
        boolean isUniqueEmail;
        do {
            isUniqueEmail = true;
            System.out.print("Enter email: ");
            email = sc.nextLine();

            if ( !email.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$")){
                System.out.println("INVALID EMAIL ! ");
                isUniqueEmail = false;
                continue;
            }
            for (User user : users) {
                if (email.equals(user.email)) {
                    System.out.println("THIS EMAIL IS TAKEN! ");
                    isUniqueEmail = false;
                    break;
                }
            }
        } while (!isUniqueEmail) ;


        String password;
        do{
            System.out.print("Enter password: ");
            password = sc.nextLine();
            if (password.isEmpty() || password.isBlank())
                System.out.println("password cannot be EMPTY OR BLANK !");
        } while (password.isEmpty() || password.isBlank());

        String confirmPassword;
        do {
            System.out.print("Re-enter password to confirm: ");
            confirmPassword = sc.nextLine();
            if (!confirmPassword.equals(password))
                System.out.println("PASSWORDS DO NOT MATCH !");
        } while (!confirmPassword.equals(password));


        // all new users has 0 videosCount, 0 subscribersCount, videos of null
        // userID's ACTUAL VALUE will be handled by MySQL auto increment feature
        return new User(0,  name, email, password, 0,0,null);
    }
    public static User login() throws Exception{
        // verifies a user's login by checking (credentials entered) with (credentials extracted from database)

        Scanner sc = new Scanner(System.in);
        boolean userExist = false;
        User currentUser = null;

        System.out.print("Enter email : ");
        String emailEntered = sc.nextLine();
        System.out.print("Enter password: ");
        String passwordEntered = sc.nextLine();

        User[] users = UserQuery.getUsers();

        for (User user : users) {
            if (emailEntered.equals(user.email)) {                  // email    in database
                if (passwordEntered.equals(user.password)) {        // password in database
                    currentUser = user;
                    userExist = true;
                    break;
                }
            }
        }

        if (userExist){
            System.out.println("-----You are now signed in ------");
            System.out.printf("-----Welcome back %s ! -----\n", currentUser.name );
        }
        else{
            System.out.println("Wrong credentials entered, please retry to Login (A) ");
        }

        return currentUser;
    }
    public void changeEmail() throws Exception{
        Scanner sc = new Scanner(System.in);
        User[] users = UserQuery.getUsers();

        System.out.println("-----CHANGING TO NEW EMAIL   -----");
        String oldEmail = email;
        String newEmail;
        boolean isUniqueEmail;
        do {
            isUniqueEmail = true;
            System.out.print("Enter NEW email: ");
            newEmail = sc.nextLine();

            if ( oldEmail.equals(newEmail)){
                System.out.println("New email CANNOT BE THE SAME as current email !");
                isUniqueEmail = false;
                continue;
            }
            if ( !newEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$")){
                System.out.println("INVALID EMAIL ! ");
                isUniqueEmail = false;
                continue;
            }
            for (User user : users) {
                if (newEmail.equals(user.email)) {
                    System.out.println("THIS EMAIL IS TAKEN! ");
                    isUniqueEmail = false;
                    break;
                }
            }
        } while (!isUniqueEmail) ;

        email = newEmail;
        UserQuery.changeEmail( this );
        System.out.println("-----Email changed successfully   -----");
        System.out.println("-----Please login again          (A)-----");
    }
    public void changePassword() throws Exception{
        Scanner sc = new Scanner(System.in);
        User[] users = UserQuery.getUsers();

        System.out.println("-----CHANGING TO NEW PASSWORD   -----");
        String oldPassword = password;
        String newPassword;
        do{
            System.out.print("Enter NEW password: ");
            newPassword = sc.nextLine();

            if (newPassword.equals(oldPassword))
                System.out.println("New password CANNOT BE THE SAME as current password !");
            if (newPassword.isEmpty() || newPassword.isBlank())
                System.out.println("NEW password cannot be EMPTY OR BLANK !");
        } while (newPassword.isEmpty() || newPassword.isBlank() || newPassword.equals(oldPassword));


        password = newPassword;
        UserQuery.changePassword( this );
        System.out.println("-----Password changed successfully   -----");
        System.out.println("-----Please login again          (A)-----");
    }
}
