package it.sevenbits.spring_boot.task_manager.config;

import it.sevenbits.spring_boot.task_manager.core.repository.SimpleTasksRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.TasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines configuration SimpleTasksRepository
 */
@Configuration
public class RepositoryConfiguration {
    /**
     * Created Repository
     *
     * @return SimpleTasksRepository
     */
    @Bean
    public TasksRepository itemRepository() {
        return new SimpleTasksRepository();
    }
}
