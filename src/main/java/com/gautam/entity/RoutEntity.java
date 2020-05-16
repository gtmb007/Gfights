package com.gautam.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rout")
public class RoutEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "rout_id")
	private int rout_id;
	
	@ElementCollection
	@Column(name ="location")
	private List<String> location = new ArrayList<String>();
	
	@ElementCollection
	@Column(name="arrival_time")
	private List<LocalTime> arrivalTime = new ArrayList<LocalTime>();;
	
	@ElementCollection
	@Column(name = "dept_time")
	private List<LocalTime> deptTime = new ArrayList<LocalTime>();;
	
	@ElementCollection
	@Column(name = "dist")
	private List<Integer> dist = new ArrayList<Integer>();
	
	@ElementCollection
	@Column(name="base_fare")
	private List<Double> baseFare;

	public int getRout_id() {
		return rout_id;
	}

	public void setRout_id(int rout_id) {
		this.rout_id = rout_id;
	}

	public List<String> getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public List<LocalTime> getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(List<LocalTime> arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public List<LocalTime> getDeptTime() {
		return deptTime;
	}

	public void setDeptTime(List<LocalTime> deptTime) {
		this.deptTime = deptTime;
	}

	public List<Integer> getDist() {
		return dist;
	}

	public void setDist(List<Integer> dist) {
		this.dist = dist;
	}

	public List<Double> getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(List<Double> baseFare) {
		this.baseFare = baseFare;
	}
	
}