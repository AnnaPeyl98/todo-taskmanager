package it.sevenbits.spring_boot.task_manager.config;

import it.sevenbits.spring_boot.task_manager.core.repository.tasks.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.TasksRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.users.DatabaseUsersRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;


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
     * The method creates instance of users repository
     * @param jdbcTemplate instance of jdbcTemplate
     * @return instance of the books repository
     */
    @Bean
    public UsersRepository usersRepository(final @Qualifier("JdbcTemplate") JdbcTemplate jdbcTemplate) {
        return new DatabaseUsersRepository(jdbcTemplate);
    }

}
