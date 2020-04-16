package it.sevenbits.spring_boot.task_manager.core.repository.tasks;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.request.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateTaskRequest;

import java.util.List;

/**
 * Interface for Repositories Tasks
 */
public interface TasksRepository {
    /**
     * Method for getting all tasks in repository
     *
     * @param owner id owners
     * @param filter filter for status
     * @param order  parameter for sort task
     * @param page   number page
     * @param size   count tasks on page
     * @return all tasks
     */
    List<Task> getAllTasks(String owner, String filter, String order, int page, int size);

    /**
     * Method for added task in repository
     *
     * @param owner id owners
     * @param task - it will add
     * @return added task
     */
    Task create(String owner, AddTaskRequest task);

    /**
     * Getter task for this id
     *
     * @param owner id owners
     * @param id - id for task
     * @return task
     */
    Task getTask(String owner, String id);

    /**
     * Delete task for this id
     *
     * @param owner id owners
     * @param id - id for task
     * @return deleted task
     */
    Task deleteTask(String owner, String id);

    /**
     * Update task in repository
     *
     * @param owner id owners
     * @param id         id task, which will be update
     * @param updateTask new informations
     * @return updated task
     */
    Task updateTask(String owner, String id, UpdateTaskRequest updateTask);

    /**
     * Method for count all tasks
     *
     * @param owner id owners
     * @param filter status tasks
     * @return count tasks for this filter
     */
    int getCountAllTasks(String owner, String filter);
}
