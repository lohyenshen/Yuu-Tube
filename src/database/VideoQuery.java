package database;
import app.Video;

import java.sql.*;
public class VideoQuery  extends Query{
    public static Video getVideo( int videoID ) throws Exception{
        // associated with PlayVideo class, main method
        // obtain the LATEST details of a REQUIRED video from database latest(likesCount, dislikesCount, etc)
        // construct an return the Video object

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query = "SELECT * " +
                       "FROM assignment.video " +
                       "WHERE videoID = " + videoID + " ;";
        ResultSet rs = st.executeQuery( query );

        rs.next();
        int       a        = rs.getInt("videoID");
        int       b        = rs.getInt("userID");

        String    c        = rs.getString("title");
        int       d        = rs.getInt("likesCount");
        int       e        = rs.getInt("dislikesCount");
        int       f        = rs.getInt("viewsCount");
        String[]  g        = rs.getString("comments").split("\n");
        String    h        = rs.getString("path");

        st.close();
        con.close();

        return new Video(a,b,c,d,e,f,g,h);
    }
    public static Video[] getVideos() throws Exception{
        // return array of ALL videos in database

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query = "SELECT * FROM assignment.video";
        ResultSet rs = st.executeQuery( query );

        int L = getSizeOFResultSet( rs );
        Video[] videos = new Video[L];


        for (int i = 0; i < videos.length; i++) {
            rs.next();

            int       a        = rs.getInt("videoID");
            int       b        = rs.getInt("userID");

            String    c        = rs.getString("title");
            int       d        = rs.getInt("likesCount");
            int       e        = rs.getInt("dislikesCount");
            int       f        = rs.getInt("viewsCount");
            String[]  g        = rs.getString("comments").split("\n");
            String    h        = rs.getString("path");

            videos[i] = new Video(a,b,c,d,e,f,g,h);
        }
        st.close();
        con.close();

        return videos;
    }
    public static Video[] getVideos(int userID) throws Exception{
        // return array of Video of an user

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query = "SELECT * FROM assignment.video WHERE userID = "+ userID +";";
        ResultSet rs = st.executeQuery( query );

        int L = getSizeOFResultSet( rs );
        Video[] videos = new Video[L];


        for (int i = 0; i < videos.length; i++) {
            rs.next();

            int       a        = rs.getInt("videoID");
            int       b        = rs.getInt("userID");

            String    c        = rs.getString("title");
            int       d        = rs.getInt("likesCount");
            int       e        = rs.getInt("dislikesCount");
            int       f        = rs.getInt("viewsCount");
            String[]  g        = rs.getString("comments").split("\n");
            String    h        = rs.getString("path");

            videos[i] = new Video(a,b,c,d,e,f,g,h);
        }
        st.close();
        con.close();

        return videos;
    }
    public static void insertNew( Video video) throws Exception{
        // this method insert a new video to database

        //     comments, path
        String query = "INSERT INTO assignment.video VALUES(?,?,?,?,?,?,?,?)";
        Connection con = getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, Integer.toString( video.getVideoID() ) );
        pst.setString(2, Integer.toString( video.getUserID() ));
        pst.setString(3,                   video.getTitle());
        pst.setString(4, Integer.toString( video.getLikesCount() ) );
        pst.setString(5, Integer.toString( video.getDislikesCount() ) );
        pst.setString(6, Integer.toString( video.getViewsCount() ) );
        pst.setString(7,                   ""                      ); // a new video has no comment initially
        pst.setString(8,                   video.getPath());

        // insert video details  in database
        pst.executeUpdate();
        // increase user's videosCount in database
        Query.increase("user", "videosCount",  1, "userId", video.getUserID());

        pst.close();
        con.close();
    }
    public static Video[] getTrendingVideos() throws  Exception{
        // return an array of videos based on viewCounts (trendingVideos)

        Connection con = getConnection();
        Statement st   = con.createStatement();
        String query   = "SELECT * \n" +
                         "FROM assignment.video \n" +
                         "ORDER BY viewsCount DESC, likesCount DESC, title ASC\n" +
                         "LIMIT 5;";
        ResultSet rs   = st.executeQuery( query );

        int L = getSizeOFResultSet( rs );
        Video[] trendingVideos = new Video[L];
        for (int i = 0; i < L; i++) {
            rs.next();

            int       a        = rs.getInt("videoID");
            int       b        = rs.getInt("userID");

            String    c        = rs.getString("title");
            int       d        = rs.getInt("likesCount");
            int       e        = rs.getInt("dislikesCount");
            int       f        = rs.getInt("viewsCount");
            String[]  g        = rs.getString("comments").split("\n");
            String    h        = rs.getString("path");

            trendingVideos[i] = new Video(a,b,c,d,e,f,g,h);
        }
        st.close();
        con.close();

        return trendingVideos;
    }

    // update
    public static void updateComments( String newComments, int videoID) throws Exception{
        // update comments of a video

        Connection con = getConnection();
        Statement st = con.createStatement();

        String query =  "UPDATE assignment.video " + "\n" +
                        "SET comments = \"" + newComments + "\"\n"+
                        "WHERE videoID = " + videoID +" ;";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void updateLikesDislikesCount( int videoID ) throws Exception{
        // update the (likesCount column) and (dislikesCount column) of a video in database based on videoID

        Connection con = getConnection();
        Statement st = con.createStatement();
        int[] ld     = LikeDislikeQuery.getLikesDislikesCount( videoID );

        // videoID, userID, title, likesCount, dislikesCount, viewsCount, comments, path
        String query =  "UPDATE assignment.video\n" +
                        "SET \n" +
                        "likesCount =    " +ld[0] +" , \n" +
                        "dislikesCount = " +ld[1] +"  \n" +
                        "WHERE videoID = " + videoID + " ; ";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void delete( int videoID ) throws Exception{
        // this method deletes a record from "video" table based on (videoID)

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query =   "DELETE \n" +
                         "FROM assignment.video\n" +
                         "WHERE videoID = " + videoID +" ; ";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
}
