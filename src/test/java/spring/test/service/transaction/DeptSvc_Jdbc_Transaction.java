package spring.test.service.transaction;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctbc.interfaces.I_DeptService;

import _00_RootConfig.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { RootConfig.class })
@ActiveProfiles(value = { "sqlite_env" }) // mssql_env / sqlite_env / mssql_itoa
public class DeptSvc_Jdbc_Transaction {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@Qualifier(value = "deptService_JdbcTemplate")
	private I_DeptService deptSvc;
	
	@Test
	public void test_000() {
		System.out.println("================== 【test_000】 ==================");
		DataSource ds = context.getBean(DataSource.class);
		try {
			DatabaseMetaData dsMeta = ds.getConnection().getMetaData();
			System.out.println("getDatabaseProductName : " + dsMeta.getDatabaseProductName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_001() throws SQLException {
		System.out.println("================== 【test_001】 ==================");
		System.out.println(" deptSvc >>> " + deptSvc);
		
		Advised advised = (Advised) deptSvc; // 因為Spring注入的是代理物件(Proxy)，所以先轉型
		Class<?> realType = advised.getTargetSource().getTargetClass(); // 再取得真正的型態
		
		System.out.println("RealType : " + realType);
		
		//---------------------------
		//        交易測試
		//---------------------------
		deptSvc.testTransaction();
	}

}
