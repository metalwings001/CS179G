import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Youtube{
    while(keepon){
    		System.out.println("MAIN MENU");
			System.out.println("---------");
			System.out.println("1. Add Account");	
	    		System.out.println("2. Add Comment");
			System.out.println("3. Add Video");
			System.out.println("4. List Account");
			System.out.println("5. < EXIT");
	    	switch (readChoice()){
				case 1: /*AddAccount();*/ break;
				case 2: /*AddComment()*/; break;
				case 3: /*AddVideo()*/; break;
				case 4: /*ListAccount()*/; break;
				case 5: keepon = false; break;
		}
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

