package it.sevenbits.spring_boot.practice_two.config;

import it.sevenbits.spring_boot.practice_two.core.repository.SimpleTasksRepository;
import it.sevenbits.spring_boot.practice_two.core.repository.TasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines configuration SimpleTasksRepository
 */
@Configuration
public class RepositoryConfig {
    /**
     * Created Repository
     * @return SimpleTasksRepository
     */
    @Bean
    public TasksRepository itemsRepository() {
        return new SimpleTasksRepository();
    }
}