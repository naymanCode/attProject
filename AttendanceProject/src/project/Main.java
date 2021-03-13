package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime; 


public class Main {

	public static void main(String[] args) {
		String login,pwd,userType=null, object="", status, dayToCheck, group;
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
			//System.out.print("Choose User Type(student/teacher/office registrar): ");
			//userType = n.nextLine();
			try {
				
				ResultSet result = statement.executeQuery("Select usertype FROM users WHERE login =\'" + login + "\' ");
				if (result.next()) {
					userType = result.getString("usertype");
					}
				
				
				
				
				
				result = statement.executeQuery("Select id FROM users WHERE login =\'" + login + "\' AND"+ " password=\'" + pwd + "\' AND"+ " userType=\'" + userType + "\' ");
				if (result.next()) {isLoginSuccess=true; System.out.println("Successfully logged in!");}
				else {System.out.println("Data is incorrect. Try again!");continue;}
			}catch (Exception e) {
            System.out.println("Somethig is wrong");}  
		
			if (userType.equalsIgnoreCase("student")) {
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
			
			if (userType.equalsIgnoreCase("teacher")) {
				System.out.println("Choose the group you want to check attendance: ");
				group = n.nextLine();
				System.out.println("Choose the date you want check attendance(YYYY-MM-DD)");
				dayToCheck = n.nextLine();
				try {
					ResultSet result = statement.executeQuery("Select subject FROM users WHERE login =\'" + login + "\' ");
					if (result.next()) {
						object = result.getString("subject");
						System.out.println("List of attendance of "+object+": ");
					}
					result = statement.executeQuery("Select * From users FULL join attendance On users.id = attendance.userid where users.studentgroup = \'"+group+"\' AND  date = \'"+dayToCheck+"\' order by users.id");
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
			if (userType.equalsIgnoreCase("office registrar")) {
				System.out.println("office");
				
			}
			
		}
		
	}
	
	public static String getLocalDate() {
		String convertedToString = String.valueOf(LocalDate.now());
	    return convertedToString;
	}
}
