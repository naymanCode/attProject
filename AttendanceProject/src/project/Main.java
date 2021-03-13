package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		Student student = new Student();
		Teacher teacher = new Teacher();
		Office office = new Office();
		IDB db = new DB();
		String login,pwd,userType=null, object="", status, dayToCheck, group;
		boolean isLoginSuccess = false;
		Scanner n = new Scanner(System.in);
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
		String URL = "jdbc:postgresql://localhost:5432/EndTermProject";
		String username="postgres";
		String password="samsung";
		Statement statement = null;
		Connection con = null;
		con = db.connect(URL, username, password);
		while (isLoginSuccess != true) {
			System.out.print("Login: ");
			login = n.nextLine();
			System.out.print("Password: ");
			pwd = n.nextLine();
			try {
				statement = con.createStatement();
				ResultSet result = statement.executeQuery("Select usertype FROM users WHERE login =\'" + login + "\' ");
				if (result.next()) {
					userType = result.getString("usertype");
					}
				result = statement.executeQuery("Select id FROM users WHERE login =\'" + login + "\' AND"+ " password=\'" + pwd + "\' AND"+ " userType=\'" + userType + "\' ");
				if (result.next()) {isLoginSuccess=true; System.out.println("Successfully logged in!");}
				else {System.out.println("Data is incorrect. Try again!");continue;}
			}catch (Exception e) {
            System.out.println("Data is incorrect");continue;}  
		
			if (userType.equalsIgnoreCase("student")) {
				Student.student(con, login);}
			if (userType.equalsIgnoreCase("teacher")) {
				Teacher.teacher(con, n, login);}
			if (userType.equalsIgnoreCase("office registrar")) {
				Office.office(con, n, login);
		}
			
		} 
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static String getLocalDate() {
		String convertedToString = String.valueOf(LocalDate.now());
	    return convertedToString;
	}
}
