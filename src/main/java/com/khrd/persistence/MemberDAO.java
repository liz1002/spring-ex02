package com.khrd.persistence;

import java.util.List;

import com.khrd.domain.MemberVO;

public interface MemberDAO {
	public void insert(MemberVO vo);
	public List<MemberVO> selectList();
	public void delete(String userid);
	public void update(MemberVO vo);
	public MemberVO selectById(String userid);
}
