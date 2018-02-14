package _00_RootConfig;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "com.ctbc.dao", "com.ctbc.jdbcTemplate.dao" })
@PropertySource(value = { "classpath:/connectionData/db_connection.properties" }, encoding = "utf-8")
@EnableTransactionManagement
public class RootConfig {

	@Autowired
	private Environment env;

	@Bean
	@Profile("sqlite_env")
	public DataSource driverManagerDatasource() {
		String connectionUrl = env.getProperty("db.sqlite.url");
		String driverClassName = env.getProperty("db.sqlite.driverClassName");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlite:" + connectionUrl);
		ds.setDriverClassName(driverClassName);
		return ds;
	}

	@Bean
	@Profile("mssql_env")
	public DataSource driverManagerDatasource2() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlserver://localhost;databaseName=DB_Emp_Dept");
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUsername("sa");
		ds.setPassword("sa123456");
		return ds;
	}

	@Bean
	@Profile("mssql_itoa")
	public DataSource driverManagerDatasource3() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlserver://172.24.17.52:1803;databaseName=ITOA_MAIN");
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUsername("ITOA_MAIN_mod");
		ds.setPassword("f3ru9cj4");
		return ds;
	}

	@Bean
	public PlatformTransactionManager txManager(DataSource myDS) throws SQLException {
		System.out.println(myDS.getConnection().getMetaData().getDatabaseProductName());
		return new DataSourceTransactionManager(myDS);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource ds) {
		return new JdbcTemplate(ds);
	}

	public static void main(String[] args) {
		// ===================================================================================
		System.setProperty("spring.profiles.active", "sqlite_env");  // 設定啟用的DB
		// System.setProperty("spring.profiles.active", "mssql_env");  // 設定啟用的DB
//		System.setProperty("spring.profiles.active", "mssql_itoa");  // 設定啟用的DB
		// ===================================================================================
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		DataSource ds = context.getBean(DataSource.class);
		JdbcTemplate jdbc = context.getBean("jdbcTemplate", JdbcTemplate.class);
		System.out.println("jdbctemplate >>> " + jdbc);
		try {
			Connection conn = ds.getConnection();
			String dbProduct = conn.getMetaData().getDatabaseProductName();
			System.out.println("dbProduct : " + dbProduct);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.close();
	}
}
