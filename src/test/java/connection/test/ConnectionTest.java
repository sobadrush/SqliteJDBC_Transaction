package connection.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConnectionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String projPath = System.getProperty("user.dir");
		String dbFile = "/emp_dept.db";
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");  
			String url = "jdbc:sqlite:" + projPath + dbFile;
			System.out.println("連線字串：" + url);
			conn = DriverManager.getConnection(url);
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println(" Database Product Name : " + meta.getDatabaseProductName());
			Assert.assertEquals("SQLite", meta.getDatabaseProductName());
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

}
