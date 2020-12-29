package operation;

import app.User;
import app.Video;
import database.LikeDislikeQuery;
import database.Query;
import database.UserQuery;
import database.VideoQuery;

import java.io.File;
import java.util.Scanner;

public class DeleteVideo {
    public static User main(User currentUser ) throws Exception{
        // this method allows currentUser to choose which video to delete
        Scanner sc = new Scanner(System.in);

        do{
            // always get the latest user details after each video deletion
            currentUser = UserQuery.getUser( currentUser.getUserID() );

            boolean validID = false;
            System.out.println();
            System.out.println("-----PROCEEDING TO DELETE VIDEOS(S)   -----");
            System.out.println( currentUser.toString() );
            System.out.println("-----LIST OF VIDEOS OWNED BY YOU      -----");
            Video.printVideoHeader();
            for (Video video : currentUser.getVideos())
                System.out.println( video.toString() );

            System.out.println("Enter  \'e\' to exit deleting videos");
            System.out.print("Enter videoID to DELETE VIDEO: ");
            String op = sc.nextLine();
            if (op.equals("e"))
                return currentUser;

            int id;
            if ( isInteger( op )){
                id = Integer.parseInt( op );
            }
            else{
                System.out.println("Enter a valid ID !");
                continue;
            }
            for (Video video : currentUser.getVideos()) {
                if (id == video.getVideoID()) {
                    validID = true;

                    // delete selected video from currentUser
                    delete( currentUser, video);
                    break;
                }
            }

            if (!validID)
                System.out.println("Enter a valid ID !");
        } while (true);

    }
    public static void delete( User currentUser, Video currentVideo ) throws Exception{

        // decrease user's videosCount in database
        Query.increase("user", "videosCount", "-", 1, "userId", currentUser.getUserID());
        // delete record from "likedislike" table
        LikeDislikeQuery.delete( currentVideo );
        // delete record from "video" table
        VideoQuery.delete( currentVideo );
        // delete video from directory
        File f = new File( System.getProperty("user.dir") + "\\videos\\" + currentVideo.getPath() );
        if ( f.delete() )
            System.out.println("Deleted \'" + currentVideo.getTitle() + "\' successfully");

        else
            System.out.println( currentVideo.getTitle() + " is not deleted");
    }
    public static boolean isInteger(String s){
        // verifies whether or not a String entered is a valid Integer

        int L = s.length();
        for (int i=0; i < L ; i++){
            if ( !Character.isDigit( s.charAt(i) ) ){
                return  false;
            }
        }
        return L > 0; // return false if L is 0 (empty string)
        // else, return true because it is (not an empty string) and (all its elements are digits)
    }
}
