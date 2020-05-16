package com.gautam.dao;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.gautam.entity.RoutEntity;
import com.gautam.model.Booking;
import com.gautam.model.FinalFlight;
import com.gautam.model.Flight;
import com.gautam.model.Passenger;
import com.gautam.model.Rout;

@Repository(value="flightDAO")
public class FlightDAOImpl implements FlightDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String addFlight(Flight flight) throws Exception {
		FlightEntity flightEntity = entityManager.find(FlightEntity.class,flight.getFlightNo());
		String flightId=null;
		if(flightEntity==null) {
			flightEntity = new FlightEntity();
			flightEntity.setFlightNo(flight.getFlightNo());
			flightEntity.setVendor(flight.getVendor());
			flightEntity.setTotalSeats(flight.getTotalSeats());
			flightEntity.setSeatMap(flight.getSeatMap());
			List<Rout> routes=flight.getRout();
			List<RoutEntity> listRoutEntity=new ArrayList<RoutEntity>();
			for(Rout rout : routes) {
				RoutEntity routEntity = new RoutEntity();
				routEntity.setLocation(rout.getLocation());
				routEntity.setArrivalTime(rout.getArrivalTime());
				routEntity.setDeptTime(rout.getDeptTime());;
				routEntity.setDist(rout.getDist());
				routEntity.setBaseFare(rout.getBaseFare());
				listRoutEntity.add(routEntity);
			}
			flightEntity.setRoutEntity(listRoutEntity);
			flightEntity.setDayList(flight.getDayList());
			entityManager.persist(flightEntity);
			flightId=flightEntity.getFlightNo();
		}
		return flightId;
	}
	
	@Override
	public Set<Flight> getFlights() throws Exception {
		String queryString = "select f.flightNo from FlightEntity f";
		Query query=entityManager.createQuery(queryString);
		List<String> flightNoList = query.getResultList();
		
		Set<Flight> flightList = new HashSet<Flight>();
		for(String flightNo: flightNoList) {
			FlightEntity fEntity = entityManager.find(FlightEntity.class, flightNo);
			Flight flight = new Flight();
			
			flight.setFlightNo(flightNo);
			flight.setVendor(fEntity.getVendor());
			flight.setTotalSeats(fEntity.getTotalSeats());

			Map<LocalDate, Integer> seatMap1=fEntity.getSeatMap();
			Map<LocalDate, Integer> seatMap=new LinkedHashMap<LocalDate, Integer>();
			for(LocalDate key : seatMap1.keySet()) {
				seatMap.put(key, seatMap1.get(key));
			}
			flight.setSeatMap(seatMap);
			
			List<Rout> routList = new ArrayList<Rout>();
			for(RoutEntity rEntity : fEntity.getRoutEntity()) {
				Rout rout = new Rout();
				
				List<String> loc = new ArrayList<String>();
				for(String l : rEntity.getLocation()) {
					loc.add(l);
				}
				rout.setLocation(loc);
				
				List<LocalTime> arrivalTime = new ArrayList<LocalTime>();
				for(LocalTime aTime : rEntity.getArrivalTime()) {
					arrivalTime.add(aTime);
				}
				rout.setArrivalTime(arrivalTime);;
				
				List<LocalTime> deptTime = new ArrayList<LocalTime>();
				for(LocalTime dTime : rEntity.getDeptTime()) {
					deptTime.add(dTime);
				}
				rout.setDeptTime(deptTime);
				
				List<Integer> dis = new ArrayList<Integer>();
				for(Integer d : rEntity.getDist()) {
					dis.add(d);
				}
				rout.setDist(dis);
				
				List<Double> baseFare = new ArrayList<Double>();
				for(Double fare : rEntity.getBaseFare()) {
					baseFare.add(fare);
				}
				rout.setBaseFare(baseFare);
				
				routList.add(rout);
			}
			flight.setRout(routList);

			List<DayOfWeek> day = new ArrayList<DayOfWeek>();
			for(DayOfWeek d : fEntity.getDayList()) {
				day.add(d);
			}
			flight.setDayList(day);
			
			flightList.add(flight);
		}
		return flightList;
	}
	
	@Override
	public List<Flight> searchFlights(DayOfWeek day, String source, String dest) throws Exception{
		String queryString = "select f.flightNo from FlightEntity f";
		Query query = entityManager.createQuery(queryString);
		List<String> fIdList = query.getResultList();

		List<Flight> flightList = new ArrayList<Flight>();
		for(String fId : fIdList) {
			Flight flight =  new Flight();
			FlightEntity fEntity = entityManager.find(FlightEntity.class, fId);
			flight.setFlightNo(fEntity.getFlightNo());
			flight.setVendor(fEntity.getVendor());
			flight.setTotalSeats(fEntity.getTotalSeats());
			Map<LocalDate, Integer> seatMap1=fEntity.getSeatMap();
			Map<LocalDate, Integer> seatMap=new LinkedHashMap<LocalDate, Integer>();
			for(LocalDate key : seatMap1.keySet()) {
				seatMap.put(key, seatMap1.get(key));
			}
			flight.setSeatMap(seatMap);
			List<DayOfWeek> days = new ArrayList<DayOfWeek>();
			for(DayOfWeek d : fEntity.getDayList()) {
				days.add(d);
			}
			flight.setDayList(days);
			int flag=0;
			if(days.contains(day)) {
				List<Rout> routList = new ArrayList<Rout>();
				for(RoutEntity rEntity : fEntity.getRoutEntity()) {
					List<String> loc = new ArrayList<String>();
					for(String l : rEntity.getLocation()) {
						loc.add(l);
					}
					int s=loc.indexOf(source);
					int d=loc.indexOf(dest);
					if(s!=-1 && d!=-1 && s<d) {
						flag=1;
						Rout rout = new Rout();
						rout.setLocation(loc);
						
						List<LocalTime> arrivalTime = new ArrayList<LocalTime>();
						for(LocalTime aTime : rEntity.getArrivalTime()) {
							arrivalTime.add(aTime);
						}
						rout.setArrivalTime(arrivalTime);;
						
						List<LocalTime> deptTime = new ArrayList<LocalTime>();
						for(LocalTime dTime : rEntity.getDeptTime()) {
							deptTime.add(dTime);
						}
						rout.setDeptTime(deptTime);;
						
						List<Integer> distance = new ArrayList<Integer>();
						for(Integer dist : rEntity.getDist()) {
							distance.add(dist);
						}
						rout.setDist(distance);
						
						List<Double> baseFare = new ArrayList<Double>();
						for(Double fare : rEntity.getBaseFare()) {
							baseFare.add(fare);
						}
						rout.setBaseFare(baseFare);
						
						routList.add(rout);
					}
				}
				flight.setRout(routList);
			}
			flightList.add(flight);
		}
		return flightList;
	}
	
	@Override
	public String removeFlight(String flightNo) throws Exception{
		FlightEntity flightEntity = entityManager.find(FlightEntity.class, flightNo);
		String fNo=null;
		if(flightEntity!=null) {
			entityManager.remove(flightEntity);
			fNo=flightEntity.getFlightNo();
		}
		return fNo;
	}
	
	@Override
	public Boolean validateBooking(String flightId, LocalDate doj, Integer noOfPassengers) throws Exception {
		FlightEntity flightEntity=entityManager.find(FlightEntity.class, flightId);
		if(flightEntity==null) throw new Exception("DAO.FLIGHT_NOT_FOUND");
		if(noOfPassengers<=flightEntity.getSeatMap().get(doj)) return true;
		return false;
	}
	
	@Override
	public Integer bookFlight(FinalFlight fFlight, Set<Passenger> passengers, Double amount) throws Exception {
		FlightEntity flightEntity=entityManager.find(FlightEntity.class, fFlight.getFlightNo());
		BookingEntity bookingEntity=new BookingEntity();
		bookingEntity.setFlightId(flightEntity.getFlightNo());
		bookingEntity.setFlightName(flightEntity.getVendor());
		bookingEntity.setSource(fFlight.getSource());
		bookingEntity.setDeparture(fFlight.getDepaTime());
		bookingEntity.setDestination(fFlight.getDest());
		bookingEntity.setArrival(fFlight.getArriTime());
		bookingEntity.setDoj(fFlight.getDateOfJourney());
		bookingEntity.setBookedOn(LocalDateTime.now());
		bookingEntity.setAmount(amount);
		Set<PassengerEntity> pEntities=new LinkedHashSet<PassengerEntity>();
		for(Passenger p : passengers) {
			PassengerEntity pEntity=new PassengerEntity();
			pEntity.setfName(p.getfName());
			pEntity.setlName(p.getlName());
			pEntity.setSeat(p.getSeat());
			pEntities.add(pEntity);
		}
		bookingEntity.setPassengers(pEntities);
		entityManager.persist(bookingEntity);
		Map<LocalDate, Integer> seatMap=flightEntity.getSeatMap();
		seatMap.put(fFlight.getDateOfJourney(), seatMap.get(fFlight.getDateOfJourney())-passengers.size());
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
			PassengerEntity pEntity=new PassengerEntity();
			pEntity.setfName(p.getfName());
			pEntity.setlName(p.getlName());
			pEntity.setSeat(p.getSeat());
			pEntities.add(pEntity);
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
			if(flightEntity!=null) {
				Map<LocalDate, Integer> seatMap=flightEntity.getSeatMap();
				LocalDate doj=bookingEntity.getDoj();
				seatMap.put(doj, seatMap.get(doj)+bookingEntity.getPassengers().size());
				flightEntity.setSeatMap(seatMap);
			}
			entityManager.remove(bookingEntity);
			bId=bookingEntity.getBookingId();
		}
		return bId;
	}
	
}
