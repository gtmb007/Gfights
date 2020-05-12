package com.gautam.dao;

import java.time.LocalDate;
import java.util.Set;

import com.gautam.model.Booking;
import com.gautam.model.Flight;
import com.gautam.model.Passenger;

public interface FlightDAO {
	
	public String addFlight(Flight flight) throws Exception;
	public Set<Flight> getFlights() throws Exception;
	public Double calculateAmount(String flightId, LocalDate doj, Integer noOfPassengers) throws Exception; 
	public Integer bookFlight(String source, String destination, LocalDate doj, String flightId, Set<Passenger> passengers, Double amount) throws Exception;
	public Booking getBooking(Integer bookingId) throws Exception; 
	public Integer updateBooking(Integer bookingId, Set<Passenger> passengers, Double newAmount) throws Exception;
	public Integer cancelBooking(Integer bookingId) throws Exception;
	
}
