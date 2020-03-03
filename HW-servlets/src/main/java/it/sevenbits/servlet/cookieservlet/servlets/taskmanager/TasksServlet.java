package it.sevenbits.servlet.cookieservlet.servlets.taskmanager;

import com.google.gson.Gson;
import it.sevenbits.servlet.cookieservlet.repository.identification.SessionMap;
import it.sevenbits.servlet.cookieservlet.repository.taskmanager.Task;
import it.sevenbits.servlet.cookieservlet.repository.taskmanager.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Server for get all tasks and set task
 */
public class TasksServlet extends HttpServlet {
    private TasksRepository tasksRepository;
    private SessionMap sessionMap;
    private int count = 0;

    /**
     * Initialization server
     */
    @Override
    public void init() {
        tasksRepository = TasksRepository.getInstance();
        sessionMap = SessionMap.getInstance();
    }

    /**
     * Get all tasks in repository
     *
     * @param request  request object
     * @param response response object
     * @throws ServletException if was some trouble
     * @throws IOException      if was trouble with writer
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getHeader("Authorization");
        if (id == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized in service");
            return;
        }
        if (sessionMap.getUser(id) == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(tasksRepository.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Add task in repository
     *
     * @param request  request object
     * @param response response object
     * @throws ServletException if was some trouble
     * @throws IOException      if was trouble with writer
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        String id = request.getHeader("Authorization");
        if (id == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized in service");
            return;
        }
        if (sessionMap.getUser(id) == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            return;
        }
        /*request.getParameter("application/json");
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Task task = gson.fromJson(reader, Task.class);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        tasksRepository.addTask(task.getTask());
        response.getWriter().write("Created task");
        response.sendError(HttpServletResponse.SC_OK);*/
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
