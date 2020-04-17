package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.web.model.request.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.GetAllTasksResponse;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.service.tasks.TaskService;
import it.sevenbits.spring_boot.task_manager.web.service.whoami.WhoAmIService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class for working with URL and repository
 */
@Controller
@Validated
@RequestMapping("/tasks")
public class TasksController {
    private final WhoAmIService whoAmIService;
    private TaskService taskService;
    private Pattern patternId;

    /**
     * Constructor for creating repository
     *
     * @param taskService   service for working with repository
     * @param whoAmIService service for identification user
     */
    public TasksController(final TaskService taskService, final WhoAmIService whoAmIService) {

        this.taskService = taskService;
        this.whoAmIService = whoAmIService;
        patternId = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
    }

    /**
     * Method for getting all tasks in json
     *
     * @param token  users token
     * @param status status for filter
     * @param order  parameter for sort task
     * @param page   number page
     * @param size   count tasks on page
     * @return all tasks
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<GetAllTasksResponse> listTasks(
            @RequestHeader(value = "Authorization") final String token,
            @RequestParam(name = "status",
                    defaultValue = "${configuration.meta.status}") final String status,
            @RequestParam(name = "order",
                    defaultValue = "${configuration.meta.order}") final String order,
            @RequestParam(name = "page",
                    defaultValue = "${configuration.meta.start-page}") final int page,
            @RequestParam(name = "size",
                    defaultValue = "${configuration.meta.page-size}") final int size

    ) {
        User user = whoAmIService.getCurrentUserInfo(token);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }
        GetAllTasksResponse taskList = taskService.getAllTasks(user.getId(), status, order, page, size);

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
     * @param token   users token
     * @param newTask task which will be add
     * @return added task in json
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestHeader(value = "Authorization") final String token,
                                       @RequestBody @Valid final AddTaskRequest newTask) {
        User user = whoAmIService.getCurrentUserInfo(token);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }
        Task createdTask = taskService.createTask(user.getId(), newTask);
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
     * @param token users token
     * @param id    - id task, which we search
     * @return find task or status
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Task> taskForId(
            @RequestHeader(value = "Authorization") final String token,
            @PathVariable("id") final String id) {
        User user = whoAmIService.getCurrentUserInfo(token);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }
        Matcher matcher = patternId.matcher(id);
        if (matcher.matches()) {
            Task findTask = taskService.getTask(user.getId(), id);
            if (findTask == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            return ResponseEntity
                    .ok()
                    .body(findTask);
        }
        return ResponseEntity
                .badRequest()
                .build();
    }

    /**
     * Method changed status task
     *
     * @param token users token
     * @param id    - id task, which will be change
     * @param task  new status for task
     * @return status
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> changeStatus(
            @RequestHeader(value = "Authorization") final String token,
            @PathVariable("id") final String id,
            @RequestBody @Valid final UpdateTaskRequest task) {
        User user = whoAmIService.getCurrentUserInfo(token);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }
        Matcher matcher = patternId.matcher(id);
        if (matcher.matches()) {
            Task findTask;
            String status = task.getStatus();
            if ((("inbox".equals(status) || "done".equals(status)) && task.getText() == null)
                    || (status == null && task.getText() != null && !"".equals(task.getText()))
                    || (("inbox".equals(status) || "done".equals(status)) && task.getText() != null && !"".equals(task.getText()))
            ) {
                findTask = taskService.updateTask(user.getId(), id, task);
                if (findTask == null) {
                    return ResponseEntity
                            .notFound()
                            .build();
                }
                return ResponseEntity
                        .ok()
                        .build();
            }
        }
        return ResponseEntity
                .badRequest()
                .build();
    }

    /**
     * Method for delete task
     *
     * @param token users token
     * @param id    - id task, which will be delete
     * @return status
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseEntity<Void> deleteTask(
            @RequestHeader(value = "Authorization") final String token,
            @PathVariable("id") final String id) {
        User user = whoAmIService.getCurrentUserInfo(token);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }
        Matcher matcher = patternId.matcher(id);
        if (matcher.matches()) {
            Task deleteTask = taskService.deleteTask(user.getId(), id);
            if (deleteTask == null) {
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
}
