package com.gautam.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "flight")
public class FlightEntity {

	@Id
	@Column(name = "flight_no")
	private String flightNo;
	
	@Column(name = "vendor")
	private String vendor;
	
	@Column(name="total_seats")
	private Integer totalSeats;
	
	@ElementCollection
	@CollectionTable(name = "seat_map", 
	  joinColumns = {@JoinColumn(name = "flight_no", referencedColumnName = "flight_no")})
	@MapKeyColumn(name = "doj")
	@Column(name = "available_seats")
	private Map<LocalDate, Integer> seatMap;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="flight_no")
	private List<RoutEntity> routEntity;
	
	@ElementCollection
	@Column(name = "day_list")
	List<DayOfWeek> dayList = new ArrayList<DayOfWeek>();

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

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Map<LocalDate, Integer> getSeatMap() {
		return seatMap;
	}

	public void setSeatMap(Map<LocalDate, Integer> seatMap) {
		this.seatMap = seatMap;
	}

	public List<RoutEntity> getRoutEntity() {
		return routEntity;
	}

	public void setRoutEntity(List<RoutEntity> routEntity) {
		this.routEntity = routEntity;
	}

	public List<DayOfWeek> getDayList() {
		return dayList;
	}

	public void setDayList(List<DayOfWeek> dayList) {
		this.dayList = dayList;
	}
	
}
