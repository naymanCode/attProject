package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

public class Student {
	public static void student(Connection con, String login)
    {
		Scanner n = new Scanner(System.in);
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Statement statement = null;
		String userType=null, object="", status;
		String time = sdf.format(date);
		String day = getLocalDate();
		String dayofweek="";
		Calendar c = Calendar.getInstance();
		int d = c.get(Calendar.DAY_OF_WEEK)-1;
		switch(d) {
			case 1: 
			   dayofweek = "Monday";
			   break;
			case 2: 
				dayofweek = "Tuesday";break;
 			case 3: 
 				dayofweek = "Wednesday";break;
				case 4: 
					dayofweek = "Thursday";break;
			case 5: 
				dayofweek = "Friday";break;
			case 6: 
				dayofweek = "Saturday";break;
			case 7: 
				dayofweek = "Sunday";break;
			
		}
			
		System.out.print("What subject do you want to put attendance on?(java, calculus2, discrete): ");
		object = n.nextLine();
		System.out.print("Status(present, late, absent): ");
		status = n.nextLine();
		
		
		try {
			statement = con.createStatement();
			ResultSet result = statement.executeQuery("Select id FROM users WHERE login =\'" + login + "\' ");
			if (result.next()) {
				int id = result.getInt("id");
				//System.out.println(id);
				statement.executeUpdate("insert into attendance(userid, subject, date, dayofweek, time, attendance) values (\'"+id+"\', \'"+ object + "\', \'"+day+"\', "+"\'"+dayofweek+"\', \'" + time + "\', \'"+status + "\')");
				System.out.println("Attendance registered!");
			}
		}catch (Exception e) {
        System.out.println("Somethig is wrong");
        }
    }
	public static String getLocalDate() {
		String convertedToString = String.valueOf(LocalDate.now());
	    return convertedToString;
	}
}
