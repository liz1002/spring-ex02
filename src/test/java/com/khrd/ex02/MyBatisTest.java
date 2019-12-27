package com.khrd.ex02;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //bean 사용 설정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MyBatisTest {
	@Autowired
	private SqlSessionFactory ss;
	
	@Test
	public void testFactory() {
		System.out.println(ss);
	}

	@Test
	public void testSession() {
		SqlSession sqlSession = ss.openSession();
		System.out.println(sqlSession);		
	}
}
