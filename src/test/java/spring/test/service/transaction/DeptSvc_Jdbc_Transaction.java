package spring.test.service.transaction;

import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctbc.jdbcTemplate.dao.DeptService_JdbcTemplate;

import _00_RootConfig.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { RootConfig.class })
@ActiveProfiles(value = { "mssql_itoa" }) // mssql_env / sqlite_env / mssql_itoa
public class DeptSvc_Jdbc_Transaction {

	@Autowired
	private DeptService_JdbcTemplate deptSvc;
	
	@Test
	public void test_001() throws SQLException {
		System.out.println("================== test_001 ==================");
		deptSvc.testTransaction();
	}

}
