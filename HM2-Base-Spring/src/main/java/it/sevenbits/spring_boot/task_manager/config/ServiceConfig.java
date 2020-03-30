package it.sevenbits.spring_boot.task_manager.config;

import it.sevenbits.spring_boot.task_manager.core.repository.TasksRepository;
import it.sevenbits.spring_boot.task_manager.web.service.DataBaseService;
import it.sevenbits.spring_boot.task_manager.web.service.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines configuration service
 */
@Configuration
public class ServiceConfig {
    /**
     * Create Service
     *
     * @param tasksRepository repository for tasks
     * @return service for this repository
     */
    @Bean
    public TaskService taskService(final TasksRepository tasksRepository) {
        return new DataBaseService(tasksRepository);
    }
}
