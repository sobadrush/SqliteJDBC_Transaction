package spring.test.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctbc.dao.I_DeptDAO;
import com.ctbc.vo.DeptVO;

import _00_RootConfig.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = { RootConfig.class })
@ActiveProfiles(value = { "mssql_itoa" }) // mssql_env / sqlite_env / mssql_itoa
public class DeptDaoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("1.setUpBeforeClass()");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("4.tearDownAfterClass()");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("2.setUp()");
		String dbName = ds.getConnection().getMetaData().getDatabaseProductName();
		System.out.println("【dbName】 >>> " + dbName);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("3.tearDown()");
	}

	@Autowired
	private I_DeptDAO deptDao;

	@Autowired
	private DataSource ds;

	@Test
	public void test_001() {
		System.out.println("================= 【test_001】 ====================");
		for (DeptVO vo : deptDao.getAll()) {
			System.out.println(vo);
		}
		assertEquals("Dept 數目錯誤!", 4, deptDao.getAll().size());
	}

	@Test
	public void test_002() throws SQLException {
		System.out.println("================= 【test_002】 ====================");
		int pen = deptDao.addDept(new DeptVO("數金部", "南港"));
		assertEquals("insert error!", 1, pen);
	}

}
