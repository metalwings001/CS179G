

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.*;

public class App{

    private final String url = "jdbc:postgresql://localhost:5000/youtube6";
    private final String user = "postgres";
    private final String password = "1234!!";
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
            //System.out.println("Connected to the PostgreSQL server successfully.");
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
            //System.out.println("Enter a new num comments: ");    
            add_num_comments = 0;
            System.out.println("Enter a new publication date: ");    
            add_publication_date = sc2.nextLine();
            System.out.println("Enter a new description: ");    
            add_description = sc2.nextLine();
            //System.out.println("Enter a new views: ");    
            add_views = 0;
            System.out.println("Enter a new video title: ");    
            add_video_title = sc2.nextLine();
            //System.out.println("Enter a new likes: ");    
            add_likes = 0;
            //System.out.println("Enter a new dislikes: ");    
            add_dislikes = 0;
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
            //HDFS backend
            Process p;
            String command;
            command = "cmd /c hdfs dfs -copyFromLocal C:\\Users\\Justin\\Desktop\\NewVideo\\";
            command += add_video_title + ".mp4 /videos";
            System.out.println("command: " + command);
            p = Runtime.getRuntime().exec(command);
            
            /*query = "UPDATE account SET views = views + 1 WHERE video_title = " + "\'" + videoTitle + "\'";
                
            stmt = conn.createStatement();
            pst = conn.prepareStatement(query);
            pst.executeUpdate();*/

            System.out.println("Table updated! Need to check on pgadmin or use the ListAllVideos() function");
            

            
            
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
                String query, command, videoTitle;
                Scanner sc = new Scanner(System.in); 
                
                System.out.println("Enter a video title to view");
                videoTitle = sc.nextLine(); 
                System.out.println("videoTitle: " + videoTitle);
                Process p;
                command = "cmd /c hdfs dfs -get /videos/";
                command += videoTitle + ".mp4";
                command += " C:\\Users\\Justin\\Desktop\\fromHDFS";
                System.out.println("command: " + command);
                p = Runtime.getRuntime().exec(command);
                Thread.sleep(2500); //give HDFS command time to execute before playing
                String player = "C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe";
                String arg = "C:\\Users\\Justin\\Desktop\\FromHDFS\\";
                arg += videoTitle + ".mp4";
                //Building a process
                ProcessBuilder builder = new ProcessBuilder(player, arg);
                System.out.println("Executing the external program . . . . . . . .");
                //Starting the process
                builder.start();
                //query = "SELECT * FROM video";            
                

                //increments view by 1 everytime you successfully view a video with correct videoTitle.
                query = "UPDATE video SET views = views + 1 WHERE video_title = " + "\'" + videoTitle + "\'";
                
                stmt = conn.createStatement();
                pst = conn.prepareStatement(query);
                pst.executeUpdate();
                 
               //need to finish above, add a view to video(views) since this function is called. doesnt work 
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

               
                
                //HDFS backend
                String videoTitle, trimmedTitle;
                query = "SELECT video_title FROM video WHERE video_id=";
                query += remove_video_id;
                rs = stmt.executeQuery(query);
                rs.next();
                videoTitle = rs.getString("video_title");
                trimmedTitle = videoTitle.trim();
                System.out.println( "Video title: " + videoTitle);
                
                
                Process p;
                String command;
                command = "cmd /c hdfs dfs -rm -R /videos/";
                command += trimmedTitle + ".mp4";
                System.out.println("command: " + command);
                p = Runtime.getRuntime().exec(command);
                command = "cmd /c del C:\\Users\\Justin\\Desktop\\fromHDFS\\" + trimmedTitle + ".mp4";
                System.out.println("command2: " + command);
                p = Runtime.getRuntime().exec(command);
                
                
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
            String query, insertQuery, query2;
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
            
            query2 = "UPDATE video SET num_comments = num_comments + 1 WHERE video_id = " + add_video_id;
         
             stmt = conn.createStatement();
             pst = conn.prepareStatement(query2);
             pst.executeUpdate();
            
