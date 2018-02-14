package com.ctbc.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.vo.DeptVO;

public interface I_DeptDAO {

	public String ADD_STMT = "INSERT INTO Z40180_deptTB( dname , loc ) VALUES ( ? , ? )";
	
	public List<DeptVO> getAll();
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, timeout = -1, rollbackFor = Exception.class)
	public int addDept(DeptVO deptVO) throws SQLException;

}
