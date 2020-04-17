package it.sevenbits.spring_boot.task_manager.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Class for configuration database
 */
@Configuration
public class TasksDataBaseConfig {
    /**
     * connection with database
     *
     * @return DataSourse for connection
     */
    @Bean
    @FlywayDataSource
    @Qualifier("tasksDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tasks")
    public DataSource tasksDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Method create object for working with operations sql
     *
     * @param tasksDataSource - connection with database
     * @return object for working with operations sql
     */
    @Bean
    @Qualifier("tasksJdbcOperations")
    public JdbcOperations tasksJdbcOperations(
            @Qualifier("tasksDataSource")
                    final DataSource tasksDataSource
    ) {
        return new JdbcTemplate(tasksDataSource);
    }
}
