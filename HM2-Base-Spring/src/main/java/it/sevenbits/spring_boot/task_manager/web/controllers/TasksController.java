package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.util.List;


/**
 * Class for working with URL and repository
 */
@Controller
@Validated
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
        List<Task> taskList = taskService.getAllTasks(status);
        if (taskList == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        return ResponseEntity.ok().body(taskList);

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
        Task createdTask = taskService.createTask(newTask);
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build()
                .toUri();
        return ResponseEntity.ok()
                .location(location)
                .body(createdTask);
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
    public ResponseEntity<Task> taskForId(
            @PathVariable("id")
            @Valid
            @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}") final String id) {
        Task findTask = taskService.getTask(id);
        if (findTask == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(findTask);

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
    public ResponseEntity<Void> changeStatus(
            @PathVariable("id")
            @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")
            @Valid final String id,
            @RequestBody @Valid final UpdateTaskRequest task) {
        String status = task.getStatus();
        if (status.equals("inbox") || status.equals("done")) {
            Task findTask = taskService.updateTask(id, task);
            if (findTask == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .build();
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
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id")
            @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")
            @Valid final String id) {
        Task deleteTask = taskService.deleteTask(id);
        if (deleteTask == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .build();
    }
}
