package database;
import app.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SubscriberQuery extends Query{
    public static boolean subscribedTo( int a, int b ) throws Exception{
        // this method check whether or not is "a" subscribed to "b"
        // if a had subscribed to b already, return true
        // if a has not subscribed to b, return false

        Connection con = getConnection();
        Statement st   = con.createStatement();
        String query   = "SELECT COUNT(*)\n" +
                         "FROM assignment.subscriber\n" +
                         "WHERE subscriberID = "+ a +" AND "+ "userID = " + b +";";
        ResultSet rs   = st.executeQuery( query );

        rs.next();
        int occ = rs.getInt( "COUNT(*)");
//        System.out.println(occ);

        st.close();
        con.close();
        return (occ == 1);
    }
    public static void insertNew(int a , int b) throws Exception{
        // insert a new record where "a" subscribers to "b"

        // subscriberID, userID
        String query = "INSERT INTO assignment.subscriber " +
                       "VALUES(?,?)";
        Connection con        = getConnection();
        PreparedStatement pst = con.prepareStatement(query);

        pst.setInt(1,    a);
        pst.setInt(2,    b);
        pst.executeUpdate();

        pst.close();
        con.close();
    }
    public static void delete(int a , int b) throws Exception{
        // delete the record where "a" subscribers to "b"

        Connection con = getConnection();
        Statement st = con.createStatement();

        // subscriberID, userID
        String query =  "DELETE\n" +
                        "FROM assignment.subscriber \n" +
                        "WHERE subscriberID = " +a+ " AND "+" userID = "+b;

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void deleteAcc( int a ) throws Exception{
        // deletes all records in "subscriber" table that contains currentUser's ID
        // a user can be (a subscriber) or (a channel) or (a subscriber and a channel at the same time)

        Connection con = getConnection();
        Statement st = con.createStatement();

        // subscriberID, userID
        String query =  "DELETE\n" +
                        "FROM assignment.subscriber \n" +
                        "WHERE subscriberID = " +a+ " OR "+" userID = "+a;

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static int getSubscribersCount( int userID ) throws Exception{
        // return subscribersCount based on a user's ID

        Connection con = getConnection();
        Statement st   = con.createStatement();
        String query   = "SELECT COUNT(*)\n" +
                         "FROM assignment.subscriber\n" +
                         "WHERE userID = " + userID + " ; ";
        ResultSet rs   = st.executeQuery( query );

        rs.next();
        int occ = rs.getInt( "COUNT(*)");

        st.close();
        con.close();
        return occ;
    }
}
