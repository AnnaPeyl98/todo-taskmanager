package it.sevenbits.courses.workshop.servlet.repository;

import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TasksRepository {
    private static TasksRepository instance;
    private ConcurrentHashMap<Integer,Task> tasks;

    private TasksRepository(){
        tasks = new ConcurrentHashMap<>();
    }
    public void addTask(Task task) {

        tasks.put(task.getId(),task);
    }
    public static TasksRepository getInstance(){
        if(instance==null){
            instance = new TasksRepository();
        }
        return instance;
    }
    public Task getTask(Integer id){
        return tasks.getOrDefault(id,null);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Map.Entry<Integer, Task> entry : tasks.entrySet()){
            sb.append(new Gson().toJson(entry.getValue()));
            sb.append(",\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
