package it.sevenbits.spring_boot.task_manager.web.service.tasks;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.TasksRepository;
import it.sevenbits.spring_boot.task_manager.web.model.request.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.GetAllTasksResponse;
import it.sevenbits.spring_boot.task_manager.web.model.GetTasksMeta;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.ValidatorSizePage;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Service for working with repository
 */
@Service
public class DataBaseService implements TaskService {
    private final TasksRepository tasksRepository;
    private final ValidatorSizePage validatorSizePage;

    /**
     * Create service
     *
     * @param tasksRepository   repository for this service
     * @param validatorSizePage parameters for validation size page
     */
    public DataBaseService(final TasksRepository tasksRepository, final ValidatorSizePage validatorSizePage) {
        this.tasksRepository = tasksRepository;
        this.validatorSizePage = validatorSizePage;
    }

    /**
     * Method for building uri for pages
     *
     * @param status status tasks
     * @param order  for sorting
     * @param page   number current page
     * @param size   count tasks on page
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
     * @param owner  id owners
     * @param filter filter for status
     * @param order  parameter for sort task
     * @param page   number page
     * @param size   count tasks on page
     * @return response with all tasks
     */
    @Override
    public GetAllTasksResponse getAllTasks(final String owner, final String filter, final String order, final int page, final int size) {

        if (("done".equals(filter) || "inbox".equals(filter))
                && ("desc".equals(order) || "asc".equals(order))
                && ((size >= validatorSizePage.getMinSize()) && (size <= validatorSizePage.getMaxSize()))) {


            int totalCount = tasksRepository.getCountAllTasks(owner, filter);
            int pagesCount = Math.max((int) Math.ceil((double) totalCount / size), 1);

            List<Task> tasksList = tasksRepository.getAllTasks(owner, filter, order, page, size);

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
     * @param owner   id owners
     * @param newTask new task
     * @return added task
     */
    @Override
    public Task createTask(final String owner, final AddTaskRequest newTask) {
        return tasksRepository.create(owner, newTask);

    }

    /**
     * Method for get task
     *
     * @param owner id owners
     * @param id    id task, which will be return
     * @return finded task
     */
    @Override
    public Task getTask(final String owner, final String id) {
        return tasksRepository.getTask(owner, id);

    }

    /**
     * Method for update task in repository
     *
     * @param owner      id owners
     * @param id         id task
     * @param updateTask new information
     * @return empty json
     */
    @Override
    public Task updateTask(final String owner, final String id, final UpdateTaskRequest updateTask) {
        return tasksRepository.updateTask(owner, id, updateTask);

    }

    /**
     * Method for delete task in repository
     *
     * @param owner id owners
     * @param id    id task
     * @return empty json
     */
    @Override
    public Task deleteTask(final String owner, final String id) {
        Task deleteTask = tasksRepository.deleteTask(owner, id);
        if (deleteTask == null || !deleteTask.getId().equals(id)) {
            return null;
        }
        return deleteTask;
    }

    @Override
    public int getCountAllTasks(final String owner, final String filter) {
        return tasksRepository.getCountAllTasks(owner, filter);
    }
}
