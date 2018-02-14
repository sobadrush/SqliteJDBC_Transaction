package com.ctbc.jdbcTemplate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.ctbc.vo.DeptVO;

import _00_RootConfig.RootConfig;

@Repository
public class DeptDAO_JdbcTemplate {

	@Autowired
	private JdbcOperations jdbcTemplate;

	private String ADD_STMT = "INSERT INTO Z40180_deptTB( dname , loc ) VALUES ( ? , ? )";
	
	public List<DeptVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public int addDept(DeptVO deptVO){
		int pen = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(ADD_STMT);
				pstmt.setString(1, deptVO.getDeptName());
				pstmt.setString(2, deptVO.getDeptLoc());
				return pstmt;
			}
		});
		return pen;
	}

	public static void main(String[] args) throws SQLException {
		// ===================================================================================
		// System.setProperty("spring.profiles.active", "sqlite_env");  // 設定啟用的DB
		// System.setProperty("spring.profiles.active", "mssql_env");  // 設定啟用的DB
		System.setProperty("spring.profiles.active", "mssql_itoa");  // 設定啟用的DB
		// ===================================================================================
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		DeptDAO_JdbcTemplate dao = context.getBean("deptDAO_JdbcTemplate", DeptDAO_JdbcTemplate.class);
		int pen = dao.addDept(new DeptVO("數金部", "南港"));
		System.out.println("pen : " + pen);
		context.close();
	}

}
