package com.gautam.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.model.Booking;
import com.gautam.model.FinalFlight;
import com.gautam.model.Passenger;

@Service(value="bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {

	@Autowired
	private FlightService flightService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Integer bookFlight(String userId, FinalFlight fFlight, Set<Passenger> passengers) throws Exception {
		Integer bookingId=null;
		if(flightService.validateBooking(fFlight.getFlightNo(), fFlight.getDateOfJourney(), passengers.size())) {
		Double amount=fFlight.getFare()*passengers.size();
		userService.payment(userId, amount);
		try {
			bookingId=flightService.bookFlight(fFlight, passengers, amount);
			userService.addBooking(userId, bookingId);
		} catch(Exception e) {
			userService.rechargeWallet(userId, amount);
			throw new Exception(e.getMessage());
		}
		}
		return bookingId;
	}
	
	@Override
	public Integer updateBooking(String userId, Integer bookingId, Set<Passenger> passengers) throws Exception {
		Booking booking=flightService.getBooking(bookingId);
		int n=booking.getPassengers().size();
		if(flightService.validateBooking(booking.getFlightId(), booking.getDoj(), passengers.size()-n)) {
			Double newAmount=(booking.getAmount()/n)*(passengers.size()-n);
			userService.payment(userId, newAmount);
			Double amount=(booking.getAmount()/n)*passengers.size();
			try {
				bookingId=flightService.updateBooking(bookingId, passengers, amount);
			} catch(Exception e) {
				userService.rechargeWallet(userId, newAmount);
				throw new Exception(e.getMessage());
			}
		}
		return bookingId;
	}
	
	@Override
	public Integer cancelBooking(String userId, Integer bookingId) throws Exception {
		Booking booking=flightService.getBooking(bookingId);
		Double amount=booking.getAmount();
		bookingId=flightService.cancelBooking(bookingId);
		userService.rechargeWallet(userId, amount);
		return bookingId;
	}
	
}
