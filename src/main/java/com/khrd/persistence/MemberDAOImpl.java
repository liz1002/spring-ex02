package com.khrd.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.khrd.domain.MemberVO;

@Repository //dao class에 달기 
public class MemberDAOImpl implements MemberDAO{
	@Autowired
	private SqlSession sqlsession;
	
	private static final String namespace = "mappers.MemberMapper";
	
	@Override
	public void insert(MemberVO vo) {
		sqlsession.insert(namespace + ".insert", vo);
	}

	@Override
	public List<MemberVO> selectList() {
		return sqlsession.selectList(namespace + ".selectList");
	}

	@Override
	public void delete(String userid) {
		sqlsession.delete(namespace + ".delete", userid);
		
	}

	@Override
	public void update(MemberVO vo) {
		sqlsession.update(namespace + ".update", vo);
		
	}

	@Override
	public MemberVO selectById(String userid) {
		return sqlsession.selectOne(namespace + ".selectById", userid);
	}
}
