package com.khrd.service;

import java.util.List;

import com.khrd.domain.Criteria;
import com.khrd.domain.ReplyVO;

public interface ReplyService {
	public void regist(ReplyVO vo);
	public List<ReplyVO> listAll(int bno);
	public void modify(ReplyVO vo);
	public void remove(int rno);
	
	public List<ReplyVO> listCri(int bno, Criteria cri);
	public int listCriTotalCount(int bno);
}
