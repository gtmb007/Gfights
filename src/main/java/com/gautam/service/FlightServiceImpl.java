package com.gautam.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.dao.FlightDAO;
import com.gautam.model.Booking;
import com.gautam.model.Flight;
import com.gautam.model.Passenger;

@Service(value="flightService")
@Transactional
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightDAO flightDAO;
	
	@Override
	public String addFlight(Flight flight) throws Exception {
		String flightId=flightDAO.addFlight(flight);
		if(flightId==null) throw new Exception("Service.FLIGHT_ALREADY_EXIST");
		return flightId;
	}
	
	@Override
	public Set<Flight> getFlights() throws Exception {
		Set<Flight> flights=flightDAO.getFlights();
		if(flights.isEmpty()) throw new Exception("Service.FLIGHT_NOT_FOUND");
		return flights;
	} 
	
	@Override
	public Double calculateAmount(String flightId, LocalDate doj, Integer noOfPassengers) throws Exception {
		Double amount=flightDAO.calculateAmount(flightId, doj, noOfPassengers);
		if(amount==null) throw new Exception("Service.SEATS_NOT_AVAILABLE");
		return amount;
	}
	
	@Override
	public Integer bookFlight(String source, String destination, LocalDate doj, String flightId, Set<Passenger> passengers, Double amount) throws Exception {
		return flightDAO.bookFlight(source, destination, doj, flightId, passengers, amount);
	}
	
	@Override
	public Booking getBooking(Integer bookingId) throws Exception {
		Booking booking=flightDAO.getBooking(bookingId);
		if(booking==null) throw new Exception("Service.BOOKING_NOT_FOUND");
		return booking;
	}
	
	@Override
	public Integer updateBooking(Integer bookingId, Set<Passenger> passengers, Double amount) throws Exception {
		Integer bId=flightDAO.updateBooking(bookingId, passengers, amount);
		if(bId==null) throw new Exception("Service.BOOKING_UPDATION_FAILED");
		return bookingId;
	}
	
	@Override
	public Integer cancelBooking(Integer bookingId) throws Exception {
		Integer bId=flightDAO.cancelBooking(bookingId);
		if(bId==null) throw new Exception("Service.BOOKING_CANCELATION_FAILED");
		return bookingId;
	}
	
}
