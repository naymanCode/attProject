package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime; 


public class Main {

	public static void main(String[] args) {
		String login,pwd,userType, object=null, status, dayToCheck, group;
		boolean isLoginSuccess = false;
		Connection con = null;
		Scanner n = new Scanner(System.in);
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String URL = "jdbc:postgresql://localhost:5432/EndTermProject";
		String username="postgres";
		String password="samsung";
		Statement statement = null;
		try {
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EndTermProject", "postgres", "samsung");
			statement = con.createStatement();
			if (statement != null) {
				System.out.println("Connected");
			} else {
				System.out.println("Not connected!");}
			} catch (Exception e) {
			e.printStackTrace();
			}
		while (isLoginSuccess != true) {
			System.out.print("Login: ");
			login = n.nextLine();
			System.out.print("Password: ");
			pwd = n.nextLine();
			System.out.print("Choose User Type(student/teacher/office registrar): ");
			userType = n.nextLine();
			try {
				ResultSet result = statement.executeQuery("Select id FROM users WHERE login =\'" + login + "\' AND"+ " password=\'" + pwd + "\' AND"+ " userType=\'" + userType + "\' ");
				if (result.next()) {isLoginSuccess=true; System.out.println("Successfully logged in!");}
				else {System.out.println("Data is incorrect. Try again!");continue;}
			}catch (Exception e) {
            System.out.println("Somethig is wrong");}  
		
			if (userType.equalsIgnoreCase("student")) {
				String time = sdf.format(date);
				String day = getLocalDate();
				System.out.print("What subject do you want to put attendance on?(java, calculus2, discrete): ");
				object = n.nextLine();
				System.out.print("Status(present, late, absent): ");
				status = n.nextLine();
				try {
					ResultSet result = statement.executeQuery("Select id FROM users WHERE login =\'" + login + "\' ");
					if (result.next()) {
						int id = result.getInt("id");
						//System.out.println(id);
						statement.executeUpdate("insert into "+object+"(id, attendance, time, date) values (\'"+id+"\', \'"+ status + "\', \'" + time + "\', \'"+day + "\')");
						System.out.println("Attendance registered!");
					}
				}catch (Exception e) {
	            System.out.println("Somethig is wrong");
	            }
			}
			else if (userType.equalsIgnoreCase("teacher")) {
				//System.out.println("Choose the group you want to check attendance: ");
				//group = n.nextLine();
				//System.out.println("Choose the date you want check attendance");
				//dayToCheck = n.nextLine();
				try {
					
					ResultSet result = statement.executeQuery("Select object FROM users WHERE login =\'" + login + "\' ");
					if (result.next()) {
						object = result.getString("object");
					}
					result = statement.executeQuery("Select * From users FULL join "+object+" On users.id = "+object+".id where users.studentgroup = 'CS2007' order by users.id");
					while (result.next())
				      {
				        int idU = result.getInt("id");
				        String nameU = result.getString("name");
				        String surnameU = result.getString("surname");
				        String groupU = result.getString("studentgroup");
				        String attU = result.getString("attendance");
				        String timeU = result.getString("time");
				        String dateU = result.getString("date");
				        // print the results
				        System.out.format("%s, %s, %s, %s, %s, %s, %s\n", idU, nameU, surnameU, groupU, attU, timeU, dateU);
				      }
					
				}catch (Exception e) {
		            System.out.println("Somethig is wrong");
		        }
			}
			else {
				
			}
		}
	}
	public static String getLocalDate() {
		String convertedToString = String.valueOf(LocalDate.now());
	    return convertedToString;
	}
	
}
