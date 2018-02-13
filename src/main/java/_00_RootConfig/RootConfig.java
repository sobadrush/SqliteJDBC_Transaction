package _00_RootConfig;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = { "classpath:/connectionData/db_connection.properties" }, encoding = "utf-8")
@EnableTransactionManagement
public class RootConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource driverManagerDatasource() {
		String connectionUrl = env.getProperty("db.url");
		String driverClassName = env.getProperty("db.driverClassName");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(connectionUrl);
		ds.setDriverClassName(driverClassName);
		return ds;
	}

	@Bean
	public PlatformTransactionManager txManager(DataSource myDS) {
		return new DataSourceTransactionManager(myDS);
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		DataSource ds = context.getBean(DataSource.class);
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
