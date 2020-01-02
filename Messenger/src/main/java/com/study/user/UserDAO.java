package com.study.user;

import java.util.List;

import com.study.cmn.DTO;

public interface UserDAO {
	
	public int save(DTO dto);
	public List<?> retrieve(DTO dto);
	public DTO selectOne(DTO dto);
	public int delete(DTO dto);
	public int update(DTO dto);
	
}
