package com.gautam.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gautam.entity.BookingEntity;
import com.gautam.entity.FlightEntity;
import com.gautam.entity.PassengerEntity;
import com.gautam.model.Booking;
import com.gautam.model.Flight;
import com.gautam.model.Passenger;

@Repository(value="flightDAO")
public class FlightDAOImpl implements FlightDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String addFlight(Flight flight) throws Exception {
		FlightEntity flightEntity=entityManager.find(FlightEntity.class, flight.getFlightId());
		String flightId=null;
		if(flightEntity==null) {
			flightEntity=new FlightEntity();
			flightEntity.setFlightId(flight.getFlightId());
			flightEntity.setFlightName(flight.getFlightName());
			
			flightEntity.setSeatMap(flight.getSeatMap());
			
			flightEntity.setBaseFare(flight.getBaseFare());
			entityManager.persist(flightEntity);
			flightId=flightEntity.getFlightId();
		}
		return flightId;
	}
	
	@Override
	public Set<Flight> getFlights() throws Exception {
		String queryString="SELECT f FROM FlightEntity f";
		Query query=entityManager.createQuery(queryString);
		List<FlightEntity> flightEntities=query.getResultList();
		Set<Flight> flights=new LinkedHashSet<Flight>();
		for(FlightEntity flightEntity : flightEntities) {
			Flight flight=new Flight();
			flight.setFlightId(flightEntity.getFlightId());
			flight.setFlightName(flightEntity.getFlightName());
			
			Map<LocalDate, Integer> seatMap1=flightEntity.getSeatMap();
			Map<LocalDate, Integer> seatMap=new LinkedHashMap<LocalDate, Integer>();
			for(LocalDate key : seatMap1.keySet()) {
				seatMap.put(key, seatMap1.get(key));
			}
			flight.setSeatMap(seatMap);
			
			flight.setBaseFare(flightEntity.getBaseFare());
			flights.add(flight);
		}
		return flights;
	}
	
	@Override
	public Double calculateAmount(String flightId, LocalDate doj, Integer noOfPassengers) throws Exception {
		FlightEntity flightEntity=entityManager.find(FlightEntity.class, flightId);
		if(flightEntity==null) throw new Exception("DAO.FLIGHT_NOT_FOUND");
		Map<LocalDate, Integer> seatMap=flightEntity.getSeatMap();
		Double amount=null;
		if(noOfPassengers<=seatMap.get(doj)) {
			amount=flightEntity.getBaseFare()*noOfPassengers;
		}
		return amount;
	}
	
	@Override
	public Integer bookFlight(String source, String destination, LocalDate doj, String flightId, Set<Passenger> passengers, Double amount) throws Exception {
		FlightEntity flightEntity=entityManager.find(FlightEntity.class, flightId);
		BookingEntity bookingEntity=new BookingEntity();
		bookingEntity.setFlightId(flightEntity.getFlightId());
		bookingEntity.setFlightName(flightEntity.getFlightName());
		bookingEntity.setSource(source);
		bookingEntity.setDeparture(LocalTime.now());
		bookingEntity.setDestination(destination);
		bookingEntity.setArrival(LocalTime.now().plusHours(2));
		bookingEntity.setDoj(doj);
		bookingEntity.setBookedOn(LocalDateTime.now());
		bookingEntity.setAmount(amount);
		Set<PassengerEntity> pEntities=new LinkedHashSet<PassengerEntity>();
		for(Passenger p : passengers) {
			PassengerEntity pEntity=new PassengerEntity();
			pEntity.setfName(p.getfName());
			pEntity.setlName(p.getlName());
			pEntities.add(pEntity);
		}
		bookingEntity.setPassengers(pEntities);
		entityManager.persist(bookingEntity);
		Map<LocalDate, Integer> seatMap=flightEntity.getSeatMap();
		seatMap.put(doj, seatMap.get(doj)-passengers.size());
		flightEntity.setSeatMap(seatMap);
		return bookingEntity.getBookingId();
	}
	
	@Override
	public Booking getBooking(Integer bookingId) throws Exception {
		BookingEntity bookingEntity=entityManager.find(BookingEntity.class, bookingId);
		Booking booking=null;
		if(bookingEntity!=null) {
			booking=new Booking();
			booking.setBookingId(bookingEntity.getBookingId());
			booking.setFlightId(bookingEntity.getFlightId());
			booking.setFlightName(bookingEntity.getFlightName());
			booking.setSource(bookingEntity.getSource());
			booking.setDeparture(bookingEntity.getDeparture());
			booking.setDestination(bookingEntity.getDestination());
			booking.setArrival(bookingEntity.getArrival());
			booking.setDoj(bookingEntity.getDoj());
			booking.setBookedOn(bookingEntity.getBookedOn());
			booking.setAmount(bookingEntity.getAmount());
			Set<PassengerEntity> pEntities=bookingEntity.getPassengers();
			Set<Passenger> passengers=new LinkedHashSet<Passenger>();
			for(PassengerEntity pEntity : pEntities) {
				Passenger p=new Passenger();
				p.setPassengerId(pEntity.getPassengerId());
				p.setfName(pEntity.getfName());
				p.setlName(pEntity.getlName());
				p.setSeat(pEntity.getSeat());
				passengers.add(p);
			}
			booking.setPassengers(passengers);
		}
		return booking;
	}
	
	@Override
	public Integer updateBooking(Integer bookingId, Set<Passenger> passengers, Double amount) throws Exception {
		BookingEntity bookingEntity=entityManager.find(BookingEntity.class, bookingId);
		Integer n=passengers.size()-bookingEntity.getPassengers().size();
		FlightEntity flightEntity=entityManager.find(FlightEntity.class, bookingEntity.getFlightId());
		Map<LocalDate, Integer> seatMap=flightEntity.getSeatMap();
		LocalDate doj=bookingEntity.getDoj();
		seatMap.put(doj, seatMap.get(doj)-n);
		flightEntity.setSeatMap(seatMap);
		Set<PassengerEntity> pEntities=new LinkedHashSet<PassengerEntity>();
		for(Passenger p : passengers) {
			PassengerEntity pEntitity=new PassengerEntity();
			pEntitity.setfName(p.getfName());
			pEntitity.setlName(p.getlName());
			pEntities.add(pEntitity);
		}
		bookingEntity.setPassengers(pEntities);
		bookingEntity.setAmount(amount);
		return bookingEntity.getBookingId();
	}
	
	@Override
	public Integer cancelBooking(Integer bookingId) throws Exception {
		BookingEntity bookingEntity=entityManager.find(BookingEntity.class, bookingId);
		Integer bId=null;
		if(bookingEntity!=null) {
			FlightEntity flightEntity=entityManager.find(FlightEntity.class, bookingEntity.getFlightId());
			Map<LocalDate, Integer> seatMap=flightEntity.getSeatMap();
			LocalDate doj=bookingEntity.getDoj();
			seatMap.put(doj, seatMap.get(doj)+bookingEntity.getPassengers().size());
			flightEntity.setSeatMap(seatMap);
			entityManager.remove(bookingEntity);
			bId=bookingEntity.getBookingId();
		}
		return bId;
	}
	
}
