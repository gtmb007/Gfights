package com.gautam.service;

import java.time.LocalDate;
import java.util.Set;

import com.gautam.model.Passenger;

public interface BookingService {

	public Integer bookFlight(String userId, String source, String destination, LocalDate doj, String flightId, Set<Passenger> passengers) throws Exception;
	
	public Integer updateBooking(String userId, Integer bookingId, Set<Passenger> passengers) throws Exception;
	
	public Integer cancelBooking(String userId, Integer bookingId) throws Exception;
	
}
