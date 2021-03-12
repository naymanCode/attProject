package project;

public class userT {
	public static String check(String userType){
		switch (userType) {
		case "student" : return "Student!!!";
		case "teacher" : return "Teacher!!!";
		case "office registrar" : return "Office Registrar!!!";
		default : return "something";
		}
		
	};
}
