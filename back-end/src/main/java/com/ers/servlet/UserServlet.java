package com.ers.servlet;

import com.ers.controller.UserController;
import com.ers.dao.MongoDao;
import com.ers.model.User;
import com.ers.service.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet class that receives http requests with the /users/ URL
 */
public class UserServlet extends HttpServlet {

    private UserController userController;
    private Logger logger;

    /**
     * Constructor of the User Servlet class. Sets ub the controller and logger.
     */
    public UserServlet() {
        this.userController = new UserController(new UserService(new MongoDao()));
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(UserServlet.class.getClassLoader().getResource(log4jConfPath));
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.FATAL);
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Initializes the general method for all type of requests
     * @param req The incoming request
     * @param resp The response to be send.
     * @throws ServletException Failed to initialize servlet
     * @throws IOException  Error parsing and convert object to JSON
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
        logger.info("User servlet has been initialized.");
        super.service(req, resp);
    }

    /**
     * Method that is in charge of the GET request
     * @param req The incoming request
     * @param resp The response to be send
     * @throws ServletException Servlet error
     * @throws IOException JSON parsing error
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.userController.getUsers(req,resp);
        logger.info("User servlet received a GET request.");
    }

    /**
     * Method that is in charge of the POST request
     * @param req The incoming request
     * @param resp The response to be send
     * @throws ServletException Servlet error
     * @throws IOException JSON parsing error
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.userController.postUsers(req,resp);
        logger.info("User servlet received a POST request.");
    }

    /**
     * Method that is in charge of the PUT request
     * @param req The incoming request
     * @param resp The response to be send
     * @throws ServletException Servlet error
     * @throws IOException JSON parsing error
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.userController.putUsers(req,resp);
        logger.info("User servlet received a PUT request.");
    }

    /**
     * Method that is in charge of the DELETE request
     * @param req The incoming request
     * @param resp The response to be send
     * @throws ServletException Servlet error
     * @throws IOException JSON parsing error
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.userController.deleteUsers(req,resp);
        logger.info("User servlet received a DELETE request.");
    }
}
