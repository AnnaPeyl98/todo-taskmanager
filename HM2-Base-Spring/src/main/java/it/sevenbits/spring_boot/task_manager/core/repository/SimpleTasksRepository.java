package it.sevenbits.spring_boot.task_manager.core.repository;

import it.sevenbits.spring_boot.task_manager.core.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Realisation TasksRepository, saved all added tasks
 */
public class SimpleTasksRepository implements TasksRepository {
    private Map<String, Task> tasks = new HashMap<>();
    /**
     * Method for getting all tasks in repository
     * @return all tasks
     */
    @Override
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
    }

    /**
     * Method for added task in repository
     * @param task - it will add
     * @return added task
     */
    @Override
    public Task create(final Task task) {
        Task newTask = new Task(task.getText());
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    /**
     * Getter task for this id
     * @param id - id for task
     * @return task or null, if repository don`t have this id
     */
    @Override
    public Task getTask(final String id) {
        return tasks.get(id);
    }

    /**
     * Delete task for this id
     *
     * @param id - id for task
     * @return deleted task or null, if repository don`t have this id
     */
    @Override
    public Task deleteTask(final String id) {
        return tasks.remove(id);
    }
}
