package it.sevenbits.spring_boot.task_manager.web.service.tasks;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.request.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.GetAllTasksResponse;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateTaskRequest;


/**
 * Interface for service, which working with repositories with tasks
 */
public interface TaskService {
    /**
     * Method for creating response with all tasks
     *
     * @param owner id owners
     * @param filter filter for status
     * @param order parameter for sort task
     * @param page number page
     * @param size count tasks on page
     * @return response with all tasks
     */
    GetAllTasksResponse getAllTasks(String owner, String filter, String order, int page, int size);

    /**
     * Method for adding task in repository creating response with added task
     *
     * @param owner id owners
     * @param newTask new task
     * @return added task
     */
    Task createTask(String owner, AddTaskRequest newTask);

    /**
     * Method for get task
     *
     * @param owner id owners
     * @param id id task, which will be return
     * @return finded task
     */
    Task getTask(String owner, String id);

    /**
     * Method for update task in repository
     *
     * @param owner id owners
     * @param id         id task
     * @param updateTask new information
     * @return empty json
     */
    Task updateTask(String owner, String id, UpdateTaskRequest updateTask);

    /**
     * Method for delete task in repository
     *
     * @param owner id owners
     * @param id id task
     * @return empty json
     */
    Task deleteTask(String owner, String id);

    /**
     * Method for count all tasks
     * @param owner id owners
     * @param filter status tasks
     * @return count tasks for this filter
     */
    int getCountAllTasks(String owner, String filter);
}
