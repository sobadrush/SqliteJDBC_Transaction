package com.ctbc.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ctbc.interfaces.I_DeptService;
import com.ctbc.vo.DeptVO;

import _00_RootConfig.RootConfig;

@Service
public class DeptService implements I_DeptService {

	@Autowired
	private DeptDAO deptDAO;

	@Autowired
	private PlatformTransactionManager transactionManager;

//	@Override
//	public void testTransaction() throws SQLException {
//		System.out.println("============= [TestTransaction] ==============");
//		int pen1 = deptDAO.addDept(new DeptVO("數金部", "南港"));
//		int pen2 = deptDAO.addDept(new DeptVO("國防部**************************************************", "中正區"));
//	}

	@Autowired
	private DataSource ds;
	
	@Override
//	@Transactional(
//			propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, 
//			readOnly = false, timeout = -1, rollbackFor = SQLException.class, transactionManager = "txManager")
	public void testTransaction() throws SQLException {
		
		DefaultTransactionDefinition transactionDef = new DefaultTransactionDefinition();
		transactionDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transactionDef.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus status = transactionManager.getTransaction(transactionDef);
		
		try {
			System.out.println("============= [TestTransaction] ==============");
			int pen1 = deptDAO.addDept(new DeptVO("數金部", "南港"));
			int pen2 = deptDAO.addDept(new DeptVO("國防部**************************************************", "中正區"));
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws SQLException {
		// ===================================================================================
		System.setProperty("spring.profiles.active", "sqlite_env");  // 設定啟用的DB
		// System.setProperty("spring.profiles.active", "mssql_env");   // 設定啟用的DB
		// System.setProperty("spring.profiles.active", "mssql_itoa");  // 設定啟用的DB
		// ===================================================================================
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		I_DeptService deptSvc = context.getBean("deptService", DeptService.class);
		deptSvc.testTransaction();
		context.close();
	}

}
