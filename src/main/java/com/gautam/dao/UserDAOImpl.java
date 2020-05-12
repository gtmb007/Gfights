package com.gautam.dao;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gautam.entity.BookingEntity;
import com.gautam.entity.PassengerEntity;
import com.gautam.entity.UserEntity;
import com.gautam.model.Booking;
import com.gautam.model.Passenger;
import com.gautam.model.User;

@Repository(value="userDAO")
public class UserDAOImpl implements UserDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String addUser(User user) throws Exception {
		UserEntity userEntity=entityManager.find(UserEntity.class, user.getUserId());
		String userId=null;
		if(userEntity==null) {
			userEntity=new UserEntity();
			userEntity.setUserId(user.getUserId());
			userEntity.setPassword(user.getPassword());
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());
			userEntity.setWalletBalance(0d);
			userEntity.setBookings(new LinkedHashSet<BookingEntity>());
			entityManager.persist(userEntity);
			userId=userEntity.getUserId();
		}
		return userId;
	}
	
	@Override
	public User getUser(String userId) throws Exception {
		UserEntity userEntity=entityManager.find(UserEntity.class, userId);
		User user=null;
		if(userEntity!=null) {
			user=new User();
			user.setUserId(userEntity.getUserId());
			user.setPassword(userEntity.getPassword());
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setWalletBalance(userEntity.getWalletBalance());
			Set<BookingEntity> bookingEntities=userEntity.getBookings();
			Set<Booking> bookings=new LinkedHashSet<Booking>();
			for(BookingEntity bookingEntity : bookingEntities) {
				Booking booking=new Booking();
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
				Set<Passenger> passengers=new HashSet<Passenger>();
				for(PassengerEntity pEntity : pEntities) {
					Passenger p=new Passenger();
					p.setPassengerId(pEntity.getPassengerId());
					p.setfName(pEntity.getfName());
					p.setlName(pEntity.getlName());
					p.setSeat(pEntity.getSeat());
					passengers.add(p);
				}
				booking.setPassengers(passengers);
				bookings.add(booking);
			}
			user.setBookings(bookings);
		}
		return user;
	}
	
	@Override
	public String updateUserName(String userId, String firstName, String lastName) throws Exception {
		UserEntity userEntity=entityManager.find(UserEntity.class, userId);
		String id=null;
		if(userEntity!=null) {
			userEntity.setFirstName(firstName);
			userEntity.setLastName(lastName);
			id=userEntity.getUserId();
		}
		return id;
	}
	
	@Override
	public String updatePassword(String userId, String password) throws Exception {
		UserEntity userEntity=entityManager.find(UserEntity.class, userId);
		String id=null;
		if(userEntity!=null) {
			userEntity.setPassword(password);
			id=userEntity.getUserId();
		}
		return id;
	}
	
	@Override
	public Boolean rechargeWallet(String userId, Double amount) throws Exception {
		UserEntity userEntity=entityManager.find(UserEntity.class, userId);
		Boolean message=false;
		if(userEntity!=null) {
			userEntity.setWalletBalance(userEntity.getWalletBalance()+amount);
			message=true;
		}
		return message;
	}
	
	@Override
	public Boolean payment(String userId, Double amount) throws Exception {
		UserEntity userEntity=entityManager.find(UserEntity.class, userId);
		Boolean message=false;
		if(userEntity!=null && userEntity.getWalletBalance()>=amount) {
			userEntity.setWalletBalance(userEntity.getWalletBalance()-amount);
			message=true;
		}
		return message;
	}
	
	@Override
	public void addBooking(String userId, Integer bookingId) throws Exception {
		UserEntity userEntity=entityManager.find(UserEntity.class, userId);
		BookingEntity bookingEntity=entityManager.find(BookingEntity.class, bookingId);
		if(userEntity!=null && bookingEntity!=null) {
			Set<BookingEntity> bookingEntities=userEntity.getBookings();
			bookingEntities.add(bookingEntity);
			userEntity.setBookings(bookingEntities);
		}
	}

}
