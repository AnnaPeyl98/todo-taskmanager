package it.sevenbits.spring_boot.task_manager.web.service;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    ResponseEntity<List<Task>> getAllTasks(String filter);
    ResponseEntity<Task> createTask(AddTaskRequest newTask);
    ResponseEntity<Task> getTask(String id);
    ResponseEntity<String> updateTask(String id, UpdateTaskRequest updateTask);
    ResponseEntity<String> deleteTask(String id);
}
