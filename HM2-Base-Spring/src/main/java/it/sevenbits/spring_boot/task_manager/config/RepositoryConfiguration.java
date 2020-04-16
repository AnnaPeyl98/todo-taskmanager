package it.sevenbits.spring_boot.task_manager.config;

import it.sevenbits.spring_boot.task_manager.core.repository.tasks.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.TasksRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.users.DatabaseUsersRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.users.UsersRepository;
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
    /**
     * Create users repository, connected to database
     *
     * @param jdbcOperations class-wrapper for better work with database
     * @return repository with users
     */
    @Bean
    @Qualifier("usersRepository")
    public UsersRepository usersRepository(
            @Qualifier(value = "tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new DatabaseUsersRepository(jdbcOperations);
    }

}
