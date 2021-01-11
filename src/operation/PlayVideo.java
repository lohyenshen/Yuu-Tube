package operation;

import app.User;
import app.Video;
import database.LikeDislikeQuery;
import database.Query;
import database.VideoQuery;

import java.util.Scanner;

public class PlayVideo {
    private static Process player;
    private final static String vlcPath = "C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe"; // replace with path to vlc.exe

    public static void withLogin (User currentUser, Video currentVideo) throws Exception {

        // this method knows which "user" is playing which "video"

        // play the video using VLC player
        String path = System.getProperty("user.dir") + "\\videos\\" + currentVideo.getPath();
        ProcessBuilder pb = new ProcessBuilder(vlcPath, "", path);
        player = pb.start();

        // increase viewsCount of video in database
        Query.increase("video", "viewsCount", 1, "videoID", currentVideo.getVideoID());

        Scanner sc = new Scanner(System.in);
        do {
            currentVideo = VideoQuery.getVideo( currentVideo.getVideoID() );
            currentVideo.printStatistics( currentUser );

            System.out.println("Enter \'e\' to EXIT playing video ");
            System.out.println("Enter \'l\' to LIKE , \'d\' to DISLIKE :");
            System.out.print("Enter anything else         to comment: ");
            String op = sc.nextLine();

            if (op.isEmpty() || op.isBlank()) {
                System.out.println("EMPTY or BLANK operation is INVALID !");
            } else if (op.equals("e")) {
                break;
            } else if (op.equals("l") || op.equals("d")) {
                boolean ld = op.equals("l");
                likes_Dislikes_Operation(currentUser, currentVideo, ld);
            }
            else{
                    String[] oldComments = currentVideo.getComments();

                    // StringBuilder is faster than String concatenation
                    StringBuilder s = new StringBuilder();
                    for (String oldComment : oldComments) {
                        s.append(oldComment);
                        s.append("\n");
                    }
                    String newComments = s.toString() + op + "\n";

                    VideoQuery.updateComments(newComments, currentVideo.getVideoID());
                }
            } while (true) ;

            // stop playing
//            player.destroy();
    }
    public static void stopPlaying(){
        player.destroy();
    }
    public static void likes_Dislikes_Operation( User currentUser, Video currentVideo, boolean ld) throws Exception{
        // ld      =          likedislike  (to like   or to dislike  based on current user operation)
        // status  = database_likedislike  (    liked or    disliked based on data in database)
        //
        // ld      = true  (user wants to like)
        //         = false (user wants to dislike)
        // status  = true  (user liked the video before)
        //         = false (user disliked the video before)

        // user liked or disliked the video before
        if ( LikeDislikeQuery.likedDisliked( currentUser.getUserID() , currentVideo.getVideoID() ) ){
            /**
             * -------------------------------------------------------------------------------
             *           status    |       ld       |               |
             * -------------------------------------------------------------------------------
             *   database's status | user operation | operation     | database's supposed latest value
             * -------------------------------------------------------------------------------
             *            dislike  |       dislike  |               | dislike
             *            dislike  |          like  | change        | like
             *               like  |       dislike  | change        | dislike
             *               like  |          like  |               | like
             *
             *   XOR operation
             *                  0  |            0   | 0 (changes not required)
             *                  0  |            1   | 1 (changes required)
             *                  1  |            0   | 1 (changes required)
             *                  1  |            1   | 0 (changes not required)
             *
             */
            boolean status = LikeDislikeQuery.getStatus( currentUser.getUserID() , currentVideo.getVideoID() );
            if (!status && ld){
                // update dislike to like
                LikeDislikeQuery.update( currentUser.getUserID(), currentVideo.getVideoID(), 1);
            }
            else if (status && !ld){
                // update like to dislike
                LikeDislikeQuery.update( currentUser.getUserID(), currentVideo.getVideoID(), 0);
            }
        }
        // user HAS NOT liked nor disliked the video before
        else{
            // store like in database
            if (ld) {
                LikeDislikeQuery.insertNew( currentUser.getUserID() , currentVideo.getVideoID(), 1);
            }
            // store dislike in database
            else{
                LikeDislikeQuery.insertNew( currentUser.getUserID(), currentVideo.getVideoID(), 0);
            }
        }

        // update likes/dislikes count in video table
        VideoQuery.updateLikesDislikesCount( currentVideo.getVideoID() );
    }
    public static void withoutLogin (Video currentVideo) throws Exception {
        // this method plays a video WITHOUT user login
        // viewsCount of video will increase
        // cannot like or dislike or comment ( because user takda login)

        // play the video using VLC player
        String path = System.getProperty("user.dir") + "\\videos\\" + currentVideo.getPath();
        ProcessBuilder pb = new ProcessBuilder(vlcPath, "", path);
        Process player = pb.start();

        // increase viewsCount of video in database
        Query.increase("video", "viewsCount", 1, "videoID", currentVideo.getVideoID());

        Scanner sc = new Scanner(System.in);
        do {
            currentVideo = VideoQuery.getVideo( currentVideo.getVideoID() );
            currentVideo.printStatisticsWithoutLogin();

            System.out.print("Enter \'e\' to EXIT playing video :");
            String op = sc.nextLine();

            if (op.isEmpty() || op.isBlank())
                System.out.println("EMPTY or BLANK operation is INVALID !");
            else if (op.equals("e"))
                break;
            else
                System.out.println("INVALID OPERATION !");
        } while (true) ;

        // stop playing
        player.destroy();
    }
}

