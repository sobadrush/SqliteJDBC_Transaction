package com.ctbc.interfaces;

import java.sql.SQLException;

public interface I_DeptService {

//	@Transactional(
//			propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, 
//			readOnly = false, timeout = -1, rollbackFor = SQLException.class, transactionManager = "txManager")
	public void testTransaction() throws SQLException;

}
