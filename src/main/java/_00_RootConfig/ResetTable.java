package _00_RootConfig;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ResetTable {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		DataSource ds = context.getBean(DataSource.class);
		try {
			Connection conn = ds.getConnection();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		context.close();
	}

}
