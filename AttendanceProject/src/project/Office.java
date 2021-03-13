package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Office {
	public static void office(Connection con, Scanner n, String login)
    {
		String userType=null, object="", status, group, dayToCheck;
		Statement statement = null;
		System.out.println("Choose the subject:");
		object = n.nextLine();
		System.out.println("Choose the group: ");
		group = n.nextLine();
		System.out.println("Choose the date(YYYY-MM-DD)");
		dayToCheck = n.nextLine();
		try {
			statement = con.createStatement();
			ResultSet result = statement.executeQuery("SELECT COUNT(attid) AS total FROM attendance WHERE attendance = 'present'; ");
			System.out.print("present: ");
			
			if (result.next())
		      {
				int count;
				count=result.getInt("total");
				System.out.println(count);
		      }
			result = statement.executeQuery("SELECT COUNT(attid) AS total FROM attendance WHERE attendance = 'late'; ");
			System.out.print("late: ");
			
			if (result.next())
		      {
				int count;
				count=result.getInt("total");
				System.out.println(count);
		      }
			result = statement.executeQuery("SELECT COUNT(attid) AS total FROM attendance WHERE attendance = 'absent'; ");
			System.out.print("absent: ");
			
			if (result.next())
		      {
				int count;
				count=result.getInt("total");
				System.out.println(count);
		      }
			
		
	}catch (Exception e) {
        System.out.println("Something is wrong");
          
    }
    }
}
