package com.khrd.ex02;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.khrd.domain.Criteria;
import com.khrd.domain.ReplyVO;
import com.khrd.persistence.ReplyDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyDAOTest {
	@Autowired
	private ReplyDAO dao;
	
//	@Test
	public void testCreate() {
		ReplyVO vo = new ReplyVO(); //new ReplyVO(1, "나도 햄토리~~ 해바라기씨 쥬아~~~'ㅅ'", "댕이")
		vo.setBno(1530);
		vo.setReplytext("해바라기씨");
		vo.setReplyer("댕이");
		dao.create(vo);
	}
	
//	@Test
	public void testList() {
		dao.list(1530);
	}
	
//	@Test
	public void testDelete() {
		dao.delete(1);
	}
	
//	@Test
	public void testUpdate() {
		ReplyVO vo = new ReplyVO();
		vo.setRno(2);
		vo.setReplytext("나도 햄토리~~ 해바라기씨 쥬아~~~'ㅅ'");
		dao.update(vo);
	}
	
	@Test
	public void testListCri() {
		Criteria cri = new Criteria();
		cri.setPage(1);
		cri.setPerPageNum(10);
		dao.listCri(1530, cri);
	}
}
