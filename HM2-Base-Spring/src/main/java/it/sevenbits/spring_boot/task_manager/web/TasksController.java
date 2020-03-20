package it.sevenbits.spring_boot.task_manager.web;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.TasksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Class for working with URL and repository
 */
@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final TasksRepository tasksRepository;

    /**
     * Constructor for creating repository
     *
     * @param tasksRepository repository
     */
    public TasksController(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * Method for getting all tasks in json
     *
     * @return all tasks
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> listTasks() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tasksRepository.getAllTasks());
    }

    /**
     * Method for adding task in repository. If field text is null or body request is empty, status will be 400 else 200
     *
     * @param newTask task which will be add
     * @return added task in json
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody Task newTask) {
        if (newTask == null || newTask.getText() == null || "".equals(newTask.getText())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Task createdTask = tasksRepository.create(newTask);
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build()
                .toUri();
        return ResponseEntity.ok()
                .location(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(createdTask);
    }

    /**
     * Method for get task from repository
     *
     * @param id - id task, which we search
     * @return find task or status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> taskForId(@PathVariable("id") String id) {

        if (id == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Task findTask = tasksRepository.getTask(id);
        if (findTask == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(findTask);

    }

    /**
     * Method changed status task
     *
     * @param id         - id task, which will be change
     * @param taskStatus new status for task
     * @return status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> changeStatus(@PathVariable("id") String id, @RequestBody Task taskStatus) {
        String status = taskStatus.getStatus();
        if (id == null || taskStatus == null || status == null || "".equals(status)) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        if (status.equals("inbox") || status.equals("done")) {
            Task findTask = tasksRepository.getTask(id);
            if (findTask == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            findTask.setStatus(status);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Task change");
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable("id") String id) {
        if (id == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Task deleteTask = tasksRepository.deleteTask(id);
        if (deleteTask == null || !deleteTask.getId().equals(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body("Task deleted successful");
    }
}
