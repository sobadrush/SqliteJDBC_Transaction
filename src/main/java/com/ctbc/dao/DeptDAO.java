package com.ctbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ctbc.vo.DeptVO;

@Repository
public class DeptDAO {

	@Autowired
	private DataSource ds;
//	private Connection conn;
	private static final String GET_ALL_STMT = "SELECT * FROM z40180_deptTB";
	private static final String ADD_STMT = "INSERT INTO Z40180_deptTB( dname , loc ) VALUES ( ? , ? )";

	@PostConstruct
	private void init() {
		System.out.println("============= DeptDAO @PostConstruct =============");
//		try {
//			conn = ds.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	public List<DeptVO> getAll() {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<DeptVO> dList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeptVO vo = new DeptVO();
				vo.setDeptNo(rs.getInt("deptno"));
				vo.setDeptName(rs.getString("dname"));
				vo.setDeptLoc(rs.getString("loc"));
				dList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dList;
	}

	public int addDept(DeptVO deptVO)  throws SQLException {
		Connection conn = ds.getConnection();
		PreparedStatement pstmt = null;
		int pen = 0;
		pstmt = conn.prepareStatement(ADD_STMT);
		pstmt.setString(1, deptVO.getDeptName());
		pstmt.setString(2, deptVO.getDeptLoc());
		pen = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return pen;
	}

	@PreDestroy
	public void destory() {
		System.out.println("============= DeptDAO @PreDestroy =============");
//		conn = null;
	}
}
