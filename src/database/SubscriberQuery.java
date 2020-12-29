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
    public static void insertNew(User a , User b) throws Exception{
        // insert a new record where "a" subscribers to "b"

        String query = "INSERT INTO assignment.subscriber " +
                "VALUES(?,?)";
        Connection con        = getConnection();
        PreparedStatement pst = con.prepareStatement(query);

        // userID, name, email, password, videosCount, subscribersCount
        pst.setString(1, Integer.toString(a.getUserID()));
        pst.setString(2, Integer.toString(b.getUserID()));
        pst.executeUpdate();

        pst.close();
        con.close();
    }
}
