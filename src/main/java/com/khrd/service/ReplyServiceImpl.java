package com.khrd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khrd.domain.Criteria;
import com.khrd.domain.ReplyVO;
import com.khrd.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDAO dao;	
	
	@Override
	public void regist(ReplyVO vo) {
		dao.create(vo);
	}

	@Override
	public List<ReplyVO> listAll(int bno) {
		return dao.list(bno);
	}

	@Override
	public void modify(ReplyVO vo) {
		dao.update(vo);
	}

	@Override
	public void remove(int rno) {
		dao.delete(rno);	
	}

	@Override
	public List<ReplyVO> listCri(int bno, Criteria cri) {
		return dao.listCri(bno, cri);
	}

	@Override
	public int listCriTotalCount(int bno) {
		return dao.listCriTotalCount(bno);
	}
	
}
