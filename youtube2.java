package com.postgresqltutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author postgresqltutorial.com
 */
public class App{

    private final String url = "jdbc:postgresql://localhost:5432/postgres"; //jdbc:postgresql://localhost/dvdrental
    private final String user = "postgres";
    private final String password = "32aLs67!";
         
    private Connection conn = null;   
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    
    public void executeUpdate (String sql) throws SQLException { 
	// creates a statement object
	Statement stmt = this.conn.createStatement ();
	
        // issues the update instruction
	stmt.executeUpdate (sql);

	// close the instruction
	stmt.close ();
    }//end executeUpdate

    public int executeQueryAndPrintResult (String query) throws SQLException {
	//creates a statement object
	Statement stmt = this.conn.createStatement ();
        
	//issues the query instruction
        ResultSet rs = stmt.executeQuery (query);

	/*
	 *  obtains the metadata object for the returned result set.  The metadata
	 *  contains row and column info.
	 */
	ResultSetMetaData rsmd = rs.getMetaData ();
	int numCol = rsmd.getColumnCount ();
	int rowCount = 0;
		
	//iterates through the result set and output them to standard out.
	boolean outputHeader = true;
	while (rs.next()){
            if(outputHeader){
		for(int i = 1; i <= numCol; i++){
                    System.out.print(rsmd.getColumnName(i) + "\t");
		}
	        System.out.println();
	        outputHeader = false;
	    }
	    for (int i=1; i<=numCol; ++i)
		System.out.print (rs.getString (i) + "\t");
	    System.out.println ();
	    ++rowCount;
	}//end while
	stmt.close ();
	return rowCount;
    }

    public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
        //creates a statement object 
	Statement stmt = this.conn.createStatement (); 
		
	//issues the query instruction 
	ResultSet rs = stmt.executeQuery (query); 
	 
	/*
	 * obtains the metadata object for the returned result set.  The metadata 
	 * contains row and column info. 
	*/ 
	ResultSetMetaData rsmd = rs.getMetaData (); 
	int numCol = rsmd.getColumnCount (); 
	int rowCount = 0; 
	 
	//iterates through the result set and saves the data returned by the query. 
	boolean outputHeader = false;
	List<List<String>> result  = new ArrayList<List<String>>(); 
	while (rs.next()){
            List<String> record = new ArrayList<String>(); 
            for (int i=1; i<=numCol; ++i) 
		record.add(rs.getString (i)); 
            result.add(record); 
	}//end while 
	stmt.close (); 
	return result; 
    }//end executeQueryAndReturnResult

    public int executeQuery (String query) throws SQLException {
	//creates a statement object
	Statement stmt = this.conn.createStatement ();

	//issues the query instruction
	ResultSet rs = stmt.executeQuery (query);

	int rowCount = 0;

	//iterates through the result set and count nuber of results.
	if(rs.next()){
            rowCount++;
	}//end while
	stmt.close ();
	return rowCount;
    }
 
    public int getCurrSeqVal(String sequence) throws SQLException {
	Statement stmt = this.conn.createStatement ();
		
	ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
	if (rs.next()) return rs.getInt(1);
	return -1;
    }
    
    public static int readChoice() {
	int input;
	// returns only if a correct value is given.
	do {
            System.out.print("Please make your choice: ");
            try { // read the integer, parse it and break.
		input = Integer.parseInt(in.readLine());
		break;
            }catch (Exception e) {
		System.out.println("Your input is invalid!");
		continue;
            }//end try
	}while (true);
	return input;
    }//end readChoice

    public static void Test(App esql){//13
	try{
		String input, query;
			 
		query = "SELECT account_id FROM public.\"Account\" ORDER BY account_id";
			
		esql.executeQueryAndPrintResult(query);
                
	}
	catch(Exception e){
		System.err.println (e.getMessage());
	}
    }
    
    public static void ListMostPopularVideos(App esql){//13
	try{
		String input, query;
			 
		query = "SELECT V.video_title, V.views FROM Video V GROUP BY V.views DESC";
			
		esql.executeQueryAndPrintResult(query);
                
	}
	catch(Exception e){
		System.err.println (e.getMessage());
	}
    }

        public static void ListMostPopularChannels(App esql){//13
	try{
		String input, query;
			 
		query = "";
			
		esql.executeQueryAndPrintResult(query);
                
	}
	catch(Exception e){
		System.err.println (e.getMessage());
	}
    }

    public static void ListMostPopularSubscriptions(App esql){//13
	try{
		String input, query;
			 
		query = "";
			
		esql.executeQueryAndPrintResult(query);
                
	}
	catch(Exception e){
		System.err.println (e.getMessage());
	}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App app = new App();
        App esql = null;
        app.connect();
        try{
            boolean keepon = true;
                while(keepon){
                    System.out.println("MAIN MENU");
                    System.out.println("---------");
                    System.out.println("0. Test");
                    System.out.println("1. Upload Video");
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


                switch(readChoice()){
                    case 0: Test(esql); break;
                    case 1: ; break;
                    case 2: ; break;
                    case 3: ; break;
                    case 4: ; break;
                    case 5: ; break;
                    case 6: ; break;
                    case 7: ; break;
                    case 8: ; break;
                    case 9: ; break;
                    case 10: ; break;
                    case 11: ; break;
                    case 12: ; break;
                    case 13: ListMostPopularVideos(esql); break;
                    case 14: ListMostPopularChannels(esql); break;
                    case 15: ListMostPopularSubscriptions(esql); break;
                    case 16: keepon = false; break;
                }
            }
        }catch(Exception e){
            System.err.println("e.getMessage()");   
        }
    }
}
