package com.khrd.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.khrd.domain.Criteria;
import com.khrd.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	private final static String namespace="mappers.ReplyMapper";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void create(ReplyVO vo) {
		sqlSession.insert(namespace + ".create", vo);
	}

	@Override
	public List<ReplyVO> list(int bno) {
		return sqlSession.selectList(namespace + ".list", bno);
	}

	@Override
	public void delete(int rno) {
		sqlSession.delete(namespace + ".delete", rno);
	}

	@Override
	public void update(ReplyVO vo) {
		sqlSession.update(namespace + ".update", vo);
	}

	@Override
	public List<ReplyVO> listCri(int bno, Criteria cri) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("cri", cri);
		return sqlSession.selectList(namespace + ".listCri", map);
	}

	@Override
	public int listCriTotalCount(int bno) {
		return sqlSession.selectOne(namespace + ".listCriTotalCount", bno);
	}

}
