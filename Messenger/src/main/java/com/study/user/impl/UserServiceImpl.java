package com.study.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.cmn.DTO;
import com.study.user.UserDAO;
import com.study.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO dao;
	
	@Override
	public int save(DTO dto) {
		return dao.save(dto);
	}

	@Override
	public List<?> retrieve(DTO dto) {
		return dao.retrieve(dto);
	}

	@Override
	public DTO selectOne(DTO dto) {
		return dao.selectOne(dto);
	}

	@Override
	public int delete(DTO dto) {
		return dao.delete(dto);
	}

	@Override
	public int update(DTO dto) {
		return dao.update(dto);
	}

}
