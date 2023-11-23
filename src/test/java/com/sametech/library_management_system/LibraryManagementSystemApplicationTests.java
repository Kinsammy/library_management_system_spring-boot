package com.sametech.library_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LibraryManagementSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testDatabaseConnection() {
		DriverManagerDataSource dataSource =
				new DriverManagerDataSource("jdbc:mysql://localhost:3306");

		try {
			Connection connection = dataSource.getConnection("root", "fannysammy53");
			System.out.println(connection);
			assertThat(connection).isNotNull();
		} catch (SQLException exception){
			throw new RuntimeException(exception.getMessage());
		}
	}


}
