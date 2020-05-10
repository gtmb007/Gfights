package com.gautam.service;

import com.gautam.model.User;

public interface UserService {
	
	public String addUser(User user) throws Exception;
	
	public String validateUser(String userId, String password) throws Exception;
	
	public User getUser(String userId) throws Exception;
	
	public String updateUserName(String userId, String firstName, String lastName) throws Exception;
	
	public String updatePassword(String userId, String password) throws Exception;
	
	public Boolean rechargeWallet(String userId, Double amount) throws Exception;
	
	public Boolean payment(String userId, Double amount) throws Exception;
	
	public void addBooking(String userId, Integer bookingId) throws Exception;
	
}
