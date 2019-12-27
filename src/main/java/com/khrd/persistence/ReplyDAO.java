package com.khrd.persistence;

import java.util.List;

import com.khrd.domain.Criteria;
import com.khrd.domain.ReplyVO;

public interface ReplyDAO {
	public void create(ReplyVO vo);
	public List<ReplyVO> list(int bno);
	public void delete(int rno);
	public void update(ReplyVO vo);
	
	public List<ReplyVO> listCri(int bno, Criteria cri);
	public int listCriTotalCount(int bno);
}
