package it.sevenbits.servlet.cookieservlet.repository.taskmanager;

/**
 * Class for sreated task
 */
public class Task {
    private String task;
    private int id;

    /**
     * Constructor for created task
     * @param task value task
     * @param id id task
     */
    public Task(final String task, final int id) {
        this.task = task;
        this.id = id;
    }

    /**
     * Get value task
     * @return string in task
     */
    public String getTask() {
        return task;
    }

    /**
     * Set value in task
     * @param task value for task
     */
    public void setTask(final String task) {
        this.task = task;
    }

    /**
     * return id task
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * set id task
     * @param id is task
     */
    public void setId(final int id) {
        this.id = id;
    }
}
