package com.gautam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.dao.AdminDAO;

@Service(value="adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO adminDAO;
	
	@Override
	public String validateAdmin(String id, String password) throws Exception {
		Boolean isValid=adminDAO.validateAdmin(id, password);
		if(!isValid) throw new Exception("Service.ADMIN_LOGIN_FAILED");
		return id;
	}
	
}