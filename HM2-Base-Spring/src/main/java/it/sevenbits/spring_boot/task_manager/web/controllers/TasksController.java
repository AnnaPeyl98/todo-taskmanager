package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;


/**
 * Class for working with URL and repository
 */
@Controller
@RequestMapping("/tasks")
public class TasksController {
    private TaskService taskService;

    /**
     * Constructor for creating repository
     *
     * @param taskService service for working with repository
     */
    public TasksController(final TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Method for getting all tasks in json
     *
     * @param status status for filter
     * @return all tasks
     */
    @RequestMapping(
            method = RequestMethod.GET,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Task>> listTasks(@RequestParam(name = "status") final String status) {
        if ("done".equals(status) || "inbox".equals(status)) {
            return taskService.getAllTasks(status);
        }
        return ResponseEntity
                .badRequest()
                .build();
    }

    /**
     * Method for adding task in repository. If field text is null or body request is empty, status will be 400 else 200
     *
     * @param newTask task which will be add
     * @return added task in json
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody @Valid final AddTaskRequest newTask) {
        return taskService.createTask(newTask);
    }

    /**
     * Method for get task from repository
     *
     * @param id - id task, which we search
     * @return find task or status
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Task> taskForId(@PathVariable("id") final String id) {
        return taskService.getTask(id);

    }

    /**
     * Method changed status task
     *
     * @param id   - id task, which will be change
     * @param task new status for task
     * @return status
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> changeStatus(@PathVariable("id") final String id, @RequestBody @Valid final UpdateTaskRequest task) {
        return taskService.updateTask(id, task);
    }

    /**
     * Method for delete task
     *
     * @param id - id task, which will be delete
     * @return status
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable("id") final String id) {
        return taskService.deleteTask(id);
    }
}
