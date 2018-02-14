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
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "com.ctbc.dao", "com.ctbc.jdbcTemplate.dao" })
@PropertySource(value = { "classpath:/connectionData/db_connection.properties" }, encoding = "utf-8")
@EnableTransactionManagement

// XML中使用： <aop:aspectj-autoproxy proxy-target-class="true"/> 
@EnableAspectJAutoProxy(proxyTargetClass = true) // (1)http://zoroeye.iteye.com/blog/2230049 (2)http://jag522.iteye.com/blog/2115923
public class RootConfig {

	@Autowired
	private Environment env;

	@Bean
	@Profile("sqlite_env")
	public DataSource driverManagerDatasource() {
		System.err.println("====================【sqlite_env】===================");
		String connectionUrl = env.getProperty("db.sqlite.url");
		String driverClassName = env.getProperty("db.sqlite.driverClassName");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlite:" + connectionUrl);
		ds.setDriverClassName(driverClassName);
		
		// 【自動Reset DB Schema...(2)】
		//------------------------------------------------------------------
		DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
		//------------------------------------------------------------------
		
		return ds;
	}

	@Bean
	@Profile("mssql_env")
	public DataSource driverManagerDatasource2() {
		System.err.println("====================【mssql_env】===================");
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
		System.err.println("====================【mssql_itoa】===================");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlserver://172.24.17.52:1803;databaseName=ITOA_MAIN");
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUsername("ITOA_MAIN_mod");
		ds.setPassword("f3ru9cj4");
		return ds;
	}

	/**
	 *  【自動Reset DB Schema 使用JavaConfig...(1)】
	 *  https://stackoverflow.com/questions/16038360/initialize-database-without-xml-configuration-but-using-configuration
	 *  
	 *  XML中使用： <jdbc:initialize-database>
	 *  				......
	 *  			</jdbc:initialize-database>
	 *  https://examples.javacodegeeks.com/enterprise-java/spring/jdbc/spring-transaction-management-example-with-jdbc-example/
	 */
    private DatabasePopulator createDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(new ClassPathResource("_00_建立資料表/建立資料表Script_Sqlite.sql"));
        return databasePopulator;
    }
	
	@Bean
	public PlatformTransactionManager txManager(DataSource myDS) throws SQLException {
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



// 2. 问题分析 
// 这个异常一般是代理问题，根据异常中的com.sun.proxy.$Proxy23可以判断。但是奇怪的是为什么会类型不对，
// 动态代理接口应该没有问题，于是查找工程中其他地方是否也使用了代理相关的功能。 
// 1)因为有好几个spring配置文件，就搜索有没有其他<tx:annotation-driven/>, <aop:aspectj-autoproxy/> 或<aop:config/>，
// 如果有的话就看下是否有proxy-target-class="true"的配置  ---结果没有 
// (2)查看是否有注解@Transactional的使用 -- 找到 
// 发现@Transactional是加在**DAO方法上的，但是这个DAO没有实现接口，而是继承的父DAO类。问题明确了，动态代理只能
// 代理实现了接口且是接口里定义的方法，否则就会强制使用cglib代理。即使DAO类实现的是接口，但@Transactional加在了不
// 是接口里定义的方法上，仍然会走cglib代理，本人遇到的就是这个问题。
