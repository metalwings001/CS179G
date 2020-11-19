import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.IOException;

/**
 *
 * @author postgresqltutorial.com
 */
public class App{

    private final String url = "jdbc:postgresql://localhost:5000/postgres";
    private final String user = "postgres";
    private final String password = "1234!!";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        App app = new App();
        Connection conn;
        try {
            conn = app.connect();
            Statement stmt; 
            ResultSet rs;
            String query;
            query = "SELECT * FROM video";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                int v_id = rs.getInt("video_id");
                String title = rs.getString("video_title");
                System.out.println( "Video title: " + title );
                System.out.println( "V_ID = " + v_id );
            }
        } catch(Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }       
      String command = "C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe";
      String arg = "C:\\Users\\Justin\\Desktop\\data\\mp4\\dog.mp4";
      //Building a process
      ProcessBuilder builder = new ProcessBuilder(command, arg);
      System.out.println("Executing the external program . . . . . . . .");
      //Starting the processs
      builder.start();
    }
}