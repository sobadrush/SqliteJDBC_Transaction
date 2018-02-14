package com.ctbc.jdbcTemplate.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.interfaces.I_DeptService;
import com.ctbc.vo.DeptVO;

import _00_RootConfig.RootConfig;

@Service
public class DeptService_JdbcTemplate implements I_DeptService {

	@Autowired
	private DeptDAO_JdbcTemplate deptDAO;

	@Override
	@Transactional(
			propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, 
			readOnly = false, timeout = -1, rollbackFor = SQLException.class, transactionManager = "txManager")
	public void testTransaction() throws SQLException {
		System.out.println("============= [TestTransaction] ==============");
		int pen1 = deptDAO.addDept(new DeptVO("數金部", "南港"));
		int pen2 = deptDAO.addDept(new DeptVO("國防部**************************************************", "中正區"));
	}

	public static void main(String[] args) throws SQLException {
		// ===================================================================================
		 System.setProperty("spring.profiles.active", "sqlite_env");  // 設定啟用的DB
		// System.setProperty("spring.profiles.active", "mssql_env");   // 設定啟用的DB
//		System.setProperty("spring.profiles.active", "mssql_itoa");    // 設定啟用的DB
		// ===================================================================================
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		I_DeptService deptSvc = context.getBean("deptService_JdbcTemplate", DeptService_JdbcTemplate.class);
		deptSvc.testTransaction();
		context.close();
	}
}
