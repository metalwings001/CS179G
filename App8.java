package com.postgresqltutorial;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
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
    
    public static void AddVideo() { //1 (Upload Video)
        App app = new App();
        Connection conn;
        
        try{
            conn = app.connect();
            Statement stmt;
            PreparedStatement pst;
            ResultSet rs;
            String query, insertQuery;
            Scanner sc = new Scanner(System.in); //int
            Scanner sc2 = new Scanner(System.in); //String
            
            query = "SELECT * FROM video";            
            insertQuery = "INSERT INTO video(video_id, account_id, num_comments, publication_date, description, views, video_title, likes, dislikes, video_length, tags) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            pst = conn.prepareStatement(insertQuery);         
            
            int add_video_id, add_account_id, add_num_comments, add_views, add_likes, add_dislikes, add_video_length;
            String add_publication_date, add_description, add_video_title, add_tags;

            System.out.println("Enter a new video id (Must be UNIQUE): ");    
            add_video_id = sc.nextInt();
            System.out.println("Enter a new account id (Must already EXIST in database): ");    
            add_account_id = sc.nextInt();
            System.out.println("Enter a new num comments: ");    
            add_num_comments = sc.nextInt();
            System.out.println("Enter a new publication date: ");    
            add_publication_date = sc2.nextLine();
            System.out.println("Enter a new description: ");    
            add_description = sc2.nextLine();
            System.out.println("Enter a new views: ");    
            add_views = sc.nextInt();
            System.out.println("Enter a new video title: ");    
            add_video_title = sc2.nextLine();
            System.out.println("Enter a new likes: ");    
            add_likes = sc.nextInt();
            System.out.println("Enter a new dislikes: ");    
            add_dislikes = sc.nextInt();
            System.out.println("Enter a new video length: ");    
            add_video_length = sc.nextInt();
            System.out.println("Enter a new tags: ");    
            add_tags = sc2.nextLine();

            pst.setInt(1, add_video_id);
            pst.setInt(2, add_account_id); 
            pst.setInt(3, add_num_comments);   
            pst.setString(4, add_publication_date); 
            pst.setString(5, add_description);
            pst.setInt(6, add_views);
            pst.setString(7, add_video_title);
            pst.setInt(8, add_likes);
            pst.setInt(9, add_dislikes);
            pst.setInt(10, add_video_length);
            pst.setString(11, add_tags);
            
            pst.executeUpdate();

            System.out.println("Table updated! Need to check on pgadmin or use the ListAllVideos() function");
            
        } catch(Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);              
        }
    }

    public static void ShareVideo() { //2
            App app = new App();
            Connection conn;

            try{
                conn = app.connect();
                Statement stmt;
                PreparedStatement pst;
                ResultSet rs;
                String query, deleteQuery;
                Scanner sc = new Scanner(System.in); //int
                
                int remove_video_id;

                System.out.println("Enter a video to delete by video id (Must not EXIST in other tables): ");   
                remove_video_id = sc.nextInt();
                
                query = "SELECT * FROM video";            
                deleteQuery = "DELETE FROM video WHERE video_id = " + remove_video_id;

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                pst = conn.prepareStatement(deleteQuery);         

                pst.executeUpdate();

                System.out.println("Table updated! Need to check on pgadmin or use the ViewVideo() function");

            } catch(Exception e) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);              
            }
        }
    
    public static void ViewVideo() { //3
            App app = new App();
            Connection conn;

            try{
                conn = app.connect();
                Statement stmt;
                PreparedStatement pst;
                ResultSet rs;
                String query, deleteQuery;
                Scanner sc = new Scanner(System.in); //int
                
                int remove_video_id;

                System.out.println("Enter a video to delete by video id (Must not EXIST in other tables): ");   
                remove_video_id = sc.nextInt();
                
                query = "SELECT * FROM video";            
                deleteQuery = "DELETE FROM video WHERE video_id = " + remove_video_id;

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                pst = conn.prepareStatement(deleteQuery);         

                pst.executeUpdate();

                System.out.println("Table updated! Need to check on pgadmin or use the ViewVideo() function");

            } catch(Exception e) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);              
            }
        }
    
    public static void DeleteVideo() { //4
            App app = new App();
            Connection conn;

            try{
                conn = app.connect();
                Statement stmt;
                PreparedStatement pst;
                ResultSet rs;
                String query, deleteQuery;
                Scanner sc = new Scanner(System.in); //int
                
                int remove_video_id;

                System.out.println("Enter a video to delete by video id (Must not EXIST in other tables): ");   
                remove_video_id = sc.nextInt();
                
                query = "SELECT * FROM video";            
                deleteQuery = "DELETE FROM video WHERE video_id = " + remove_video_id;

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                pst = conn.prepareStatement(deleteQuery);         

                pst.executeUpdate();

                System.out.println("Table updated! Need to check on pgadmin or use the ListAllVideos() function");

            } catch(Exception e) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);              
            }
        }    
    
    public static void AddComment() { //5
        App app = new App();
        Connection conn;
        
        try{
            conn = app.connect();
            Statement stmt;
            PreparedStatement pst;
            ResultSet rs;
            String query, insertQuery;
            Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            
            query = "SELECT * FROM comments";
            insertQuery = "INSERT INTO comments(comment_id, account_id, video_id, comment_content) VALUES (?,?,?,?)";
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            pst = conn.prepareStatement(insertQuery);
              
            int add_comment_id, add_account_id, add_video_id;
            String add_comment_content;
         
            System.out.println("Enter a new comment id (Must be UNIQUE): ");    
            add_comment_id = sc.nextInt();
            System.out.println("Enter a new account id (Must already EXIST in database): ");                
            add_account_id = sc.nextInt();
            System.out.println("Enter a new video id (Must already EXIST in database): ");                
            add_video_id = sc.nextInt();
            System.out.println("Enter new comment content: ");                
            add_comment_content = sc2.nextLine(); 

            pst.setInt(1, add_comment_id);
            pst.setInt(2, add_account_id); 
            pst.setInt(3, add_video_id);   
            pst.setString(4, add_comment_content); 
            
            pst.executeUpdate();

            System.out.println("Table updated! Need to check on pgadmin or use the ListAllComments() function");
            
        } catch(Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);              
        }
    }    

            public static void ViewComment(){ //6
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;
                Scanner sc = new Scanner(System.in);    
                
                System.out.println("Enter a comment id to search: ");
                
                int search_comment_id = sc.nextInt();
                
                
                query = "SELECT * FROM comments WHERE comment_id = " + "\'" + search_comment_id + "\'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {
                    String comment_content = rs.getString("comment_content");
                    int comment_id = rs.getInt("comment_id");
                    System.out.println(  "Comment id: " + comment_id + " - " + comment_content );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }
    
            public static void SearchVideoTitle(){ //7
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;
                Scanner sc = new Scanner(System.in);    
                
                System.out.println("Enter a video title to search: ");
                
                String search_video_title = sc.nextLine();
                
                
                query = "SELECT * FROM video WHERE video_title = " + "\'" + search_video_title + "\'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {
                    cnt++;
                    String video_title = rs.getString("video_title");
                    int views = rs.getInt("views");
                    System.out.println( cnt + ".) " + video_title + ": " + views + " views"  );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

            public static void SearchVideoKeyword(){ //8
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;
                Scanner sc = new Scanner(System.in);    
                
                System.out.println("Enter a keyword/tag to search: ");
                
                String search_video_keyword = sc.nextLine();
                
                
                query = "SELECT * FROM video WHERE tags = " + "\'" + search_video_keyword + "\'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {
                    cnt++;
                    String video_title = rs.getString("video_title");
                    String tags = rs.getString("tags");
                    System.out.println( cnt + ".) " + video_title + ": " + tags + " category"  );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

            public static void SearchVideoRating(){ //9
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;
                Scanner sc = new Scanner(System.in);    
                
                System.out.println("Enter a video rating to search: ");
                
//                int search_video_likes = sc.nextInt();
//                int search_video_dislikes = sc.nextInt();      
//                float rating = Float.intBitsToFloat(search_video_likes/(search_video_likes + search_video_dislikes));
                
                query = "SELECT * FROM video";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {
                    cnt++;
                    String video_title = rs.getString("video_title");
                    int likes = rs.getInt("likes");
                    int dislikes = rs.getInt("dislikes");
                    float rating = (likes/(likes + dislikes));
                    System.out.println( cnt + ".) " + video_title + " - rating: " + rating  );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

            public static void SearchVideoPublicationDate(){ //10
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;
                Scanner sc = new Scanner(System.in);    
                
                System.out.println("Enter a video publication date to search: ");
                
                String search_video_publication_date = sc.nextLine();
                
                
                query = "SELECT * FROM video WHERE publication_date = " + "\'" + search_video_publication_date + "\'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {
                    cnt++;
                    String video_title = rs.getString("video_title");
                    String publication_date = rs.getString("publication_date");
                    System.out.println( cnt + ".) " + video_title + " - Published on: " + publication_date );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

            public static void SearchVideoOwner(){ //11
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;
                Scanner sc = new Scanner(System.in);    
                
                System.out.println("Enter an account username search: ");
                
                String search_username = sc.nextLine();
                
                
                query = "SELECT * FROM account WHERE username = " + "\'" + search_username + "\'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next()) {
                    cnt++;
                    String username = rs.getString("username");
                    int subcount = rs.getInt("subcount");
                    int num_videos = rs.getInt("num_videos");
                    System.out.println( cnt + ".) " + username + " - " + subcount + " subscribers, " + num_videos + " videos" );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }
            
        public static void ListVideoRecommendations(){ //12
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

                query = "FIX";

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

        public static void ListAllAccounts(){ //16
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;

                query = "SELECT * FROM account";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                System.out.println("Showing all accounts in database:");
                System.out.println("account_id | subcount | num_videos | username | password");
                
                while(rs.next()) {
                    cnt++;
                    int account_id = rs.getInt("account_id");
                    int subcount = rs.getInt("subcount");
                    int num_videos = rs.getInt("num_videos");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    System.out.println( cnt + ".) " + account_id + " | " + subcount + " | " + num_videos + " | " + username + " | " + password );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

        public static void ListAllVideos(){ //17
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;

                query = "SELECT * FROM video";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                System.out.println("Showing all videos in database:");
                System.out.println("video_id | account_id | num_comments | publication_date | description | views | video_title | likes | dislikes | video_length | tags");
                
                while(rs.next()) {
                    cnt++;
                    int video_id = rs.getInt("video_id");
                    int account_id = rs.getInt("account_id");
                    int num_comments = rs.getInt("num_comments");
                    String publication_date = rs.getString("publication_date");
                    String description = rs.getString("description");
                    int views = rs.getInt("views");
                    String video_title = rs.getString("video_title");
                    int likes = rs.getInt("likes");
                    int dislikes = rs.getInt("dislikes");
                    int video_length = rs.getInt("video_length");
                    String tags = rs.getString("tags");
                    System.out.println( cnt + ".) " + video_id + " | " + account_id + " | " + num_comments + " | " + publication_date + " | " + description + " | " + views + " | " + video_title + " | " + likes + " | " + dislikes + " | " + video_length + " | " + tags);                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

        public static void ListAllComments(){ //18
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs;
                String query;

                query = "SELECT * FROM comments";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                System.out.println("Showing all comments in database:");
                System.out.println("comment_id | account_id | video_id | comment_content");
                
                while(rs.next()) {
                    cnt++;
                    int comment_id = rs.getInt("comment_id");
                    int account_id = rs.getInt("account_id");
                    int video_id = rs.getInt("video_id");
                    String comment_content = rs.getString("comment_content");
                    System.out.println( cnt + ".) " + comment_id + " | " + account_id + " | " + video_id + " | " + comment_content );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }
        
    public static void main(String[] args) {
        
        boolean running = true;
        
        while(running){
            System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("1. Upload Video"); //DONE
            System.out.println("2. Share Video");
            System.out.println("3. View Video"); 
            System.out.println("4. Delete Video"); //DONE
            System.out.println("5. Add Comment"); //DONE
            System.out.println("6. View Comment"); //DONE      
            System.out.println("7. Search Video Title"); //DONE
            System.out.println("8. Search Video Keyword"); //DONE
            System.out.println("9. Search Video Rating");
            System.out.println("10. Search Video Publication Date"); //DONE
            System.out.println("11. Search Video Owner"); //DONE
            System.out.println("12. List Video Recommendations");        
            System.out.println("13. List Most Popular Videos"); //DONE
            System.out.println("14. List Most Popular Channels"); 
            System.out.println("15. List Most Popular Subscriptions"); //DONE   
            System.out.println("16. List All Accounts"); //DONE
            System.out.println("17. List All Videos"); //DONE
            System.out.println("18. List All Comments"); //DONE
            System.out.println("19. Exit"); //DONE
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch(choice){
                case 1: AddVideo(); break;
                case 2: ShareVideo(); break;
                case 3: ViewVideo(); break;
                case 4: DeleteVideo(); break;
                case 5: AddComment(); break;
                case 6: ViewComment(); break;
                case 7: SearchVideoTitle(); break;
                case 8: SearchVideoKeyword(); break;
                case 9: SearchVideoRating(); break;
                case 10: SearchVideoPublicationDate(); break;
                case 11: SearchVideoOwner(); break;
                case 12: ListVideoRecommendations(); break;
                case 13: ListMostPopularVideos(); break;
                case 14: ListMostPopularChannels(); break;
                case 15: ListMostPopularSubscriptions(); break;
                case 16: ListAllAccounts(); break;
                case 17: ListAllVideos(); break;
                case 18: ListAllComments(); break;
                case 19: running = false; System.out.println("Sayonara!"); break;
                default: break;
            }
        }
    }
}
