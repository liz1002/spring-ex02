package com.khrd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khrd.domain.MemberVO;
import com.khrd.persistence.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO dao;
	
	@Override
	public void regist(MemberVO vo) {
		dao.insert(vo);
	}

	@Override
	public void remove(String userid) {
		dao.delete(userid);
	}

	@Override
	public void modify(MemberVO vo) {
		dao.update(vo);
	}

	@Override
	public List<MemberVO> listAll() {
		return dao.selectList();
	}

	@Override
	public MemberVO read(String userid) {
		return dao.selectById(userid);
	}

}
