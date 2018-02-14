package com.ctbc.interfaces;

import java.sql.SQLException;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface I_DeptService {

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, 
			readOnly = false, timeout = -1, rollbackFor = SQLException.class, transactionManager = "txManager")
	public void testTransaction() throws SQLException;

}
