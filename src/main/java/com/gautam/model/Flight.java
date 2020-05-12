package com.gautam.model;

import java.time.LocalDate;
import java.util.Map;


public class Flight {
	
	private String flightId;
	private String flightName;
	private Double baseFare;
	private Map<LocalDate, Integer> seatMap;
	
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
	public Double getBaseFare() {
		return baseFare;
	}
	public void setBaseFare(Double baseFare) {
		this.baseFare = baseFare;
	}
	public Map<LocalDate, Integer> getSeatMap() {
		return seatMap;
	}
	public void setSeatMap(Map<LocalDate, Integer> seatMap) {
		this.seatMap = seatMap;
	}
	
}
