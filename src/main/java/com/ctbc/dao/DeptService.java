package com.ctbc.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.ctbc.interfaces.I_DeptService;
import com.ctbc.vo.DeptVO;

import _00_RootConfig.RootConfig;

@Service
public class DeptService implements I_DeptService {

	@Autowired
	private DeptDAO deptDAO;

	@Override
	public void testTransaction() throws SQLException {
		System.out.println("============= [TestTransaction] ==============");
		int pen1 = deptDAO.addDept(new DeptVO("數金部", "南港"));
		int pen2 = deptDAO.addDept(new DeptVO("小吃部**************************************************", "中和"));
	}

	public static void main(String[] args) throws SQLException {
		// ===================================================================================
		// System.setProperty("spring.profiles.active", "sqlite_env");  // 設定啟用的DB
		// System.setProperty("spring.profiles.active", "mssql_env");  // 設定啟用的DB
		System.setProperty("spring.profiles.active", "mssql_itoa");  // 設定啟用的DB
		// ===================================================================================
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		DeptService deptSvc = context.getBean("deptService", DeptService.class);
		deptSvc.testTransaction();
		context.close();
	}

}
