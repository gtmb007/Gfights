package com.gautam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.gautam.model.Booking;
import com.gautam.model.FinalFlight;
import com.gautam.model.Flight;
import com.gautam.model.Passenger;

public interface FlightService {
	
	public String addFlight(Flight flight) throws Exception;
	public Set<Flight> getFlights() throws Exception;
	public List<FinalFlight> searchFlights(LocalDate doj, String source, String dest) throws Exception;
	public String removeFlight(String flightNo) throws Exception;
	public Boolean validateBooking(String flightId, LocalDate doj, Integer noOfPassengers) throws Exception; 
	public Integer bookFlight(FinalFlight fFlight, Set<Passenger> passengers, Double amount) throws Exception;
	public Booking getBooking(Integer bookingId) throws Exception; 
	public Integer updateBooking(Integer bookingId, Set<Passenger> passengers, Double newAmount) throws Exception;
	public Integer cancelBooking(Integer bookingId) throws Exception;
	
}
