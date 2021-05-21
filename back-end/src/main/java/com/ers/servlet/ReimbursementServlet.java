package com.ers.servlet;

import com.ers.controller.ReimbursementController;
import com.ers.dao.MongoDao;
import com.ers.service.ReimbursementService;
import org.apache.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that is in charge of the HTTP request for the /reimbursements/ URL
 */
public class ReimbursementServlet extends HttpServlet {

    private ReimbursementController reimbursementController;
    private Logger logger;

    /**
     * Constructor for the class that initializes the logger and controller
     */
    public ReimbursementServlet() {
        reimbursementController = new ReimbursementController(new ReimbursementService(new MongoDao()));
        logger = Logger.getLogger(this.getClass().getName());
        logger.info("ReimbursementServlet has been initialized.");

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
        super.service(req, resp);
        logger.info("Has been configured to handle http requests.");
    }

    /**
     * Method that is in charge of the GET request
     * @param req The incoming request
     * @param resp The response to be send
     * @throws ServletException Servlet error
     * @throws IOException JSON parsing error
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.reimbursementController.getReimbursements(req,resp);
        logger.info("received a GET request");
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
        this.reimbursementController.postReimbursements(req,resp);
        logger.info("received a POST request");
    }

    /**
     * Method that is in charge of the PUT request
     * @param req The incoming request
     * @param resp The response to be send
     * @throws ServletException Servlet error
     * @throws IOException JSON parsing error
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.reimbursementController.putReimbursements(req,resp);
        logger.info("received a PUT request");
    }
    /**
     * Method that is in charge of the DELETE request
     * @param req The incoming request
     * @param resp The response to be send
     * @throws ServletException Servlet error
     * @throws IOException JSON parsing error
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.reimbursementController.deleteReimbursements(req,resp);
        logger.info("Received a delete request");
    }
}
