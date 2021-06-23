package app;

import database.*;
import operation.*;

import java.util.Scanner;

public class MainPage {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        User currentUser = null;
        int i;

        while (true) {
            Video[] trendingVideos = VideoQuery.getTrendingVideos();
            printHeader( trendingVideos );
            char op = sc.nextLine().charAt(0);
            boolean validOperation = true;


            if (op == 'e'){
                break;
            } else if(op == 'L'){
                currentUser = null;
                System.out.println("-----LOG OUT successfully -----");
            } else if ( Character.isDigit( op ) ){
                i = Character.getNumericValue( op ) - 1;
                if (i>=0 && i<trendingVideos.length)
                    ; // validOperation remain as true

                else
                    validOperation = false;


                if ( validOperation ){
                    if ( currentUser == null )
                        PlayVideo.withoutLogin( trendingVideos[i] );
                    else
                        PlayVideo.withLogin( currentUser , trendingVideos[i] );
                }
            } else if (op == 'A') {
                currentUser = null;
                currentUser = User.login();
            } else if (op == 'B') {
                User.register();
            } else if (op == 'C') {
                if ( currentUser == null){
                    System.out.println("-----Please login before proceeding to SEARCH -----");
                    System.out.println("-----Login now          (A)-----");
                }
                else{
                    Search.main( currentUser );
                }
            } else if (op == 'D'){
                if ( currentUser == null){
                    System.out.println("-----Please login before proceeding to UPLOAD VIDEO -----");
                    System.out.println("-----Login now          (A)-----");
                }
                else{
                    UploadVideo.main( currentUser );
                }
            } else if (op == 'E'){
                if ( currentUser == null){
                    System.out.println("-----Please login before proceeding to CHANGING EMAIL -----");
                    System.out.println("-----Login now          (A)-----");
                }
                else{
                    currentUser.changeEmail();
                    currentUser = null;
                }
            } else if (op == 'F'){
                if ( currentUser == null){
                    System.out.println("-----Please login before proceeding to CHANGING PASSWORD -----");
                    System.out.println("-----Login now          (A)-----");
                }
                else{
                    currentUser.changePassword();
                    currentUser = null;
                }
            } else if (op == 'G'){
                if ( currentUser == null){
                    System.out.println("-----Please login before proceeding to DELETE VIDEO(S) -----");
                    System.out.println("-----Login now          (A)-----");
                }
                else{
                    currentUser = DeleteVideo.main( currentUser );
                }
            } else if (op == 'H'){
                if ( currentUser == null){
                    System.out.println("-----Please login before proceeding to DELETE ACCOUNT -----");
                    System.out.println("-----Login now          (A)-----");
                }
                else{
                    System.out.println("Are you sure about DELETING your account?");
                    System.out.println("All data associated with this account will be DELETED !");
                    System.out.print("Enter \'yes\' to proceed to ACCOUNT DELETION: ");
                    String s = sc.nextLine();

                    if (s.equals("yes")){
                        DeleteAccount.main( currentUser );
                        System.out.println("-----Account DELETED successfully-----");
                        currentUser = null;
                    }
                    else{
                        System.out.println("Account NOT DELETED");
                    }
                }
            }
            else {
                validOperation = false;
            }

            // operation validity will be determined by return value from method
            if (!validOperation)
                System.out.println("\nINVALID OPERATION !\nPLEASE ENTER AN VALID OPERATION!");
        }
        System.out.println();
        System.out.println("-----LOG OUT successfully -----");
        System.out.println("Exiting from Yuu-Tube.....");
        System.out.println("Exit done. ");
    }

    public static void printHeader( Video[] trendingVideos ){
        System.out.println();
        System.out.println("-----Welcome to Yuu-Tube v1.0 ! -----");
        System.out.printf("---------------Top %d trending videos------------------\n", trendingVideos.length);
        System.out.println("                                           Views Count");
        for (int i = 0; i < trendingVideos.length ; i++) {
            System.out.printf("%d) %-50s%-5d\n" , (i+1), trendingVideos[i].getTitle(), trendingVideos[i].getViewsCount() );
        }
        System.out.println();
        /*System.out.println("\n\n\n\n\n");
        System.out.println("A. Login");
        System.out.println("B. Register");
        System.out.println("C. Search");
        System.out.println("D. Upload Video");
        System.out.println("E. Change Email");
        System.out.println("F. Change Password");
        System.out.println("G. Delete Video(s)");
        System.out.println("H. Delete Account");
        System.out.println("L. Log out");
        System.out.println();*/
        System.out.println("--------------------------------------------------");
        System.out.println("To exit Yuu-Tube        Please enter \"e\" ");
        System.out.print("What are you up to now? Please select  [1-5][A-L]: ");
    }
}
