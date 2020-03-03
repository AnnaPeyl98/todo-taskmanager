package it.sevenbits.servlet.cookieservlet.servlets.identification;

import it.sevenbits.servlet.cookieservlet.repository.identification.SessionMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * Server for identification
 */
public class CookieServlet extends HttpServlet {
    private SessionMap sessionMap;

    /**
     * Initialization server
     */
    @Override
    public void init() {
        sessionMap = SessionMap.getInstance();
    }

    /**
     * Method added name in session map and save key in cookie
     * @param request request object
     * @param response response object
     * @throws SecurityException if was some trouble
     * @throws IOException if was trouble with writing
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws SecurityException, IOException {
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        if (name == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter name not found");
            return;
        }
        String uuid = UUID.randomUUID().toString();
        sessionMap.addUser(uuid, name);
        Cookie cookie = new Cookie("sessionId", uuid);
        response.addCookie(cookie);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    /**
     * Method prints the page for the name that id matches
     * @param request request object
     * @param response response object
     * @throws SecurityException if was some trouble
     * @throws IOException if was trouble with writing
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws SecurityException, IOException {

        response.setCharacterEncoding("UTF-8");

        if (request.getCookies() == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Optional<Cookie> opt = Arrays.stream(request.getCookies())
                .filter(monster -> "sessionId".equals(monster.getName()))
                .findFirst();
        if (!opt.isPresent()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Cookie requestCookie = opt.get();

        String userName = sessionMap.getUser(requestCookie.getValue());
        if (userName == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        response.getWriter().write(
                String.format("<!DOCTYPE html><html><body><p>Current user is %s</p></body></html>", userName));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
