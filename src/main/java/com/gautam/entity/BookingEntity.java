package com.gautam.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="booking")
public class BookingEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="booking_id")
	private Integer bookingId;
	
	@Column(name="flight_id")
	private String flightId;
	
	@Column(name="flight_name")
	private String flightName;
	
	@Column(name="source")
	private String source;
	
	@Column(name="destination")
	private String destination;
	
	@Column(name="doj")
	private LocalDate doj;
	
	@Column(name="departure")
	private LocalTime departure;
	
	@Column(name="arrival")
	private LocalTime arrival;
	
	@Column(name="booked_on")
	private LocalDateTime bookedOn;
	
	@Column(name="amount")
	private Double amount;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="booking_id")
	private Set<PassengerEntity> passengers;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDate getDoj() {
		return doj;
	}

	public void setDoj(LocalDate doj) {
		this.doj = doj;
	}

	public LocalTime getDeparture() {
		return departure;
	}

	public void setDeparture(LocalTime departure) {
		this.departure = departure;
	}

	public LocalTime getArrival() {
		return arrival;
	}

	public void setArrival(LocalTime arrival) {
		this.arrival = arrival;
	}

	public LocalDateTime getBookedOn() {
		return bookedOn;
	}

	public void setBookedOn(LocalDateTime bookedOn) {
		this.bookedOn = bookedOn;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Set<PassengerEntity> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<PassengerEntity> passengers) {
		this.passengers = passengers;
	}
	
}
