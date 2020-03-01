package it.sevenbits.servlet.cookieservlet.servlets.taskmanager;

import com.google.gson.Gson;
import it.sevenbits.servlet.cookieservlet.repository.identification.SessionMap;
import it.sevenbits.servlet.cookieservlet.repository.taskmanager.TasksRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Server for work with one task
 */
public class TaskServlet extends HttpServlet {
    private TasksRepository tasksRepository;
    private SessionMap sessionMap;

    /**
     * Initialization server
     */
    @Override
    public void init() {
        tasksRepository = TasksRepository.getInstance();
        sessionMap = SessionMap.getInstance();
    }

    /**
     * Return task of her id
     *
     * @param request  request object
     * @param response response object
     * @throws SecurityException if was some trouble
     * @throws IOException       if was trouble with writer
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws SecurityException, IOException {
        String sessionId = request.getHeader("Authorization");
        if (sessionId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized in service");
            return;
        }
        if (sessionMap.getUser(sessionId) == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            return;
        }
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No id");
            return;
        }
        TasksRepository tasksRepository = TasksRepository.getInstance();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson json = new Gson();
        String findTask = json.toJson(tasksRepository.getTask(Integer.parseInt(id)));
        if (findTask == null || "".equals(findTask)) {
            response.sendError(HttpServletResponse.SC_FOUND, "Not found task");
        }
        response.getWriter().write(findTask);
        response.sendError(HttpServletResponse.SC_OK);
    }
}
