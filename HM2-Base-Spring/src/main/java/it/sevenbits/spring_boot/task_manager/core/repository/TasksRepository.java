package it.sevenbits.spring_boot.task_manager.core.repository;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;

import java.util.List;

/**
 * Interface for Repositories Tasks
 */
public interface TasksRepository {
    /**
     * Method for getting all tasks in repository
     * @param filter filter for status
     * @return all tasks
     */
    List<Task> getAllTasks(String filter);

    /**
     * Method for added task in repository
     *
     * @param task - it will add
     * @return added task
     */
    Task create(AddTaskRequest task);

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

    /**
     * Update task in repository
     *
     * @param id         id task, which will be update
     * @param updateTask new informations
     * @return updated task
     */
    Task updateTask(String id, UpdateTaskRequest updateTask);
}
