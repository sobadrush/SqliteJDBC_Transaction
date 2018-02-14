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
@ActiveProfiles(value = { "sqlite_env" }) // mssql_env / sqlite_env / mssql_itoa
public class DeptSvc_Jdbc_Transaction {

//	@Autowired
//	private ApplicationContext context;
	
	@Autowired
	private DeptService_JdbcTemplate deptSvc;
	
//	@Test
//	public void test_000() {
//		// sqlite_env 時，txManager會有問題，context initial失敗
//		System.out.println("================== test_000 ==================");
//		DataSource ds = context.getBean(DataSource.class);
//		try {
//			DatabaseMetaData dsMeta = ds.getConnection().getMetaData();
//			System.out.println("getDatabaseProductName : " + dsMeta.getDatabaseProductName());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void test_001() throws SQLException {
		System.out.println("================== test_001 ==================");
		deptSvc.testTransaction();
	}

}
