package it.sevenbits.spring_boot.task_manager.core.repository;

import it.sevenbits.spring_boot.task_manager.core.model.Task;

import java.util.List;

/**
 * Interface for Repositories Tasks
 */
public interface TasksRepository {
    /**
     * Method for getting all tasks in repository
     *
     * @return all tasks
     */
    List getAllTasks();

    /**
     * Method for added task in repository
     *
     * @param task - it will add
     * @return added task
     */
    Task create(Task task);

    /**
     * Getter task for this id
     *
     * @param id - id for task
     * @return task
     */
    Task getTask(String id);

    /**
     * Delete task for this id
     *
     * @param id - id for task
     * @return deleted task
     */
    Task deleteTask(String id);
}
