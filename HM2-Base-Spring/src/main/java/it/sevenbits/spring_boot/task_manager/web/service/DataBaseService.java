package it.sevenbits.spring_boot.task_manager.web.service;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.TasksRepository;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.GetAllTasksResponse;
import it.sevenbits.spring_boot.task_manager.web.model.GetTasksMeta;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Service for working with repository
 */
@Service
public class DataBaseService implements TaskService {
    private final TasksRepository tasksRepository;
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 50;

    /**
     * Create service
     *
     * @param tasksRepository repository for this service
     */
    public DataBaseService(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * Method for building uri for pages
     * @param status status tasks
     * @param order for sorting
     * @param page number current page
     * @param size count tasks on page
     * @return uri for page
     */
    private String buildUriFor(final String status, final String order, final int page, final int size) {
        return UriComponentsBuilder
                .fromPath("/tasks")
                .queryParam("status", status)
                .queryParam("order", order)
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString();
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
    public GetAllTasksResponse getAllTasks(final String filter, final String order, final int page, final int size) {
        if (("done".equals(filter) || "inbox".equals(filter))
                && ("desc".equals(order) || "asc".equals(order))
                && ((size >= MIN_SIZE) && (size <= MAX_SIZE))) {


            int totalCount = tasksRepository.getCountAllTasks(filter);
            int pagesCount = Math.max((int) Math.ceil((double) totalCount / size), 1);

            List<Task> tasksList = tasksRepository.getAllTasks(filter, order, page, size);

            String firstPage = buildUriFor(filter, order, 1, size);
            String lastPage = buildUriFor(filter, order, pagesCount, size);
            String nextPage = page == pagesCount
                    ? null
                    : buildUriFor(filter, order, page + 1, size);
            String prevPage = page == 1
                    ? null
                    : buildUriFor(filter, order, page - 1, size);
            GetTasksMeta meta = new GetTasksMeta(totalCount, filter, page, size, order, firstPage, lastPage, nextPage, prevPage);
            return new GetAllTasksResponse(meta, tasksList);
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

    @Override
    public int getCountAllTasks(final String filter) {
        return tasksRepository.getCountAllTasks(filter);
    }
}
