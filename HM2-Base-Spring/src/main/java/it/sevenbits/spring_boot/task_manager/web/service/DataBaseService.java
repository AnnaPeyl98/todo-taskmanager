package it.sevenbits.spring_boot.task_manager.web.service;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.TasksRepository;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with repository
 */
@Service
public class DataBaseService implements TaskService {
    private final TasksRepository tasksRepository;

    /**
     * Create service
     *
     * @param tasksRepository repository for this service
     */
    public DataBaseService(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * Method for creating response with all tasks
     *
     * @param filter filter for status
     * @param order  parameter for sort task
     * @param page   number page
     * @param size   count tasks on page
     * @return response with all tasks
     */
    @Override
    public List<Task> getAllTasks(final String filter, final String order, final int page, final int size) {
        if ("done".equals(filter) || "inbox".equals(filter) || "desc".equals(order) || "asc".equals(order)) {
            return tasksRepository.getAllTasks(filter, order, page, size);
        }
        return null;
    }

    /**
     * Method for adding task in repository creating response with added task
     *
     * @param newTask new task
     * @return added task
     */
    @Override
    public Task createTask(final AddTaskRequest newTask) {
        return tasksRepository.create(newTask);

    }

    /**
     * Method for get task
     *
     * @param id id task, which will be return
     * @return finded task
     */
    @Override
    public Task getTask(final String id) {
        return tasksRepository.getTask(id);

    }

    /**
     * Method for update task in repository
     *
     * @param id         id task
     * @param updateTask new information
     * @return empty json
     */
    @Override
    public Task updateTask(final String id, final UpdateTaskRequest updateTask) {
        return tasksRepository.updateTask(id, updateTask);

    }

    /**
     * Method for delete task in repository
     *
     * @param id id task
     * @return empty json
     */
    @Override
    public Task deleteTask(final String id) {
        Task deleteTask = tasksRepository.deleteTask(id);
        if (deleteTask == null || !deleteTask.getId().equals(id)) {
            return null;
        }
        return deleteTask;
    }
}
