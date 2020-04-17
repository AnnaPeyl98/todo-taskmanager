package it.sevenbits.spring_boot.task_manager.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.GetTasksMeta;

import java.util.List;
import java.util.Objects;

/**
 * Model for response with all tasks and meta
 */
public class GetAllTasksResponse {
    @JsonProperty("_meta")
    private GetTasksMeta meta;
    @JsonProperty("tasks")
    private List<Task> tasks;

    /**
     * Constructor
     *
     * @param meta  - meta for tasks
     * @param tasks - all finding tasks
     */
    public GetAllTasksResponse(final GetTasksMeta meta, final List<Task> tasks) {
        this.meta = meta;
        this.tasks = tasks;
    }

    /**
     * Get meta
     *
     * @return meta
     */
    @JsonProperty("_meta")
    public GetTasksMeta getMeta() {
        return meta;
    }

    /**
     * Set meta
     *
     * @param meta new meta
     */
    public void setMeta(final GetTasksMeta meta) {
        this.meta = meta;
    }

    /**
     * Return all tasks
     *
     * @return all tasks
     */
    @JsonProperty("tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Setter for new list with tasks
     *
     * @param tasks new lists with tasks
     */
    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GetAllTasksResponse that = (GetAllTasksResponse) o;
        return Objects.equals(meta, that.meta) &&
                Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meta, tasks);
    }
}
