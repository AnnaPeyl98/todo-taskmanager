package it.sevenbits.courses.workshop.servlet.servlets;

import com.google.gson.Gson;
import it.sevenbits.courses.workshop.servlet.repository.Task;
import it.sevenbits.courses.workshop.servlet.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class TasksServlet extends HttpServlet {
    private int count=0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TasksRepository tasksRepository = TasksRepository.getInstance();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(tasksRepository.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException,IOException{
        request.getParameter("application/json");
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Task task = gson.fromJson(reader, Task.class);
        task.setId(count);
        count++;
        TasksRepository tasksRepository = TasksRepository.getInstance();
        tasksRepository.addTask(task);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(task));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
