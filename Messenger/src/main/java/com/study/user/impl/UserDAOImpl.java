package com.study.user.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.cmn.DTO;
import com.study.cmn.SearchVO;
import com.study.user.UserDAO;
import com.study.user.UserVO;

@Repository
public class UserDAOImpl implements UserDAO{

	@Autowired
	private SqlSessionTemplate sst;
	
	private String namespace = "com.study.user";
	
	@Override
	public int save(DTO dto) {
		String statement = namespace + ".save";
		UserVO vo = (UserVO) dto;
		int flag = sst.insert(statement, vo);
		return flag;
	}

	@Override
	public List<?> retrieve(DTO dto) {
		String statement = namespace + ".retrieve";
		SearchVO vo = new SearchVO();
		List<UserVO> list = sst.selectList(statement, vo);
		return list;
	}

	@Override
	public DTO selectOne(DTO dto) {
		String statement = namespace + ".selectOne";
		UserVO vo = (UserVO) dto;
		UserVO out = sst.selectOne(statement, vo);
		return out;
	}

	@Override
	public int delete(DTO dto) {
		String statement = namespace + ".delete";
		UserVO vo = (UserVO) dto;
		int flag = sst.delete(statement, vo);
		return flag;
	}

	@Override
	public int update(DTO dto) {
		String statement = namespace + ".update";
		UserVO vo = (UserVO) dto;
		int flag = sst.update(statement, vo);
		return flag;
	}

}
