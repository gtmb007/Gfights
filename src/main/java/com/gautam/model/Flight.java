package com.gautam.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Flight {
	
	private String flightNo;
	private String vendor;
	private Map<LocalDate, Integer> seatMap;
	private List<Rout> rout;
	private Integer totalSeats;
	private List<DayOfWeek> dayList = new ArrayList<DayOfWeek>();
	
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
	public Map<LocalDate, Integer> getSeatMap() {
		return seatMap;
	}
	public void setSeatMap(Map<LocalDate, Integer> seatMap) {
		this.seatMap = seatMap;
	}
	public List<Rout> getRout() {
		return rout;
	}
	public void setRout(List<Rout> rout) {
		this.rout = rout;
	}
	public Integer getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}
	public List<DayOfWeek> getDayList() {
		return dayList;
	}
	public void setDayList(List<DayOfWeek> dayList) {
		this.dayList = dayList;
	}

}