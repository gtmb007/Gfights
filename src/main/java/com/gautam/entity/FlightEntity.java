package com.gautam.entity;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name="flight")
public class FlightEntity {
	
	@Id
	@Column(name="flight_id")
	private String flightId;
	
	@Column(name="flight_name")
	private String flightName;
	
	@Column(name="base_fare")
	private Double baseFare;
	
	@ElementCollection
    @CollectionTable(name = "seat_map", 
      joinColumns = {@JoinColumn(name = "flight_id", referencedColumnName = "flight_id")})
    @MapKeyColumn(name = "doj")
    @Column(name = "available_seats")
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
