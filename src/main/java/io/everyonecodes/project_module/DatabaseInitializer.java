package io.everyonecodes.project_module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner initDatabase(
            DataSource dataSource,
            @Value("${app.db.recreate-schema:false}") boolean recreateDb,
            @Value("classpath:01_createSchema_HeroQuest.sql") Resource schemaSql,
            @Value("classpath:02_insertMockData_HeroQuest.sql") Resource mockData) {

        if (recreateDb) {
            ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator(schemaSql);
            schemaPopulator.execute(dataSource);
            System.out.println("Database schema re-created.");
            ResourceDatabasePopulator mockPopulator = new ResourceDatabasePopulator(mockData);
            mockPopulator.execute(dataSource);
            System.out.println("Mock data inserted.");
        }
        return args -> {
        };
    }
}
