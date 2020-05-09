package com.gautam.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository(value="adminDAO")
public class AdminDAOImpl implements AdminDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Map<String, String> admins=new HashMap<String, String>() {
		{
			put("gtmb007", "gtmb@1998");
			put("sintu8757", "sintu@1997");
		}
	};
	
	@Override
	public Boolean validateAdmin(String id, String password) throws Exception {
		Boolean isValid=false;
		if(admins.containsKey(id) && admins.get(id).equals(password)) isValid=true;
		return isValid;
	}
	
}
