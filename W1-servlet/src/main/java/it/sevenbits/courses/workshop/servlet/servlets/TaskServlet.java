package it.sevenbits.courses.workshop.servlet.servlets;

import com.google.gson.Gson;
import it.sevenbits.courses.workshop.servlet.repository.Task;
import it.sevenbits.courses.workshop.servlet.repository.TasksRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException{
        String id = request.getParameter("id");
        TasksRepository tasksRepository = TasksRepository.getInstance();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson json = new Gson();
        Task findTask = tasksRepository.getTask(Integer.parseInt(id));
        if(findTask==null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Task not found");
            return;
        }
        response.getWriter().write(json.toJson(findTask));
        response.setStatus(HttpServletResponse.SC_OK);
    }
 }
