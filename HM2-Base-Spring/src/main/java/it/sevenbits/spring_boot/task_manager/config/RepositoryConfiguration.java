package it.sevenbits.spring_boot.task_manager.config;

import it.sevenbits.spring_boot.task_manager.core.repository.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;


/**
 * Defines configuration Repository
 */
@Configuration
public class RepositoryConfiguration {
    /**
     * Created Repository
     * @param jdbcOperations object for working with sql
     * @return SimpleTasksRepository
     */
    @Bean
    public TasksRepository tasksRepository(
            @Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new DatabaseTaskRepository(jdbcOperations);
    }

}
