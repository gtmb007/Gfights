package com.gautam.model;

import java.time.LocalTime;
import java.util.ArrayList;
//import java.sql.Time;
import java.util.List;

public class Rout {
	private int rout_id;
	List<String> location = new ArrayList<String>();
	List<LocalTime> arrivalTime  = new ArrayList<LocalTime>();
	List<LocalTime> deptTime = new ArrayList<LocalTime>();
	List<Integer> dist = new ArrayList<Integer>();
	private List<Double> baseFare;
	
	public int getRout_id() {
		return rout_id;
	}
	public void setRout_id(int rout_id) {
		this.rout_id = rout_id;
	}
	public List<Integer> getDist() {
		return dist;
	}
	public void setDist(List<Integer> dist) {
		this.dist = dist;
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
	public List<Double> getBaseFare() {
		return baseFare;
	}
	public void setBaseFare(List<Double> baseFare) {
		this.baseFare = baseFare;
	}
	
}