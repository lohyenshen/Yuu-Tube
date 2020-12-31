package app;

import database.LikeDislikeQuery;
public class Video {
    private int videoID;                // unique, auto_increased, PRIMARY KEY in database
    private int userID;                 //                       , FOREIGN KEY in database that reference to user table's PRIMARY KEY

    private String title;
    private int likesCount;
    private int dislikesCount;
    private int viewsCount;
    private String[] comments;
    private String path;

    // constructor
    public Video(int videoID, int userID, String title, int likesCount, int dislikesCount, int viewsCount, String[] comments, String path) {
        this.videoID = videoID;
        this.userID = userID;
        this.title = title;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.viewsCount = viewsCount;
        this.comments = comments;
        this.path = path;
    }

    // getters
    public int getVideoID() {
        return videoID;
    }

    public int getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public String[] getComments() {
        return comments;
    }

    public String getPath() {
        return path;
    }

    public String toString(){
        // for fun, depends on GUI
        // videoID, userID, title, likesCount, dislikesCount, viewsCount,

        String details = String.format("%20d|%20d|%60s|%20d|%20d|%20d", videoID, userID, title, likesCount, dislikesCount, viewsCount);
        return details;
    }
    public static void printVideoHeader(){
        // for fun, depends on GUI

        String header  = String.format("%20s|%20s|%60s|%20s|%20s|%20s|\n", "videoID", "userID", "title", "likesCount", "dislikesCount", "viewsCount");
        String m = "";
        for (int i = 0; i < 166; i++)
            m += "-";
        m += "\n";

        System.out.print(m + header + m);
    }
    // methods
    public void printStatistics( User currentUser ) throws Exception{
        // this code is just for fun
        // actual implementation depends on GUI

        System.out.println();
        System.out.printf("%50s|%20s|%20s|%20s|\n", "Title", "Views Count", "Likes Count", "Dislikes Count" );
        for (int i = 0; i < 114; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("%50s|%20d|%20d|%20d|\n", title, viewsCount, likesCount, dislikesCount);


        System.out.println();
        if ( LikeDislikeQuery.likedDisliked( currentUser.getUserID() , this.videoID )){
            boolean status = LikeDislikeQuery.getStatus( currentUser.getUserID() , this.videoID);
            if (status){
                System.out.println("You LIKED this video before");
            }
            else{
                System.out.println("You DISLIKED this video before");
            }
        }

        System.out.println();
        System.out.println("Comments");
        for (int i = 0; i < 114; i++)
            System.out.print("-");
        System.out.println();
        // comments of new video is always initialized with ""
        // therefore we need to skip the first comment, comments[0]
        for (int i = 1; i < comments.length; i++) {
            System.out.println( (i) + ") " + comments[i] );
        }
        System.out.println();
    }
    public void printStatisticsWithoutLogin() throws Exception{
        // this code is just for fun
        // actual implementation depends on GUI

        System.out.println();
        System.out.printf("%50s|%20s|%20s|%20s|\n", "Title", "Views Count", "Likes Count", "Dislikes Count" );
        for (int i = 0; i < 114; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("%50s|%20d|%20d|%20d|\n", title, viewsCount, likesCount, dislikesCount);


        System.out.println();

        System.out.println();
        System.out.println("Comments");
        for (int i = 0; i < 114; i++)
            System.out.print("-");
        System.out.println();
        // comments of new video is always initialized with ""
        // therefore we need to skip the first comment, comments[0]
        for (int i = 1; i < comments.length; i++) {
            System.out.println( (i) + ") " + comments[i] );
        }
        System.out.println();
        System.out.println("You cannot LIKE/ DISLIKE/ COMMENT because you're not LOGGED IN");
    }
}