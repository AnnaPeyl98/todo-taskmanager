package it.sevenbits.servlet.cookieservlet.repository.taskmanager;

import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for saving tasks
 */
public final class TasksRepository {
    private static TasksRepository instance;
    private ConcurrentHashMap<Integer, Task> tasks;
    private int count;

    /**
     * Constructor
     */
    private TasksRepository() {
        tasks = new ConcurrentHashMap<>();
        count = 0;
    }

    /**
     * Added task in repository
     *
     * @param task task, which will be added in repository
     */
    public void addTask(final Task task) {

        tasks.put(task.getId(), task);
    }

    /**
     * Get repository or created it
     *
     * @return repository for tasks
     */
    public static TasksRepository getInstance() {
        if (instance == null) {
            instance = new TasksRepository();
        }
        return instance;
    }

    /**
     * Get task for id
     *
     * @param id id for task
     * @return task
     */
    public Task getTask(final Integer id) {
        return tasks.getOrDefault(id, null);
    }

    /**
     * Convert repository to string array json
     *
     * @return string array json
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            sb.append(new Gson().toJson(entry.getValue()));
            sb.append(",\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
