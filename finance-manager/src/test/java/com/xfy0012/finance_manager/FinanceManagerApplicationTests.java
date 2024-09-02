package com.xfy0012.finance_manager;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class FinanceManagerApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SpringLiquibase springLiquibase;

	@Test
	void contextLoads() throws SQLException {
		// manual set the liquibase
		try {
			springLiquibase.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Liquibase migration failed", e);
		}

		//test database connection
		try (Connection connection = dataSource.getConnection()) {
			assertNotNull(connection, "Database connection should not be null");


			//check the user is availble
			ResultSet resultSet = connection.getMetaData().getTables(null, null, "users", null);
			boolean usersTableExists = false;
			System.out.println("Tables in the H2 Database:");
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				System.out.println(tableName);  // output the table name
				if ("USERS".equalsIgnoreCase(tableName)) {
					usersTableExists = true;
				}
			}
			assertTrue(resultSet.next(), "The 'users' table should exist in the database");

		}

		}


	}



