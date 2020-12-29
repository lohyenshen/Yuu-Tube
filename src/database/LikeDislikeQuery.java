package database;

import app.User;
import app.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LikeDislikeQuery extends Query{
    public static int[] getLikesDislikesCount( int videoID ) throws Exception{
        // this method SUMS the likes Count and dislikes Count of a video based on videoID
        // return the result as an array
        // [0] = likesCount
        // [1] = dislikesCount

        Connection con = getConnection();
        Statement st = con.createStatement();

        String query   = "SELECT \n" +
                         "COALESCE(SUM(like_dislike = 1), 0) AS likes,\n" +
                         "COALESCE(SUM(like_dislike = 0), 0) AS dislikes\n" +
                         "FROM assignment.likedislike\n" +
                         "WHERE videoID = " + videoID + " ; ";
        ResultSet rs   = st.executeQuery( query );

        rs.next();
        int[] ld = new int[2];
        ld[0] = rs.getInt("likes");
        ld[1] = rs.getInt("dislikes");


        st.close();
        con.close();
        return ld;
    }
    public static boolean likedDisliked (int userID, int videoID) throws Exception{
        // this method checks whether or not has "user" (liked or disliked) "video"
        // return true if "user" (liked or disliked) "video" in the past
        // return false otherwise

        // userID, videoID, like_dislike
        Connection con = getConnection();
        Statement st   = con.createStatement();
        String query   = "SELECT COUNT(*) " + "\n" +
                         "FROM assignment.likedislike " + "\n" +
                         "WHERE     userID = "    + userID + "\n" +
                         "AND "+ " videoID = "    + videoID + "\n ;" ;

        ResultSet rs   = st.executeQuery( query );

        rs.next();
        int occ = rs.getInt( "COUNT(*)");

        st.close();
        con.close();
        return (occ == 1);
    }
    public static boolean getStatus( int userID, int videoID ) throws Exception {
        // this method is used after confirming "user" has either (liked or disliked) "video"
        //
        // returns true   if user "liked" video
        //         false  if user "disliked" video

        Connection con = getConnection();
        Statement st   = con.createStatement();
        String query   = "SELECT like_dislike " + "\n" +
                         "FROM assignment.likedislike " + "\n" +
                         "WHERE     userID = "    + userID + "\n" +
                         "AND "+ " videoID = "    + videoID + "\n ;" ;
        ResultSet rs   = st.executeQuery( query );

        rs.next();
        int status = rs.getInt("like_dislike");
        st.close();
        con.close();

        return (status == 1);
    }
    public static void insertNew( int userID , int videoID , int i) throws Exception{
        // this method insert a new record where "user" (likes or dislikes) "video"
        /**
         *  i   =  1 (like)
         *      =  0 (dislike)
         */

        String query = "INSERT INTO assignment.likedislike " +
                       "VALUES(?,?,?)";
        Connection con        = getConnection();
        PreparedStatement pst = con.prepareStatement(query);

        // userID, videoID, like_dislike
        pst.setString(1, Integer.toString( userID ) );
        pst.setString(2, Integer.toString( videoID ) );
        pst.setInt(3, i);
        pst.executeUpdate();

        pst.close();
        con.close();
    }
    public static void update( int userID, int videoID, int i) throws Exception{
        // this method is used after confirming "user" has either (liked or disliked) an "video"
        //                              but now the "user" changes from like to dislike
        //                                                         or
        //                                                         from dislike to like
        // this method updates the like_dislike (column) in likedislike(table)

        Connection con = getConnection();
        Statement st = con.createStatement();

        // userID, videoID, like_dislike
        String query =   "UPDATE assignment.likedislike\n" +
                         "SET like_dislike = " + i +   "\n" +
                         "WHERE     userID = "    + userID + "\n" +
                         "AND "+ " videoID = "    + videoID + "\n ;" ;

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void delete( int videoID ) throws Exception{
        // this methods deletes all records from "likedislike" table based on videoID

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query =   "DELETE \n" +
                         "FROM assignment.likedislike \n" +
                         "WHERE videoID = " + videoID +" ; ";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void deleteAcc( int userID ) throws Exception{
        // this methods deletes all records from "likedislike" table based on userID

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query =   "DELETE \n" +
                         "FROM assignment.likedislike \n" +
                         "WHERE userID = " + userID +" ; ";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
}
