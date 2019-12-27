package com.khrd.service;

import java.util.List;

import com.khrd.domain.MemberVO;

public interface MemberService {
	public void regist(MemberVO vo);
	public void remove(String userid);
	public void modify(MemberVO vo);
	public List<MemberVO> listAll();
	public MemberVO read(String userid);
}
