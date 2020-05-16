package com.gautam.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.dao.FlightDAO;
import com.gautam.model.Booking;
import com.gautam.model.FinalFlight;
import com.gautam.model.Flight;
import com.gautam.model.Passenger;
import com.gautam.model.Rout;

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
	public List<FinalFlight> searchFlights(LocalDate doj, String source, String dest) throws Exception {
		List<Flight> flightList = flightDAO.searchFlights(doj.getDayOfWeek(), source, dest);
		if(flightList.isEmpty()) throw new Exception("Service.FLIGHT_NOT_FOUND");
		List<FinalFlight> finalFlightList = new ArrayList<FinalFlight>();
		for(Flight fl : flightList) {
			List<Rout> routList = fl.getRout();
			for(Rout route : routList) {
				FinalFlight finalFlight = new FinalFlight();
				finalFlight.setFlightNo(fl.getFlightNo());
				finalFlight.setVendor(fl.getVendor());
				finalFlight.setDateOfJourney(doj);
				finalFlight.setSeat(fl.getSeatMap().get(doj));
				finalFlight.setSource(source);
				finalFlight.setDest(dest);
				List<String> location = route.getLocation();
				int s = location.indexOf(source);
				int d = location.indexOf(dest);
				int noOfStops=d-s-1;
				finalFlight.setNoOfstops(noOfStops);
				List<Integer> dist=route.getDist();
				int distance=dist.get(d)-dist.get(s);
				finalFlight.setDistance(distance);
				List<Double> basePrice=route.getBaseFare();
				Double baseFare=basePrice.get(d)-basePrice.get(s);
				Double extraFare=baseFare*(fl.getTotalSeats()-fl.getSeatMap().get(doj))/100;
				finalFlight.setFare(baseFare+extraFare);
				finalFlight.setDepaTime(route.getDeptTime().get(s));
				finalFlight.setArriTime(route.getArrivalTime().get(d));
				finalFlightList.add(finalFlight);
			}
			
		}
		return finalFlightList;
	}
	
	@Override
	public String removeFlight(String flightNo) throws Exception{
		String fId = flightDAO.removeFlight(flightNo);
		if(fId==null) throw new Exception("Service.FLIGHT_NOT_FOUND");
		return fId;
	}
	
	@Override
	public Boolean validateBooking(String flightId, LocalDate doj, Integer noOfPassengers) throws Exception {
		Boolean flag=flightDAO.validateBooking(flightId, doj, noOfPassengers);
		if(!flag) throw new Exception("Service.SEATS_NOT_AVAILABLE");
		return flag;
	}
	
	@Override
	public Integer bookFlight(FinalFlight fFlight, Set<Passenger> passengers, Double amount) throws Exception {
		return flightDAO.bookFlight(fFlight, passengers, amount);
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
