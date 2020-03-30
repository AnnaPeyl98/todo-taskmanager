package it.sevenbits.spring_boot.task_manager.web.service;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.SimpleTasksRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.TasksRepository;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class MapTaskService implements TaskService {
    private final TasksRepository tasksRepository;

    public MapTaskService() {
        this.tasksRepository = new SimpleTasksRepository();
    }
    @Override
    public ResponseEntity<List<Task>> getAllTasks(String filter) {
        return ResponseEntity
                .ok()
                .body(tasksRepository.getAllTasks(filter));
    }

    @Override
    public ResponseEntity<Task> createTask(final AddTaskRequest newTask) {
        Task createdTask = tasksRepository.create(newTask);
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build()
                .toUri();
        return ResponseEntity.ok()
                .location(location)
                .body(createdTask);
    }

    @Override
    public ResponseEntity<Task> getTask(final String id) {
        Task findTask = tasksRepository.getTask(id);
        if (findTask == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(findTask);
    }

    @Override
    public ResponseEntity<String> updateTask(final String id, final UpdateTaskRequest updateTask) {
        String status = updateTask.getStatus();
        if (status.equals("inbox") || status.equals("done")) {
            Task findTask = tasksRepository.updateTask(id,updateTask);
            if (findTask == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            return ResponseEntity
                    .ok("{}");
        }
        return ResponseEntity
                .badRequest()
                .build();
    }

    @Override
    public ResponseEntity<String> deleteTask(final String id) {
        Task deleteTask = tasksRepository.deleteTask(id);
        if (deleteTask == null || !deleteTask.getId().equals(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok("{}");
    }
}
