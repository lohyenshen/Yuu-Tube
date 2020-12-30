package operation;

import app.User;
import app.Video;
import database.LikeDislikeQuery;
import database.SubscriberQuery;
import database.UserQuery;
import database.VideoQuery;

import java.io.File;

public class DeleteAccount {
    public static void main( User currentUser) throws Exception{
        System.out.println();
        System.out.println("-----PROCEEDING TO DELETE ACCOUNT   -----");
        System.out.println( currentUser.toString() );
        System.out.println("-----LIST OF VIDEOS OWNED BY YOU      -----");
        Video.printVideoHeader();
        for (Video video : currentUser.getVideos())
            System.out.println( video.toString() );

        /**
         * PHASE 1
         *      deletes all records in                   "subscriber" table that contains currentUser's ID
         *      updates subscribersCount of all users in "user" table
         */
        SubscriberQuery.deleteAcc( currentUser.getUserID() );
        User[] users = UserQuery.getUsers();
        for (User user : users)
            UserQuery.updateSubscribersCount( user.getUserID() );

        /**
         * PHASE 2
         *      deletes all records in                             "likedislike" table that contains currentUser's ID
         *      updates likesCount, dislikesCount of all videos in "video" table
         */
        LikeDislikeQuery.deleteAcc( currentUser.getUserID() );
        Video[] videos = VideoQuery.getVideos();
        for (Video video : videos)
            VideoQuery.updateLikesDislikesCount( video.getVideoID() );

        /**
         * PHASE 3
         *      deletes all videos in "video" table       uploaded by currentUser
         *      deletes all videos in DIRECTORY (videos)  uploaded by currentUser
         */
        for (Video video : currentUser.getVideos()){
            // delete a record from "video" table
            VideoQuery.delete( video.getVideoID()  );
            // delete video from directory
            DeleteVideo.fromDirectory( video );
        }

        /**
         * PHASE 4
         *      delete a record in "user" table based on currentUser's ID
         *      delete the user's directory
         */
        UserQuery.deleteAcc( currentUser.getUserID() );
        File f = new File( System.getProperty("user.dir") + "\\videos\\" + currentUser.getName() );
        if (f.delete())
            System.out.println("Your directory to store video(s) DELETED successfully");
        else
            System.out.println("Your directory to store video(s) IS NOT DELETED");
    }
}
