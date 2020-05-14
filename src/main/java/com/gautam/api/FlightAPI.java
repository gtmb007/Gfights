package com.gautam.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gautam.model.Booking;
import com.gautam.model.Flight;
import com.gautam.model.User;
import com.gautam.service.AdminService;
import com.gautam.service.BookingService;
import com.gautam.service.FlightService;
import com.gautam.service.UserService;

@RestController
@RequestMapping(value="/flight")
public class FlightAPI {

	@Autowired
	private FlightService flightService;
	
	@Autowired 
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private Environment environment;
	
	@GetMapping(value="/admin/{id}/{password}")
	public ResponseEntity<String> adminLogin(@PathVariable String id, @PathVariable String password) throws Exception {
		try {
			String adminId=adminService.validateAdmin(id, password);
			String message=environment.getProperty("API.ADMIN_LOGIN_SUCCESS")+adminId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PostMapping(value="/user")
	public ResponseEntity<String> userSignup(@RequestBody User user) throws Exception {
		try {
			String userId=userService.addUser(user);
			String message=environment.getProperty("API.USER_SIGNUP_SUCCESS")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	} 
	
	@GetMapping(value="/user/{id}/{password}")
	public ResponseEntity<String> userLogin(@PathVariable String id, @PathVariable String password) throws Exception {
		try {
			String userId=userService.validateUser(id, password);
			String message=environment.getProperty("API.USER_LOGIN_SUCCESS")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable String id) throws Exception {
		try {
			User user=userService.getUser(id);
			ResponseEntity<User> response=new ResponseEntity<User>(user, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/user/name/{id}")
	public ResponseEntity<String> updateUserName(@PathVariable String id, @RequestBody User user) throws Exception {
		try {
			String userId=userService.updateUserName(id, user.getFirstName(), user.getLastName());
			String message=environment.getProperty("API.USER_NAME_UPDATED")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/user/password/{id}")
	public ResponseEntity<String> updatePassword(@PathVariable String id, @RequestBody User user) throws Exception {
		try {
			String userId=userService.updatePassword(id, user.getPassword());
			String message=environment.getProperty("API.USER_PASSWORD_UPDATED")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/user/{id}/{amount}")
	public ResponseEntity<String> rechargeWallet(@PathVariable String id, @PathVariable Double amount) throws Exception {
		try {
			userService.rechargeWallet(id, amount);
			String message=environment.getProperty("API.WALLET_RECHARGE_SUCCESS");
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/")
	public ResponseEntity<Set<Flight>> getFlights() throws Exception {
		try {
			Set<Flight> hotels=flightService.getFlights();
			ResponseEntity<Set<Flight>> response=new ResponseEntity<Set<Flight>>(hotels, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/booking/{userId}")
	public ResponseEntity<String> bookHotel(@PathVariable String userId, @RequestBody Booking booking) throws Exception {
		try {
			Integer bookingId=bookingService.bookFlight(userId, booking.getSource(), booking.getDestination(), booking.getDoj(), booking.getFlightId(), booking.getPassengers());
			String message=environment.getProperty("API.BOOKING_SUCCESS")+bookingId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/booking/{userId}/{bookingId}")
	public ResponseEntity<String> updateBooking(@PathVariable String userId, @PathVariable Integer bookingId, @RequestBody Booking booking) throws Exception {
		try {
			Integer bId=bookingService.updateBooking(userId, bookingId, booking.getPassengers());
			String message=environment.getProperty("API.BOOKING_UPDATED")+bId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@DeleteMapping(value="/booking/{userId}/{bookingId}")
	public ResponseEntity<String> cancelBooking(@PathVariable String userId, @PathVariable Integer bookingId) throws Exception {
		try {
			Integer bId=bookingService.cancelBooking(userId, bookingId);
			String message=environment.getProperty("API.BOOKING_CANCELLED")+bId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
}
