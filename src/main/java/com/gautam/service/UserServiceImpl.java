package com.gautam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.dao.UserDAO;
import com.gautam.model.User;

@Service(value="userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public String addUser(User user) throws Exception {
		String userId=userDAO.addUser(user);
		if(userId==null) throw new Exception("Service.USER_SIGNUP_FAILED");
		return userId;
	}
	
	@Override
	public String validateUser(String userId, String password) throws Exception {
		User user=userDAO.getUser(userId);
		if(user==null || !user.getPassword().equals(password)) throw new Exception("Service.USER_LOGIN_FAILED");
		return user.getUserId();
	}
	
	@Override
	public User getUser(String userId) throws Exception {
		User user=userDAO.getUser(userId);
		if(user==null) throw new Exception("Service.USER_NOT_FOUND");
		return user;
	}
	
	@Override
	public String updateUserName(String userId, String firstName, String lastName) throws Exception {
		String id=userDAO.updateUserName(userId, firstName, lastName);
		if(id==null) throw new Exception("Service.USERNAME_UPDATION_FAILED");
		return id;
	}
	
	@Override
	public String updatePassword(String userId, String password) throws Exception {
		String id=userDAO.updatePassword(userId, password);
		if(id==null) throw new Exception("Service.USERPASSWORD_UPDATION_FAILED");
		return id;
	}
	
	@Override
	public Boolean rechargeWallet(String userId, Double amount) throws Exception {
		Boolean message=userDAO.rechargeWallet(userId, amount);
		if(!message) throw new Exception("Service.WALLET_RECHARGE_FAILED");
		return message;
	}
	
	@Override
	public Boolean payment(String userId, Double amount) throws Exception {
		Boolean message=userDAO.payment(userId, amount);
		if(!message) throw new Exception("Service.PAYMENT_FAILED");
		return message;
	}
	
	@Override
	public void addBooking(String userId, Integer bookingId) throws Exception {
		userDAO.addBooking(userId, bookingId);
	}

}
