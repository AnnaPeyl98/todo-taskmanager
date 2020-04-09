package it.sevenbits.spring_boot.task_manager.core.repository;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Realisation TasksRepository, saved all added tasks
 */
public class SimpleTasksRepository implements TasksRepository {
    private Map<String, Task> tasks = new HashMap<>();

    /**
     * Method for getting all tasks in repository
     *
     * @return all tasks
     */
    @Override
    public List<Task> getAllTasks(final String filter, final String order, final int page, final int size) {
        return new ArrayList<>(tasks.values()).stream().filter(i -> i.getStatus().equals(filter)).collect(Collectors.toList());
    }

    /**
     * Method for added task in repository
     *
     * @param task - it will add
     * @return added task
     */
    @Override
    public Task create(final AddTaskRequest task) {
        Task newTask = new Task(task.getText());
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    /**
     * Getter task for this id
     *
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

    /**
     * Update task in repository
     *
     * @param id         id task, which will be update
     * @param updateTask new informations
     * @return updated task
     */
    @Override
    public Task updateTask(final String id, final UpdateTaskRequest updateTask) {
        Task findTask = getTask(id);
        if (findTask == null) {
            return null;
        }
        findTask.setStatus(updateTask.getStatus());
        findTask.setText(updateTask.getText());
        return findTask;
    }
    /**
     * Return count tasks in repository
     * @param filter status tasks
     * @return count tasks
     */
    @Override
    public int getCountAllTasks(final String filter) {
        return tasks.size();
    }
}
