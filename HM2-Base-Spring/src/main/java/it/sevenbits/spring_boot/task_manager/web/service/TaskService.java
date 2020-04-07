package it.sevenbits.spring_boot.task_manager.web.service;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;

import java.util.List;

/**
 * Interface for service, which working with repositories with tasks
 */
public interface TaskService {
    /**
     * Method for creating response with all tasks
     *
     * @param filter filter for status
     * @return response with all tasks
     */
    List<Task> getAllTasks(String filter, String order, int page, int size);

    /**
     * Method for adding task in repository creating response with added task
     *
     * @param newTask new task
     * @return added task
     */
    Task createTask(AddTaskRequest newTask);

    /**
     * Method for get task
     *
     * @param id id task, which will be return
     * @return finded task
     */
    Task getTask(String id);

    /**
     * Method for update task in repository
     *
     * @param id         id task
     * @param updateTask new information
     * @return empty json
     */
    Task updateTask(String id, UpdateTaskRequest updateTask);

    /**
     * Method for delete task in repository
     *
     * @param id id task
     * @return empty json
     */
    Task deleteTask(String id);
}
