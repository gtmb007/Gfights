package com.gautam.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class FinalFlight {
	private String flightNo;
	private String vendor;
	private String source;
	private String dest;
	private LocalTime arriTime;
	private LocalTime depaTime;
	private LocalDate dateOfJourney;
	private Integer seat;
	private Integer noOfstops;
	private Integer distance;
	private Double fare; 
	
	public LocalDate getDateOfJourney() {
		return dateOfJourney;
	}
	public void setDateOfJourney(LocalDate dateOfJourney) {
		this.dateOfJourney = dateOfJourney;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public LocalTime getArriTime() {
		return arriTime;
	}
	public void setArriTime(LocalTime arriTime) {
		this.arriTime = arriTime;
	}
	public LocalTime getDepaTime() {
		return depaTime;
	}
	public void setDepaTime(LocalTime depaTime) {
		this.depaTime = depaTime;
	}
	public Integer getSeat() {
		return seat;
	}
	public void setSeat(Integer seat) {
		this.seat = seat;
	}
	public Integer getNoOfstops() {
		return noOfstops;
	}
	public void setNoOfstops(Integer noOfstops) {
		this.noOfstops = noOfstops;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Double getFare() {
		return fare;
	}
	public void setFare(Double fare) {
		this.fare = fare;
	}
}