            System.out.println("Table updated! Need to check on pgadmin or use the ListComments() function");
            
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
                    System.out.println(  "Comment id: " + comment_id + " - " + comment_content.trim() );                    
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
                    System.out.println( cnt + ".) " + video_title.trim() + ": " + views + " views"  );                    
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
                    System.out.println( cnt + ".) " + video_title.trim() + ": " + tags.trim() + " category"  );                    
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
                    System.out.println( cnt + ".) " + video_title.trim() + " - rating: " + rating  );                    
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
                    System.out.println( cnt + ".) " + video_title.trim() + " - Published on: " + publication_date.trim() );                    
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
                    System.out.println( cnt + ".) " + username.trim() + " - " + subcount + " subscribers, " + num_videos + " videos" );                    
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
                    System.out.println( cnt + ".) " + title.trim() );                    
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
                    System.out.println( cnt + ".) " + title.trim() );                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }
        
        public static void ListMostPopularChannels(){ //14 //need to order account with summed views
            App app = new App();
            Connection conn;
            int cnt = 0;
            int cnt2 = 0;
            try{
                conn = app.connect();
                Statement stmt; 
                ResultSet rs, rs2;
                String query, query2;
                int total_views = 0;
                
                Vector<Integer>all_account_id = new Vector<>();
                Vector<Integer>all_views = new Vector<>();
                Vector<Integer>list_total_views = new Vector<>();
                Vector<Integer>single_record_account_id = new Vector<>();
                                      
                query2 = "SELECT * FROM video ORDER BY account_id ASC";

                stmt = conn.createStatement();

                rs2 = stmt.executeQuery(query2);

                System.out.println("Most viewed users:");

                while(rs2.next()) {
                    cnt++;
                    int account_id = rs2.getInt("account_id");
                    int views = rs2.getInt("views"); //store views in vector
                    
                    all_account_id.add(account_id);
                    all_views.add(views);
                    
                }
                
                //if you want to debug
                //System.out.println("Values in vector all_account_id: " + all_account_id);
                //System.out.println("Values in vector all_views: " + all_views);

                int i, j; 
                for(i = j = 0; i < all_account_id.size() - 1 && j < all_views.size() - 1; ++i, ++j){
                    if(all_account_id.elementAt(i) == all_account_id.elementAt(i + 1)){
                        total_views += all_views.elementAt(j);
                    }
                    else{
                        cnt2++;
                        total_views += all_views.elementAt(j);
                        single_record_account_id.add(all_account_id.elementAt(j));
                        System.out.println( cnt2 + ".) " + total_views);
                        list_total_views.add(total_views);
                        total_views = 0;
                    }
                }
                
                cnt2++;
                total_views += all_views.elementAt(all_views.size() - 1);
                System.out.println( cnt2 + ".) " + total_views);
                single_record_account_id.add(all_account_id.elementAt(j));
                list_total_views.add(total_views);
                
                //Unsorted                
                //System.out.println("\nValues in vector list_total_views: " + list_total_views);                
                //System.out.println("\nValues in vector single_record_account_id: " + single_record_account_id + "\n");

                int k, l;
                
                for(k = l = 0; k < single_record_account_id.size() - 1 && l < list_total_views.size() - 1; ++k, ++l){
                    for(int m = l + 1; m < list_total_views.size(); ++m){
                        if(list_total_views.elementAt(l) < list_total_views.elementAt(m)){
                            Collections.swap(list_total_views, l, m);  
                            Collections.swap(single_record_account_id, k, m);
                        }
                    }
                }
                
                //Sorted
                //System.out.println("\nValues in vector list_total_views: " + list_total_views);                
                //System.out.println("\nValues in vector single_record_account_id: " + single_record_account_id + "\n");
                
                int cnt3 = 0;
                
                for(int p = 0; p < single_record_account_id.size(); ++p){
                    query = "SELECT * FROM account WHERE account_id = " + "\'" + single_record_account_id.elementAt(p) + "\'";  
                    rs = stmt.executeQuery(query);
                    while(rs.next()){
                        String username = rs.getString("username");
                        cnt3++;
                        System.out.println( cnt3 + ".)" + username.trim() + " - " + list_total_views.elementAt(p) + " views" );                        
                    }
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
                    System.out.println( cnt + ".) " + username.trim() + ": " + subcount + " subscribers"  );                    
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
                    System.out.println( cnt + ".) " + account_id + " | " + subcount + " | " + num_videos + " | " + username.trim() + " | " + password.trim() );                    
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
                    
                    System.out.println( cnt + ".) " + video_id + " | " + account_id + " | " + num_comments + " | " + publication_date.trim() + " | " + description.trim() + " | " + views + " | " + video_title.trim() + " | " + likes + " | " + dislikes + " | " + video_length + " | " + tags.trim());                    
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }

        public static void ListComments(){ //18
            App app = new App();
            Connection conn;
            int cnt = 0;
            try{
                conn = app.connect();
                Statement stmt, stmt2; 
                ResultSet rs, rs2;
                String query, query2,username;
                Scanner sc = new Scanner(System.in);
                
                System.out.println("Enter a video ID to search: ");
                
                int searchVideoID = sc.nextInt();
                query = "SELECT * FROM comments WHERE video_id = " + "\'" + searchVideoID + "\'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                System.out.println("Showing comments from videoID: " + searchVideoID);
                //System.out.println("comment_id | account_id | video_id | comment_content");
                
                while(rs.next()) {
                    cnt++;
                    int account_id = rs.getInt("account_id");
                    String comment_content = rs.getString("comment_content");
                    query2 = "SELECT username FROM account WHERE account_id=" + account_id;
                    stmt2 = conn.createStatement();
                    rs2 = stmt2.executeQuery(query2);
                    rs2.next();
                    username = rs2.getString("username");
                    //int comment_id = rs.getInt("comment_id");
                    //int account_id = rs.getInt("account_id");
                    //int video_id = rs.getInt("video_id");
                    
                    //System.out.println( cnt + ".) " + comment_id + " | " + account_id + " | " + video_id + " | " + comment_content.trim() );  
                    System.out.println( cnt + ".) " + username.trim() + ": " + comment_content.trim() );  
                }            

            } catch(Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);           
            }
        }
        
    public static void main(String[] args) {
        
        boolean running = true;
        String mainMenu = "MAIN MENU\n---------\n1. Upload Video\n3. View Video\n4. Delete Video\n5. Add Comment\n6. View Comment\n7. Search Video Title\n8. Search Video Keyword\n9. Search Video Rating\n"
                + "10. Search Video Publication Date\n11. Search Video Owner\n12. List Video Recommendations\n13. List Most Popular Videos\n14. List Most Popular Channels\n15. List Most Popular Subscriptions\n"
                + "16. List All Accounts\n17. List All Videos\n18. List Comment from Video\n19. Exit";
        while(running){
            /*System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("1. Upload Video"); //DONE
            System.out.println("2. Share Video");
            System.out.println("3. View Video"); //ARA
            System.out.println("4. Delete Video"); //DONE
            System.out.println("5. Add Comment"); //DONE
            System.out.println("6. View Comment"); //DONE      
            System.out.println("7. Search Video Title"); //DONE
            System.out.println("8. Search Video Keyword"); //DONE
            System.out.println("9. Search Video Rating"); //FIX
            System.out.println("10. Search Video Publication Date"); //DONE
            System.out.println("11. Search Video Owner"); //DONE
            System.out.println("12. List Video Recommendations"); //FIX    
            System.out.println("13. List Most Popular Videos"); //DONE
            System.out.println("14. List Most Popular Channels"); //FIX
            System.out.println("15. List Most Popular Subscriptions"); //DONE   
            System.out.println("16. List All Accounts"); //DONE
            System.out.println("17. List All Videos"); //DONE
            System.out.println("18. List Comment from Video"); //DONE
            System.out.println("19. Exit"); //DONE*/
            System.out.println(mainMenu);
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch(choice){
                case 1: AddVideo(); break;
                //case 2: ShareVideo(); break;
                case 3: ViewVideo(); break; //work
                case 4: DeleteVideo(); break;
                case 5: AddComment(); break;
                case 6: ViewComment(); break;
                case 7: SearchVideoTitle(); break;
                case 8: SearchVideoKeyword(); break;
                case 9: SearchVideoRating(); break;
                case 10: SearchVideoPublicationDate(); break;
                case 11: SearchVideoOwner(); break;
                case 12: ListVideoRecommendations(); break; //work
                case 13: ListMostPopularVideos(); break;
                case 14: ListMostPopularChannels(); break;
                case 15: ListMostPopularSubscriptions(); break;
                case 16: ListAllAccounts(); break;
                case 17: ListAllVideos(); break;
                case 18: ListComments(); break;
                case 19: running = false; System.out.println("Sayonara!"); break;
                default: break;
            }
        }
    }
}
