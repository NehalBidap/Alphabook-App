package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loginandsignup implements Alphabook{
	Scanner sc=new Scanner(System.in);
	String email="";
	String psd="";
	Boolean email_flag=false;
	Boolean password_flag=false;
	int pwd_count=0;
	String status="Exits";
	String dateandtime;

	@Override
	public void login() {


		System.out.println("*************LOGIN TO ALPHABOOK***************");
		if(email=="") {
			System.out.println("Enter the email_id as username");
			email=sc.nextLine();
		}
		if(psd=="") {
			System.out.println("Enter the password:");
			psd=sc.nextLine();
		}
		String url="jdbc:mysql://localhost:3306/alphabook";
		String username="root";
		String password="root";
		String query="select * from book where email="+"\""+email+"\""; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection(url,username,password);
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(query);
			int i=0;
			while(i==0) {
				if(rs.next()) {
					if(email.equals(rs.getString(2))) {
						email_flag=true;
					}
					if(psd.equals(rs.getString(3))) {
						password_flag=true;
						pwd_count=0;
					}
					if(email_flag==true&&password_flag==true) {
						System.out.println("Login Successful");
						System.out.println("Welcome to AlphaBook:");
						System.out.println("1.My details\n2.Post\n3.Exit");
						System.out.println("please enter your choice");
						int ch;
						ch=sc.nextInt();
						switch(ch) {
						case 1:{
							System.out.println();
							System.out.println("**********************************");
							System.out.println("Firstname:" + rs.getString(4));
							System.out.println("Lastname:"+rs.getString(5));
							System.out.println("Gender:"+rs.getString(6));
							System.out.println("ContactNumber:"+rs.getString(7));
							System.out.println("DOB:"+rs.getString(8));
							System.out.println("***********************************");
							System.out.println();
							System.out.println();
							break;
						}
						case 2:{
							post();
							break;
						}
						case 3:{
							status="LOGOUT";
							exit();
							break;
						}
						default:throw new IllegalArgumentException("invalid choice"+ch+"please enter "
								+ "the valid choice");
						}
					}
				}
				if(!rs.next()) {
					if(email_flag==false) {
						System.out.println("Email doesnot exists we have to signup first");
						signup();
						email="";
						psd="";
						email_flag=false;
						password_flag=false;
						break;

					}
					else if(email_flag==true&&password_flag==false) {
						pwd_count++;
						if(pwd_count<3) {

							System.out.println("INVALID Password 3 attempts remaining "
									+ pwd_count + " completed");
							System.out.println( (3-pwd_count) + "attempts are remaining ");
							System.out.println("***************************");
							System.out.println("re-enter the password");
							psd=sc.nextLine();

						}

						else {
							System.out.println("Sorry!!! you exceeds 3 attempts please login again");
							email="";
							psd="";
							email_flag=false;
							password_flag=false;			
							break;
						}  
					}
					else {
						break;
					}
				}
			}
			conn.close();
			st.close();


		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void signup() {
        int slno=0;
		System.out.println("*************SIGNUP******************");
		System.out.println("***********ALPHA BOOK***************");

		System.out.println("slno");
		String sl=sc.next();
		System.out.println("**********************************");
		System.out.println("1.gmail\n2.outlook\n3.yahoo");
		System.out.println("choice your option");
		String cs=sc.next();
		System.out.println("Enter emailid");
		String email=sc.next();	
		System.out.println("***************************");
		System.out.println("Enter the password");
		String psd=sc.next();
		if(! checkpass(psd))
		{
			while(true) { 
				psd = sc.next();
				if(checkpass(psd))
				{
					break;
				}
			}
		}


		System.out.println("***************************");
		System.out.println("Enter firstname");
		String firstname=sc.next();
		System.out.println("***************************");
		System.out.println("Enter lastname");
		String lastname=sc.next();
		System.out.println("****************************");
		String gender=null;
		while(true)
		{
			System.out.println("Select Gender : ");
			System.out.println("  1. Female \n  2. Male \n  3. Others");
			int ch = sc.nextInt();
			switch(ch)
			{
			case 1:gender="Female";
			break;
			case 2:gender="Male";
			break;
			case 3:gender="Others";
			break;
			default:System.out.println("Invalid choice......");
			break;
			}
			if(ch==1 || ch==2 || ch==3)
			{
				break;
			}
			else {
				continue;
			}
		}

		System.out.println("***************************");
		System.out.println("Enetr contactno");
		String mbl=sc.next();
		if(mbl.length()!=10)
		{
			while(true)
			{
				System.out.println("Enter valid phone number : ");
				mbl=sc.next();
				if(mbl.length()==10)
				{
					break;
				}
			}
		}
		System.out.println("***************************");
		System.out.println("Enter the DOB");
		System.out.print("Enter your date of birth (YYYY-MM-DD): ");
		String dob=sc.next();

		String status="Available";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        String dateandtime=dtf.format(now);


		String url="jdbc:mysql://localhost:3306/alphabook";
		String username="root";
		String password="root";
		String query="insert into book values(?,?,?,?,?,?,?,?,?,?)";
		String query1="select * from book";
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection(url,username,password);
			PreparedStatement ps=conn.prepareStatement(query);
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(query1);
			while(rs.next()) {
				slno++;
			}
			ps.setInt(1, slno);
			ps.setString(2, email);
			ps.setString(3, psd);
			ps.setString(4,firstname);
			ps.setString(5, lastname);
			ps.setString(6, gender);
			ps.setString(7, mbl);
			ps.setString(8, dob);
			ps.setString(9, status);
			ps.setString(10, dateandtime);

			System.out.println("1.SUBMIT\n2.CANCEL");
			System.out.println("Enter your choice:");
			int choice=sc.nextInt();
			System.out.println("CHOISE:"+choice);
			 switch(choice) {
			  case 1: ps.execute();
		    System.out.println("SIGNUP SUCCESSFULL");
			System.out.println();
			System.out.println("*************************************");
			System.out.println();
			Scanner sc=new Scanner(System.in);
			System.out.println("*************Welcome to alphabook****************");
			System.out.println("*************************************************");
			System.out.println("select the option");
			System.out.println("*************************************************");
			System.out.println("1.Login\n2.Signup\n3.Exit");
			Alphabook a=new Loginandsignup();
			int choice1=sc.nextInt();
			switch(choice1) {
			case 1:
	     	a.login();
	     	break;
			    
			case 3:
			a.exit();
			break;
	       default:System.out.println("Enter the correct choice");
			}
			 }
			conn.close();
			ps.close();
		} catch (ClassNotFoundException | SQLException e ) {

			e.printStackTrace();
		}

	}
public static boolean
    checkpass(String password)
    {
	String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";

Pattern p = Pattern.compile(regex);

if (password == null) {
 return false;
}
Matcher m = p.matcher(password);
return m.matches();
}



	@Override
	public void post() {
		System.out.println("********************************");
		int ch = 0;
		Scanner sc = new Scanner(System.in);
		Alphabook a=new Loginandsignup();
		while (ch != 3) {
			//System.out.println("OPT YOUR OPTION");
			System.out.println("1.POST STATUS\n2.VIEW STATUS\n3.EXIT");

			ch = sc.nextInt();
			switch (ch) {
			case 1:
				System.out.println("ADD STATUS");
				String status = sc.next();
		    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
       			LocalDateTime now = LocalDateTime.now();
				String dateandtime = dtf.format(now);
				String url = "jdbc:mysql://localhost:3306/alphabook";
				String username = "root";
				String password = "root";
				String query = "UPDATE book SET status=?, dateandtime=? WHERE email=?";

				try (Connection conn = DriverManager.getConnection(url, username, password);
						PreparedStatement ps = conn.prepareStatement(query)) {
					
					    ps.setString(1, status);
	                    ps.setString(2, dateandtime);
	                    ps.setString(3, email);

					int rowsUpdated = ps.executeUpdate();
					String t=sc.nextLine();
					if (rowsUpdated > 0) {
						System.out.println("Status updated successfully.");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				String url1 = "jdbc:mysql://localhost:3306/alphabook";
				String username1 = "root";
				String password1 = "root";
				String query2 = "SELECT * FROM book WHERE email=?";

				try (Connection conn = DriverManager.getConnection(url1, username1, password1);
						PreparedStatement ps = conn.prepareStatement(query2)) {
					ps.setString(1, email);

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						System.out.println("STATUS: " + rs.getString("status") + "     "
								+ "date and time: " + rs.getString("dateandtime"));
					} else {
						System.out.println("No status found for this user.");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 3: 
			exit();
				sc.close();
				break;
			default:
				System.out.println("INVALID");
				break;
			}
		}

	}
	@Override
	public void exit() {
		email="";
		psd="";
		pwd_count=0;
		System.out.println("Logout successfull");
		System.out.println("*************************************");
		System.out.println("Thank you");
	}

}
