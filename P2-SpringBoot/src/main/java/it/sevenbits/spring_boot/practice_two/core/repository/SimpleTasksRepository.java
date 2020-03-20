package it.sevenbits.spring_boot.practice_two.core.repository;

import it.sevenbits.spring_boot.practice_two.core.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Realisation TasksRepository, saved all added tasks
 */
public class SimpleTasksRepository implements TasksRepository {
    private List<Task> tasks = new ArrayList<>();

    /**
     * Method for getting all tasks in repository
     * @return all tasks
     */
    @Override
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Method for added task in repository
     * @param task - it will add
     * @return added task
     */
    @Override
    public Task create(final Task task) {
        Task newTask = new Task(task.getText());
        tasks.add(newTask);
        return newTask;
    }
}