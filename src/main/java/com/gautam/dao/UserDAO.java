package com.gautam.dao;

import com.gautam.model.User;

public interface UserDAO {

public String addUser(User user) throws Exception;
	
	public User getUser(String userId) throws Exception;
	
	public String updateUserName(String userId, String firstName, String lastName) throws Exception;
	
	public String updatePassword(String userId, String password) throws Exception;
	
	public Boolean rechargeWallet(String userId, Double amount) throws Exception;
	
	public Boolean payment(String userId, Double amount) throws Exception;
	
	public void addBooking(String userId, Integer bookingId) throws Exception;
	
}
