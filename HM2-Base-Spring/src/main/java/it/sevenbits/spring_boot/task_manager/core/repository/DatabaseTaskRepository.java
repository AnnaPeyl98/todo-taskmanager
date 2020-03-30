package it.sevenbits.spring_boot.task_manager.core.repository;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.Date;
import java.util.List;

/**
 * Repository for working with database
 */
public class DatabaseTaskRepository implements TasksRepository {
    private JdbcOperations jdbcOperations;

    /**
     * Create repository
     * @param jdbcOperations object for working with database
     */
    public DatabaseTaskRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /**
     * Method for getting all tasks in repository
     *
     * @return all tasks
     */
    @Override
    public List<Task> getAllTasks(final String filter) {
        return jdbcOperations.query(
                "SELECT id, text, status, createAt FROM task",
                (resultSet, i) -> {
                    String id = resultSet.getString(1);
                    String text = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    Date createAt = resultSet.getDate(4);
                    return new Task(id, text, status, createAt);
                });
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
        String id = newTask.getId();
        String text = newTask.getText();
        String status = newTask.getStatus();
        Date createAt = newTask.getCreateAt();
        jdbcOperations.update(
                "INSERT INTO task (id, text, status, createAt) VALUES (?, ?, ?, ?)",
                id, text, status, createAt
        );
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
        return null;
    }

    /**
     * Delete task for this id
     *
     * @param id - id for task
     * @return deleted task or null, if repository don`t have this id
     */
    @Override
    public Task deleteTask(final String id) {
        return null;
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
        return null;
    }
}
