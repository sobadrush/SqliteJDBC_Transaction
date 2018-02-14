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

import com.ctbc.dao.DeptService;

import _00_RootConfig.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { RootConfig.class })
@ActiveProfiles(value = { "mssql_env" }) // mssql_env / sqlite_env
public class DeptSvc_Transaction {

	@Autowired
	private DeptService deptSVC;
	
	@Test
	public void test_001() {
		try {
			deptSVC.testTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
