package com.khrd.ex02;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //bean 가져올 수 있도록 등록
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) //bean 경로 지정
public class DataSourceTest {
	
	@Autowired //bean 가져오기
	private DataSource ds;
	
	@Test
	public void testConnection() throws SQLException {
		System.out.println(ds);
		System.out.println(ds.getConnection());
	}
}
