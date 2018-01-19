package com.demo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.dao.Dao;

@org.springframework.stereotype.Service
@Transactional
public class Service {

	@Autowired
	public Dao dao;
	
	public void addUser() {
		dao.getJdbcTemplate().update("insert into CASE_APAINFO(NAME_,SEX_) VALUES('zhang','gb')");
		throw new RuntimeException("运行例外");
	}
}
