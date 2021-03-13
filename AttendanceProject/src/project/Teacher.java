package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Teacher {
	public static void teacher(Connection con, Scanner n, String login)
    {
		Statement statement = null;
		String userType=null, object="", status, group, dayToCheck;
		System.out.println("Choose the group you want to check attendance: ");
		group = n.nextLine();
		System.out.println("Choose the date you want check attendance(YYYY-MM-DD)");
		dayToCheck = n.nextLine();
		try {
			statement = con.createStatement();
			ResultSet result = statement.executeQuery("Select subject FROM users WHERE login =\'" + login + "\' ");
			if (result.next()) {
				object = result.getString("subject");
				System.out.println("List of attendance of "+object+": ");
			}
			result = statement.executeQuery("Select * From users FULL join attendance On users.id = attendance.userid where users.studentgroup = \'"+group+"\' AND  date = \'"+dayToCheck+"\' AND attendance.subject = \'"+object+"\' order by users.id");
			while (result.next())
		      {
				String idU = result.getString("userid");
		        String nameU = result.getString("name");
		        String surnameU = result.getString("surname");
		        String groupU = result.getString("studentgroup");
		        String dayU = result.getString("dayofweek");
		        String timeU = result.getString("time");
		        String attU = result.getString("attendance");
		        String dateU = result.getString("date");
		        String statusU = result.getString("attendance");
		        // print the results
		     
		        System.out.format("%s, %s, %s, %s, %s, %s, %s, %s\n", idU, nameU, surnameU, groupU, dateU, dayU, timeU, attU, statusU);
		      }
			System.out.println("Do you want to change the attendance status?(Yes/No): ");
			boolean repeat = true;
			while(repeat) {
			String ans = n.nextLine();
			if (ans.equalsIgnoreCase("Yes")){
				System.out.println("Choose the ID of student: ");
				String change = n.nextLine();
				System.out.println("Choose the status: ");
				String newStatus = n.nextLine();
				statement.executeUpdate("update attendance set attendance = \'"+newStatus+"\' where userid = "+change+";");
				System.out.println("Updated! Do you want to change anything else?: ");
			}
			else
			repeat = false;
			}													
			
		}catch (Exception e) {
            System.out.println("Somethig is wrong");
          
        }
    }
}
