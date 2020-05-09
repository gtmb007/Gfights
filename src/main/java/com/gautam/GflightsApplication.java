package com.gautam;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.gautam.service.AdminService;

@SpringBootApplication
public class GflightsApplication implements CommandLineRunner {
	
	@Autowired
	private Environment environment;
	
	private Scanner sc=new Scanner(System.in);
	
	@Autowired
	private AdminService adminService;

	public static void main(String[] args) {
		SpringApplication.run(GflightsApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		while(true) {
			System.out.println("\nPlease Enter...\n1.) Admin Login\n2.) User Login\n0.) Exit");
			int opt=sc.nextInt();
			if(opt==0) return;
			else if(opt==1) adminLogin();
			else if(opt==2) userLogin();
			else System.out.println("\n"+environment.getProperty("UI.INVALID_OPTION"));
		}
	}
	
	public void adminLogin() {
		System.out.print("\nEnter Admin Id: ");
		String id=sc.next();
		System.out.print("Enter Password: ");
		String password=sc.next();
		try {
			id=adminService.validateAdmin(id, password);
			System.out.println("\n"+environment.getProperty("API.ADMIN_LOGIN_SUCCESS")+id);
			while(true) {
				System.out.println("\nPlease Enter...\n1.) Add Flight\n2.) Get Flight\n3.) Update Flight\n4.) Remove Flight\n0.) Log Out");
				int opt=sc.nextInt();
				if(opt==0) return;
				else if(opt==1) addFlight();
				else if(opt==2) getFlight();
				else if(opt==3) updateFlight();
				else if(opt==4) removeFlight();
				else System.out.println("\n"+environment.getProperty("UI.INVALID_OPTION"));
			}
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void addFlight() {
		
	}
	
	public void getFlight() {
		
	}
	
	public void updateFlight() {
		
	}
	
	public void removeFlight() {
		
	}
	
	public void userLogin() {
		while(true) {
			System.out.println("\nPlease Enter...\n1.) User Sign Up\n2.) User Sign In\n0.) Exit as User");
			int opt=sc.nextInt();
			if(opt==0) return;
			else if(opt==1) signUp();
			else if(opt==2) signIn();
			else System.out.println("\n"+environment.getProperty("UI.INVALID_OPTION"));
		}
	}
	
	public void signUp() {
		
	}
	
	public void signIn() {
		
	}

}
