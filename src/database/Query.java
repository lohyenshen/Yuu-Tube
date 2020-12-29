package database;

import app.Video;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Query {
    public static Connection getConnection() throws Exception{
        String url = "jdbc:mysql://localhost:3306/assignment?autoReconnect=true&useSSL=false";
        String uname = "root";
        String mysqlPassword = "wix1002";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection( url , uname , mysqlPassword);
        return con;
    }
    public static int getSizeOFResultSet(ResultSet rs){
        int size = 0;
        try{
            if (rs != null){
                rs.last();          // moves cursor user_details table's last row
                size = rs.getRow(); // get last row id which is equal to the length
            }
            // move cursor to the default position (before first row) after getting the length!
            rs.beforeFirst();
            return size;
        }
        catch(Exception e){
            return size;
        }
    }
    public static void increase(String table, String column, String operation, int value, String conColumn, int conValue) throws Exception{
        Connection con = getConnection();
        Statement st = con.createStatement();

        String query =  "UPDATE assignment."+ table +"\n" +
                "SET "+ column +" = "+column + operation + " "+ value +" "+"\n" +
                "WHERE " + conColumn + " = " + conValue +" ;";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
}
