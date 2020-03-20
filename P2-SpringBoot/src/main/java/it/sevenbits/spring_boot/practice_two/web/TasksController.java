package it.sevenbits.spring_boot.practice_two.web;

import it.sevenbits.spring_boot.practice_two.core.model.Task;
import it.sevenbits.spring_boot.practice_two.core.repository.TasksRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
     * @param itemsRepository repository
     */
    public TasksController(final TasksRepository itemsRepository) {
        this.tasksRepository = itemsRepository;
    }

    /**
     * Method for getting all tasks in json
     * @return all tasks
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> list() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(tasksRepository.getAllTasks());
    }

    /**
     * Method for adding task in repository. If field text is null or body request is empty, status will be 400 else 200
     * @param task task which will be add
     * @return added task in json
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody Task task) {
        if (task == null || task.getText() == null || "".equals(task.getText())) {
            return ResponseEntity.badRequest().build();
        }
        Task createdItem = tasksRepository.create(task);
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdItem.getId()))
                .build().toUri();
        return ResponseEntity.created(location).contentType(MediaType.APPLICATION_JSON_UTF8).body(createdItem);

    }
}
