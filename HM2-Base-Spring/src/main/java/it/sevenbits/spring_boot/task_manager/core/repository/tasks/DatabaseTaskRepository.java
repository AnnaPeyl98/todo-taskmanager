package it.sevenbits.spring_boot.task_manager.core.repository.tasks;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Repository for working with database
 */
public class DatabaseTaskRepository implements TasksRepository {
    private JdbcOperations jdbcOperations;
    private static final int INDEX_ID = 1;
    private static final int INDEX_TEXT = 2;
    private static final int INDEX_STATUS = 3;
    private static final int INDEX_CREATEDAT = 4;
    private static final int INDEX_UPDATEDAT = 5;

    /**
     * Create repository
     *
     * @param jdbcOperations object for working with database
     */
    public DatabaseTaskRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /**
     * Method for getting all tasks in repository
     *
     * @param filter filter for status task
     * @param order  parameter for sort task
     * @param page   number page
     * @param size   count tasks on page
     * @return all tasks
     */
    @Override
    public List<Task> getAllTasks(final String filter, final String order, final int page, final int size) {
        String ascQuery =
                "SELECT id,text,status,createdAt,updatedAt FROM task WHERE status=? ORDER BY createdAt ASC OFFSET ? LIMIT ?";
        String descQuery =
                "SELECT id,text,status,createdAt,updatedAt FROM task WHERE status=? ORDER BY createdAt DESC OFFSET ? LIMIT ?";
        String query = "asc".equalsIgnoreCase(order) ? ascQuery : descQuery;
        int start = (page - 1) * size;
        return jdbcOperations.query(
                query,
                (resultSet, i) -> {
                    String id = resultSet.getString(INDEX_ID);
                    String text = resultSet.getString(INDEX_TEXT);
                    String status = resultSet.getString(INDEX_STATUS);
                    Timestamp createdAt = new Timestamp(resultSet.getDate(INDEX_CREATEDAT).getTime());
                    Timestamp updatedAt = new Timestamp(resultSet.getDate(INDEX_UPDATEDAT).getTime());
                    return new Task(id, text, status, createdAt, updatedAt);
                },
                filter,
                start,
                size);
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
        Timestamp createdAt = newTask.getCreatedAt();
        Timestamp updatedAt = newTask.getUpdatedAt();
        jdbcOperations.update(
                "INSERT INTO task (id, text, status, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)",
                id, text, status, createdAt, updatedAt
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
        try {

            return jdbcOperations.queryForObject(
                    "SELECT id, text, status, createdAt, updatedAt FROM task WHERE id = ?",
                    (resultSet, i) -> {
                        String rowId = resultSet.getString(INDEX_ID);
                        String rowText = resultSet.getString(INDEX_TEXT);
                        String rowStatus = resultSet.getString(INDEX_STATUS);
                        Timestamp rowCreatedAt = new Timestamp(resultSet.getDate(INDEX_CREATEDAT).getTime());
                        Timestamp rowUpdatedAt = new Timestamp(resultSet.getDate(INDEX_UPDATEDAT).getTime());
                        return new Task(rowId, rowText, rowStatus, rowCreatedAt, rowUpdatedAt);
                    },
                    id);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    /**
     * Delete task for this id
     *
     * @param id - id for task
     * @return deleted task or null, if repository don`t have this id
     */
    @Override
    public Task deleteTask(final String id) {
        Task deleteTask = getTask(id);
        if (deleteTask == null) {
            return null;
        }
        jdbcOperations.update(
                "DELETE FROM task WHERE id = ?", id);
        return deleteTask;
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
        Task newTask = getTask(id);
        if (newTask == null) {
            return null;
        }
        newTask.setUpdateAt();
        if (updateTask.getText() != null && !"".equals(updateTask.getText())) {
            newTask.setText(updateTask.getText());
        }
        if (updateTask.getStatus() != null) {
            newTask.setStatus(updateTask.getStatus());
        }
        String text = newTask.getText();
        String status = newTask.getStatus();
        Timestamp updatedAt = newTask.getUpdatedAt();
        jdbcOperations.update(
                "UPDATE task SET text = ?, status = ?, updatedAt = ? WHERE id = ?",
                text, status, updatedAt, id
        );
        return newTask;
    }

    /**
     * Return count tasks in repository
     * @param filter status tasks
     * @return count tasks
     */
    @Override
    public int getCountAllTasks(final String filter) {
        Integer count = jdbcOperations.queryForObject(
                "SELECT COUNT(*) FROM task WHERE status=?",
                (resultSet, i) -> resultSet.getInt(1),
                filter
        );
        return Optional
                .ofNullable(count)
                .orElse(0);
    }
}
