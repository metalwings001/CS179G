package com.postgresqltutorial;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.*;

/**
 *
 * @author postgresqltutorial.com
 */
public class App{

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "32aLs67!";
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

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
    
    public static void test1() {
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
    }
    
    public static void test2(){
        App app = new App();
        Connection conn;
        try{
            conn = app.connect();
            Statement stmt; 
            ResultSet rs;
            String query;
            
            query = "SELECT * FROM Comments";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                int c_id = rs.getInt("comment_id");
                String comment_content = rs.getString("comment_content");
                System.out.println( "Comment content: " + comment_content );
                System.out.println( "V_ID = " + c_id );
            }            
            
        } catch(Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);           
        }
        
    }
    
    public static void AddVideo() {
        App app = new App();
        Connection conn;
        
        try{
            conn = app.connect();
            Statement stmt;
            ResultSet rs;
            String query, data;
            
            //data = sc.nextLine();
            
            query = "INSERT INTO video (video_id, account_id, num_comments, publication_date, description, views, video_title, likes, dislikes, video_length, tags)\nVALUES";
            System.out.print("Enter video id:\n");
            data = in.readLine() + ",";
            System.out.print("Enter account id:\n");
            data += in.readLine() + ",";
            System.out.print("Enter num_comments:\n");
            data += in.readLine() + ",";
            System.out.print("Enter publication_date(Example format: 11/28/2009:15:00):\n");
            data += in.readLine() + ",";
            System.out.print("Enter description:\n");
            data += in.readLine() + ",";      
            System.out.print("Enter views:\n");
            data += in.readLine() + ",";    
            System.out.print("Enter video_title:\n");
            data += in.readLine() + ",";    
            System.out.print("Enter likes:\n");
            data += in.readLine() + ",";    
            System.out.print("Enter dislikes:\n");
            data += in.readLine() + ",";  
            System.out.print("Enter video_length:\n");
            data += in.readLine() + ",";  
            System.out.print("Enter tags:\n");
            data += in.readLine() + ",";  
            
            System.out.print("Data so far: " + data);
            
        } catch(Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);              
        }
    }
 
    public static void AddComment() {
        App app = new App();
        Connection conn;
        
        try{
            conn = app.connect();
            Statement stmt;
            ResultSet rs;
            String query, data;
            
            //data = sc.nextLine();
            
            query = "INSERT INTO comments (comment_id, account_id, video_id, comment_content)\nVALUES";
            System.out.print("Enter comment id:\n");
            data = in.readLine() + ",";
            System.out.print("Enter account id:\n");
            data += in.readLine() + ",";
            System.out.print("Enter video id:\n");
            data += in.readLine() + ",";
            System.out.print("Enter comment content:\n");
            data += in.readLine() + ",";
            
            System.out.print("Data so far: " + data);
            
        } catch(Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);              
        }
    }    
    
        public static void ListMostPopularVideos(){ //13
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;

                query = "SELECT * FROM video ORDER BY views DESC";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                System.out.println("Most viewed videos:");

                while(rs.next()) {
                    cnt++;
                    String title = rs.getString("video_title");
                    System.out.println( cnt + ".) " + title );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

        public static void ListMostPopularChannels(){ //14
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;

                query = "SELECT SUM(v.views) FROM video v, account a WHERE a.account_id = v.account_id GROUP BY v.views";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                System.out.println("Most viewed users:");

                while(rs.next()) {
                    cnt++;
                    int account_id = rs.getInt("account_id");
                    int views = rs.getInt("views");
                    System.out.println( cnt + ".) " + account_id + ": " + views );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }
        
        public static void ListMostPopularSubscriptions(){ //15
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;

                query = "SELECT * FROM account ORDER BY subcount DESC";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                System.out.println("Most subscribed accounts:");

                while(rs.next()) {
                    cnt++;
                    String username = rs.getString("username");
                    int subcount = rs.getInt("subcount");
                    System.out.println( cnt + ".) " + username + ": " + subcount + " subscribers"  );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }            
    
    public static void main(String[] args) {
               
        while(true){
            System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("0. Test");
            System.out.println("1. Upload Video"); //add video to database
            System.out.println("2. Share Video");
            System.out.println("3. View Video"); //add video to history
            System.out.println("4. Delete Video");
            System.out.println("5. Add Comment");           
            System.out.println("6. View Comment"); //on specific video            
            System.out.println("7. Search Video Title");
            System.out.println("8. Search Video Keyword"); //category
            System.out.println("9. Search Video Rating");
            System.out.println("10. Search Video Publication Date");
            System.out.println("11. Search Video Owner");
            System.out.println("12. List Video Recommendations"); //by category then by views (KNN implementation?)           
            System.out.println("13. List Most Popular Videos");
            System.out.println("14. List Most Popular Channels"); //by total viewcount
            System.out.println("15. List Most Popular Subscriptions"); //by total subcount            
            System.out.println("16. Exit"); //or use preferences for recommended
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if(choice == 1){
                AddVideo();
            }
            else if(choice == 4){
                AddComment();
            }
            else if(choice == 13){
                ListMostPopularVideos();
            }       
            else if(choice == 14){
                ListMostPopularChannels();
            }
            else if(choice == 15){
                ListMostPopularSubscriptions();
            }
            else if(choice == 16){
                System.out.println("Sayonara!");                
                break;                
            }
            else if(choice == 42){
                test1();                
            }
            else if(choice == 43){
                test2();                
            }
        }
    }
}
