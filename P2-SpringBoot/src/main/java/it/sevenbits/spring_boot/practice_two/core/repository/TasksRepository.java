package it.sevenbits.spring_boot.practice_two.core.repository;

import it.sevenbits.spring_boot.practice_two.core.model.Task;

import java.util.List;

/**
 * Interface for Repositories Tasks
 */
public interface TasksRepository {
    /**
     * Method for getting all tasks in repository
     * @return all tasks
     */
    List<Task> getAllTasks();
    /**
     * Method for added task in repository
     * @param task - it will add
     * @return added task
     */
    Task create(Task task);
}
