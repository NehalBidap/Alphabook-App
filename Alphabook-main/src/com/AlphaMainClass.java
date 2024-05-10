package com;

import java.util.Scanner;

public class AlphaMainClass {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("*************Welcome to alphabook****************");
		System.out.println("*************************************************");
		System.out.println("select the option");
		System.out.println("*************************************************");
		System.out.println("1.Login\n2.Signup\n3.Exit");
		Alphabook a=new Loginandsignup();
		int choice=sc.nextInt();
		switch(choice) {
		case 1:
     	a.login();
     	break;
     	
		case 2:
		System.out.println("**************SIGNUP**********************");
		System.out.println();
	     a.signup();
		  break;
		  
		case 3:
		a.exit();
		break;
       default:System.out.println("Enter the correct choice");
		}
	}

}
