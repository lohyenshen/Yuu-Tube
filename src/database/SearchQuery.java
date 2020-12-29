package database;

import app.User;
import app.Video;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SearchQuery extends Query{
    public static User[] searchUsers( String keyword, User currentUser ) throws Exception{
        // returns an array of User
        // based on searching (user's name) that matches/similar to the keyword
        // checks also whether "currentUser" has subscribed to the "users"

        String[] elements = keyword.split("\\s+");
        String regexp = elements[0];
        for (int i = 1; i < elements.length ; i++)
            regexp += "|" + elements[i];

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query =  "SELECT * \n" +
                        "FROM assignment.user\n" +
                        "WHERE name REGEXP \"" + regexp + "\"\n" +
                        "LIMIT 10;";
        ResultSet rs = st.executeQuery( query );

        int L = getSizeOFResultSet( rs );
        User[] users = new User[L];

        for (int i = 0; i < users.length; i++) {
            rs.next();

            int    a  = rs.getInt("userID");
            String b  = rs.getString("name");
            String c  = rs.getString("email");
            String d  = rs.getString("password");
            int    e  = rs.getInt("videosCount");
            int    f  = rs.getInt("subscribersCount");
            Video[] g = VideoQuery.getVideos( a );
            boolean h = SubscriberQuery.subscribedTo( currentUser.getUserID(), a );

            users[i] = new User(a, b, c, d, e, f, g, h);
        }
        st.close();
        con.close();

        return users;
    }
    public static Video[] searchVideos(String keyword) throws Exception{
        // returns an array of Video
        // based on searching (video's title) that matches/similar to the keyword

        String[] elements = keyword.split("\\s+");
        String regexp = elements[0];
        for (int i = 1; i < elements.length ; i++)
            regexp += "|" + elements[i];

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query =  "SELECT * \n" +
                        "FROM assignment.video\n" +
                        "WHERE title REGEXP \"" + regexp + "\"\n" +
                        "LIMIT 10;";
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
            String[]  g        = rs.getString("comments").split(",");
            String    h        = rs.getString("path");

            videos[i] = new Video(a,b,c,d,e,f,g,h);
        }
        st.close();
        con.close();

        return videos;
    }
}
