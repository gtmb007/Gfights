package com.gautam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.gautam.model.Booking;
import com.gautam.model.FinalFlight;
import com.gautam.model.Flight;
import com.gautam.model.Passenger;
import com.gautam.model.Rout;
import com.gautam.model.User;
import com.gautam.service.AdminService;
import com.gautam.service.BookingService;
import com.gautam.service.FlightService;
import com.gautam.service.UserService;

@SpringBootApplication
public class GflightsApplication implements CommandLineRunner {
	
	@Autowired
	private Environment environment;
	
	private Scanner sc=new Scanner(System.in);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private BookingService bookingService;

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
				System.out.println("\nPlease Enter...\n1.) Add Flight\n2.) Get Flights\n3.) Update Flight\n4.) Remove Flight\n0.) Log Out");
				int opt=sc.nextInt();
				if(opt==0) return;
				else if(opt==1) addFlight();
				else if(opt==2) getFlights();
				else if(opt==3) updateFlight();
				else if(opt==4) removeFlight();
				else System.out.println("\n"+environment.getProperty("UI.INVALID_OPTION"));
			}
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void addFlight() {
		Flight flight = new Flight();
		
		System.out.print("\nEnter Flight Number: ");
		flight.setFlightNo(sc.next());
		
		System.out.print("Enter Vendor Name: ");
		flight.setVendor(sc.next());
		
		LocalDate today=LocalDate.now();
		Map<LocalDate, Integer> seatMap=new LinkedHashMap<LocalDate, Integer>();
		System.out.print("Enter Number of Seats: ");
		Integer seats=sc.nextInt();
		flight.setTotalSeats(seats);
		for(int i=0;i<365;i++) {
			seatMap.put(today.plusDays(i), seats);
		}
		flight.setSeatMap(seatMap);
		
		List<Rout> routes = new ArrayList<Rout>();
		for(int j=0;j<2;j++) {
			if(j==0) System.out.println("\nEnter Up Route Details... ");
			else System.out.println("\nEnter Down Route Details: ");
			Rout route = new Rout();
			System.out.print("Enter Number of Stopies: ");
			int n = sc.nextInt();				
			List<String> stopies = new ArrayList<String>();
			System.out.print("Enter Name Of Stopies: ");
			for(int i=0;i<n;i++) {
				stopies.add(sc.next());
			}
			route.setLocation(stopies);
			
			System.out.print("Enter Arrival Timing: ");
			List<LocalTime> arrivalTime = new ArrayList<LocalTime>();
			for(int i=0;i<n;i++) {
				arrivalTime.add(LocalTime.parse(sc.next()));
			}
			route.setArrivalTime(arrivalTime);
	
			System.out.print("Enter Departure Timing: ");
			List<LocalTime> deptTime = new ArrayList<LocalTime>();
			for(int i=0;i<n;i++) {
				deptTime.add(LocalTime.parse(sc.next()));
			}
			route.setDeptTime(deptTime);
			
			System.out.print("Enter Distance from Starting Journey: ");
			List<Integer> dist = new ArrayList<Integer>();
			for(int i=0;i<n;i++) {
				dist.add(sc.nextInt());
			}	
			route.setDist(dist);
			
			System.out.print("Enter Base Fare: ");
			List<Double> baseFare = new ArrayList<Double>();
			for(int i=0;i<n;i++) {
				baseFare.add(sc.nextDouble());
			}	
			route.setBaseFare(baseFare);
			routes.add(route);
		}
		flight.setRout(routes);
		
		System.out.print("Enter Number of Schedule day: ");
		int y=sc.nextInt();
		List<DayOfWeek> day = new ArrayList<DayOfWeek>();
		System.out.print("Enter Schedued Day(1-7): ");
		for(int i=0;i<y;i++) {
			day.add(DayOfWeek.of(sc.nextInt()));
		}
		flight.setDayList(day);
		
		try {
			String flightId=flightService.addFlight(flight);
			System.out.println("\n"+environment.getProperty("API.FLIGHT_ADDED")+flightId);
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void getFlights() {
		try {
			Set<Flight> flightList = flightService.getFlights();
			for(Flight fl : flightList) {
				System.out.println("\nFlight No: "+fl.getFlightNo());
				System.out.println("Vendor Name: "+fl.getVendor());
				System.out.println("Seat Map: ");
				Map<LocalDate, Integer> seatMap=fl.getSeatMap();
				for(LocalDate key : seatMap.keySet()) {
					System.out.println("DOJ: "+key+"\tAvailable Seats: "+seatMap.get(key));
				}
				
				int i=1;
				for(Rout route : fl.getRout()) {
					System.out.println("Route"+i+": ");
					List<String> loc=route.getLocation();
					for(int j=0;j<loc.size();j++) {
						System.out.println("Stop: "+loc.get(j)+"\tArrival Time: "+route.getArrivalTime().get(j)
								+"\tDeparture Time: "+route.getDeptTime().get(j)+"\tDistance: "+route.getDist().get(j)+"km"
								+"\tBase Fare: "+route.getBaseFare().get(j));
					}
					i++;
				}
				
				System.out.println("Running Days: ");
				for(DayOfWeek day: fl.getDayList()) {
					System.out.print(day+"\t");
				}
				System.out.println();
			}
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void updateFlight() {
		//TODO
	}
	
	public void removeFlight() {
		try {
			System.out.print("\nEnter Flight No: ");
			String fNo=flightService.removeFlight(sc.next());
			System.out.println("\n"+environment.getProperty("API.FLIGHT_REMOVED")+fNo);
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
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
		User user=new User();
		System.out.print("\nEnter User Id: ");
		user.setUserId(sc.next());
		System.out.print("Enter First Name: ");
		user.setFirstName(sc.next());
		System.out.print("Enter Last Name: ");
		user.setLastName(sc.next());
		System.out.print("Enter Password: ");
		user.setPassword(sc.next());
		try {
			String userId=userService.addUser(user);
			System.out.println("\n"+environment.getProperty("API.USER_SIGNUP_SUCCESS")+userId);
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void signIn() {
		System.out.print("\nEnter User Id: ");
		String id=sc.next();
		System.out.print("Enter Password: ");
		String password=sc.next();
		try {
			id=userService.validateUser(id, password);
			System.out.println("\n"+environment.getProperty("API.USER_LOGIN_SUCCESS")+id);
			while(true) {
				System.out.println("\nPlease Enter...\n1.) User Details\n2.) Update Name\n3.) Update Password\n4.) Recharge Wallet\n5.) Search & Book Flight\n6.) Update Booking\n7.) Cancel Booking\n0.) Log Out");
				int opt=sc.nextInt();
				if(opt==0) return;
				else if(opt==1) userDetails(id);
				else if(opt==2) updateName(id);
				else if(opt==3) updatePassword(id);
				else if(opt==4) rechargeWallet(id);
				else if(opt==5) searchFlight(id);
				else if(opt==6) updateBooking(id);
				else if(opt==7) cancelBooking(id);
				else System.out.println("\n"+environment.getProperty("UI.INVALID_OPTION"));
			}
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void userDetails(String id) {
		try {
			User user=userService.getUser(id);
			System.out.println("\nUser Details...");
			System.out.println("User Id: "+user.getUserId());
			System.out.println("User Name: "+user.getFirstName()+" "+user.getLastName());
			System.out.println("Wallet Balance: "+user.getWalletBalance());
			Set<Booking> bookings=user.getBookings();
			int i=1;
			for(Booking booking : bookings) {
				System.out.println("\nBooking"+i+"...");
				System.out.println("Booking Id: "+booking.getBookingId());
				System.out.println("Flight Id: "+booking.getFlightId());
				System.out.println("Flight Name: "+booking.getFlightName());
				System.out.println("Source: "+booking.getSource()+"\tDeparture Time: "+booking.getDeparture());
				System.out.println("Destination: "+booking.getDestination()+"\tArrival Time: "+booking.getArrival());
				System.out.println("Date of Journey: "+booking.getDoj());
				System.out.println("Booked On: "+booking.getBookedOn());
				System.out.println("Amount: "+booking.getAmount());
				Set<Passenger> passengers=booking.getPassengers();
				int j=1;
				for(Passenger p : passengers) {
					System.out.println("\nPassenger"+j+"...");
					System.out.println("Passenger Id: "+p.getPassengerId());
					System.out.println("Passenger Name: "+p.getfName()+" "+p.getlName());
					System.out.println("Seat No: "+p.getSeat());
					j++;
				}
				i++;
			}
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void updateName(String id) {
		System.out.print("\nEnter First Name: ");
		String fName=sc.next();
		System.out.print("Enter Last Name: ");
		String lName=sc.next();
		try {
			id=userService.updateUserName(id, fName, lName);
			System.out.println("\n"+environment.getProperty("API.USER_NAME_UPDATED")+id);
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void updatePassword(String id) {
		System.out.print("\nEnter New Password: ");
		String password=sc.next();
		try {
			id=userService.updatePassword(id, password);
			System.out.println("\n"+environment.getProperty("API.USER_PASSWORD_UPDATED")+id);
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void rechargeWallet(String id) {
		System.out.print("\nEnter amount: ");
		Double amount=sc.nextDouble();
		try {
			userService.rechargeWallet(id, amount);
			System.out.println("\n"+environment.getProperty("API.WALLET_RECHARGE_SUCCESS"));
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void searchFlight(String userId) {
		System.out.print("\nEnter Source: ");
		String source=sc.next();
		System.out.print("Enter Destination: ");
		String dest=sc.next();
		System.out.print("Enter Date of Journey: ");
		String s=sc.next();
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yy");
		LocalDate doj= LocalDate.parse(s, formatter);
		try {
			List<FinalFlight> flightList = flightService.searchFlights(doj, source, dest);
			for(FinalFlight fl : flightList) {
				System.out.println("\nFlight No: "+fl.getFlightNo());
				System.out.println("Vendor Name: "+fl.getVendor());
				System.out.println("DOJ: "+fl.getDateOfJourney()+"\tAvailable Seats: "+fl.getSeat());
				System.out.println("Source: "+fl.getSource()+"\tDeparture Time: "+fl.getDepaTime());
				System.out.println("Destination: "+fl.getDest()+"\t Arrival Time: "+fl.getArriTime());
				System.out.println("No of Stops: "+fl.getNoOfstops()+"\tTotal Distance: "+fl.getDistance()+"km");
				System.out.println("Fare: "+fl.getFare());
			}
			System.out.print("\nEnter Flight No to Book: ");
			String fNo=sc.next();
			FinalFlight sFlight=null;
			for(FinalFlight fl : flightList) {
				if(fNo.equals(fl.getFlightNo())) {
					sFlight=fl;
					break;
				}
			}
			if(sFlight==null) System.out.println("\n"+environment.getProperty("API.INVALID_SELECTION"));
			else {
				System.out.print("Enter Number of Passangers: ");
				int n=sc.nextInt();
				Set<Passenger> passengers=new LinkedHashSet<Passenger>();
				for(int i=1;i<=n;i++) {
					Passenger passenger=new Passenger();
					System.out.print("Enter Passanger"+i+" First Name: ");
					passenger.setfName(sc.next());
					System.out.print("Enter Passanger"+i+" Last Name: ");
					passenger.setlName(sc.next());
					System.out.print("Enter Seat No: ");
					passenger.setSeat(sc.next());
					passengers.add(passenger);
				}
				Integer bookingId=bookingService.bookFlight(userId, sFlight, passengers);
				System.out.println("\n"+environment.getProperty("API.BOOKING_SUCCESS")+bookingId);
			}
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void updateBooking(String userId) {
		System.out.print("\nEnter Booking Id: ");
		Integer bookingId=sc.nextInt();
		System.out.print("Enter Number of Passangers: ");
		int n=sc.nextInt();
		Set<Passenger> passengers=new LinkedHashSet<Passenger>();
		for(int i=1;i<=n;i++) {
			Passenger passenger=new Passenger();
			System.out.print("Enter Passanger"+i+" First Name: ");
			passenger.setfName(sc.next());
			System.out.print("Enter Passanger"+i+" Last Name: ");
			passenger.setlName(sc.next());
			System.out.print("Enter Seat No: ");
			passenger.setSeat(sc.next());
			passengers.add(passenger);
		}
		try {
			bookingId=bookingService.updateBooking(userId, bookingId, passengers);
			System.out.println("\n"+environment.getProperty("API.BOOKING_UPDATED")+bookingId);
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}
	
	public void cancelBooking(String userId) {
		System.out.print("\nEnter Booking Id: ");
		Integer bookingId=sc.nextInt();
		try {
			bookingId=bookingService.cancelBooking(userId, bookingId);
			System.out.println("\n"+environment.getProperty("API.BOOKING_CANCELLED")+bookingId);
		} catch(Exception e) {
			System.out.println("\n"+environment.getProperty(e.getMessage()));
		}
	}

}
