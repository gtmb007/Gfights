package com.gautam.model;

import java.util.Set;

public class User {

	private String userId;
	private String firstName;
	private String lastName;
	private String password;
	private Double walletBalance;
	private Set<Booking> bookings;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Double getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(Double walletBalance) {
		this.walletBalance = walletBalance;
	}
	public Set<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
	
}
