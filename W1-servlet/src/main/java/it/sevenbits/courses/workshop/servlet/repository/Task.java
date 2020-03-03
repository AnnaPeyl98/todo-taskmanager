package it.sevenbits.courses.workshop.servlet.repository;

public class Task {
    private String task;
    private int id;

    public Task(final String task, final int id) {
        this.task = task;
        this.id = id;
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task=task;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
